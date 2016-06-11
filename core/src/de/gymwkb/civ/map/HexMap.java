package de.gymwkb.civ.map;

import com.badlogic.gdx.utils.OrderedMap;

import de.gymwkb.civ.registry.Hexture;

/**
 * The HexMap is responsible for storing all hex cells and their position.
 */
public class HexMap {
    private final OrderedMap<Hex, Cell> cells;

    public static final Hex[] NEIGHBORS = new Hex[] {new Hex(1, -1, 0), new Hex(0, 1, -1), new Hex(-1, 0, 1),
            new Hex(-1, 1, 0), new Hex(0, -1, 1), new Hex(1, 0, -1)};
    public static final int LAYER_COUNT = LayerType.values().length;

    public HexMap() {
        this.cells = new OrderedMap<Hex, Cell>();
    }

    /**
     * @return An iterator of all hex coordinates in the map (use {@link #getCell} to retrieve the corresponding cell)
     */
    public Iterable<Hex> getHexes() {
        return cells.keys();
    }

    /**
     * @param hex The hex coordinate of the cell to be retrieved
     * @return The cell object at the given hex coordinate containing layers
     */
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

    /**
     * A cell is one hexagon on the map.
     * It consists of one {@link Layer} for each {@link LayerType}.
     */
    public static class Cell {
        private Layer[] layers;

        public Cell() {
            layers = new Layer[LAYER_COUNT];
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
