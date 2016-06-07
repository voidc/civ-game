package de.gymwkb.civ;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.gymwkb.civ.map.HexMap;

public class Unit implements HexMap.Cell.Layer {
    private UnitType type;

    @Override
    public TextureRegion getTexture() {
        return type.getTexture();
    }
}
