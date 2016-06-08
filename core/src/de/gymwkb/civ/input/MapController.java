package de.gymwkb.civ.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.map.HexMapLayout;

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
//                map.getCell(hex).setLayer(LayerType.FOREGROUND, new SelectionLayer());
                map.getCell(hex).selected = true;
            } else {           
//                Cell last = map.getCell(lastHex);
//                map.getCell(hex).setLayer(LayerType.FOREGROUND, last.getLayer(LayerType.FOREGROUND));
//                last.setLayer(LayerType.FOREGROUND, null);
                map.getCell(lastHex).selected = false;
                map.getCell(hex).selected = true;
            }
            lastHex = hex;
        }
    }
    
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        camera.unproject(mousePos3.set(x, y, 0));
        mousePos2.set(mousePos3.x, mousePos3.y);
        setSelectedHex(layout.cartesianToHex(mousePos2));
        return true;
    }

}
