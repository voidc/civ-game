package de.gymwkb.civ.game.ai;

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
    public boolean findPath(Array<Hex> path, Hex start, Hex destination, int length) {
        visitedCells.clear();
        if(path != null) {
            path.clear();
            return dfs(start, destination, path, length);
        } else return dfs(start, destination, 0, length);
    }
    
    private boolean dfs(Hex hex, Hex destination, int depth, int maxLength) {
        visitedCells.add(hex);
        depth++;
        
        if(hex.equals(destination))
            return true;
        
        if(depth > maxLength) {
            depth--;
            return false;
        }
        
        Array<Hex> neighbors = new Array<Hex>();
        
        for(int i = 0; i < NEIGHBORS.length; i++) {     
            neighbors.add(hex.add(NEIGHBORS[i]));
        }
        
        neighbors.sort((a, b) -> b.mhDist(destination) - a.mhDist(destination));
        
        while(neighbors.size > 0) {
            Hex n = neighbors.pop();
            if(map.contains(n) && canMoveTo(n, destination) && !isVisited(n)) {
                if(dfs(n, destination, depth, maxLength)) {
                    return true;
                }
            }
        }
        
        depth--;
        return false;
    }

    private boolean dfs(Hex hex, Hex destination, Array<Hex> path, int maxLength) {
        visitedCells.add(hex);
        path.add(hex);
        
        if(hex.equals(destination))
            return true;
        
        if(path.size > maxLength) {
            path.pop();
            return false;
        }
        
        Array<Hex> neighbors = new Array<Hex>();
        
        for(int i = 0; i < NEIGHBORS.length; i++) {     
            neighbors.add(hex.add(NEIGHBORS[i]));
        }
        
        neighbors.sort((a, b) -> b.mhDist(destination) - a.mhDist(destination));
        
        while(neighbors.size > 0) {
            Hex n = neighbors.pop();
            if(map.contains(n) && canMoveTo(n, destination) && !isVisited(n)) {
                if(dfs(n, destination, path, maxLength)) {
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
