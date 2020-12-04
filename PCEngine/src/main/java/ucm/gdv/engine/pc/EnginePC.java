package ucm.gdv.engine.pc;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFrame;

import ucm.gdv.engine.Logic;


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
            is = new FileInputStream("Assets/" + filename);
        }
        catch (Exception e) {
            System.err.println("Error cargando la fuente: " + e);
            return null;
        }
        return is;
    }

    public void init()
    {
        jf = new JFrame("Jframe");
        g = new GraphicsPC(jf,640,480);
        input = new InputPC();
    }

    public void run(){
        long lastFrameTime = System.nanoTime();
        jf.addMouseListener(input.getMl());
        boolean working = true;
        while(working) {
            if(logic!=null) {
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                double deltaTime = (double) nanoElapsedTime / 1.0E9;
                g.render(logic);
                logic.update(deltaTime);
                logic.handleInput();
                working = logic.isWorking();
                if(working == false)
                    System.out.println("hemos finiquitao");
            }
        }
    }

    public void setLogic(Logic lo){
        logic=lo;
    }
    private  GraphicsPC g;
    private InputPC input;

    JFrame jf;
    Logic logic=null;
}
