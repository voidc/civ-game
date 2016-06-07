package de.gymwkb.civ.hexutils;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import de.gymwkb.civ.Hex;

public class Layout {
    public static Orientation pointy = new Orientation((float) Math.sqrt(3.0), (float) Math.sqrt(3.0) / 2.0f, 0.0f,
            3.0f / 2.0f, (float) Math.sqrt(3.0) / 3.0f, -1.0f / 3.0f, 0.0f, 2.0f / 3.0f, 0.5f);
    public static Orientation flat = new Orientation(3.0f / 2.0f, 0.0f, (float) Math.sqrt(3.0) / 2.0f,
            (float) Math.sqrt(3.0), 2.0f / 3.0f, 0.0f, -1.0f / 3.0f, (float) Math.sqrt(3.0) / 3.0f, 0.0f);
    public final Orientation orientation;
    public final Vector2 size;
    public final Vector2 origin;

    public Layout(Orientation orientation, Vector2 size, Vector2 origin) {
        this.orientation = orientation;
        this.size = size;
        this.origin = origin;
    }

    public static Vector2 hexToPixel(Layout layout, Hex h) {
        Orientation M = layout.orientation;
        Vector2 size = layout.size;
        Vector2 origin = layout.origin;
        float x = (M.f0 * h.q + M.f1 * h.r) * size.x;
        float y = (M.f2 * h.q + M.f3 * h.r) * size.y;
        return new Vector2(x + origin.x, y + origin.y);
    }


    public static FractionalHex pixelToHex(Layout layout, Vector2 p) {
        Orientation M = layout.orientation;
        Vector2 size = layout.size;
        Vector2 origin = layout.origin;
        Vector2 pt = new Vector2((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);
        double q = M.b0 * pt.x + M.b1 * pt.y;
        double r = M.b2 * pt.x + M.b3 * pt.y;
        return new FractionalHex(q, r, -q - r);
    }


    public static Vector2 hexCornerOffset(Layout layout, int corner) {
        Orientation M = layout.orientation;
        Vector2 size = layout.size;
        float angle = 2.0f * MathUtils.PI * (corner + M.start_angle) / 6;
        return new Vector2(size.x * MathUtils.cos(angle), size.y * MathUtils.sin(angle));
    }


    public static ArrayList<Vector2> polygonCorners(Layout layout, Hex h) {
        ArrayList<Vector2> corners = new ArrayList<Vector2>() {
            {
            }
        };
        Vector2 center = Layout.hexToPixel(layout, h);
        for (int i = 0; i < 6; i++) {
            Vector2 offset = Layout.hexCornerOffset(layout, i);
            corners.add(new Vector2(center.x + offset.x, center.y + offset.y));
        }
        return corners;
    }

    public static class Orientation {
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
