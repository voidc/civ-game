package de.gymwkb.civ.game;

import de.gymwkb.civ.game.GameController.GameListener;
import de.gymwkb.civ.map.HexMap;

/**
 * Communicates with the GameController. (client role)
 * Can be an AI or an local or remote human player.
 */
public abstract class PlayerController implements GameListener {
    protected GameController game;
    protected Player player;
    protected HexMap map;
    
    public PlayerController(GameController game, int playerId) {
        this.game = game;
        game.registerListener(this);
        this.player = game.getPlayer(playerId);
        map = game.getMap();
    }
    
    public HexMap getMap() {
        return map;
    }
    
    public Player getPlayer() {
        return player;
    }
}