package de.gymwkb.civ.game;

import com.badlogic.gdx.utils.Array;

import de.gymwkb.civ.map.HexMap;

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
    
    public GameController() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Implemented by classes which want to be notified if certain events occur.
     * If the game is played over a network, this listeners can be used to synchronize server and client state.
     */
    public interface GameListener {
        
    }
}
