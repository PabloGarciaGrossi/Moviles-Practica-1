package ucm.gdv.offthelinelogic;

public class Segment {

    public Segment(Point p1_, Point p2_){
        p1 = p1_;
        p2 = p2_;
    }

    public float getDistance()
    {
        return Utils.sqrDistancePointPoint(p2, p1);
    }
    public Point p1;
    public Point p2;
}
