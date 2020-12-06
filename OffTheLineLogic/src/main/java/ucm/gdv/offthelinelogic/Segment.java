package ucm.gdv.offthelinelogic;

public class Segment {

    public Segment(Point p1_, Point p2_){
        p1 = p1_;
        p2 = p2_;
    }

    //Devuleve la distancia del segmento
    public float getDistance()
    {
        return Utils.sqrDistancePointPoint(p2, p1);
    }

    //Los dos puntos que conforman dicho segmento
    public Point p1;
    public Point p2;
}
