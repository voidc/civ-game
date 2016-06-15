package de.gymwkb.civ.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.gymwkb.civ.game.HumanPlayerController;
import de.gymwkb.civ.map.HexMapLayout;

public class MapInputProcessor extends InputAdapter {
    private HumanPlayerController controller;
    private HexMapLayout layout;
    private OrthographicCamera camera;
    private final Vector3 mousePos3 = new Vector3();
    private final Vector2 mousePos2 = new Vector2();
    
    public MapInputProcessor(HumanPlayerController ctrl, HexMapLayout layout, OrthographicCamera camera) {
        this.controller = ctrl;
        this.layout = layout;
        this.camera = camera;
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
            controller.onHexClicked(layout.cartesianToHex(mousePos2));
            return true;
        }
        return false;
    }

}