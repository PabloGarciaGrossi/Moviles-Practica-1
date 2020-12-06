package ucm.gdv.engine.pc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import ucm.gdv.engine.Input;

public class InputPC implements ucm.gdv.engine.Input {

    //Controlador del mouse
    public class MouseHandler implements MouseListener {
        @Override

        //Detecta que el ratón ha sido pulsado y añade un nuevo evento PULSAR a la lisat de eventos
        //Este evento guarda la posición en la que se ha realizado el click
        public void mouseClicked(MouseEvent mouseEvent) {
            Input.TouchEvent t = new Input.TouchEvent();
            t.typeEvent = Input.type.PULSAR;
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            float scale = calculateScale();

            int w=_window.getWidth();
            int h=_window.getHeight();

            t.posx = (int)((x - w/2) * 1/scale);
            t.posy = (int)((y - h/2) * 1/scale);

            events.add(t);
        }

        /*Posibles futuras implementaciones para el control del ratón*/
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

    //Inicialización del input, con su Jframe y su MouseHandler
    public InputPC(JFrame window){
        _window = window;
        ml = new MouseHandler();
        events = new ArrayList<TouchEvent>();
    }

    //Devuelve una copia de los eventos almacenados
    public List<TouchEvent> getTouchEvents(){
        List<TouchEvent> ret = new ArrayList<TouchEvent>(events);
        return ret;
    }
    //limpiar la lista de eventos
    public void clearEvents(){
        events.clear();
    }

    public MouseListener getMl(){
        return ml;
    }

    //Calcula la escala de la pantalla para comprobar dónde se está realizando realmente el click del ratón
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
