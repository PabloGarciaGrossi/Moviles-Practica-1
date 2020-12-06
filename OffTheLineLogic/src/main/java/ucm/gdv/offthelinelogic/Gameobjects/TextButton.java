package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;
import ucm.gdv.engine.Input;
import ucm.gdv.offthelinelogic.Point;
import ucm.gdv.offthelinelogic.Segment;
import ucm.gdv.offthelinelogic.Utils;

public class TextButton extends GameObject{
    public TextButton(float x, float y, int size, int color, int width, int height, String text, Callback callback){
        super(x,y,size, color);
        _text=text;
        w=width;
        h=height;
        _callback=callback;
    }

    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);
        e.getGraphics().drawText(_text, p.x - w/2, p.y + h/2);
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    public void handleInput(Engine e){
        if(_callback!=null) {
            for (Input.TouchEvent event : e.getInput().getTouchEvents()) {
                if (event.typeEvent == Input.type.PULSAR) {
                    System.out.println(event.posx + " " + event.posy);
                    if (event.posx >= p.x - w / 2 && event.posx <= p.x + w / 2) {
                        if(event.posy >= p.y - h / 2 && event.posy <= p.y + h / 2){
                            _callback.callfunction();
                        }
                    }
                }
            }
        }
    }

    public void OnCollision(GameObject other) {
    }

    String _text;
    Callback _callback;
    int w;
    int h;
}
