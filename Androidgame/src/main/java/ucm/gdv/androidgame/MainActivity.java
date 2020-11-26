package ucm.gdv.androidgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
        _logic = new OffTheLineLogic(_engine);
        _engine.setLogic(_logic);
        _engine.init();
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
}
