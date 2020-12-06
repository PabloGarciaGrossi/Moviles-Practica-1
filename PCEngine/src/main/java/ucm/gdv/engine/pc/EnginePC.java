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

    //Abre un InputStream en al carpeta de lectura del PC
    public InputStream openInputStream(String filename){
        InputStream is = null;
        try{
            is = new FileInputStream("Assets/" + filename);
        }
        catch (Exception e) {
            System.err.println("Error cargando el archivo: " + e);
            return null;
        }
        return is;
    }

    //Inicialización del Jframe, los gráficos del PC y del input. A ellos se les pasa el Jframe para sus correspondientes operaciones
    public void init()
    {
        jf = new JFrame("Jframe");
        g = new GraphicsPC(jf,640,480);
        input = new InputPC(jf);
    }

    //Bucle principal del juego
    public void run(){
        long lastFrameTime = System.nanoTime();
        //Registra al ratón del inputmanager para recibir los eventos de ratón
        jf.addMouseListener(input.getMl());
        boolean working = true;
        while(working) {
            if(logic!=null) {
                //cálculo del deltaTime
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                double deltaTime = (double) nanoElapsedTime / 1.0E9;
                //Llamada al renderizado de la lógica a través de los gráficos
                g.render(logic);
                //update de la lógica
                logic.update(deltaTime);
                //input de la lógica
                logic.handleInput();
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
