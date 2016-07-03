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
        if(path != null) {
            path.clear();
            return dfs(start, destination, path);
        } else return dfs(start, destination);
    }
    
    private boolean dfs(Hex hex, Hex destination) {
        visitedCells.add(hex);
        if(hex.equals(destination))
            return true;
        
        for(int i = 0; i < NEIGHBORS.length; i++) {
            Hex neighbor = hex.add(NEIGHBORS[i]);
            if(map.contains(neighbor) && canMoveTo(neighbor, destination) && !isVisited(neighbor)) {
                if(dfs(neighbor, destination)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    private boolean dfs(Hex hex, Hex destination, Array<Hex> path) {
        visitedCells.add(hex);
        path.add(hex);
        if(hex.equals(destination))
            return true;
        
        for(int i = 0; i < NEIGHBORS.length; i++) {
            Hex neighbor = hex.add(NEIGHBORS[i]);
            if(map.contains(neighbor) && canMoveTo(neighbor, destination) && !isVisited(neighbor)) {
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
    
    private boolean canMoveTo(Hex hex, Hex dest) {
        return (map.getUnit(hex) == null || hex.equals(dest)) && !map.getTerrain(hex).equals(Terrain.ROCKS);
    }

}
