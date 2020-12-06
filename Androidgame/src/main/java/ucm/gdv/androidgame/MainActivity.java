package ucm.gdv.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.SurfaceView;

import ucm.gdv.engine.android.EngineAndroid;
import ucm.gdv.offthelinelogic.OffTheLineLogic;

public class MainActivity extends AppCompatActivity {
    EngineAndroid _engine;
    OffTheLineLogic _logic;
    SurfaceView _surface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _surface = new SurfaceView(this);//creación del surface
        _engine = new EngineAndroid(_surface, getAssets());//creación del motor
        _engine.init();//inicialización

        _logic = new OffTheLineLogic(_engine);//creación de la lógica

        _engine.setLogic(_logic);//asignación de la lógica al motor
        setContentView(_engine.getSurfaceView());//asignar el content view al surface view del motor
    }
    protected void onResume(){
        super.onResume();
        _engine.on_resume();
    }
    protected void onPause(){
        super.onPause();
        _engine.on_pause();
    }
}
