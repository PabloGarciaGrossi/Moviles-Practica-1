package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class Coin extends GameObject{
    public Coin(int x, int y, int size, String color, float speed, float radius, float angle){
        super(x,y,size,color);
        _speed=speed;
        _radius = radius;
        _angle = angle;
    }

    @Override
    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);
        e.getGraphics().drawLine((int)_x - _size/2, (int)_y -_size/2, (int)_x + _size/2, (int)_y - _size/2);
        e.getGraphics().drawLine((int)_x + _size/2, (int)_y -_size/2, (int)_x + _size/2, (int)_y + _size/2);
        e.getGraphics().drawLine((int)_x + _size/2, (int)_y +_size/2, (int)_x - _size/2, (int)_y + _size/2);
        e.getGraphics().drawLine((int)_x - _size/2, (int)_y +_size/2, (int)_x - _size/2, (int)_y - _size/2);
    }



    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        //_x+=_speed*deltaTime;
    }

    float _speed;
    float _radius;
    float _angle;
}
