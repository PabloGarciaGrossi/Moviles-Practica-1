package ucm.gdv.pcgame;

import javax.swing.JFrame;

import ucm.gdv.engine.pc.EnginePC;
import ucm.gdv.offthelinelogic.OffTheLineLogic;

public class PCGame extends JFrame {
    static EnginePC _engine;
    static OffTheLineLogic _logic;
    static boolean _run = true;


    static void init(){
        _engine = new EnginePC();
        _engine.init();
    }
    static void render(){
        _engine.render();
    }

    public static void  main(String[] args){
        init();
        while(_run)
        {
            render();
        }
    }

}