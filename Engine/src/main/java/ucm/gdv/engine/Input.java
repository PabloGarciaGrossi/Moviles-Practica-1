package ucm.gdv.engine;

import java.util.List;

public interface Input{

    public class TouchEvent{
        public enum type {PULSAR, SOLTAR, DESPLAZAR};
        int id;

        int posx;
        int posy;
    }

    List<TouchEvent> getTouchEvents();
}