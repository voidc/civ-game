package de.gymwkb.civ;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.OrderedMap;

public class HexMap {
    private OrderedMap<Hex, Cell> cells;
    
    public HexMap() {
        this.cells = new OrderedMap<Hex, Cell>();
    }
    
    public OrderedMap<Hex, Cell> getCells() {
		return cells;
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
    	UNIT,
    	TERRAIN;
    }
}
