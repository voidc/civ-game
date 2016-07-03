package de.gymwkb.civ.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.Cell.ILayer;
import de.gymwkb.civ.registry.Hexture;

public class HexMapRenderer {
    private HexMap map;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private HexMapLayout layout;
    private Rectangle viewBounds;
    private AtlasRegion[] cachedHextures;

    private final Vector2[] vertexBuffer;

    public HexMapRenderer(HexMap map, HexMapLayout layout, SpriteBatch batch) {
        this.map = map;
        this.batch = batch;
        this.layout = layout;
        this.shapeRenderer = new ShapeRenderer();
        this.viewBounds = new Rectangle();
        
        vertexBuffer = new Vector2[6];
        for(int i = 0; i < 6; i++) {
            vertexBuffer[i] = new Vector2();
        }
    }

    /**
     * Sets the projection matrix and also calculates which part of the map should be rendered
     */
    public void setView(OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        float width = camera.viewportWidth * camera.zoom + layout.getHexWidth();
        float height = camera.viewportHeight * camera.zoom + layout.getHexHeight();
        viewBounds.set(camera.position.x - width / 2, camera.position.y - height / 2, width, height);
    }

    /**
     * Trys to find an {@link AtlasRegion} for each {@link Hexture} and stores them.
     * If a region cannot be fond an RuntimeException is thrown.
     * MUST be called before rendering.
     * @param atlas An TextureAtlas containing textures for each hexture defined in {@link Hexture}
     */
    public void loadHextures(TextureAtlas atlas) {
        Hexture[] types = Hexture.values();
        cachedHextures = new AtlasRegion[types.length];
        for(int i = 0; i < types.length; i++) {
            AtlasRegion reg = atlas.findRegion(types[i].name());
            if(reg == null)
                throw new RuntimeException("Hexture " + types[i].name() + " not found in atlas!");
            cachedHextures[i] = reg;
        }
    }

    /**
     * {@link #loadHextures} must be called before using this method.
     * @return An AtlasRegion with the texture for the given hexture.
     */
    public AtlasRegion getCachedHexture(Hexture hexture) {
        return cachedHextures[hexture.ordinal()];
    }

    /**
     * Draws the visible part of the map using the Batch for the cell layers and a ShapeRenderer for the outline of each cell.
     * {@link #loadHextures} must be called before using this method.
     */
    public void render() {
        shapeRenderer.begin(ShapeType.Line);
        batch.begin();
        
        for (Hex hex : map.getHexes()) {
            Vector2 coord = layout.hexToCartesian(hex);
            if(!viewBounds.contains(coord))
                continue;
            Cell cell = map.getCell(hex);
            drawOutline(hex, cell);
            for(ILayer l : cell.getLayers()) {
                drawLayer(hex, l);
            }
        }
        
        if(map.getPath() != null) {
            drawPath(map.getPath());
        }
        
        batch.end();
        shapeRenderer.end();
    }
    
    private void drawOutline(Hex hex, Cell cell) {
        shapeRenderer.setColor(1, 1, 0, 1);
        layout.getVertices(hex, vertexBuffer);
        for(int i = 0; i < vertexBuffer.length; i++) {
            shapeRenderer.line(vertexBuffer[i], vertexBuffer[(i+1)%6]);
        }
    }
    
    private void drawLayer(Hex hex, Cell.ILayer layer) {
        if(layer == null || layer.getHexture() == null)
            return;
        batch.setColor(layer.getTint());
        TextureRegion texture = getCachedHexture(layer.getHexture());
        Rectangle bounds = layout.getTextureBounds(hex);
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    private void drawPath(Array<Hex> path) {
        shapeRenderer.setColor(0, 0, 1, 1);
        for(int i = 1; i < path.size; i++) {
            Vector2 a = layout.hexToCartesian(path.get(i - 1));
            Vector2 b = layout.hexToCartesian(path.get(i));
            shapeRenderer.line(a, b);
        }
    }
}
