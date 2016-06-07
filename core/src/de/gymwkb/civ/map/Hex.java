package de.gymwkb.civ.map;

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

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + q;
        result = 31 * result + r;
        result = 31 * result + s;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hex other = (Hex) obj;
        if (q != other.q)
            return false;
        if (r != other.r)
            return false;
        if (s != other.s)
            return false;
        return true;
    }
    
    

}
