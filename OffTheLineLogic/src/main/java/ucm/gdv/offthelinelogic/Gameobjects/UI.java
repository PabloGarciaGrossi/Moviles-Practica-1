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
            e.getGraphics().translate(p.x + i*2*_size, p.y);
            e.getGraphics().drawLine(p.x -_size / 2 , p.y -_size / 2, p.x +_size / 2, p.y -_size / 2);
            e.getGraphics().drawLine(p.x +_size / 2, p.y -_size / 2, p.x +_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x +_size / 2, p.y +_size / 2, p.x -_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x -_size / 2, p.y +_size / 2, p.x -_size / 2, p.y -_size / 2);
            e.getGraphics().restore();
        }
        e.getGraphics().setColor("red");
        for (int i = lives; i < 10; i++) {
            e.getGraphics().translate(p.x + i*2*_size, p.y);
            e.getGraphics().drawLine(p.x -_size / 2 , p.y -_size / 2, p.x +_size / 2, p.y +_size / 2);
            e.getGraphics().drawLine(p.x -_size / 2, p.y +_size / 2, p.x +_size / 2, p.y -_size / 2);
            e.getGraphics().restore();
        }
    }

    public int lives;
}
