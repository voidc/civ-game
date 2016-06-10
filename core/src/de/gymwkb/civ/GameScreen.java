package de.gymwkb.civ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.gymwkb.civ.input.CameraController;
import de.gymwkb.civ.input.MapController;
import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.LayerType;
import de.gymwkb.civ.map.HexMapLayout;
import de.gymwkb.civ.map.HexMapRenderer;
import de.gymwkb.civ.map.Terrain;
import de.gymwkb.civ.registry.HextureRegistry;
import de.gymwkb.civ.registry.HextureRegistry.Hexture;

public class GameScreen implements Screen {
    private final CivGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private HexMap map;
    private HexMapLayout layout;
    private HexMapRenderer renderer;
    private TextureAtlas hextures;
    private HextureRegistry hexreg;
    
    public static final float WORLD_HEIGHT = 480f;

    public GameScreen(CivGame game) {
        this.game = game;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, (w / h) * WORLD_HEIGHT, WORLD_HEIGHT);
        this.camera.update();
        this.viewport = new ScreenViewport(camera);
        
        hextures = new TextureAtlas(Gdx.files.internal("hextures/pack.atlas"));
        hexreg = new HextureRegistry(hextures);
        
        this.map = testMap();
        this.layout = new HexMapLayout(HexMapLayout.FLAT, new Vector2(100f, 100f), new Vector2());
        this.renderer = new HexMapRenderer(map, layout, game.batch, hexreg);
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new CameraController(camera));
        inputMultiplexer.addProcessor(new MapController(map, layout, camera));
        Gdx.input.setInputProcessor(inputMultiplexer);

    }
    
    /**
     * Zu Testzwecken bis Martin fertig ist.
     */
    private HexMap testMap() {
        HexMap map = new HexMap();
        Terrain defaultTerrain = new Terrain(Hexture.TERRAIN_DEFAULT);
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 10; j++) {
                Cell c = new Cell();
                c.setLayer(LayerType.TERRAIN, defaultTerrain);
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
        viewport.update(width, height);
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
    }
}
