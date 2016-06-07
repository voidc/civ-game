package de.gymwkb.civ.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.Cell.Layer;

public class HexMapRenderer {
    private HexMap map;
    private Batch batch;
    private ShapeRenderer shapeRenderer;
    private Layout layout;
    private Rectangle viewBounds;
    
    private final float outlineWidth = 1f;

    public HexMapRenderer(HexMap map, Batch batch) {
        this.map = map;
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        layout = new Layout(Layout.FLAT, new Vector2(100f, 100f), Vector2.Zero);
        viewBounds = new Rectangle();
    }

    public void setView(OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        float width = camera.viewportWidth * camera.zoom;
        float height = camera.viewportHeight * camera.zoom;
        viewBounds.set(camera.position.x - width / 2, camera.position.y - height / 2, width, height);
    }

    public void render() {
        shapeRenderer.begin(ShapeType.Line);
        batch.begin();
        
        for (Hex hex : map.getHexes()) {
            Vector2 coord = layout.hexToCartesian(hex);
            if(!viewBounds.contains(coord))
                continue;
            drawOutline(hex);
            Cell cell = map.getCell(hex);
            for(Layer l : cell.getLayers()) {
                drawLayer(l);
            }
        }
        
        batch.end();
        shapeRenderer.end();
    }
    
    private void drawOutline(Hex hex) {
        shapeRenderer.setColor(1, 1, 0, 1);
        Vector2[] vertices = layout.getVertices(hex);
        for(int i = 0; i < vertices.length; i++) {
            shapeRenderer.rectLine(vertices[i], vertices[(i+1)%6], outlineWidth);
        }
    }
    
    private void drawLayer(Cell.Layer layer) {
        //TODO: draw textures
    }
}
