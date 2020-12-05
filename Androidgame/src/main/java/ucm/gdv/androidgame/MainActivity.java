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

        _surface = new SurfaceView(this);
        _engine = new EngineAndroid(_surface, getAssets());
        _engine.init();

        if(savedInstanceState==null) {
            _logic = new OffTheLineLogic(_engine);
        }
        else {
            _logic = new OffTheLineLogic(_engine);
        }
        _engine.setLogic(_logic);
        setContentView(_engine.getSurfaceView());
    }
    protected void onResume(){
        super.onResume();
        _engine.on_resume();
    }
    protected void onPause(){
        super.onPause();
        _engine.on_pause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
