package de.gymwkb.civ.game;

import com.badlogic.gdx.utils.Array;
import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.registry.Terrain;

import static de.gymwkb.civ.map.HexMap.NEIGHBORS;

public class DFSPathfinder implements Pathfinder {
    private HexMap map;
    private Array<Hex> visitedCells;
    
    public DFSPathfinder(HexMap map) {
        this.map = map;
        visitedCells = new Array<>();
    }

    @Override
    public boolean findPath(Array<Hex> path, Hex start, Hex destination) {
        visitedCells.clear();
        path.clear();
        return dfs(start, destination, path);
    }
    
    private boolean dfs(Hex hex, Hex destination, Array<Hex> path) {
        visitedCells.add(hex);
        path.add(hex);
        if(hex.equals(destination))
            return true;
        
        for(int i = 0; i < NEIGHBORS.length; i++) {
            Hex neighbor = hex.add(NEIGHBORS[i]);
            if(map.contains(neighbor) && canMoveTo(neighbor) && !isVisited(neighbor)) {
                if(dfs(neighbor, destination, path)) {
                    return true;
                }
            }
        }
        
        path.pop();
        return false;
    }
    
    private boolean isVisited(Hex hex) {
        return visitedCells.contains(hex, false);
    }
    
    private boolean canMoveTo(Hex hex) {
        return map.getUnit(hex) == null && !map.getTerrain(hex).equals(Terrain.ROCKS);
    }

}
