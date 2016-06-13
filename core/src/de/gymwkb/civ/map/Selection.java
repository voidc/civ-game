package de.gymwkb.civ.map;

import de.gymwkb.civ.registry.Hexture;

public class Selection implements HexMap.Cell.ILayer {

    @Override
    public Hexture getHexture() {
        return Hexture.SELECTION;
    }

}
