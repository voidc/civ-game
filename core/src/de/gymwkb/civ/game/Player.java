package de.gymwkb.civ.game;

import com.badlogic.gdx.utils.Array;
import de.gymwkb.civ.registry.Resource;

/**
 * Contains the state of one player no matter if human or remote. (no logic, only model)
 */
public class Player {
    public final int id;
    private Array<Unit> units;
    private int[] resources;
    
    public Player(int id) {
        this.id = id;
        units = new Array<Unit>();
        resources = new int[Resource.COUNT];
    }
    
    public void addUnit(Unit unit) {
        units.add(unit);
    }
    
    public int getResource(Resource resource) {
        return resources[resource.ordinal()];
    }
    
    public void setResource(Resource resource, int amount) {
        resources[resource.ordinal()] = amount;
    }

    public Iterable<Unit> getUnits() {
        return units;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player && ((Player) obj).id == id;
    }
}
