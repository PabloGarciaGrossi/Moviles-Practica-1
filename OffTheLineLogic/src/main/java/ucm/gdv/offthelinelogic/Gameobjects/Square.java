package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class Square extends GameObject{
    public Square (int x, int y, int size, String color, float speed){
        super(x,y,size,color);
        _speed=speed;
    }

    @Override
    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);
        e.getGraphics().fillRect((int)_x, (int)_y, _size, _size);
    }



    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        _x+=_speed*deltaTime;
    }

    float _speed;
}
