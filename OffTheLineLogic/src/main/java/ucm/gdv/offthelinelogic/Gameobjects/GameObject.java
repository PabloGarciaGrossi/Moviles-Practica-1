package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;
import ucm.gdv.offthelinelogic.Point;


public class GameObject {
    public GameObject(float x, float y, float size, int color)
    {
        p = new Point(x,y);
        _color = color;
        _size = size;
    }
    public void render(Engine e){

    };
    public void update (double deltaTime){};

    public void handleInput(Engine e){};

    public Point getPos(){return p;}

    public void setPos(Point pos){p = pos;}

    public float getSize(){return _size;}

    Point p;
    float _size;
    int _color;
}
