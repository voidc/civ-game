package de.gymwkb.civ.registry;

import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.Cell.ILayer;

public class Unit implements HexMap.Cell.ILayer {
    private Hexture hexture;
    
    public Unit(Hexture hexture) {
        this.hexture = hexture;
    }

    @Override
    public Hexture getHexture() {
        return hexture;
    }
}
