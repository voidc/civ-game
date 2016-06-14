package de.gymwkb.civ.game;

import com.badlogic.gdx.utils.Array;

/**
 * Contains the state of one player no matter if human or remote. (no logic, only model)
 */
public class Player {
    public final int id;
    private Array<Unit> units;
    
    public Player(int id) {
        this.id = id;
        units = new Array<Unit>();
    }
    
    public void addUnit(Unit unit) {
        units.add(unit);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player && ((Player) obj).id == id;
    }
}
