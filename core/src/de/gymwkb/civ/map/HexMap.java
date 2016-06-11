package de.gymwkb.civ.map;

import com.badlogic.gdx.utils.OrderedMap;

import de.gymwkb.civ.registry.HextureRegistry.Hexture;

public class HexMap {
    private final OrderedMap<Hex, Cell> cells;

    public static final Hex[] NEIGHBORS = new Hex[] {new Hex(1, -1, 0), new Hex(0, 1, -1), new Hex(-1, 0, 1),
            new Hex(-1, 1, 0), new Hex(0, -1, 1), new Hex(1, 0, -1)};
    public static final int LAYER_COUNT = LayerType.values().length;

    public HexMap() {
        this.cells = new OrderedMap<Hex, Cell>();
    }

    public Iterable<Hex> getHexes() {
        return cells.keys();
    }

    public Cell getCell(Hex hex) {
        return cells.get(hex);
    }

    public boolean contains(Hex hex) {
        return cells.containsKey(hex);
    }

    public void addCell(Hex hex, Cell cell) {
        cells.put(hex, cell);
    }

    public Hex getHexAt(int index) {
        return cells.orderedKeys().get(index);
    }

    public int getSize() {
        return cells.size;
    }

    public static class Cell {
        private Layer[] layers;
        public boolean selected;

        public Cell() {
            layers = new Layer[LAYER_COUNT];
            selected = false;
        }

        public Layer getLayer(LayerType type) {
            return layers[type.ordinal()];
        }

        public void setLayer(LayerType type, Layer layer) {
            layers[type.ordinal()] = layer;
        }

        public Layer[] getLayers() {
            return layers;
        }

        public interface Layer {
            Hexture getHexture();
        }
    }

    public enum LayerType {
        TERRAIN, UNIT, FOREGROUND
    }
}
