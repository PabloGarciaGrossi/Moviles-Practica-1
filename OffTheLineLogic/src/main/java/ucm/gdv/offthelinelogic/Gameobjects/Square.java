package ucm.gdv.offthelinelogic.Gameobjects;

import ucm.gdv.engine.Engine;

public class Square extends GameObject{
    public Square (int x, int y, int size, String color){
        super(x,y,size,color);
    }

    @Override
    public void render(Engine e) {
        super.render(e);
        e.getGraphics().setColor(_color);
        e.getGraphics().fillRect(_x - _size/2, _y - _size/2, _x + _size/2, _y + _size/2);
    }

    @Override
    public void update() {
        super.update();
    }
}
