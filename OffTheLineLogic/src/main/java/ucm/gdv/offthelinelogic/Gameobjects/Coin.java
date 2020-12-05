package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class Coin extends GameObject{
    public Coin(float x, float y, int color,float size){
        super(x,y,size,color);
        _tag = "Coin";
    }

    @Override
    public void render(Engine e) {
        super.render(e);

        double b = Math.toRadians(_angle);
        float s = (float) Math.sin(b);
        float c = (float) Math.cos(b);
        e.getGraphics().setColor(_color);
        e.getGraphics().save();
        e.getGraphics().translate(c * ((p.x - _radius) - p.x) - s * ((p.y - _radius) - p.y) + p.x, s * ((p.x - _radius) - p.x) + c * ((p.y - _radius) - p.y) + p.y);
        e.getGraphics().rotate(-_angle);

        e.getGraphics().drawLine(-_size / 2, -_size / 2, _size / 2, -_size / 2);
        e.getGraphics().drawLine(_size / 2, -_size / 2, _size / 2, _size / 2);
        e.getGraphics().drawLine(_size / 2, _size / 2, -_size / 2, _size / 2);
        e.getGraphics().drawLine(-_size / 2, _size / 2, -_size / 2, -_size / 2);
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

        if(initDeath){
            //_radius += 1;
            _size += 50 * (float)deltaTime;
            deadCD += deltaTime;
            if(deadCD >= 0.5f)
                dead = true;
        }
    }


    public void OnCollision(GameObject other){
        super.OnCollision(other);
        if(other._tag=="Player") initDeath();
    }

    public void initDeath(){initDeath = true;}

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

    public boolean isDead(){return dead;}
    float _speed;
    float _radius;
    float _angle;

    boolean initDeath = false;
    boolean dead = false;
    float deadCD = 0f;
    Player _pl;
}
