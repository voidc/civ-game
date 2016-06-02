package de.gymwkb.civ;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public class HexMap {
    private OrderedMap<Object, Cell> cells; //TODO: replace Object with class representing a hex coordinate

    public HexMap() {
        this.cells = new OrderedMap<>();
    }

    public static class Cell {
        private Array<Layer> layers;

        public interface Layer {
            TextureRegion getTexture();
        }
    }
}
