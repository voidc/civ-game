package de.gymwkb.civ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.gymwkb.civ.input.CameraController;
import de.gymwkb.civ.input.MapController;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.map.HexMapLayout;
import de.gymwkb.civ.map.HexMapRenderer;
import de.gymwkb.civ.map.HexagonGenerator;

public class GameScreen implements Screen {
    private final CivGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private HexagonGenerator generator;
    private HexMap map;
    private HexMapRenderer renderer;
    private TextureAtlas hextures;
    private HUD hud;
    
    public static final float WORLD_HEIGHT = 480f;

    public GameScreen(CivGame game) {
        this.game = game;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, (w / h) * WORLD_HEIGHT, WORLD_HEIGHT);
        this.camera.update();
        this.viewport = new ScreenViewport(camera);
        
        this.hud = new HUD();
        
        hextures = game.assets.get("hextures/pack.atlas");
        
        HexMapLayout layout = new HexMapLayout(HexMapLayout.FLAT, new Vector2(100f, 100f), new Vector2());
        this.generator = new HexagonGenerator(layout);
        this.map = generator.generateMap(HexagonGenerator.SIZE, false);
        this.renderer = new HexMapRenderer(map, game.batch);
        renderer.loadHextures(hextures);
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud);
        inputMultiplexer.addProcessor(new CameraController(camera));
        inputMultiplexer.addProcessor(new MapController(map, layout, camera));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        camera.update();
        renderer.setView(camera);
        renderer.render();
        hud.getViewport().apply();
        hud.act(delta);
        hud.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hud.getViewport().update(width, height, true);
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
        hextures.dispose();
        hud.dispose();
    }
}
