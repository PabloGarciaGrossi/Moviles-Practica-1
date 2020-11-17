package ucm.gdv.engine.pc;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFrame;


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

    public void init()
    {
        g = new GraphicsPC(800,800);

        input = new InputPC();
    }

    public void render(){
        g.render();
    }

    public  void update(){

    }
    private  GraphicsPC g;
    private InputPC input;
}
