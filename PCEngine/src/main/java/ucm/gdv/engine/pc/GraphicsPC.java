package ucm.gdv.engine.pc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.swing.JFrame;

import ucm.gdv.engine.Logic;


public class GraphicsPC implements ucm.gdv.engine.Graphics {

    public GraphicsPC(int w, int h){
        _window = new JFrame("ventana");
        _window.setSize(w, h);
        _window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _window.setIgnoreRepaint(true);
        _window.setVisible(true);

        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                _window.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        _strategy = _window.getBufferStrategy();
    }

    public void render(Logic logic){
        do {
            do {
                _graphics = _strategy.getDrawGraphics();
                try {
                    clear("black");
                    //Dibujar cosas
                    save();
                    translate(getWidth()/2, getHeight()/2);
                    scale(calculateScale());
                    rotate(180);
                    logic.render();
                }
                finally {
                    _graphics.dispose();
                }
            } while(_strategy.contentsRestored());
            _strategy.show();
        } while(_strategy.contentsLost());
    }

    public void setGraphics(java.awt.Graphics graph){
        _graphics=graph;
    }
    public java.awt.image.BufferStrategy getBufferStrategy(){
        return _strategy;
    }

    public void onDraw(float x1, float y1, float x2, float y2){

    }

    public Font newFont(String filename, int size, Boolean isBold){
        Font baseFont =  null;
        String file = "Assets/Fonts/" + filename;
        try (InputStream is = new FileInputStream(file)) {
            baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
        }
        catch (Exception e) {
            // Ouch. No está.
            System.err.println("Error cargando la fuente: " + e);
        }
        if(isBold)
            return baseFont.deriveFont(Font.BOLD, size);
        else
            return baseFont.deriveFont(Font.PLAIN, size);
    }

    public void clear (String color){
        setColor(color);
        fillRect(0, 0,(int) getWidth(),(int) getHeight());
    }

    public void translate(float x, float y){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.translate(x, y);
    }

    public void scale (float x){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.scale(-x, x);
    }

    public void rotate(float angle){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.rotate(Math.toRadians(angle));
    }

    public void save(){
        Graphics2D _g = (Graphics2D) _graphics;
        old = _g.getTransform();
    }

    public void restore(){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.setTransform(old);
    }

    public float calculateScale(){
        float s1 = 0;
        float s2 = 0;
        s1 = getWidth()/_logicW;
        s2 = getHeight()/_logicH;
        if (s1 < s2)
            return s1;
        else return s2;
    }
    public void setColor(String colorName){
        colorName = colorName.toLowerCase();

        switch(colorName){
            case "red":
                _colorbg = Color.red;
                break;
            case "yellow":
                _colorbg = Color.yellow;
                break;
            case "blue":
                _colorbg = Color.blue;
                break;
            case "white":
                _colorbg = Color.white;
            default:
                _colorbg = Color.black;
                break;
        }
        _graphics.setColor(_colorbg);
    }

    public void drawLine(float x1, float y1, float x2, float y2){
        _graphics.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

    public void fillRect(int x, int y, int w, int h){
        _graphics.fillRect(x, y, w, h);
    }

    public void drawText(String text, float x, float y){
        _graphics.drawString(text, (int) x, (int) y);
    }

    public float getWidth(){
        return _window.getWidth();
    }

    public float getHeight(){
        return _window.getHeight();
    }
    private class Coord{
        public Coord(int x, int y){
            X=x;
            Y=y;
        }
        public int X, Y;
    }
    JFrame _window;
    private Color _colorbg;
    private java.awt.Graphics _graphics;
    private java.awt.image.BufferStrategy _strategy;
    private AffineTransform old;

    private float _logicW = 640;
    private float _logicH = 480;
}