package de.gymwkb.civ.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.registry.Hexture;
import de.gymwkb.civ.registry.UnitType;

public class Unit implements HexMap.Cell.ILayer {
    public final UnitType type;
    private int ownerId;
    private float health;
    private float ep;
    
    public static final float EP_PER_LEVEL = 100f;
    
    private static final Color[] COLORS = new Color[]{
            new Color(0xff0000ff),
            new Color(0xffff00ff),
            new Color(0x00ff00ff),
            new Color(0x00ffffff),
            new Color(0x0000ffff),
            new Color(0xff00ffff)
    };
    
    public Unit(int ownerId, UnitType type) {
        this.ownerId = ownerId;
        this.type = type;
        this.health = type.maxHealth;
        this.ep = 0f;
    }

    @Override
    public Hexture getHexture() {
        return type.hexture;
    }
    
    @Override
    public Color getTint() {
        return COLORS[ownerId % COLORS.length];
    }
    
    public int getOwnerId() {
        return ownerId;
    }
    
    public float getHealth() {
        return health;
    }
    
    public float getHealthPercentage() {
        return MathUtils.clamp(health / type.maxHealth, 0, 1);
    }
    
    public void modHealth(float amount) {
        health = MathUtils.clamp(health + amount, 0f, type.maxHealth);
    }
    
    public int getLevel() {
        return MathUtils.floor(ep / EP_PER_LEVEL) + 1;
    }
    
    public float getLevelProgress() {
        return (ep % EP_PER_LEVEL) / EP_PER_LEVEL;
    }
    
    public void addEp(float amount) {
        ep += Math.abs(amount);
    }
    
}
