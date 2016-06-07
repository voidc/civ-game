package de.gymwkb.civ;

import de.gymwkb.civ.HexMap.Cell;

public class HexagonGenerator {
    private static final int SIZE = 100;

    public HexMap generateMap() {
        HexMap map = new HexMap();
        Hex init = new Hex(0, 0, 0);
        Cell initCell = new Cell();
        map.addCell(init, initCell);
        // generate map
    }

    private Hex[] N(Hex hex) {
        Hex[] N = new Hex[6];
        for (int i = 0; i < 6; i++) {
            N[i] = hex.add(HexMap.NEIGHBORS[i]);
        }
        return N;
    }

    public void genNextTo(Hex h) {
        if (N(init)[0] == null) {
            Hex newHex = new Hex(init.getP() + 1, init.getQ(), init.getS());
            init.addN(newHex, 0);
        }
    }

  public void tryToGenerate(Hex h, int Ind){
		if(h.getN()[Ind]==null){
			int oldP = h.getP();
			int oldQ = h.getQ();
			int oldS = h.getS();
			
			int[] cords = new int[3];
			Hex newHex;
			switch Ind{
			case 0: cords = {oldP+1,oldQ,-OldQ-OldR};break;
			
			
			}
				
			}
			}
}}}
