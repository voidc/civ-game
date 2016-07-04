package de.gymwkb.civ.game.ai;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.Queue.QueueIterator;

import de.gymwkb.civ.map.Hex;
import de.gymwkb.civ.map.HexMap;
import de.gymwkb.civ.registry.Terrain;

import static de.gymwkb.civ.map.HexMap.NEIGHBORS;

public class AStarPathfinder implements Pathfinder{
    private HexMap map;
    public Terrain[] blockedFields = {Terrain.ROCKS};
    Array<Terrain> unpassable = new Array<Terrain>();
    
    public AStarPathfinder(HexMap map){
        this.map = map;
        unpassable.addAll(blockedFields);
    }

    @Override
    public boolean findPath(Array<Hex> path, Hex start, Hex destination) {
        Queue<Hex>  openList = new Queue<Hex>();
        Queue<Hex> closedList= new Queue<Hex>();
        start.f = 0;
        start.parent = null;
        start.g = 0;
        openList.addFirst(start);
        while(!(openList.size == 0)){
            Hex q = getFMin(openList);
            openList.removeValue(q, false);
            Array<Hex> N = getN(q);
            while(N.size != 0){
                Hex successor = N.pop();
                if(successor.equals(destination)){
                    path.add(destination);
                    return true;
                }
                successor.parent = q;
                successor.g = q.g + ManhattanDistance(successor, q);
                successor.h = ManhattanDistance(destination, successor);
                successor.f = successor.g + successor.h;
                closedList.addLast(successor);
            }
            getRoute(start, destination, path);
        }
        return false;
    }
    private boolean canMoveTo(Hex hex, Hex dest) {
        return (map.getUnit(hex) == null || hex.equals(dest)) && !(unpassable.contains(map.getTerrain(hex),false));
    }
    
    public Hex getFMin (Queue<Hex> Queue){
        Hex min = null;
        float minF = Float.POSITIVE_INFINITY;
        QueueIterator<Hex> it = new QueueIterator<Hex>(Queue);
        while(it.hasNext()){
            Hex toCheck = it.next();
            if(toCheck.f < minF){
                minF = toCheck.f;
                min = toCheck;
            }
        }
        return min;
    }
    
    public Array<Hex> getN(Hex h){
        Array<Hex> rtn = new Array<Hex>();
        for(int i = 0; i < NEIGHBORS.length; i++) {
            Hex neighbor = h.add(NEIGHBORS[i]);
            if(map.contains(neighbor) && canMoveTo(neighbor, h)) {
            rtn.add(neighbor);
            }
        }
        return rtn;
    }
    
    public float ManhattanDistance(Hex a, Hex b){
        return ((Math.abs(a.q - b.q) + Math.abs(a.r - b.r) + Math.abs(a.s - b.s)) / 2);
    }
    
    public boolean checkIfInListWithLowerF(Queue<Hex> Queue,Hex actualHex, float actualF){
        QueueIterator<Hex> it = new QueueIterator<Hex>(Queue);
        while(it.hasNext()){
            Hex toCheck = it.next();
            if(toCheck.equals(actualHex) && toCheck.f < actualF){
                return true;
            }
        }
        return false;
    }
    
    public void getRoute(Hex start, Hex destination, Array<Hex> Queue){
        Queue.clear();
        Hex currentHex = destination;
        boolean finished = false;
        while(!finished && currentHex != null){
            if(currentHex != null && currentHex.equals(start)){
                Queue.add(start);
                finished = true;
            }
            else{
                Queue.add(currentHex.parent);
                currentHex = currentHex.parent;
            }
        }
    }
}
