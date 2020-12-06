package ucm.gdv.offthelinelogic;

public class Utils {

    //Calcula si dos segmentos formados por esos 4 puntos se cortan en algún punto y devuelve el punto de corte
    static public Point segmentCollition(Point a, Point b, Point c, Point d){
        Point v1 = new Point(b.x - a.x, b.y- a.y);
        Point v2 = new Point(d.x - c.x, d.y- c.y);

        float s = (c.y - a.y)*v2.x*v1.x + a.x*v1.y*v2.x-c.x*v2.y*v1.x;
        float x = (s)/(v1.y*v2.x - v2.y*v1.x);
        float y;
        if(v1.x == 0) {
            y = ((x * v2.y - c.x * v2.y) / v2.x) + c.y;
        }
        else {
            y = ((x * v1.y - a.x * v1.y) / v1.x) + a.y;
        }

        if(x > 2000 || x < -2000 || y > 2000 || y < -2000) {
            return null;
        }

        Point corte = new Point(x, y);

        if(areEqual(corte, a) || areEqual(corte, b) || areEqual(corte, c) || areEqual(corte, d)) {
            return null;
        }
        if(insideSegment(corte, a, b) && insideSegment( corte, c, d)){
            return corte;
        }

        return null;
    }

    //Comprueba que el punto p se encuentre dentro del segmento formado por a y b
    static boolean insideSegment(Point p, Point a, Point b){
        Point v1 = new Point(b.x - a.x, b.y- a.y);
        Point v2 = new Point(b.x - p.x, b.y- p.y);

        return vectorDistance(v1) > vectorDistance(v2) && v1.x*v2.x >= 0 && v1.y*v2.y >= 0;
    }

    //Devuelve la distancia de un punto a un sesgmento, no lo hemos utilizado así que se podría borrar
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

    //Devuelve la distancia entre dos puntos
    public static float sqrDistancePointPoint(Point p1, Point p2){
        float ret = 0.0f;
        ret = (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        return ret;
    }

    //Devuelve el vector unitario de s
    public static Point normalize(Segment s)
    {
        Point d = new Point(s.p2.x - s.p1.x, s.p2.y - s.p1.y);
        float a = (float) Math.sqrt(Math.abs((d.x * d.x)) + Math.abs((d.y * d.y)));
        d.x = d.x / a;
        d.y = d.y/ a;

        return d;
    }

    //Devuelve la distancia del vector p
    public static float vectorDistance(Point p)
    {
        float a = (float) Math.sqrt((p.x * p.x)+ (p.y * p.y));
        return a;
    }

    //Devuelve el vector unitario del vector d
    public static Point normalize(Point d)
    {
        float a = (float) Math.sqrt(Math.abs((d.x * d.x)) + Math.abs((d.y * d.y)));
        d.x = d.x / a;
        d.y = d.y/ a;

        return d;
    }

    //Comprueba que dos puntos sean iguales
    public static boolean areEqual(Point p1, Point p2){
        boolean b1 = p1.x == p2.x;
        boolean b2 = p1.y == p2.y;

        return  b1 && b2;
    }

    //Multiplica un vector por un valor determinado
    public static Point multVector(Point p, float n){
        Point np;
        np = new Point(p.x * n, p.y *n);
        return  np;
    }

    //Devuelve la normal en un sentido u otro dependiendo de la dirección en la que se avance por el segmento
    public static Point getNormal(Segment s, boolean counterclock){
        if(!counterclock)
            return Utils.normalize(new Point((s.p2.y - s.p1.y), -(s.p2.x - s.p1.x)));
        else
            return Utils.normalize(new Point(-(s.p2.y - s.p1.y), (s.p2.x - s.p1.x)));
    }
}
