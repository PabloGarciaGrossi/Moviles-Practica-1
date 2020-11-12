package ucm.gdv.engine;

import java.awt.Font;
public interface Graphics {
    public void onDraw(float x1, float y1, float x2, float y2);

    public Font newFont(String filename, int size, Boolean isBold);

    public void clear (String color);

    public void translate(int x, int y);

    public void scale (int x, int y);

    public void rotate(float angle);

    public void save();

    public void restore();

    public void setColor(String color);

    public void drawLine(int x1, int y1, int x2, int y2);

    public void fillRect(int x1, int y1, int x2, int y2);

    public void drawText(String text, int x, int y);

    public int getWidth();

    public int getHeight();
}