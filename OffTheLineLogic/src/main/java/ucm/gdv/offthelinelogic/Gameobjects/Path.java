package ucm.gdv.offthelinelogic.Gameobjects;

import java.util.ArrayList;
import java.util.List;

import ucm.gdv.engine.Engine;

public class Path extends GameObject{
    public Path(float x, float y, String color)
    {
        super(x,y,color);
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
        _vertex.add(new Vertex(x, y));
    }

    class Vertex{
        public Vertex(float x_, float y_){ x = x_; y = y_;}
        float x;
        float y;
    }


    List<Vertex> _vertex = new ArrayList<>();
}
