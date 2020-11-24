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
        _actualSegment = _path.directions.get(0);
        _dirSegment = Utils.normalize(_actualSegment);
    }

    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);

        e.getGraphics().save();
        //e.getGraphics().rotate(_angle);
        e.getGraphics().translate(p.x, p.y);
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
        p.x += _dirSegment.x * _speed * (float) deltaTime;
        p.y += _dirSegment.y * _speed * (float) deltaTime;

        if (Utils.sqrDistancePointPoint(p,_actualSegment.p2) < 1)
        {
            _actualSegment = _path.directions.get(_actualVertex);

            _dirSegment = Utils.normalize(_actualSegment);
            _actualVertex ++;
            if (_actualVertex == _path._vertex.size())
                _actualVertex = 0;
        }

    }

    private float _angle = 20f;
    private float _speed = 100f;
    private Segment _actualSegment;
    private Point _dirSegment;
    private Path _path;

    private int _actualVertex = 1;
}
