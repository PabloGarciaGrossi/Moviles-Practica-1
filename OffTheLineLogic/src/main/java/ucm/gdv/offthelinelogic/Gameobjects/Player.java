package ucm.gdv.offthelinelogic.Gameobjects;

import java.util.List;

import ucm.gdv.engine.Engine;

public class Player extends GameObject {
    public Player(float x, float y, String color, float size, Path path){
        super(x,y,size,color);
        _path = path;
        _x = _path._vertex.get(0).x;
        _y = _path._vertex.get(0).y;
        _dir.x = _path._vertex.get(1).x - _x;
        _dir.y = _path._vertex.get(1).y - _y;
        normalize(_dir);
    }

    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);

        e.getGraphics().save();
        //e.getGraphics().rotate(_angle);
        e.getGraphics().translate(_x, _y);
        e.getGraphics().drawLine(- _size/2, - _size/2,  _size/2, - _size/2);
        e.getGraphics().drawLine(_size/2, - _size/2,  _size/2,  _size/2);
        e.getGraphics().drawLine( _size/2, _size/2,  - _size/2,  _size/2);
        e.getGraphics().drawLine(- _size/2,  _size/2, - _size/2, - _size/2);
        e.getGraphics().restore();
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
        //_x+=_speed*deltaTime;
        _angle += 120 * deltaTime;
        _x += _dir.x * _speed * (float) deltaTime;
        _y += _dir.y * _speed * (float) deltaTime;

        if (isNear(_path._vertex.get(_actualVertex).x, _path._vertex.get(_actualVertex).y))
        {
            _actualVertex ++;
            _dir.x = _path._vertex.get(_actualVertex).x - _x;
            _dir.y = _path._vertex.get(_actualVertex).y - _y;

            normalize(_dir);
            System.out.println(_dir);
        }

    }
    class Direction{
        public void Direction(float _x, float _y){
            x = _x;
            y = _y;
        }
        private float x;
        private float y;
    }

    private boolean isNear(float x, float y){
        if (Math.abs(_x - x) < 0.5 && Math.abs(_y - y) < 0.5)
        {
            return true;
        }
        else return false;
    }
    private void normalize(Direction d)
    {
        float a = (float) Math.sqrt(Math.abs((d.x *d.x)) + Math.abs((d.y + d.y)));
        d.x = d.x / a;
        d.y = d.y/ a;
    }

    private float _angle = 20f;
    private float _speed = 50f;
    private Direction _dir = new Direction();
    private Path _path;
    private int _actualVertex = 1;
}
