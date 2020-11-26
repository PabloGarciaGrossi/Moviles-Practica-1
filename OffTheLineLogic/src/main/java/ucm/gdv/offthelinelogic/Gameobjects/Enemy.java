package ucm.gdv.offthelinelogic.Gameobjects;

import java.sql.Struct;

import ucm.gdv.engine.Engine;
import ucm.gdv.offthelinelogic.Point;
import ucm.gdv.offthelinelogic.Segment;

public class Enemy extends GameObject {
    public Enemy(float x, float y, float size, String color){
        super(x,y,size,color);
        _segment = new Segment(new Point(x-size/2, y), new Point(x+_size/2, y));
    }

    public void render(Engine e){
        e.getGraphics().setColor(_color);

        double b = Math.toRadians(_rotateAngle);
        float s = (float) Math.sin(b);
        float c = (float) Math.cos(b);

        e.getGraphics().save();
        e.getGraphics().translate(c * (_segment.p1.x - p.x) - s * (_segment.p1.y -p.y) + p.x, s * (_segment.p2.x - p.x) + c * (_segment.p2.y - p.y) + p.y);
        e.getGraphics().rotate(_rotateAngle);
        e.getGraphics().drawLine(_segment.p1.x, _segment.p1.y,  _segment.p2.x, _segment.p2.y);
        e.getGraphics().restore();
    }

    public void update (double deltatime){
        _rotateAngle += _speed * deltatime;
    }

    public void set_angle(float angle){
        _angle = angle;

        double b = Math.toRadians(_angle);
        float s = (float) Math.sin(b);
        float c = (float) Math.cos(b);

        float x1 = p.x - _size/2;
        float x2 = p.x + _size/2;
        if(angle != 0)
            _segment = new Segment(new Point((x1 - p.x) * c + p.x, (x1 - p.x) * s + p.y), new Point((x2 - p.x) * c + p.x, (x2 - p.x) * s + p.y));
    }
    public void set_speed(float speed){
        _speed = speed;
    }

    private float _angle;
    private float _rotateAngle;
    private float _speed;
    private Segment _segment;
}
