package de.gymwkb.civ.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.LayerType;
import de.gymwkb.civ.map.HexMapLayout;
import de.gymwkb.civ.map.Selection;

public class MapController extends InputAdapter {
    private HexMap map;
    private HexMapLayout layout;
    private OrthographicCamera camera;
    private final Vector3 mousePos3 = new Vector3();
    private final Vector2 mousePos2 = new Vector2();
    private Hex lastHex;
    
    public MapController(HexMap map, HexMapLayout layout, OrthographicCamera camera) {
        this.map = map;
        this.layout = layout;
        this.camera = camera;
    }
    
    public Hex getSelectedHex() {
        return lastHex;
    }
    
    public void setSelectedHex(Hex hex) {
        if(hex != null && !hex.equals(lastHex) && map.contains(hex)) {
            if(lastHex == null) {
                map.getCell(hex).setLayer(LayerType.FOREGROUND, new Selection());
            } else {           
                Cell last = map.getCell(lastHex);
                map.getCell(hex).setLayer(LayerType.FOREGROUND, last.getLayer(LayerType.FOREGROUND));
                last.setLayer(LayerType.FOREGROUND, null);
            }
            lastHex = hex;
        }
    }
    
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        mousePos3.set(x, y, 0);
        return false;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(screenX / 10 == ((int) mousePos3.x) / 10 && screenY / 10 == ((int) mousePos3.y) / 10) {
            camera.unproject(mousePos3);
            mousePos2.set(mousePos3.x, mousePos3.y);
            setSelectedHex(layout.cartesianToHex(mousePos2));
            return true;
        }
        return false;
    }

}