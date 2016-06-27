package de.gymwkb.civ.game;

import de.gymwkb.civ.map.HexMap;

/**
 * Communicates with the GameController. (client role)
 * Can be an AI or an local or remote human player.
 */
public abstract class PlayerController {
    protected GameController game;
    protected Player player;
    protected HexMap map;
    
    public PlayerController(GameController game, int playerId) {
        this.game = game;
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