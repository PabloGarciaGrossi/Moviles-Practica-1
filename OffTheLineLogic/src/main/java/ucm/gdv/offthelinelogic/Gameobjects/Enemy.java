package ucm.gdv.offthelinelogic.Gameobjects;

import java.sql.Struct;

import ucm.gdv.engine.Engine;
import ucm.gdv.offthelinelogic.Point;
import ucm.gdv.offthelinelogic.Segment;
import ucm.gdv.offthelinelogic.Utils;

public class Enemy extends GameObject {
    public Enemy(float x, float y, float size, String color){
        super(x,y,size,color);
        _initial = new Point(x,y);
        _segment = new Segment(new Point(x-size/2, y), new Point(x+_size/2, y));
    }

    public void render(Engine e){
        e.getGraphics().setColor(_color);

        //double b = Math.toRadians(_rotateAngle);
        //float s = (float) Math.sin(b);
        //float c = (float) Math.cos(b);

        //e.getGraphics().save();
        //e.getGraphics().translate(c * ((p.x - _radius) - p.x) - s * ((p.y - _radius) -p.y) + p.x, s * ((p.x - _radius) - p.x) + c * ((p.y - _radius) - p.y) + p.y);
        //e.getGraphics().translate(c * ( - p.x) - s * (-p.y) + p.x, s * ( - p.x) + c * (- p.y) + p.y);
        //e.getGraphics().rotate(_rotateAngle);
        e.getGraphics().drawLine(_segment.p1.x, _segment.p1.y,  _segment.p2.x, _segment.p2.y);
        //e.getGraphics().restore();
    }

    public void update (double deltatime){
        /*if(move) {
            Point dir = (goingOffset) ? _offset : _initial;
            p.x += Utils.normalize(dir).x * 250 * (float)deltatime;
            p.y += Utils.normalize(dir).y * 250 * (float)deltatime;

            _segment.p1.x = p.x - _size/2;
            _segment.p1.y = p.y;
            _segment.p2.x = p.x + _size/2;
            _segment.p2.y = p.y;
        }*/
        _rotateAngle = (float)_speed * (float) deltatime;

        double b = Math.toRadians(_rotateAngle);
        float s = (float) Math.sin(b);
        float c = (float) Math.cos(b);

        _segment.p1.x -= p.x;
        _segment.p1.y -= p.y;
        _segment.p2.x -= p.x;
        _segment.p2.y -= p.y;

        float xnew1 = _segment.p1.x * c - _segment.p1.y * s;
        float ynew1 = _segment.p1.x * s + _segment.p1.y *c;

        float xnew2 = _segment.p2.x * c - _segment.p2.y * s;
        float ynew2 = _segment.p2.x * s + _segment.p2.y *c;

        _segment.p1.x = xnew1 + p.x;
        _segment.p1.y = ynew1 + p.y;

        _segment.p2.x = xnew2 + p.x;
        _segment.p2.y = ynew2 + p.y;
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

    public void set_offset(float x, float y)
    {
        _offset = new Point(x,y);
    }

    public void set_time1(float t){
        _time1 = t;
        _movementSpeed = Utils.sqrDistancePointPoint(_initial, _offset)/_time1;
        move = true;
    }
    private float _angle;
    private float _rotateAngle;
    private float _speed;
    private Point _offset;
    private Point _initial;
    private boolean goingOffset = true;
    private boolean move = false;
    private float _movementSpeed;
    private float _distance = 0f;
    private float _time1 = 0f;
    private Segment _segment;
}
