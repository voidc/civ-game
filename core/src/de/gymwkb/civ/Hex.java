package de.gymwkb.civ;

public class Hex {
    public final int q;
    public final int r;
    public final int s;

    public Hex(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public Hex add(Hex other) {
        return new Hex(q + other.q, r + other.r, s + other.s);
    }

}
