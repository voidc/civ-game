package de.gymwkb.civ.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.gymwkb.civ.registry.UnitType;

public class Unit implements HexMap.Cell.Layer {
    private UnitType type;

    @Override
    public TextureRegion getTexture() {
        return type.getTexture();
    }
}
