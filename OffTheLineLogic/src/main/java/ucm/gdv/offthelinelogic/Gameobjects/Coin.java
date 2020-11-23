package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class Coin extends GameObject{
    public Coin(float x, float y, String color,float size){
        super(x,y,size,color);
    }

    @Override
    public void render(Engine e) {
        super.render(e);
        double b = Math.toRadians(_angle);
        float s = (float) Math.sin(b);
        float c = (float) Math.cos(b);
        e.getGraphics().setColor(_color);
        /*float x1 = c * ((_x - _size/2) - _x) - s * ((_y - _size/2) -_y) + _x;
        float x2 = c * ((_x + _size/2) - _x) - s * ((_y - _size/2) - _y) + _x;
        float x3 = c * ((_x + _size/2) - _x) - s * ((_y + _size/2) - _y) + _x;
        float x4 = c * ((_x - _size/2) - _x) - s * ((_y + _size/2) - _y) + _x;

        float y1 = s * ((_x - _size/2) - _x) + c * ((_y - _size/2) - _y) + _y;
        float y2 = s * ((_x + _size/2) - _x) + c * ((_y - _size/2) - _y) + _y;
        float y3 = s * ((_x + _size/2) - _x) + c * ((_y + _size/2) - _y) + _y;
        float y4 = s * ((_x - _size/2) - _x) + c * ((_y + _size/2) - _y) + _y;
        e.getGraphics().drawLine(x1, y1, x2, y2);
        e.getGraphics().drawLine(x2, y2, x3,y3);
        e.getGraphics().drawLine(x3, y3, x4, y4);
        e.getGraphics().drawLine(x4, y4, x1, y1);*/

        e.getGraphics().save();
        e.getGraphics().translate(c * ((_x - _radius) - _x) - s * ((_y - _radius) -_y) + _x, s * ((_x - _radius) - _x) + c * ((_y - _radius) - _y) + _y);
        e.getGraphics().rotate(_angle);
        e.getGraphics().drawLine(- _size/2, - _size/2,  _size/2, - _size/2);
        e.getGraphics().drawLine(_size/2, - _size/2,  _size/2,  _size/2);
        e.getGraphics().drawLine( _size/2, _size/2,  - _size/2,  _size/2);
        e.getGraphics().drawLine(- _size/2,  _size/2, - _size/2, - _size/2);
        e.getGraphics().restore();
    }



    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        //_x+=_speed*deltaTime;
        if(_speed != 0)
            _angle += _speed * deltaTime;
        else
            _angle += 80 * deltaTime;

    }

    public void set_angle(float angle){
        _angle = angle;
    }
    public void set_speed(float speed){
        _speed = speed;
    }
    public void set_radius(float radius)
    {
        _radius = radius;
    }
    float _speed;
    float _radius;
    float _angle;
}
