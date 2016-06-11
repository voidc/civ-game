package de.gymwkb.civ.map;

import de.gymwkb.civ.registry.Hexture;

public class Unit implements HexMap.Cell.Layer {
    private Hexture hexture;
    
    public Unit(Hexture hexture) {
        this.hexture = hexture;
    }

    @Override
    public Hexture getHexture() {
        return hexture;
    }
}
