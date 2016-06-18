package de.gymwkb.civ.game;

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
            Unit u = new Unit(players[currentPlayer], type);
            players[currentPlayer].addUnit(u);
            map.getCell(position).setLayer(LayerType.UNIT, u);
        }
    }
    
    /**
     * Implemented by classes which want to be notified if certain events occur.
     * If the game is played over a network, these listeners can be used to synchronize server and client state.
     */
    public interface GameListener {
        void onTurn(int playerId);
    }
}
