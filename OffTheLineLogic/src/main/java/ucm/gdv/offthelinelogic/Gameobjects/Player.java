package ucm.gdv.offthelinelogic.Gameobjects;

import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.engine.Input;
import ucm.gdv.offthelinelogic.Point;
import ucm.gdv.offthelinelogic.Segment;
import ucm.gdv.offthelinelogic.Utils;

public class Player extends GameObject {
    public Player(float x, float y, int color, float size, Path path, float speed){
        super(x,y,size,color);
        _path = path;
        p.x = _path._vertex.get(0).x;
        p.y = _path._vertex.get(0).y;
        _actualSegment = _path.segments.get(0);
        _dirSegment = Utils.normalize(_actualSegment);
        distToPoint = _actualSegment.getDistance();
        _tag = "Player";
        _speed=speed;
    }

    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);
        double b = Math.toRadians(_angle);
        float s = (float) Math.sin(b);
        float c = (float) Math.cos(b);

        e.getGraphics().translate(p.x, p.y);
        e.getGraphics().rotate(_angle * dirNum);
        e.getGraphics().drawLine(- _size/2,  - _size/2,   + _size/2,  - _size/2);
        e.getGraphics().drawLine( + _size/2,  - _size/2,   + _size/2,   + _size/2);
        e.getGraphics().drawLine(  + _size/2,  + _size/2,   - _size/2,  + _size/2);
        e.getGraphics().drawLine(- _size/2,  + _size/2, - _size/2, - _size/2);
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
        //_x+=_speed*deltaTime;
        _angle += 120 * deltaTime;
        Point prev = new Point(p.x, p.y);

        float actSpeed= (!jumping) ? _speed : _jumpingSpeed;

        p.x += _dirSegment.x * dirNum * actSpeed * (float) deltaTime;
        p.y += _dirSegment.y * dirNum * actSpeed * (float) deltaTime;

        if(!jumping) {//mientras no encuentre otra solución necesito esta wea para que se ajuste al camino en android
            if (_dirSegment.x == 0) p.x = _actualSegment.p1.x;
            else if (_dirSegment.y == 0) p.y = _actualSegment.p1.y;
        }

        _collisionSegment = new Segment(prev, p);

        //distance += _speed * (float) deltaTime;
        distance += actSpeed * (float) deltaTime;

        if (!jumping && distance > distToPoint)
        {
            _counter += dirNum;
            if (_counter == _path.segments.size())
                _counter = 0;
            else if(_counter < 0)
                _counter = _path.segments.size()-1;

            _actualSegment = _path.segments.get(_counter);

            if(dirNum > 0) {
                _dirSegment = Utils.normalize(_actualSegment);
                p.x = _actualSegment.p1.x;
                p.y = _actualSegment.p1.y;
            }
            else {
                _dirSegment = Utils.normalize(_actualSegment.inverted());
                p.x = _actualSegment.p2.x;
                p.y = _actualSegment.p2.y;
            }
                distToPoint = _actualSegment.getDistance();
                distance = 0;
        }

    }

    public void handleInput(Engine e){
        for(Input.TouchEvent event: e.getInput().getTouchEvents()){
            if(event.typeEvent==Input.type.PULSAR){
                if(!jumping) jump();
                e.getInput().clearEvents();
            }
        }
    }

    public void OnCollision(GameObject other) {
        super.OnCollision(other);
        if(other._tag=="Coin") System.out.println("Buenardo");
    }

    public void jump(){
        if(_path._directions.isEmpty())
            _dirSegment = Utils.getNormal(_actualSegment, dirNum < 1);
        else {
            _dirSegment = Utils.multVector(_path._directions.get(_counter), dirNum);
        }
        jumping = true;
        distance = 0;
    }

    public Segment get_collisionSegment(){
        return _collisionSegment;
    }
    public Segment get_actualSegment() {return  _actualSegment;}
    public float getDistance(){return  distance;}

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

    public void playerDeath(Path pa){
        _path = pa;
        p = new Point(_path._vertex.get(0).x, _path._vertex.get(0).y);
        _actualSegment = _path.segments.get(0);
        _dirSegment = Utils.normalize(_actualSegment);
        distToPoint = _actualSegment.getDistance();
        distance = 0;
        dirNum = 1;
        jumping = false;
        _counter = 0;
    }

    private float _angle = 20f;
    private float _speed;
    private float _jumpingSpeed = 1500f;
    private float distance = 0f;
    private float distToPoint = 0;
    private Segment _actualSegment;
    private Point _dirSegment;
    private Segment _collisionSegment;
    private Path _path;
    public boolean jumping;
    private int dirNum = 1;
    private int _counter = 0;
}
