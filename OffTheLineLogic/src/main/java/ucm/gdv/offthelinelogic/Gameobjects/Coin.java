package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class Coin extends GameObject{
    public Coin(float x, float y, String color,float radius, float speed, float angle){
        super(x,y,color);
        _speed=speed;
        _radius = radius;
        _angle = angle;
    }

    @Override
    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);
        e.getGraphics().drawLine(_x - _radius/2, _y -_radius/2, _x + _radius/2, _y - _radius/2);
        e.getGraphics().drawLine(_x + _radius/2, _y -_radius/2, _x + _radius/2, _y + _radius/2);
        e.getGraphics().drawLine(_x + _radius/2, _y +_radius/2, _x - _radius/2, _y + _radius/2);
        e.getGraphics().drawLine(_x - _radius/2, _y +_radius/2, _x - _radius/2, _y - _radius/2);
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
