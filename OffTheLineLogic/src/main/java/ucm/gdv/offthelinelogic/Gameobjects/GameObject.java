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

    public void OnCollision(GameObject other){};

    public void handleInput(Engine e){};

    public Point getPos(){return p;}

    public float getSize(){return _size;}

    Point p;
    float _size;
    String _color;
    String _tag;
}
