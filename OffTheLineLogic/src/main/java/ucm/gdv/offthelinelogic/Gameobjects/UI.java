package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class UI extends GameObject{

    public UI(float x, float y, int size,  boolean isDificult, int lives_){
        super(x,y,size,0xFFFFFF00);
        lives = lives_;
    }

    public void update(double deltatime){

    }

    //Dibujado de cada uno de los cuadrados y cruces dependiendo del número de vidas actual de la partida
    public void render(Engine e){
        e.getGraphics().setColor(0xFF1E90FF);
        for (int i = 0; i < lives; i++) {
            e.getGraphics().drawLine(p.x + (i*2 * _size) -_size / 2 , p.y -_size / 2, p.x + (i*2 * _size) +_size / 2, p.y -_size / 2);
            e.getGraphics().drawLine(p.x + (i*2 * _size) +_size / 2, p.y -_size / 2, p.x + (i*2 * _size) +_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x + (i*2 * _size) +_size / 2, p.y +_size / 2, p.x + (i*2 * _size) -_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x + (i*2 * _size) -_size / 2, p.y +_size / 2, p.x + (i*2 * _size) -_size / 2, p.y -_size / 2);
        }
        e.getGraphics().setColor(0xFFFF0000);
        for (int i = lives; i < 10; i++) {
            e.getGraphics().drawLine(p.x + (i*2 * _size) -_size / 2 , p.y -_size / 2, p.x + (i*2 * _size) +_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x  + (i*2 * _size) -_size / 2, p.y +_size / 2, p.x + (i*2 * _size) +_size / 2, p.y -_size / 2);
        }
    }

    public int lives;
}
