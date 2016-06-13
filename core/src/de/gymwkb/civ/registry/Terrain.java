package de.gymwkb.civ.registry;

import de.gymwkb.civ.map.HexMap;

public enum Terrain implements HexMap.Cell.ILayer {
    DEFAULT(Hexture.TERRAIN_DEFAULT);
    
    private Hexture hexture;
    
    private Terrain(Hexture hexture) {
        this.hexture = hexture;
    }

    @Override
    public Hexture getHexture() {
        return hexture;
    }
}
