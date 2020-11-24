package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;
import ucm.gdv.offthelinelogic.Point;

public class GameObject {
    public GameObject(float x, float y, float size, String color)
    {
        p = new Point(x,y);
        _color = color;
        _size = size;
    }
    public void render(Engine e){

    };
    public void update (double deltaTime){};

    Point p;
    float _size;
    String _color;
}
