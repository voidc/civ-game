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
    
    public HumanPlayerController(GameController game, int playerId) {
        super(game, playerId);
        listeners = new Array<PlayerListener>();
        actionPath = new Array<Hex>();
    }
    
    public void onTurn(int playerId) {
        if(playerId == player.id) {
            System.out.println("Your turn!");
        } else {
            System.out.println("Turn of player " + playerId);
        }
        
    }

    public void onMove(Hex unit, Hex target) {
        if(unit.equals(selectedHex) && target.equals(actionHex)) {
            selectUnit(target);
        }
        
    }

    public void onAttack(Hex attacker, Hex target, float damage) {
        // TODO Auto-generated method stub    
    }

    public void onHexClicked(Hex hex, int button) {
        if(hex == null || !map.contains(hex))
            return;
        
        Unit u = map.getUnit(hex);
        if(u != null) {
            if(hex.equals(selectedHex)) {
                selectUnit(null);
            } else if(u.getOwnerId() == player.id){
                selectUnit(hex);
            } else if(selectedHex != null) {
                actionHex = hex;
                game.attack(player.id, selectedHex, hex);
            }
        } else {
            if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
                game.spawnUnit(player.id, hex, UnitType.values()[MathUtils.random(UnitType.COUNT - 1)]);
            } else if(Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)) {
                game.spawnUnit(player.id+1, hex, UnitType.values()[MathUtils.random(UnitType.COUNT - 1)]);
            } else if(selectedHex != null) {
                actionHex = hex;
                game.move(player.id, selectedHex, hex);
            }
        }
    }
    
    public void onHexHover(Hex hex) {
        if(hex != null)
            listeners.forEach(l -> l.showInfo(hex.toString()));
        
        if(actionHex != null) {
            actionHex = null;
            actionPath.clear();
        }
        
        if(hex != null && map.contains(hex) &&
                selectedHex != null && !hex.equals(selectedHex)) {
            actionHex = hex;
            boolean pathExists = game.getPathfinder().findPath(actionPath, selectedHex, actionHex);
            if(pathExists) {
                action = map.getUnit(hex) == null ? UnitAction.MOVE : UnitAction.ATTACK;
            } else {
                action = UnitAction.NONE;
            }
        }
    }
    
    public void selectUnit(Hex hex) {
        if(hex == null) {
            selectedHex = null;
            onHexHover(null);
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
