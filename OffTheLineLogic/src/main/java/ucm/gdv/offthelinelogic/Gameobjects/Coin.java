package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class Coin extends GameObject{
    public Coin(float x, float y, String color,float size,float radius, float speed, float angle){
        super(x,y,size,color);
        _speed=speed;
        _radius = radius;
        _angle = angle;
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
        e.getGraphics().translate(_x, _y);
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
        _angle += 250 * deltaTime;
    }

    float _speed;
    float _radius;
    float _angle;
}
