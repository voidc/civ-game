package de.gymwkb.civ.game;

import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.registry.Hexture;
import de.gymwkb.civ.registry.UnitType;

public class Unit implements HexMap.Cell.ILayer {
    public final UnitType type;
    private Player owner;
    private int health;
    private int ep;
    
    public Unit(Player owner, UnitType type) {
        this.owner = owner;
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
