package ucm.gdv.offthelinelogic.Gameobjects;

import java.util.ArrayList;
import java.util.List;

import ucm.gdv.engine.Engine;

public class Path extends GameObject{
    public Path(String color)
    {
        super(0,0,0,color);
    }

    public void render (Engine e){
        e.getGraphics().setColor(_color);
        for (int i = 0; i < _vertex.size() - 1; i++) {
            e.getGraphics().drawLine(_vertex.get(i).x, _vertex.get(i).y, _vertex.get(i+1).x, _vertex.get(i+1).y);
            directions.add(new Vertex(_vertex.get(i+1).y - _vertex.get(i).y, -(_vertex.get(i+1).x - _vertex.get(i).x)));
        }
        e.getGraphics().drawLine(_vertex.get(_vertex.size()-1).x, _vertex.get(_vertex.size()-1).y, _vertex.get(0).x, _vertex.get(0).y);
        directions.add(new Vertex(_vertex.get(_vertex.size()-1).y - _vertex.get(0).y, -(_vertex.get(_vertex.size()-1).x - _vertex.get(0).x)));
    }

    public void update (double deltaTime){

    }

    public void addVertex(float x, float y){
        _vertex.add(new Vertex(x, y));
    }

    class Vertex{
        public Vertex(float x_, float y_){ x = x_; y = y_;}
        float x;
        float y;
    }


    List<Vertex> _vertex = new ArrayList<>();
    List<Vertex> directions = new ArrayList<>();
}
