package de.gymwkb.civ.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap.Cell.ILayer;
import de.gymwkb.civ.map.HexMap.LayerType;
import de.gymwkb.civ.registry.Hexture;
import de.gymwkb.civ.registry.UnitType;

/**
 * Processes inputs before sending them to the game controller.
 */
public class HumanPlayerController extends PlayerController {
    private Hex selectedUnit;
    private Hex secondaryHex;
    private Array<PlayerListener> listeners;
    
    public static final ILayer SELECTION_UNIT = Hexture.SELECTION_BASE.createLayer(new Color(0x00b2aeff));
    public static final ILayer SELECTION_MOVE = Hexture.SELECTION_BASE.createLayer(new Color(0x718000ff));
    public static final ILayer SELECTION_ATTACK = Hexture.SELECTION_BASE.createLayer(new Color(0xb22300ff));
    
    public HumanPlayerController(GameController game, int playerId) {
        super(game, playerId);
        listeners = new Array<PlayerListener>();
    }
    
    public void onTurn(int playerId) {
        if(playerId == player.id) {
            System.out.println("Your turn!");
        } else {
            System.out.println("Turn of player " + playerId);
        }
        
    }

    public void onMove(Hex unit, Hex target) {
        if(unit.equals(selectedUnit) && target.equals(secondaryHex)) {
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
            if(hex.equals(selectedUnit)) {
                selectUnit(null);
            } else if(u.getOwnerId() == player.id){
                selectUnit(hex);
            } else if(selectedUnit != null) {
                secondaryHex = hex;
                game.attack(player.id, selectedUnit, hex);
            }
        } else {
            if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
                game.spawnUnit(player.id, hex, UnitType.values()[MathUtils.random(UnitType.COUNT - 1)]);
            } else if(selectedUnit != null) {
                secondaryHex = hex;
                game.move(player.id, selectedUnit, hex);
            }
        }
    }
    
    public void onHexHover(Hex hex) {
        if(hex != null)
            listeners.forEach(l -> l.showInfo(hex.toString()));
        
        if(secondaryHex != null) {
            map.getCell(secondaryHex).setLayer(LayerType.FOREGROUND, null);
            secondaryHex = null;
            map.setPath(null);
        }
        
        if(hex != null && map.contains(hex) &&
                selectedUnit != null && !hex.equals(selectedUnit)) {
            ILayer sel = map.getUnit(hex) == null ? SELECTION_MOVE : SELECTION_ATTACK;
            map.getCell(hex).setLayer(LayerType.FOREGROUND, sel);
            secondaryHex = hex;
            Array<Hex> path = game.getPathfinder().findPath(selectedUnit, secondaryHex);
            map.setPath(path);
        }
    }
    
    public void selectUnit(Hex hex) {
        if(hex == null) {
            map.getCell(selectedUnit).setLayer(LayerType.FOREGROUND, null);
            selectedUnit = null;
            onHexHover(null);
            listeners.forEach(listener -> listener.onUnitSelected(null));
        } else {
            if(selectedUnit != null) {
                selectUnit(null);
            }
            map.getCell(hex).setLayer(LayerType.FOREGROUND, SELECTION_UNIT);
            selectedUnit = hex;
            listeners.forEach(listener -> listener.onUnitSelected(map.getUnit(selectedUnit)));
        }
    }

    public Hex getSelectedUnit() {
        return selectedUnit;
    }

    public void registerListener(PlayerListener l) {
        listeners.add(l);
    }

    public interface PlayerListener {
        void onUnitSelected(Unit unit);
        void showInfo(String info);
    }
    
}
