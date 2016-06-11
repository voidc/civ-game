package de.gymwkb.civ.map;

import java.util.Random;

import de.gymwkb.civ.map.HexMap.Cell;
import de.gymwkb.civ.map.HexMap.LayerType;
import de.gymwkb.civ.registry.Hexture;

public class HexagonGenerator {
    public static final int SIZE = 100;

    public HexMap generateMap(int size, boolean branch) {
        HexMap map = new HexMap();

        Hex startHex = new Hex(0, 0, 0);
        Cell startCell = generateCell();
        map.addCell(startHex, startCell);

        Random random = new Random();

        for (int i = 1; i < size; i++) {
            for (int tries = 0; tries < i; tries++) {

                int r;
                
                if (branch) {
                    // Verzweigung wahrscheinlich:
                    int x = random.nextInt(i); // 0
                    float y = random.nextFloat(); // 0-1
                    r = Math.min(i - (int) Math.ceil(Math.pow(x, y)), i - 1);
                } else {
                    // Verzweigung unwahrscheinlich:
                    r = random.nextInt(i);
                }

                Hex hex = map.getHexAt(r);
                Hex newHex = null;

                int[] neighborIndexes = {0, 1, 2, 3, 4, 5};

                shuffleArray(random, neighborIndexes);

                for (int j = 0; j < neighborIndexes.length; j++) {
                    Hex neighbor = hex.add(HexMap.NEIGHBORS[neighborIndexes[j]]);

                    if (!map.contains(neighbor)) {
                        newHex = neighbor;
                        break;
                    }
                }

                if (newHex != null) {
                    map.addCell(newHex, generateCell());
                    break;
                }
            }
        }

        return map;
    }

    public Cell generateCell() {
        Terrain defaultTerrain = new Terrain(Hexture.TERRAIN_DEFAULT);
        Cell c = new Cell();
        c.setLayer(LayerType.TERRAIN, defaultTerrain);
        return c;
    }

    private static void shuffleArray(Random rnd, int[] ar) {
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
