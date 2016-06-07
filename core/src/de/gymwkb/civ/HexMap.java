package de.gymwkb.civ;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.OrderedMap;

public class HexMap {
    private final OrderedMap<Hex, Cell> cells;

    public static final Hex[] NEIGHBORS = new Hex[] {new Hex(1, 0, 0), new Hex(0, 1, 0), new Hex(0, 0, 1),
            new Hex(-1, 0, 0), new Hex(0, -1, 0), new Hex(0, 0, -1)};

    public HexMap() {
        this.cells = new OrderedMap<Hex, Cell>();
    }

    public Iterable<Hex> getHexes() {
        return cells.keys();
    }

    public Cell getCell(Hex hex) {
        return cells.get(hex);
    }

    public void addCell(Hex hex, Cell cell) {
        cells.put(hex, cell);
    }

    public static class Cell {
        private Layer[] layers;

        public Cell() {
            layers = new Layer[LayerType.values().length];
        }

        public Layer getLayer(LayerType layer) {
            return layers[layer.ordinal()];
        }

        public interface Layer {
            TextureRegion getTexture();
        }
    }

    public enum LayerType {
        UNIT, TERRAIN;
    }
}
