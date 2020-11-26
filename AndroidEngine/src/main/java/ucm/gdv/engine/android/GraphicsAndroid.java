package ucm.gdv.engine.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ucm.gdv.engine.Graphics;
import ucm.gdv.engine.Font;
import ucm.gdv.engine.Logic;

public class GraphicsAndroid implements ucm.gdv.engine.Graphics {

    SurfaceView surfaceView_;
    Paint _paint;
    AssetManager _manager;
    Canvas canvas_;
    SurfaceHolder _holder;

    public GraphicsAndroid(SurfaceView surfaceView, AssetManager manager, SurfaceHolder holder){
        surfaceView_ = surfaceView;
        _manager = manager;
        _holder=holder;
        _paint = new Paint();
    }

    public void onDraw(float x1, float y1, float x2, float y2){

    };

    public Font newFont(String filename, int size, Boolean isBold){
        _font = new FontAndroid();
        _font.font = _font.font.createFromAsset(_manager, filename);
        _paint.setTypeface(_font.font);
        _paint.setTextSize(size);
        _paint.setFakeBoldText(isBold);
        return _font;
    };

    public void render(Logic lo){
        while(!_holder.getSurface().isValid())
            ;
        canvas_ = _holder.lockCanvas();
        clear("Black");
        save();
        translate(getWidth()/2, getHeight()/2);
        scale(calculateScale());
        rotate(180);
        lo.render();
        _holder.unlockCanvasAndPost(canvas_);
    }

    public void clear (String color){
        setColor(color);
        fillRect(0, 0, (int)getWidth(), (int)getHeight());
    };

    public void translate(float x, float y){
        canvas_.translate(x, y);
    };

    public void scale (float x){
        canvas_.scale(x, x);
    };

    public void rotate(float angle){
        canvas_.rotate(angle);
    };

    public void save(){
        canvas_.save();
    };

    public void restore(){
        canvas_.restore();
    };

    public void setColor(String color){
        color = color.toLowerCase();
        int _colorRGBA;
        switch(color){
            case "red":
                _colorRGBA = Color.RED;
                break;
            case "yellow":
                _colorRGBA = Color.YELLOW;
                break;
            case "blue":
                _colorRGBA = Color.BLUE;
                break;
            case "white":
                _colorRGBA = Color.WHITE;
            default:
                _colorRGBA = Color.BLACK;
                break;
        }
        _paint.setColor(_colorRGBA);
    };

    public void drawLine(float x1, float y1, float x2, float y2){
        canvas_.drawLine(x1, y1, x2, y2, _paint);
    };

    public void fillRect(int x1, int y1, int x2, int y2){
        Rect r = new Rect();
        r.left = x1;
        r.right = x2;
        r.bottom = y2;
        r.top = y1;

        canvas_.drawRect(r, _paint);
    };

    public void drawText(String text, float x, float y){
        canvas_.drawText(text, (int)x, (int)y, _paint);
    };

    public float getWidth(){
        return surfaceView_.getWidth();
    };

    public float getHeight(){
        return surfaceView_.getHeight();
    };

    public float calculateScale(){
        float s1 = 0;
        float s2 = 0;
        s1 = getWidth() / _logicW;
        s2 = getHeight() / _logicH;
        if (s1 < s2)
            return s1;
        else return s2;
    };
    FontAndroid _font;

    private float _logicW = 640;
    private float _logicH = 480;
}
