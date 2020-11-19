package ucm.gdv.pcgame;

import javax.swing.JFrame;

import ucm.gdv.engine.pc.EnginePC;
import ucm.gdv.offthelinelogic.OffTheLineLogic;

public class PCGame {
    static EnginePC _engine;
    static OffTheLineLogic _logic;
    static boolean _run = true;


    static void init(){
        _engine = new EnginePC();
        _engine.init();
        _logic = new OffTheLineLogic(_engine);

    }

    public static void  main(String[] args){
        init();

        _engine.setLogic(_logic);
        _engine.run();
    }

}