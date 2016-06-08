package de.gymwkb.civ.map;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class HexMapLayout {
    private static final float SQRT3 = (float) Math.sqrt(3.0);
    public static final Orientation POINTY = new Orientation(SQRT3, SQRT3 / 2.0f, 0.0f, 3.0f / 2.0f, SQRT3 / 3.0f,
            -1.0f / 3.0f, 0.0f, 2.0f / 3.0f, 0.5f);
    public static final Orientation FLAT = new Orientation(3.0f / 2.0f, 0.0f, SQRT3 / 2.0f, SQRT3, 2.0f / 3.0f, 0.0f,
            -1.0f / 3.0f, SQRT3 / 3.0f, 0.0f);

    private Orientation orientation;
    private Vector2 size;
    private Vector2 origin;
    
    private final Vector2[] offsets;

    public HexMapLayout(Orientation orientation, Vector2 size, Vector2 origin) {
        this.orientation = orientation;
        this.size = size;
        this.origin = origin;
        this.offsets = new Vector2[6];
        recalculate();
    }
    
    public void recalculate() {
        for(int i = 0; i < 6; i++) {
            offsets[i] = vertexOffset(i);
        }  
    }

    public Vector2 hexToCartesian(Hex h) {
        float x = (orientation.f0 * h.q + orientation.f1 * h.r) * size.x;
        float y = (orientation.f2 * h.q + orientation.f3 * h.r) * size.y;
        return new Vector2(x + origin.x, y + origin.y);
    }

    public Hex cartesianToHex(Vector2 p) {
        Vector2 pt = new Vector2((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);
        float q = orientation.b0 * pt.x + orientation.b1 * pt.y;
        float r = orientation.b2 * pt.x + orientation.b3 * pt.y;
        return roundHex(q, r);
    }

    private Hex roundHex(float qf, float rf) {
        int q = MathUtils.round(qf);
        int r = MathUtils.round(rf);
        int s = MathUtils.round(-qf - rf);
        double q_diff = Math.abs(q - qf);
        double r_diff = Math.abs(r - rf);
        double s_diff = Math.abs(s - (-qf - rf));
        if (q_diff > r_diff && q_diff > s_diff) {
            q = -r - s;
        } else if (r_diff > s_diff) {
            r = -q - s;
        } else {
            s = -q - r;
        }
        return new Hex(q, r, s);
    }

    private Vector2 vertexOffset(int corner) {
        float angle = 2.0f * MathUtils.PI * (corner + orientation.start_angle) / 6;
        return new Vector2(size.x * MathUtils.cos(angle), size.y * MathUtils.sin(angle));
    }

    public Vector2[] getVertices(Hex h) {
        Vector2[] vertices = new Vector2[6];
        Vector2 center = hexToCartesian(h);
        for (int i = 0; i < 6; i++) {
            vertices[i] = center.add(offsets[i]);
        }
        return vertices;
    }
    
    public void getVertices(Hex h, Vector2[] buffer) {
        Vector2 center = hexToCartesian(h);
        for (int i = 0; i < 6; i++) {
            buffer[i].set(center.add(offsets[i]));
        }
    }

    private static class Orientation {
        public final float f0;
        public final float f1;
        public final float f2;
        public final float f3;
        public final float b0;
        public final float b1;
        public final float b2;
        public final float b3;
        public final float start_angle;

        public Orientation(float f0, float f1, float f2, float f3, float b0, float b1, float b2, float b3,
                float start_angle) {
            this.f0 = f0;
            this.f1 = f1;
            this.f2 = f2;
            this.f3 = f3;
            this.b0 = b0;
            this.b1 = b1;
            this.b2 = b2;
            this.b3 = b3;
            this.start_angle = start_angle;
        }
    }
}
