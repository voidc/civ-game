package de.gymwkb.civ.game;

import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.LayerType;
import de.gymwkb.civ.map.Selection;
import de.gymwkb.civ.registry.UnitType;

/**
 * Processes inputs before sending them to the game controller.
 */
public class HumanPlayerController extends PlayerController {
    private Hex selection;
    
    public HumanPlayerController(GameController game, int playerId) {
        super(game, playerId);
        game.spawnUnit(player.id, new Hex(0, 0, 0), UnitType.TEST);
    }
    
    public Hex getSelectedHex() {
        return selection;
    }
    
    public void setSelectedHex(Hex hex) {
        if(hex != null && !hex.equals(selection) && map.contains(hex)) {
            if(selection == null) {
                map.getCell(hex).setLayer(LayerType.FOREGROUND, new Selection());
            } else {           
                Cell last = map.getCell(selection);
                map.getCell(hex).setLayer(LayerType.FOREGROUND, last.getLayer(LayerType.FOREGROUND));
                last.setLayer(LayerType.FOREGROUND, null);
            }
            selection = hex;
        }
    }
    
    public void onHexClicked(Hex hex) {
        if(hex != null && map.contains(hex) && map.getUnit(hex) != null)
            setSelectedHex(hex);
    }
    
}
