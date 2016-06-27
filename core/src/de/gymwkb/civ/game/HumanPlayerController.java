package de.gymwkb.civ.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
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
    
    public void registerListener(PlayerListener l) {
        listeners.add(l);
    }
    
    public Hex getSelectedUnit() {
        return selectedUnit;
    }
    
    public void selectUnit(Hex hex) {
        if(hex == null || hex.equals(selectedUnit)) {
            map.getCell(selectedUnit).setLayer(LayerType.FOREGROUND, null);
            selectedUnit = null;
            onHexHover(null);
            listeners.forEach(listener -> listener.onUnitSelected(null));
        } else {
            map.getCell(hex).setLayer(LayerType.FOREGROUND, SELECTION_UNIT);
            if(selectedUnit != null) {
                map.getCell(selectedUnit).setLayer(LayerType.FOREGROUND, null);
                secondaryHex = null;
            }
            selectedUnit = hex;
            listeners.forEach(listener -> listener.onUnitSelected(map.getUnit(selectedUnit)));
        }
    }
    
    public void onHexClicked(Hex hex, int button) {
        if(hex == null || !map.contains(hex))
            return;
        
        if(button == Buttons.RIGHT && hex.equals(secondaryHex)) {
            if(map.getUnit(hex) != null) {
                game.attack(player.id, selectedUnit, secondaryHex);
            } else {
                game.move(player.id, selectedUnit, secondaryHex);
            }
            return;
        }
        
        if(button == Buttons.LEFT && map.getUnit(hex) != null) {
            selectUnit(hex);
        } else if(Gdx.input.isKeyPressed(Keys.NUM_1)) {
            game.spawnUnit(player.id, hex, UnitType.TEST);
        } else if(Gdx.input.isKeyPressed(Keys.NUM_2)) {
            game.spawnUnit(player.id, hex, UnitType.ARCHER);
        } else if(Gdx.input.isKeyPressed(Keys.NUM_3)) {
            game.spawnUnit(player.id, hex, UnitType.PIKEMAN);
        } else if(selectedUnit != null) {
            selectUnit(null);
        }
    }
    
    public void onHexHover(Hex hex) {
        if(secondaryHex != null) {
            map.getCell(secondaryHex).setLayer(LayerType.FOREGROUND, null);
            secondaryHex = null;
        }
        
        if(hex != null && map.contains(hex) &&
                selectedUnit != null && !hex.equals(selectedUnit)) {
            ILayer sel = map.getUnit(hex) == null ? SELECTION_MOVE : SELECTION_ATTACK;
            map.getCell(hex).setLayer(LayerType.FOREGROUND, sel);
            secondaryHex = hex;
        }
    }
    
    public interface PlayerListener {
        void onUnitSelected(Unit unit);
    }
    
}
