package ucm.gdv.engine;

import java.util.ArrayList;
import java.util.List;

public interface Input{

    public class TouchEvent{
        public int id;
        public type typeEvent;
        public int posx;
        public int posy;
    }

    List<TouchEvent> getTouchEvents();
    public enum type {PULSAR, SOLTAR, DESPLAZAR};
}