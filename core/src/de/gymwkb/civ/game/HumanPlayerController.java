package de.gymwkb.civ.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.registry.UnitType;

/**
 * Processes inputs before sending them to the game controller.
 */
public class HumanPlayerController extends PlayerController {
    private Array<PlayerListener> listeners;
    private Hex selectedHex;
    private Hex actionHex;
    private Array<Hex> actionPath;
    private UnitAction action;

    private static final String TAG = "HumanPlayerController";
    
    public HumanPlayerController(GameController game, int playerId) {
        super(game, playerId);
        listeners = new Array<PlayerListener>();
        actionPath = new Array<Hex>();
    }
    
    public void onTurn(int playerId) {
        if(playerId == player.id) {
            Gdx.app.log(TAG, "Your turn!");
        }
    }

    public void onMove(Hex unit, Hex target) {
        if(unit.equals(selectedHex) && target.equals(actionHex)) {
            selectUnit(target);
        }
        
    }

    public void onAttack(Hex attacker, Hex target, float damage) {
        Gdx.app.log(TAG, "Attack damage: " + damage);
    }

    @Override
    public void onDeath(Hex deadUnit) {
    }

    public void nextTurn() {
        game.finishTurn(player.id);
    }

    public void onHexClicked(Hex hex, int button) {
        if(hex == null || !map.contains(hex))
            return;
        
        Unit u = map.getUnit(hex);
        if(u != null && u.getOwnerId() == player.id) {
            selectUnit(hex);
        } else if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
            game.spawnUnit(player.id, hex, UnitType.values()[MathUtils.random(UnitType.COUNT - 1)]);
        } else if(Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)) {
            game.spawnUnit(player.id+1, hex, UnitType.values()[MathUtils.random(UnitType.COUNT - 1)]);
        } else {
            action = checkAction(hex);
            try {
                executeAction();
            } catch (IllegalMoveException e) {
                Gdx.app.log(TAG, e.getMessage());
            }
        }
    }
    
    public void onHexHover(Hex hex) {
        if(hex != null) {
            String dist = selectedHex != null ? ", d: " + String.valueOf(selectedHex.mhDist(hex)) : "";
            listeners.forEach(l -> l.showInfo(hex.toString() + dist));
            
        }
        
        action = checkAction(hex);
    }
    
    /**
     * Checks which action can be performed targeting the given hex.
     * In doing so it also checks attack- and movementRange.
     * Also sets actionHex and actionPath if the the given hex is valid.
     * @return A fitting action or NONE if no action is possible.
     */
    private UnitAction checkAction(Hex hex) {
        if(hex != null && map.contains(hex) &&
                selectedHex != null && !hex.equals(selectedHex)) {
            actionHex = hex;
            
            int mhDist = selectedHex.mhDist(actionHex);
            Unit target = map.getUnit(hex);
            Unit unit = map.getUnit(selectedHex); //assert: not null
            if(target != null) { //check attack action
                if(target.getOwnerId() != player.id && mhDist <= unit.type.attackRange) {
                    return UnitAction.ATTACK;
                }
            } else { //check move action
                boolean pathExists = game.getPathfinder().findPath(actionPath, selectedHex, actionHex,
                        unit.getRemainingMoves());
                if(pathExists)
                    return UnitAction.MOVE;
            }
        } else {
            actionHex = null;
        }
        
        return UnitAction.NONE;
    }
    
    /**
     * Calls the method of the GameController, which corresponds to action.
     * If action is NONE, nothing will happen.
     */
    private void executeAction() throws IllegalMoveException {
        switch(action) {
            case MOVE:
                game.move(player.id, selectedHex, actionHex);
                break;
            case ATTACK:
                game.attack(player.id, selectedHex, actionHex);
            default:
                break;
        }
    }

    /**
     * - deletes selection if hex equals selectedHex<br/>
     * - does NOT check if there is an unit on the given hex
     */
    private void selectUnit(Hex hex) {
        if(hex == null || hex.equals(selectedHex)) {
            selectedHex = null;
            action = checkAction(null);
            listeners.forEach(listener -> listener.onUnitSelected(null));
        } else {
            if(selectedHex != null) {
                selectUnit(null);
            }
            selectedHex = hex;
            listeners.forEach(listener -> listener.onUnitSelected(map.getUnit(selectedHex)));
        }
    }
    
    public Hex getSelectedHex() {
        return selectedHex;
    }
    
    public UnitAction getAction() {
        return action;
    }
    
    public Hex getActionHex() {
        return actionHex;
    }
    
    public Array<Hex> getActionPath() {
        return actionPath;
    }

    public void registerListener(PlayerListener l) {
        listeners.add(l);
    }

    public interface PlayerListener {
        void onUnitSelected(Unit unit);
        void showInfo(String info);
    }
    
    public enum UnitAction {
        NONE(Color.LIGHT_GRAY),
        MOVE(new Color(0x718000ff)),
        ATTACK(new Color(0xb22300ff));
        
        public final Color actionColor;
        
        public static final int COUNT = UnitAction.values().length;
        
        private UnitAction(Color actionColor) {
            this.actionColor = actionColor;
        }
    }
    
}
