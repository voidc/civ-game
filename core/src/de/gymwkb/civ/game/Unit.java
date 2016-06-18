package de.gymwkb.civ.game;

import com.badlogic.gdx.graphics.Color;

import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.registry.Hexture;
import de.gymwkb.civ.registry.UnitType;

public class Unit implements HexMap.Cell.ILayer {
    public final UnitType type;
    private Player owner;
    private int health;
    private int ep;
    
    private static final Color[] COLORS = new Color[]{
            new Color(0xff0000ff),
            new Color(0xffff00ff),
            new Color(0x00ff00ff),
            new Color(0x00ffffff),
            new Color(0x0000ffff),
            new Color(0xff00ffff)
    };
    
    public Unit(Player owner, UnitType type) {
        this.owner = owner;
        this.type = type;
    }

    @Override
    public Hexture getHexture() {
        return type.hexture;
    }
    
    @Override
    public Color getTint() {
        return COLORS[owner.id % COLORS.length];
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
