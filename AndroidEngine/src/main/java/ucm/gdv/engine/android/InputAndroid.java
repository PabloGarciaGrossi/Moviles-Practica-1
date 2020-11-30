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
                    e.posx = (int)event.getX();
                    e.posy=(int)event.getY();
                    events.add(e);
                    return true;
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){
                    TouchEvent e= new TouchEvent();
                    e.id = event.getDeviceId();
                    e.typeEvent = type.SOLTAR;
                    e.posx = (int)event.getX();
                    e.posy=(int)event.getY();
                    events.add(e);
                    return true;
                }
                else if(event.getAction()==MotionEvent.ACTION_MOVE){
                    TouchEvent e = new TouchEvent();
                    e.id = event.getDeviceId();
                    e.typeEvent = type.DESPLAZAR;
                    e.posx = (int)event.getX();
                    e.posy=(int)event.getY();
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
    SurfaceView _view;
    ArrayList<TouchEvent> events;
}
