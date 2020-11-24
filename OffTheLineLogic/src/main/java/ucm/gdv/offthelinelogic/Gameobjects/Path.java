package ucm.gdv.offthelinelogic.Gameobjects;

import java.util.ArrayList;
import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.offthelinelogic.Point;
import ucm.gdv.offthelinelogic.Segment;

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

    public void createDirections()
    {
        for (int i = 0; i < _vertex.size() - 1; i++) {
            directions.add(new Segment(new Point(_vertex.get(i).x, _vertex.get(i).y), new Point(_vertex.get(i+1).x, _vertex.get(i+1).y)));
        }
        directions.add(new Segment(new Point(_vertex.get(_vertex.size()-1).x, _vertex.get(_vertex.size()-1).y), new Point(_vertex.get(0).x, _vertex.get(0).y)));
    }

    List<Point> _vertex = new ArrayList<>();
    List<Segment> directions = new ArrayList<>();
}
