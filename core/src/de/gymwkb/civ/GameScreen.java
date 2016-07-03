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
import de.gymwkb.civ.input.CameraInputProcessor;
import de.gymwkb.civ.input.MapInputProcessor;
import de.gymwkb.civ.map.HexMapLayout;
import de.gymwkb.civ.view.HUD;
import de.gymwkb.civ.view.HexMapRenderer;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private HexMapRenderer renderer;
    private TextureAtlas hextures;
    private HUD hud;
    
    public static float GAME_HEIGHT = 720f;
    public static float GAME_WIDTH = 1280f;

    public GameScreen(HumanPlayerController controller) {
        GAME_WIDTH = (Gdx.graphics.getWidth() / Gdx.graphics.getHeight()) * GAME_HEIGHT;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, GAME_HEIGHT, GAME_WIDTH);
        this.camera.update();
        this.viewport = new ScreenViewport(camera);
        
        this.hud = new HUD(controller);
        
        hextures = CivGame.instance.assets.get("hextures/pack.atlas");
        
        HexMapLayout layout = new HexMapLayout(HexMapLayout.POINTY, new Vector2(100f, 100f), new Vector2());
        this.renderer = new HexMapRenderer(controller, layout, CivGame.instance.batch);
        renderer.loadHextures(hextures);
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud);
        inputMultiplexer.addProcessor(new CameraInputProcessor(camera));
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
        hud.show();
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
