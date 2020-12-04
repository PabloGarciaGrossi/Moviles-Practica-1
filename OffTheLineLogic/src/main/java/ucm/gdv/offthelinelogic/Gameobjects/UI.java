package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class UI extends GameObject{

    public UI(float x, float y, int size,  boolean isDificult, int lives_){
        super(x,y,size,"white");
        lives = lives_;
    }

    public void update(double deltatime){

    }

    public void render(Engine e){
        e.getGraphics().setColor("white");
        for (int i = 0; i < lives; i++) {
            e.getGraphics().drawLine(p.x + (i*2 * _size) -_size / 2 , p.y -_size / 2, p.x + (i*2 * _size) +_size / 2, p.y -_size / 2);
            e.getGraphics().drawLine(p.x + (i*2 * _size) +_size / 2, p.y -_size / 2, p.x + (i*2 * _size) +_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x + (i*2 * _size) +_size / 2, p.y +_size / 2, p.x + (i*2 * _size) -_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x + (i*2 * _size) -_size / 2, p.y +_size / 2, p.x + (i*2 * _size) -_size / 2, p.y -_size / 2);
        }
        e.getGraphics().setColor("red");
        for (int i = lives; i < 10; i++) {
            e.getGraphics().drawLine(p.x + (i*2 * _size) -_size / 2 , p.y -_size / 2, p.x + (i*2 * _size) +_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x  + (i*2 * _size) -_size / 2, p.y +_size / 2, p.x + (i*2 * _size) +_size / 2, p.y -_size / 2);
        }
    }

    public int lives;
}
