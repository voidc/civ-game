package de.gymwkb.civ.game;

import com.badlogic.gdx.utils.Array;

import de.gymwkb.civ.map.Hex;

public interface Pathfinder {
    /**
     * @return An Array containing the hex vertices of the path if one exists, null otherwise
     */
    Array<Hex> findPath(Hex start, Hex destination);
}
