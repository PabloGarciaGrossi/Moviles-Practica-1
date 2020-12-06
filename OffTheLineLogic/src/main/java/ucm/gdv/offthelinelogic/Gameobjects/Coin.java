package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class Coin extends GameObject{
    public Coin(float x, float y, int color,float size){
        super(x,y,size,color);
    }

    @Override
    public void render(Engine e) {
        super.render(e);

        double b = Math.toRadians(_angle);
        float s = (float) Math.sin(b);
        float c = (float) Math.cos(b);
        e.getGraphics().setColor(_color);

        //Guarda el estado del canvas para solo rotar esta moneda
        e.getGraphics().save();

        //Traslación de la moneda según el centro de rotación sobre al que ha de girar y su radio.
        e.getGraphics().translate(c * ((p.x - _radius) - p.x) - s * ((p.y - _radius) - p.y) + p.x, s * ((p.x - _radius) - p.x) + c * ((p.y - _radius) - p.y) + p.y);

        //Rotación sobre sí misma
        e.getGraphics().rotate(-_angle);

        //Dibujado de cada una de las líneas del cuadrado
        e.getGraphics().drawLine(-_size / 2, -_size / 2, _size / 2, -_size / 2);
        e.getGraphics().drawLine(_size / 2, -_size / 2, _size / 2, _size / 2);
        e.getGraphics().drawLine(_size / 2, _size / 2, -_size / 2, _size / 2);
        e.getGraphics().drawLine(-_size / 2, _size / 2, -_size / 2, -_size / 2);

        //Restauración del canvas
        e.getGraphics().restore();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        //Actualización del ángulo según si viene o no indicada la velocidad de la moneda
        if(_speed != 0)
            _angle += _speed * deltaTime;
        else
            _angle += 80 * deltaTime;

        //Incremento del tamañi de la moneda según si ha comenzado su muerte. A los 0.5 segundos se le declara muerta.
        if(initDeath){
            //_radius += 1;
            _size += 50 * (float)deltaTime;
            deadCD += deltaTime;
            if(deadCD >= 0.5f)
                dead = true;
        }
    }


    //Inicia la muerte de la moneda
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
