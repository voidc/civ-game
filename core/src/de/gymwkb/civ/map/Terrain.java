package de.gymwkb.civ.map;

import de.gymwkb.civ.registry.Hexture;

public class Terrain implements HexMap.Cell.Layer {
    private Hexture hexture;
    
    public Terrain(Hexture hexture) {
        this.hexture = hexture;
    }

    @Override
    public Hexture getHexture() {
        return hexture;
    }
}
