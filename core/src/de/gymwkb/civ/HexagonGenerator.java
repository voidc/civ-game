package de.gymwkb.civ;

import de.gymwkb.civ.HexMap.Cell;

public class HexagonGenerator {
    private static final int SIZE = 100;

    public HexMap generateMap() {
        HexMap map = new HexMap();
        Hex init = new Hex(0, 0, 0);
        Cell initCell = new Cell();
        map.addCell(init, initCell);
        //generate map
        
        // FIXME kein return-statement
    }

    // FIXME Methodenname verstößt gegen Namensfestlegungen
    private Hex[] N(Hex hex) {
        Hex[] N = new Hex[6];
        for(int i = 0; i < 6; i++) {
            N[i] = hex.add(HexMap.NEIGHBORS[i]);
        }
        return N;
    }

    public void genNextTo(Hex h) {
        // FIXME init?
        if (N(init)[0] == null) {
            Hex newHex = new Hex(init.getP() + 1, init.getQ(), init.getS());
            init.addN(newHex, 0);
        }
    }

    public void tryToGenerate(Hex h, int hexNumber /* an Namensfestlegungen angepasst */) {
        if (h.getN(/* FIXME kein Attribut */)[hexNumber] == null) {
            int oldQ = h.q;
            int oldR = h.r; // kein p vorhanden sondern r
            int oldS = h.s;

            // Name von cords in coords geändert
            int[] coords = new int[3];
            Hex newHex;
            switch (hexNumber) {
                case 0:
                    // FIXME
                    // coords = {oldP + 1, oldQ, -OldQ - OldR}; Nicht möglich
                    // stattdessen:
                    // coords[0] = ;
                    // coords[1] = ;
                    // coords[2] = ;
                    break;
            }

        }
    }
}
