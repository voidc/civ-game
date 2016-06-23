package de.gymwkb.civ.registry;

public enum UnitType {
    TEST(Hexture.UNIT_BASE, 1, 100, 10, 10, 1, 1),
    ARCHER(Hexture.UNIT_BASE, 3, 100, 10, 10, 3, 3),
    PIKEMAN(Hexture.UNIT_BASE, 1, 100, 10, 10, 1, 1),
    LIGHT_CAVALRY(Hexture.UNIT_BASE, 3, 100, 10, 10, 1, 2),
    HEAVY_CAVALRY(Hexture.UNIT_BASE, 1, 100, 10, 10, 1, 2),
    SWORDSMAN(Hexture.UNIT_BASE, 1, 100, 10, 10, 1, 1);

    private UnitType(Hexture hexture, int movementRange, int maxHealth, int strength, int defence, int attackRange,
            int viewRange) {
        this.hexture = hexture;
        this.movementRange = movementRange;
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.defence = defence;
        this.attackRange = attackRange;
        this.viewRange = viewRange;
    }

    public final Hexture hexture;
    public final int movementRange;
    public final float maxHealth;
    public final float strength;
    public final float defence;
    public final int attackRange;
    public final int viewRange;
}
