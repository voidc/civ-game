package de.gymwkb.civ.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.map.HexagonGenerator;
import de.gymwkb.civ.map.HexMap.LayerType;
import de.gymwkb.civ.registry.UnitType;

/**
 * Manages the common state of all PlayerControllers. (server role)
 * Has no information about the PlayerControllers (eg. UI state).
 * Validates all incoming inputs and notifies GameListeners about the effects.
 */
public class GameController {
    private Array<GameListener> listeners;
    private HexMap map;
    private Player[] players;
    private int currentPlayer;
    private int turn;
    
    public static final int PLAYER_COUNT = 2;
    
    public GameController() {
        HexagonGenerator gen = new HexagonGenerator();
        this.map = gen.generateMap(HexagonGenerator.SIZE, false);
        players = new Player[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++) {
            players[i] = new Player(i);
        }
        
        turn = 0;
    }
    
    public void move(int playerId, Hex unitHex, Hex targetHex) {
        if (currentPlayer != playerId) {
            System.out.println("Wrong turn!");
            return;
        }
        
        Unit unit = map.getUnit(unitHex);
        if(unit == null || unit.getOwner().id != playerId) {
            System.out.println("No unit!");
            return;
        }
        
        Unit target = map.getUnit(targetHex);
        if(target != null) {
            System.out.println("Hex occupied!");
            return;
        }
        
        //check if target is in movement range
        
        map.getCell(unitHex).setLayer(LayerType.UNIT, null);
        map.getCell(targetHex).setLayer(LayerType.UNIT, unit);
    }
    
    public void attack(int playerId, Hex unitHex, Hex targetHex) {
        if (currentPlayer != playerId)
            return;
        
        Unit unit = map.getUnit(unitHex);
        if(unit == null || unit.getOwner().id != playerId || unit.type.strength == 0)
            return;
        
        Unit target = map.getUnit(targetHex);
        if(target == null || target.getOwner().id == playerId)
            return;
        
        //check if target is in attack range
        
        float attackDamage = (unit.type.strength / target.type.defence) * unit.ep * (unit.health / unit.type.maxHealth);
        target.health = Math.max(0, target.health - attackDamage);
    }
    
    public void finishTurn(int playerId) {
        if (currentPlayer != playerId) {
            return;
        }
        
        currentPlayer = (currentPlayer + 1) % players.length;
        
        if (currentPlayer == 0) {
            turn++;
        }
        
        listeners.forEach(listener -> listener.onTurn(currentPlayer));
    }
    
    public HexMap getMap() {
        return map;
    }
    
    public Player getPlayer(int id) {
        return players[id];
    }
    
    public void spawnUnit(int playerId, Hex position, UnitType type) {
        if(playerId == currentPlayer) {
            Unit u = new Unit(new Player(MathUtils.random(5)), type);
            //Unit u = new Unit(players[currentPlayer], type);
            //players[currenvtPlayer].addUnit(u);
            map.getCell(position).setLayer(LayerType.UNIT, u);
        }
    }
    
    /**
     * Implemented by classes which want to be notified if certain events occur.
     * If the game is played over a network, these listeners can be used to synchronize server and client state.
     */
    public interface GameListener {
        void onTurn(int playerId);
        void onAttack(Hex attacker, Hex target);
    }
}
