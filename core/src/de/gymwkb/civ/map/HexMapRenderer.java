package de.gymwkb.civ.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.Cell.Layer;

public class HexMapRenderer {
    private HexMap map;
    private Batch batch;
    private Matrix4 projMatrix;
    private ShapeRenderer shapeRenderer;
    private Layout layout;
    
    private final float outlineWidth = 1f;

    public HexMapRenderer(HexMap map, Batch batch) {
        this.map = map;
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        layout = new Layout(Layout.FLAT, new Vector2(100f, 100f), Vector2.Zero);
    }

    public void setView(OrthographicCamera camera) {
        //TODO: set view bounds to limit rendering
        projMatrix = camera.combined;
    }

    public void render() {
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setProjectionMatrix(projMatrix);
        shapeRenderer.setColor(1, 1, 0, 1);
        batch.begin();
        batch.setProjectionMatrix(projMatrix);
        
        for (Hex hex : map.getHexes()) {
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
        Vector2[] vertices = layout.getVertices(hex);
        for(int i = 0; i < vertices.length; i++) {
            shapeRenderer.rectLine(vertices[i], vertices[(i+1)%6], outlineWidth);
        }
    }
    
    private void drawLayer(Cell.Layer layer) {
        //TODO: draw textures
    }
}
