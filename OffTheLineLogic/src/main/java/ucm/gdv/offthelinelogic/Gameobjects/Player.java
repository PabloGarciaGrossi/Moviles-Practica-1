package ucm.gdv.offthelinelogic.Gameobjects;

import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.engine.Input;
import ucm.gdv.offthelinelogic.Point;
import ucm.gdv.offthelinelogic.Segment;
import ucm.gdv.offthelinelogic.Utils;

public class Player extends GameObject {
    //Inicialización del player
    public Player(float x, float y, int color, float size, Path path, float speed){
        super(x,y,size,color);
        _path = path;
        //Se inicia la posición del jugador en el primer vértice del path
        p.x = _path._vertex.get(0).x;
        p.y = _path._vertex.get(0).y;
        //Se inicializa el segmento que sigue el jugador al primer segmento del path
        _actualSegment = _path.segments.get(0);
        //Se calcula la dirección que debe seguir el ugador sobre el segmento
        _dirSegment = Utils.normalize(_actualSegment);
        //Distancia que tiene que recorrer sobre ese segmento
        distToPoint = _actualSegment.getDistance();
        _speed=speed;
    }

    //Dibujado del jugador según la posición actual y la rotación sobre sí mismo
    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);

        e.getGraphics().translate(p.x, p.y);
        e.getGraphics().rotate(_angle * dirNum);
        e.getGraphics().drawLine(- _size/2,  - _size/2,   + _size/2,  - _size/2);
        e.getGraphics().drawLine( + _size/2,  - _size/2,   + _size/2,   + _size/2);
        e.getGraphics().drawLine(  + _size/2,  + _size/2,   - _size/2,  + _size/2);
        e.getGraphics().drawLine(- _size/2,  + _size/2, - _size/2, - _size/2);
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
        //Velocidad del ángulo de giro
        _angle += 120 * deltaTime;
        //posición del frame anterior
        Point prev = new Point(p.x, p.y);

        //Velocidad según si el jugador está saltando o no
        float actSpeed= (!jumping) ? _speed : _jumpingSpeed;

        //Traslación de la posición del jugador dependiendo de su dirección, sentido y velocidad.
        p.x += _dirSegment.x * dirNum * actSpeed * (float) deltaTime;
        p.y += _dirSegment.y * dirNum * actSpeed * (float) deltaTime;


        //Cálculo del vector de colisión según su última posición y su posición actual
        _collisionSegment = new Segment(prev, p);

        //Distancia recorrida sobre el segmento
        distance += actSpeed * (float) deltaTime;

        //Si la distancia recorrida  es mayor a la distancia del segmento a recorrer, procedemos a cambiar al próximo segmento del path en el que se encuentra el jugador.
        if (!jumping && distance > distToPoint)
        {
            //Se actualiza el índice counter para seleccionar el segmento adecuado en el path según la dirección en la que lo estamos recorriendo
            _counter += dirNum;
            if (_counter == _path.segments.size())
                _counter = 0;
            else if(_counter < 0)
                _counter = _path.segments.size()-1;

            //Se selecciona el próximo segmento a seguir
            _actualSegment = _path.segments.get(_counter);

            //Dependiendo de la dirección, se asigna una dirección o la inversa y el próximo destino del segmento que estamos recorriendo
            if(dirNum > 0) {
                _dirSegment = Utils.normalize(_actualSegment);
                p.x = _actualSegment.p1.x;
                p.y = _actualSegment.p1.y;
            }
            else {
                _dirSegment = Utils.normalize(new Segment(_actualSegment.p1, _actualSegment.p2));
                p.x = _actualSegment.p2.x;
                p.y = _actualSegment.p2.y;
            }
            //Se reinicia la distancia al seguir del nuevo segmento
                distToPoint = _actualSegment.getDistance();
                distance = 0;
        }

    }

    //Se comprueba si se ha pulsado el ratón o la pantalla y en ese caso, salta.
    public void handleInput(Engine e){
        for(Input.TouchEvent event: e.getInput().getTouchEvents()){
            if(event.typeEvent==Input.type.PULSAR){
                if(!jumping) jump();
            }
        }
    }

    //Salto del jugador
    public void jump(){
        //Dependiendo de si el vector de direcciones del path está vacío, se crea
        //una normal o se utiliza la normal proporcionada por el json para el segmento en el que se encuentra el jugador
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

    /*Método que actualiza la dirección y segmento a seguir del jugador tras finalizar un salto.
    * Se modifican el segmento a seguir y el path en el que se encuentra y se cambia la dirección "dirNum"
    * Dependiendo de la dirección a la que haya que dirigirse, se actualiza el vector de dirección y la distancia que falyta hacia el destino siguiente*/
    public void setNewDirSegment(Segment s, Path pa){
        _actualSegment = s;
        _path = pa;
        jumping = false;
        _counter = _path.segments.indexOf(s);
            dirNum = -dirNum;
        if(dirNum < 1) {
            _dirSegment = Utils.normalize(new Segment(_actualSegment.p1, _actualSegment.p2));
            distToPoint = Utils.sqrDistancePointPoint(p, _actualSegment.p1);
        }
        else {
            _dirSegment = Utils.normalize(_actualSegment);
            distToPoint = Utils.sqrDistancePointPoint(p, _actualSegment.p2);
        }

        distance = 0;
    }

    //Similar a la inicialización del jugador
    //Se establece su path y posición al primer path del nivel y a la posición del primer vértice del nivel
    //Se reestablecen el resto de variables
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

    //Segmento actual a seguir
    private Segment _actualSegment;

    //Dirección del segmento actual
    private Point _dirSegment;

    //Segmento formado por la posición actual del jugador y su última posición en el anterior frame
    private Segment _collisionSegment;
    private Path _path;
    public boolean jumping;
    //Sentido en el que avanza el jugador, valdrá siempre 1 o -1
    private int dirNum = 1;
    //Índice del segmento que estamos siguiendo en el Path
    private int _counter = 0;
}
