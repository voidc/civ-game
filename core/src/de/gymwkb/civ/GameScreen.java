package de.gymwkb.civ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMapRenderer;

public class GameScreen implements Screen {
    private final CivGame game;
    private OrthographicCamera camera;
    private HexMap map;
    private HexMapRenderer renderer;

    public GameScreen(CivGame game) {
        this.game = game;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 480, 480);
        camera.update();
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new CameraController(camera));
        Gdx.input.setInputProcessor(inputMultiplexer);

        map = testMap();
        renderer = new HexMapRenderer(map, game.batch);
    }
    
    /**
     * Zu Testzwecken bis Martin fertig ist.
     */
    private HexMap testMap() {
        HexMap map = new HexMap();
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                Cell c = new Cell();
                map.addCell(new Hex(i, j, -i-j), c);
            }
        }
        return map;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
