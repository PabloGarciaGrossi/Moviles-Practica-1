package ucm.gdv.engine.android;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ucm.gdv.engine.Input;

public class InputAndroid implements ucm.gdv.engine.Input{

    public InputAndroid(SurfaceView view){
        _view=view;
        events= new ArrayList<TouchEvent>();
        _view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    TouchEvent e= new TouchEvent();
                    e.id = event.getDeviceId();
                    e.typeEvent = type.PULSAR;
                    float x = event.getX();
                    float y = event.getY();
                    float w=_view.getWidth();
                    float h=_view.getHeight();
                    e.posx = (int)((event.getX() - _view.getWidth()/2) * 1/calculateScale());
                    e.posy = (int)((event.getY() - _view.getHeight()/2) * 1/calculateScale());
                    events.add(e);
                    return true;
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){
                    TouchEvent e= new TouchEvent();
                    e.id = event.getDeviceId();
                    e.typeEvent = type.SOLTAR;
                    e.posx = (int)(event.getX() * calculateScale() + _view.getWidth()/2);
                    e.posy = (int)(event.getY() * calculateScale() + _view.getHeight()/2);
                    events.add(e);
                    return true;
                }
                else if(event.getAction()==MotionEvent.ACTION_MOVE){
                    TouchEvent e = new TouchEvent();
                    e.id = event.getDeviceId();
                    e.typeEvent = type.DESPLAZAR;
                    e.posx = (int)(event.getX() * calculateScale() + _view.getWidth()/2);
                    e.posy = (int)(event.getY() * calculateScale() + _view.getHeight()/2);
                    events.add(e);
                    return true;
                }
                return false;
            }
        });
    }
    public List<TouchEvent> getTouchEvents(){
        List<TouchEvent> ret = new ArrayList<TouchEvent>(events);
        events.clear();
        return ret;
    }

    public float calculateScale(){
        float s1 = 0;
        float s2 = 0;
        s1 = _view.getWidth() / _logicW;
        s2 = _view.getHeight() / _logicH;
        if (s1 < s2)
            return s1;
        else return s2;
    };
    SurfaceView _view;
    private float _logicW = 640;
    private float _logicH = 480;
    ArrayList<TouchEvent> events;
}
