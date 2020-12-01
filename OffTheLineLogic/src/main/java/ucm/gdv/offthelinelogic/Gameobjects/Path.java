package ucm.gdv.offthelinelogic.Gameobjects;

import java.util.ArrayList;
import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.offthelinelogic.Point;
import ucm.gdv.offthelinelogic.Segment;
import ucm.gdv.offthelinelogic.Utils;

public class Path extends GameObject{
    public Path(String color)
    {
        super(0,0,0,color);
    }

    public void render (Engine e){
        e.getGraphics().setColor(_color);
        for (int i = 0; i < _vertex.size() - 1; i++) {
            e.getGraphics().drawLine(_vertex.get(i).x, _vertex.get(i).y, _vertex.get(i+1).x, _vertex.get(i+1).y);
        }
        e.getGraphics().drawLine(_vertex.get(_vertex.size()-1).x, _vertex.get(_vertex.size()-1).y, _vertex.get(0).x, _vertex.get(0).y);
    }

    public void update (double deltaTime){

    }

    public void addVertex(float x, float y){
        _vertex.add(new Point(x, y));
    }
    public void addDirection(float x, float y){
        _directions.add(new Point(x, y));
    }
    public void addNullDirection(){_directions.add(null);}

    public void automatizeDirections(){
        for (int i = 0; i < segments.size(); i++){
            Segment aux = segments.get(i);
            _directions.add(Utils.getNormal(aux, false));
        }
    }

    public void createSegments()
    {
        for (int i = 0; i < _vertex.size() - 1; i++) {
            Segment s = new Segment(new Point(_vertex.get(i).x, _vertex.get(i).y), new Point(_vertex.get(i+1).x, _vertex.get(i+1).y));
            segments.add(s);
        }
        Segment s = new Segment(new Point(_vertex.get(_vertex.size()-1).x, _vertex.get(_vertex.size()-1).y), new Point(_vertex.get(0).x, _vertex.get(0).y));
        segments.add(s);
    }

    public List<Segment> getSegments(){
        return segments;
    }
    List<Point> _vertex = new ArrayList<>();
    List<Point> _directions = new ArrayList<>();
    List<Segment> segments = new ArrayList<>();
}
