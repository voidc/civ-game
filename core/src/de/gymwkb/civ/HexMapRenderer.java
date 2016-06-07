package de.gymwkb.civ;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import de.gymwkb.civ.HexMap.Cell;

public class HexMapRenderer {
    private HexMap map;
    private Batch batch;
    private Matrix4 projMatrix;
    private ShapeRenderer shapeRenderer;

    public HexMapRenderer(HexMap map, Batch batch) {
        this.map = map;
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
    }

    public void setView(OrthographicCamera camera) {
        projMatrix = camera.projection;
    }

    public void render() {
        for (Hex hex : map.getHexes()) {
            Cell cell = map.getCell(hex);

        }
    }
}
