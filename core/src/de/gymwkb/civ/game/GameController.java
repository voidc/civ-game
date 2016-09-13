package de.gymwkb.civ.game;

import com.badlogic.gdx.utils.Array;
import de.gymwkb.civ.game.ai.DFSPathfinder;
import de.gymwkb.civ.game.ai.Pathfinder;
import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.map.HexMap.LayerType;
import de.gymwkb.civ.map.HexagonGenerator;
import de.gymwkb.civ.registry.UnitType;

/**
 * Manages the common state of all PlayerControllers. (server role)
 * Has no information about the PlayerControllers (eg. UI state).
 * Validates all incoming inputs and notifies GameListeners about the effects.
 */
public class GameController {
    private Array<GameListener> listeners;
    private Pathfinder pathfinder;
    private HexMap map;
    private Player[] players;
    private int currentPlayer;
    private int turn;
    private Array<Hex> pathBuffer;
    
    public static final int PLAYER_COUNT = 2;
    
    public GameController() {
        HexagonGenerator gen = new HexagonGenerator();
        this.map = gen.generateMap(HexagonGenerator.SIZE, false);
        
        players = new Player[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++) {
            players[i] = new Player(i);
        }
        
        pathfinder = new DFSPathfinder(map);
        listeners = new Array<>();

        pathBuffer = new Array<>();
        turn = 0;
    }
    
    public void registerListener(GameListener listener) {
        listeners.add(listener);
    }
    
    public void finishTurn(int playerId) {
        if (currentPlayer != playerId) {
            return;
        }
        
        currentPlayer = (currentPlayer + 1) % players.length;
        
        if (currentPlayer == 0) {
            turn++;
        }

        players[currentPlayer].getUnits().forEach(Unit::resetMoves);

        System.out.println("Turn of player " + currentPlayer);
        for (int i = 0; i < listeners.size; i++) {
            listeners.get(i).onTurn(currentPlayer);
        }
    }

    public void move(int playerId, Hex unitHex, Hex targetHex) throws IllegalMoveException {
        if (currentPlayer != playerId) {
            throw new IllegalMoveException("Wrong turn!");
        }
        
        Unit unit = map.getUnit(unitHex);
        if(unit == null || unit.getOwnerId() != playerId) {
            throw new IllegalMoveException("No unit!");
        }
        
        Unit target = map.getUnit(targetHex);
        if(target != null) {
            throw new IllegalMoveException("Hex occupied!");
        }

        boolean pathExists = pathfinder.findPath(pathBuffer, unitHex, targetHex, unit.getRemainingMoves());
        if (!pathExists) {
            throw new IllegalMoveException("Hex out of reach!");
        }

        unit.addMoves(pathBuffer.size - 1);
        map.moveUnit(unitHex, targetHex);
        listeners.forEach(listener -> listener.onMove(unitHex, targetHex));
    }

    public void attack(int playerId, Hex unitHex, Hex targetHex) throws IllegalMoveException {
        if (currentPlayer != playerId) {
            throw new IllegalMoveException("Wrong turn!");
        }
        
        Unit unit = map.getUnit(unitHex);
        if (unit == null || unit.getOwnerId() != playerId) {
            throw new IllegalMoveException("No unit!");
        }

        if (unit.type.strength == 0) {
            throw new IllegalMoveException("Unit cannot attack!");
        }
        
        Unit target = map.getUnit(targetHex);
        if (target == null || target.getOwnerId() == playerId) {
            throw new IllegalMoveException("No valid target!");
        }

        if (unitHex.mhDist(targetHex) > unit.type.attackRange) {
            throw new IllegalMoveException("Target out of reach!");
        }
        
        float attackDamage = ((unit.type.strength * unit.type.strength) / target.type.defence) * unit.getLevel() * unit.getHealthPercentage();
        dealDamage(targetHex, target, attackDamage);
        listeners.forEach(listener -> listener.onAttack(unitHex, targetHex, attackDamage));
    }
    
    private void dealDamage(Hex hex, Unit unit, float damage) {
        unit.modHealth(-damage);
        if(unit.getHealth() == 0) {
            map.getCell(hex).setLayer(LayerType.UNIT, null);
            //players[unit.getOwnerId()].removeUnit(unit);
            listeners.forEach(listener -> listener.onDeath(hex));
        }
    }

    public void spawnUnit(int playerId, Hex position, UnitType type) {
        if (playerId < players.length /*playerId == currentPlayer*/) {
            Unit u = new Unit(playerId, type);
            players[playerId].addUnit(u);
            map.getCell(position).setLayer(LayerType.UNIT, u);
        }
    }

    public HexMap getMap() {
        return map;
    }
    
    public Player getPlayer(int id) {
        return players[id];
    }
    
    public Pathfinder getPathfinder() {
        return pathfinder;
    }
    
    /**
     * Implemented by classes which want to be notified if certain events occur.
     * If the game is played over a network, these listeners can be used to synchronize server and client state.
     */
    public interface GameListener {
        void onTurn(int playerId);
        void onMove(Hex unit, Hex target);
        void onAttack(Hex attacker, Hex target, float damage);
        void onDeath(Hex deadUnit);
    }
}
