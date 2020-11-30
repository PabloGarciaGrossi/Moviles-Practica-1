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

    public InputPC(){
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

    private Scanner keyboard;
    private MouseHandler ml;
    private List<TouchEvent> events;
}
