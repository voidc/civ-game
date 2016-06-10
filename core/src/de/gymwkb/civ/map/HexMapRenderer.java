package de.gymwkb.civ.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.Cell.Layer;
import de.gymwkb.civ.registry.HextureRegistry;

public class HexMapRenderer {
    private HexMap map;
    private Batch batch;
    private ShapeRenderer shapeRenderer;
    private HexMapLayout layout;
    private Rectangle viewBounds;
    private HextureRegistry hexreg;
    
    private final Vector2[] vertexBuffer;

    public HexMapRenderer(HexMap map, HexMapLayout layout, Batch batch, HextureRegistry hexreg) {
        this.map = map;
        this.batch = batch;
        this.layout = layout;
        this.hexreg = hexreg;
        this.shapeRenderer = new ShapeRenderer();
        this.viewBounds = new Rectangle();
        
        vertexBuffer = new Vector2[6];
        for(int i = 0; i < 6; i++) {
            vertexBuffer[i] = new Vector2();
        }
    }

    public void setView(OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        float width = camera.viewportWidth * camera.zoom + layout.getHexWidth();
        float height = camera.viewportHeight * camera.zoom + layout.getHexHeight();
        viewBounds.set(camera.position.x - width / 2, camera.position.y - height / 2, width, height);
    }

    public void render() {
        shapeRenderer.begin(ShapeType.Line);
        batch.begin();
        
        for (Hex hex : map.getHexes()) {
            Vector2 coord = layout.hexToCartesian(hex);
            if(!viewBounds.contains(coord))
                continue;
            Cell cell = map.getCell(hex);
            drawOutline(hex, cell);
            for(Layer l : cell.getLayers()) {
                drawLayer(hex, l);
            }
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
    
    private void drawLayer(Hex hex, Cell.Layer layer) {
        if(layer == null || layer.getHexture() == null)
            return;
        TextureRegion texture = hexreg.getTexture(layer.getHexture());
        Rectangle bounds = layout.getTextureBounds(hex);
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
