package ucm.gdv.offthelinelogic.Gameobjects;

import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.offthelinelogic.Point;
import ucm.gdv.offthelinelogic.Segment;
import ucm.gdv.offthelinelogic.Utils;

public class Player extends GameObject {
    public Player(float x, float y, String color, float size, Path path){
        super(x,y,size,color);
        _path = path;
        p.x = _path._vertex.get(0).x;
        p.y = _path._vertex.get(0).y;
        _actualSegment = _path.segments.get(0);
        _dirSegment = Utils.normalize(_actualSegment);
        distToPoint = _actualSegment.getDistance();
    }

    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);

        //e.getGraphics().save();
        //e.getGraphics().rotate(_angle);
        //e.getGraphics().translate(p.x, p.y);
        Point segPOS=_path._vertex.get(0);
        Point playerPOS=p;

        e.getGraphics().drawLine(p.x - _size/2, p.y - _size/2,  p.x +_size/2, p.y - _size/2);
        e.getGraphics().drawLine(p.x + _size/2, p.y - _size/2,  p.x + _size/2,  p.y + _size/2);
        e.getGraphics().drawLine( p.x + _size/2, p.y + _size/2,  p.x - _size/2,  p.y + _size/2);
        e.getGraphics().drawLine(p.x - _size/2,  p.y + _size/2, p.x - _size/2, p.y - _size/2);
        //e.getGraphics().restore();
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
        //_x+=_speed*deltaTime;
        _angle += 120 * deltaTime;
        Point prev = new Point(p.x, p.y);
        p.x += _dirSegment.x * dirNum * _speed * (float) deltaTime;
        p.y += _dirSegment.y * dirNum * _speed * (float) deltaTime;

        _collisionSegment = new Segment(prev, p);
        distance += _speed * (float) deltaTime;

        if (!jumping && distance > distToPoint)
        {
            _counter += dirNum;
            if (_counter == _path.segments.size())
                _counter = 0;
            else if(_counter < 0)
                _counter = _path.segments.size()-1;

            _actualSegment = _path.segments.get(_counter);

            if(dirNum > 1)
                _dirSegment = Utils.normalize(_actualSegment);
            else
                _dirSegment = Utils.normalize(_actualSegment.inverted());
            distToPoint = _actualSegment.getDistance();
            distance = 0;
        }

    }

    public void jump(){
        _dirSegment = Utils.getNormal(_actualSegment, dirNum < 1);
        jumping = true;
    }

    public Segment get_collisionSegment(){
        return _collisionSegment;
    }
    public Segment get_actualSegment() {return  _actualSegment;}

    public void setNewDirSegment(Segment s, Path pa){
        _actualSegment = s;
        _path = pa;
        jumping = false;
        _counter = _path.segments.indexOf(s);
        dirNum = -dirNum;
        if(dirNum < 1) {
            _dirSegment = Utils.normalize(_actualSegment.inverted());
            distToPoint = Utils.sqrDistancePointPoint(p, _actualSegment.p1);
        }
        else {
            _dirSegment = Utils.normalize(_actualSegment);
            distToPoint = Utils.sqrDistancePointPoint(p, _actualSegment.p2);
        }
        distance = 0;
    }

    private float _angle = 20f;
    private float _speed = 250f;
    private float distance = 0f;
    private float distToPoint = 0;
    private Segment _actualSegment;
    private Point _dirSegment;
    private Segment _collisionSegment;
    private Path _path;
    public boolean jumping;
    private boolean inverted = false;
    private int dirNum = 1;

    private int _counter = 0;
}
