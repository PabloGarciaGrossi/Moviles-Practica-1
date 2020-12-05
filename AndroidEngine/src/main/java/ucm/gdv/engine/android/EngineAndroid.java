package ucm.gdv.engine.android;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.SurfaceView;

import java.io.FileInputStream;
import java.io.InputStream;

import ucm.gdv.engine.Graphics;
import ucm.gdv.engine.Input;
import ucm.gdv.engine.Logic;

public class EngineAndroid implements ucm.gdv.engine.Engine, Runnable{
    public EngineAndroid(SurfaceView surfaceView, AssetManager manager){
        _surfaceview = surfaceView;
        _manager = manager;
    }
    public GraphicsAndroid getGraphics(){
        return _g;
    };

    public Input getInput(){
        return _input;
    };

    public InputStream openInputStream(String filename){
        InputStream is = null;
        try{
            is = _manager.open(filename);
        }
        catch (Exception e) {
            System.err.println("Error cargando la fuente: " + e);
            return null;
        }
        return is;
    };

    public void init(){
        _g = new GraphicsAndroid(_surfaceview, _manager, _surfaceview.getHolder());
        _input = new InputAndroid(_surfaceview);
    };
    public void on_resume(){
        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva, nunca se sabe quién va a
            // usarnos...)
            _running = true;
            // Lanzamos la ejecución de nuestro método run()
            // en una hebra nueva.
            _renderThread = new Thread(this);
            _renderThread.start();
        }
    }
    public void on_pause(){
        if (_running) {
            _running = false;
            while (true) {
                try {
                    _renderThread.join();
                    _renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            } // while(true)
        }
    }
    public void run(){
        if (_renderThread != Thread.currentThread()) {
            throw new RuntimeException("run() should not be called directly");
        }
        // Antes de saltar a la simulación, confirmamos que tenemos
        // un tamaño mayor que 0. Si la hebra se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while(_running && getGraphics().getWidth() == 0)
            // Espera activa. Sería más elegante al menos dormir un poco.
            ;
        long lastFrameTime = System.nanoTime();
        // Bucle principal.
        boolean gameRunning=true;
        while(_running && gameRunning) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            _logic.update(elapsedTime);
            _g.render(_logic);
            _logic.handleInput();
            gameRunning = _logic.isWorking();
        }
    };

    public void setLogic(Logic lo){
        _logic=lo;
    };

    public SurfaceView getSurfaceView(){
        return _surfaceview;
    }

    GraphicsAndroid _g;
    InputAndroid _input;
    SurfaceView _surfaceview;
    AssetManager _manager;
    Logic _logic;
    Thread _renderThread;
    boolean _running = false;
}
