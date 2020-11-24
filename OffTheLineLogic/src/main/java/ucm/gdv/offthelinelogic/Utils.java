package ucm.gdv.offthelinelogic;

public class Utils {

    public static Point segmentsIntersection(Segment s1, Segment s2){
        Point p = null;
        float s1_x, s1_y, s2_x, s2_y;
        s1_x = s1.p2.x - s1.p1.x;     s1_y = s1.p2.y - s1.p1.y;
        s2_x = s2.p2.x - s2.p1.x;     s2_y = s2.p2.y - s2.p1.y;

        float s, t;
        s = (-s1_y * (s1.p1.x - s2.p1.x) + s1_x * (s1.p1.y - s2.p1.y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = ( s2_x * (s1.p1.y - s2.p1.y) - s2_y * (s1.p1.y - s2.p1.y)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
        {
            // Collision detected
            p.x = s1.p1.x + (t * s1_x);
            p.y = s1.p1.y + (t * s1_y);
        }
        return p;
    }

    public static float sqrDistancePointSegment(Segment seg, Point p){
        float A = p.x - seg.p1.x; // position of point rel one end of line
        float B = p.y - seg.p1.y;
        float C = seg.p2.x - seg.p1.x; // vector along line
        float D = seg.p2.y - seg.p1.y;
        float E = -D; // orthogonal vector
        float F = C;

        float dot = A * E + B * F;
        float len_sq = E * E + F * F;

        return (float) (Math.abs(dot) / Math.sqrt(len_sq));
    }

    public static float sqrDistancePointPoint(Point p1, Point p2){
        float ret = 0.0f;
        ret = (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        return ret;
    }

    public static Point normalize(Segment s)
    {
        Point d = new Point(s.p2.x - s.p1.x, s.p2.y - s.p1.y);
        float a = (float) Math.sqrt(Math.abs((d.x * d.x)) + Math.abs((d.y * d.y)));
        d.x = d.x / a;
        d.y = d.y/ a;

        return d;
    }

    public static Point normalize(Point d)
    {
        float a = (float) Math.sqrt(Math.abs((d.x * d.x)) + Math.abs((d.y * d.y)));
        d.x = d.x / a;
        d.y = d.y/ a;

        return d;
    }
}
