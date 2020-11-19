package ucm.gdv.engine.pc;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.swing.JFrame;


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

    public void render(){
        do {
            do {
                _graphics = _strategy.getDrawGraphics();
                try {
                    setColor("black");
                    fillRect(0, 0, getWidth(), getHeight());
                    setColor("yellow");
                    drawLine(-getWidth()/3, getHeight()/2, getWidth()/3, getHeight()/2);
                    _graphics.setFont(newFont("Bangers-Regular.ttf", 24, true));
                    drawText("hola mundo", -24, getHeight()/2);
                    //Dibujar cosas
                }
                finally {
                    _graphics.dispose();
                }
            } while(_strategy.contentsRestored());
            _strategy.show();
        } while(_strategy.contentsLost());
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

        x1 += getWidth()/2;
        x2 += getWidth()/2;
        y1 = getHeight() - y1;
        y2 = getHeight() - y2;
        _graphics.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x1, int y1, int x2, int y2){
        _graphics.fillRect(x1,y1,x2,y2);
    }

    public void drawText(String text, int x, int y){
        x += getWidth()/2;
        y = getHeight() - y;
        _graphics.drawString(text, x, y);
    }

    public int getWidth(){
        return _window.getWidth();
    }

    public int getHeight(){
        return _window.getHeight();
    }

    JFrame _window;
    private Color _colorbg;
    private java.awt.Graphics _graphics;
    private java.awt.image.BufferStrategy _strategy;
}