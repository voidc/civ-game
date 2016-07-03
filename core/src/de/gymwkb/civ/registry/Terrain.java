package de.gymwkb.civ.registry;

import de.gymwkb.civ.map.HexMap;

public enum Terrain implements HexMap.Cell.ILayer {
    GRASS(Hexture.TERRAIN_GRASS),
    FOREST(Hexture.TERRAIN_FOREST),
    ROCKS(Hexture.TERRAIN_ROCKS),
    SAND(Hexture.TERRAIN_SAND),
    DESERT(Hexture.TERRAIN_DESERT),
    STONE(Hexture.TERRAIN_STONE);
    
    private Hexture hexture;
    
    public static final int COUNT = Terrain.values().length;
    
    private Terrain(Hexture hexture) {
        this.hexture = hexture;
    }

    @Override
    public Hexture getHexture() {
        return hexture;
    }
}
