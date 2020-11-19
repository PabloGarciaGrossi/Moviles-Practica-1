package ucm.gdv.offthelinelogic.Gameobjects;

public class GameObject {
    public GameObject(int x, int y, int size, String color)
    {
        _x = x;
        _y = y;
        _size = size;
        _color = color;
    }
    public void render(){};
    public void update (){};

    int _x;
    int _y;
    int _size;
    String _color;
}
