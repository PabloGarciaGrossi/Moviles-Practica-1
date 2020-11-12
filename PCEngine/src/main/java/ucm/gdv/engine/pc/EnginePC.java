package ucm.gdv.engine.pc;

import java.io.FileInputStream;
import java.io.InputStream;



public class EnginePC implements ucm.gdv.engine.Engine {
    public GraphicsPC getGraphics(){
        return g;
    }

    public InputPC getInput(){
        return input;
    }

    public InputStream openInputStream(String filename){
        InputStream is = null;
        try{
            is = new FileInputStream(filename);
        }
        catch (Exception e) {
            System.err.println("Error cargando la fuente: " + e);
            return null;
        }
        return is;
    }

    private  GraphicsPC g;
    private InputPC input;
}
