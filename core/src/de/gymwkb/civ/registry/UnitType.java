package de.gymwkb.civ.registry;

public enum UnitType {
    TEST(Hexture.UNIT_BASE, 1, 100, 10, 10, 1);
    
    private UnitType(Hexture hexture, int movementRange, int maxHealth, int strength, int defence, int attackRange) {
        this.hexture = hexture;
        this.movementRange = movementRange;
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.defence = defence;
        this.attackRange = attackRange;
    }
    
    public final Hexture hexture;
    public final int movementRange;
    public final int maxHealth;
    public final int strength;
    public final int defence;
    public final int attackRange;
}
