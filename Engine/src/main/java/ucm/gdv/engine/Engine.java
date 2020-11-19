package ucm.gdv.engine;

import java.io.InputStream;

public interface Engine {
    Graphics getGraphics();

    Input getInput();

    InputStream openInputStream(String filename);

    void init();

    void run();

    void setLogic(Logic lo);

}
