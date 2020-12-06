package ucm.gdv.engine.pc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.FileInputStream;
import java.io.InputStream;
import ucm.gdv.engine.pc.FontPC;
import ucm.gdv.engine.Font;
import java.lang.reflect.Field;

import javax.swing.JFrame;

import ucm.gdv.engine.Logic;


public class GraphicsPC implements ucm.gdv.engine.Graphics {

    public GraphicsPC(JFrame win, int w, int h){

        //Inicialización de la ventana del JFrame
        _window = win;
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
        //Creación del bufferStrategy para asignárselo a los Graphics de PC
        _strategy = _window.getBufferStrategy();
        _graphics = _strategy.getDrawGraphics();

    }

    public void render(Logic logic){
        do {
            do {
                _graphics = _strategy.getDrawGraphics();
                try {
                    clear(0xFF000000);
                    //Guarda el estado anterior para las posibles transformaciones
                    save();
                    //traslaciónb del canvas para que el 0,0 se encuentre en el centro de la pantalal
                    translate(getWidth()/2, getHeight()/2);
                    //Escalado de la pantalla para que se adecue al tamaño de ventana
                    scale(calculateScale());
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

    //Lectura y carga de una nueva fuente a utilizar
    public Font newFont(String filename, int size, Boolean isBold){
        FontPC baseFont =  new FontPC();
        //Se lee la fuente de Assets y si existe, se crea y se le establecen los valores introducidos
        String file = "Assets/Fonts/" + filename;
        try (InputStream is = new FileInputStream(file)) {
            baseFont.font = baseFont.font.createFont(java.awt.Font.TRUETYPE_FONT, is);

            if(isBold) baseFont.font = baseFont.font.deriveFont(java.awt.Font.BOLD, size);
            else baseFont.font = baseFont.font.deriveFont(java.awt.Font.PLAIN, size);

            _font=baseFont;
        }
        catch (Exception e) {
            // Ouch. No está.
            System.err.println("Error cargando la fuente: " + e);
        }

        //Devuelve la fuente independientemente de si ha sido cargada o no
        return baseFont;
    }

    //Pinta de un color la pantalla
    public void clear (int color){
        setColor(color);
        fillRect(0, 0,(int) getWidth(),(int) getHeight());
    }

    //Traslada el canvas la cantidad indicada en x e y
    public void translate(float x, float y){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.translate(x, y);
    }

    //Escala la altura y la anchura del canvas las proporciones indicadas.
    //El eje y se invierte para que la y incremente hacia arriba y no hacia abajo como lo hace por defecto
    public void scale (float x){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.scale(x, -x);
    }

    //Igual que el escalado anterior, solo que permite escalar la altura y la anchura con valores diferentes
    public void scale (float x, float y){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.scale(x, -y);
    }

    //Rota el canvas el ángulo indicado
    public void rotate(float angle){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.rotate(Math.toRadians(angle));
    }

    //Guarda el estado de los gráficos
    public void save(){
        Graphics2D _g = (Graphics2D) _graphics;
        old = _g.getTransform();
    }

    //Recupera el antiguo estado de los gráficos antes de las transformaciones que se hayan realizado
    public void restore(){
        Graphics2D _g = (Graphics2D) _graphics;
        _g.setTransform(old);
    }

    //Calcula cuánto ha de escalarse el juego dependiendo del tamaño de la lógica y del tamaño actual de la ventana.
    //SE escalará siempre al menor de los dos valores
    public float calculateScale(){
        float s1 = 0;
        float s2 = 0;
        s1 = getWidth()/_logicW;
        s2 = getHeight()/_logicH;
        if (s1 < s2)
            return s1;
        else return s2;
    }

    //Establece el próximo color que se va a utilizar para dibujar en pantalal
    public void setColor(int color){
        Color c = new Color(color, true);

        _graphics.setColor(c);
    }

    //Dibuja una línea desde las posiciones x1, y1 a las posiciones x2, y2
    public void drawLine(float x1, float y1, float x2, float y2){
        _graphics.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

    //Dibuja un cuadrilátero
    public void fillRect(int x1, int y1, int x2, int y2){
        _graphics.fillRect(x1, y1, x2, y2);
    }

    //Dibuja el texto indicado en pantalla en las posiciones indicadas con la fuente establecida previamente
    public void drawText(String text, float x, float y){
        save();
        scale(1, 1);
        _graphics.setFont(_font.font);
        _graphics.drawString(text, (int) x, (int) y);
        restore();
    }

    public float getWidth(){
        return _window.getWidth();
    }

    public float getHeight(){
        return _window.getHeight();
    }

    JFrame _window;
    private java.awt.Graphics _graphics;
    private java.awt.image.BufferStrategy _strategy;
    private AffineTransform old;

    private float _logicW = 640;
    private float _logicH = 480;

    FontPC _font;
}