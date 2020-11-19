package ucm.gdv.engine.pc;

import java.awt.Color;
import java.awt.Font;
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
                    logic.render();
                    setColor("yellow");
                    /*
                    setColor("yellow");
                    drawLine(-getWidth()/3, getHeight()/2, getWidth()/3, getHeight()/2);
                    _graphics.setFont(newFont("Bangers-Regular.ttf", 24, true));
                    drawText("hola mundo", -24, getHeight()/2);
                    */

                    //Dibujar cosas
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
        fillRect(0, 0, getWidth(), getHeight());
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
            default:
                _colorbg = Color.black;
                break;
        }
        _graphics.setColor(_colorbg);
    }

    public void drawLine(int x1, int y1, int x2, int y2){
        Coord coord1=conversionCoord(x1, y1);
        Coord coord2=conversionCoord(x2, y2);
        _graphics.drawLine(coord1.X, coord1.Y, coord2.X, coord2.Y);
    }

    public void fillRect(int x, int y, int w, int h){
        Coord coord=conversionCoord(x, y);
        _graphics.fillRect(coord.X, coord.Y, w, h);
    }

    public void drawText(String text, int x, int y){
        Coord coord=conversionCoord(x, y);
        _graphics.drawString(text, coord.X, coord.Y);
    }

    public int getWidth(){
        return _window.getWidth();
    }

    public int getHeight(){
        return _window.getHeight();
    }

    private Coord conversionCoord(int x, int y){
        Coord coord = new Coord(x+9, y+38);
        return coord;
    };

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
}