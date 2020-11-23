package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class GameObject {
    public GameObject(float x, float y, String color)
    {
        _x = x;
        _y = y;
        _color = color;
    }
    public void render(Engine e){

    };
    public void update (double deltaTime){};

    float _x;
    float _y;
    String _color;
}
