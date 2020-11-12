package ucm.gdv.engine.pc;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Field;
import java.awt.Graphics;

public class GraphicsPC implements ucm.gdv.engine.Graphics {

    public GraphicsPC(int w, int h){
        _w = w;
        _h = h;
    }
    public void onDraw(float x1, float y1, float x2, float y2){

    }

    public Font newFont(String filename, int size, Boolean isBold){
        return font;
    }

    public void clear (String color){
        setColor(color);
        fillRect(0, 0, _w, _h);
    }

    public void translate(int x, int y){

    }

    public void scale (int x, int y){

    }

    public void rotate(float angle){

    }

    public void save(){

    }

    public void restore(){

    }

    public void setColor(String colorName){
        Color color;
        try {
            Field field = Class.forName("java.awt.Color").getField(colorName);
            color = (Color)field.get(null);
            colorbg = color;
        } catch (Exception e) {
            color = null; // Not defined
            colorbg = Color.BLACK;
        }
        g.setColor(colorbg);
    }

    public void drawLine(int x1, int y1, int x2, int y2){
        g.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x1, int y1, int x2, int y2){
        g.fillRect(x1,y1,x2,y2);
    }

    public void drawText(String text, int x, int y){
        g.drawString(text, x, y);
    }

    public int getWidth(){
        return _w;
    }

    public int getHeight(){
        return _h;
    }

    private int _w;
    private int _h;
    private Color colorbg;
    private Graphics g;
    private Font font;

}