package de.gymwkb.civ.registry;

public enum UnitType {
    TEST("Test", Hexture.UNIT_BASE, 5, 100, 10, 10, 1, 1),
    ARCHER("Bogenschütze", Hexture.UNIT_BASE, 5, 100, 10, 10, 3, 3),
    PIKEMAN("Pikenier", Hexture.UNIT_BASE, 3, 100, 10, 10, 1, 1),
    LIGHT_CAVALRY("Leichte Kavallerie", Hexture.UNIT_BASE, 4, 100, 10, 10, 1, 2),
    HEAVY_CAVALRY("Schwere Kavallerie", Hexture.UNIT_BASE, 2, 100, 10, 10, 1, 2),
    SWORDSMAN("Schwetkämpfer", Hexture.UNIT_BASE, 3, 100, 10, 10, 1, 1);
    
    public final String name;
    public final Hexture hexture;
    public final int movementRange;
    public final float maxHealth;
    public final float strength;
    public final float defence;
    public final int attackRange;
    public final int viewRange;
    
    public static final int COUNT = UnitType.values().length;

    private UnitType(String name, Hexture hexture, int movementRange, int maxHealth, int strength,
            int defence, int attackRange, int viewRange) {
        this.name = name;
        this.hexture = hexture;
        this.movementRange = movementRange;
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.defence = defence;
        this.attackRange = attackRange;
        this.viewRange = viewRange;
    }
}
