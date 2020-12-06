package ucm.gdv.engine.pc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import ucm.gdv.engine.Input;

public class InputPC implements ucm.gdv.engine.Input {

    public class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            Input.TouchEvent t = new Input.TouchEvent();
            t.typeEvent = Input.type.PULSAR;
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            float scale = calculateScale();

            int w=_window.getWidth();
            int h=_window.getHeight();

            x = (int)(x*scale);
            y = (int)(y*scale);

            t.posx = x - w/2;
            t.posy = y - h/2;

            events.add(t);
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    public InputPC(JFrame window){
        _window = window;
        ml = new MouseHandler();
        events = new ArrayList<TouchEvent>();
    }

    public List<TouchEvent> getTouchEvents(){
        List<TouchEvent> ret = new ArrayList<TouchEvent>(events);
        events.clear();
        return ret;
    }

    public MouseListener getMl(){
        return ml;
    }

    public float calculateScale(){
        float s1 = 0;
        float s2 = 0;
        s1 = _window.getWidth() / _logicW;
        s2 = _window.getHeight() / _logicH;
        if (s1 < s2)
            return s1;
        else return s2;
    };

    private float _logicW = 640;
    private float _logicH = 480;

    JFrame _window;

    private Scanner keyboard;
    private MouseHandler ml;
    private List<TouchEvent> events;
}
