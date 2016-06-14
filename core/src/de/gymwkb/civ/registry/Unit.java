package de.gymwkb.civ.registry;

import de.gymwkb.civ.map.HexMap;

public class Unit implements HexMap.Cell.ILayer {
    public final UnitType type;
    private int health;
    private int ep;
    
    public Unit(UnitType type) {
        this.type = type;
    }

    @Override
    public Hexture getHexture() {
        return type.hexture;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, type.maxHealth);
    }

    public int getEp() {
        return ep;
    }

    public void setEp(int ep) {
        this.ep = ep;
    }
}
