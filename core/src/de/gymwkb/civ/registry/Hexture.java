package de.gymwkb.civ.registry;

import com.badlogic.gdx.graphics.Color;

import de.gymwkb.civ.map.HexMap.Cell.ILayer;

/**
 * This enumeration contains all available hextures.
 * A hexture is a hexagonal texture which is displayed on the map.
 * It can either be an unit, terrain or foreground texture.
 * The corresponding textures are loaded from a TextureAtlas and stored in the {@link HexMapRenderer}.
 */
public enum Hexture {
    //FOREGROUND
    SELECTION_BASE,
    //TERRAIN
    TERRAIN_GRASS,
    TERRAIN_FOREST,
    TERRAIN_ROCKS,
    TERRAIN_SAND,
    TERRAIN_DESERT,
    TERRAIN_STONE,
    //UNITS
    UNIT_BASE;
    
    public static final int COUNT = Hexture.values().length;
    
    public ILayer createLayer(Color tint) {
        return new ILayer() {

            @Override
            public Hexture getHexture() {
                return Hexture.this;
            }
            
            @Override
            public Color getTint() {
                return tint;
            }
            
        };
    }
}
