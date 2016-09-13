package de.gymwkb.civ.game;

import de.gymwkb.civ.map.Hex;

public class DummyPlayerController extends PlayerController {

    public DummyPlayerController(GameController game, int playerId) {
        super(game, playerId);
    }

    @Override
    public void onTurn(int playerId) {
        if (playerId == player.id) {
            System.out.println("Dummy Turn");
            game.finishTurn(playerId);
        }
    }

    @Override
    public void onMove(Hex unit, Hex target) {

    }

    @Override
    public void onAttack(Hex attacker, Hex target, float damage) {

    }

    @Override
    public void onDeath(Hex deadUnit) {

    }
}
