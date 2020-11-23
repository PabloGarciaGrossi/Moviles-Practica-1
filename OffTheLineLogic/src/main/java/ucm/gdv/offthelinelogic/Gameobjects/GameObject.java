package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class GameObject {
    public GameObject(float x, float y, float size, String color)
    {
        _x = x;
        _y = y;
        _color = color;
        _size = size;
    }
    public void render(Engine e){

    };
    public void update (double deltaTime){};

    float _x;
    float _y;
    float _size;
    String _color;
}
