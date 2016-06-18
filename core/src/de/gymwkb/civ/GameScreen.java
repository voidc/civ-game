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

import de.gymwkb.civ.game.HumanPlayerController;
import de.gymwkb.civ.input.CameraController;
import de.gymwkb.civ.input.HUD;
import de.gymwkb.civ.input.MapInputProcessor;
import de.gymwkb.civ.map.HexMapLayout;
import de.gymwkb.civ.map.HexMapRenderer;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private HexMapRenderer renderer;
    private TextureAtlas hextures;
    private HUD hud;
    
    public static final float WORLD_HEIGHT = 480f;

    public GameScreen(HumanPlayerController controller) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, (w / h) * WORLD_HEIGHT, WORLD_HEIGHT);
        this.camera.update();
        this.viewport = new ScreenViewport(camera);
        
        this.hud = new HUD(controller);
        
        hextures = CivGame.instance.assets.get("hextures/pack.atlas");
        
        HexMapLayout layout = new HexMapLayout(HexMapLayout.POINTY, new Vector2(100f, 100f), new Vector2());
        this.renderer = new HexMapRenderer(controller.getMap(), layout, CivGame.instance.batch);
        renderer.loadHextures(hextures);
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud);
        inputMultiplexer.addProcessor(new CameraController(camera));
        inputMultiplexer.addProcessor(new MapInputProcessor(controller, layout, camera));
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
