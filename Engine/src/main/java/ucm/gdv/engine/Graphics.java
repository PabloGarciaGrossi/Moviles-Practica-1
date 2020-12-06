package ucm.gdv.engine;

public interface Graphics {

    public Font newFont(String filename, int size, Boolean isBold);

    public void clear (int color);

    public void translate(float x, float y);

    public void scale (float x);

    public void rotate(float angle);

    public void save();

    public void restore();

    public void setColor(int color);

    public void drawLine(float x1, float y1, float x2, float y2);

    public void fillRect(int x1, int y1, int x2, int y2);

    public void drawText(String text, float x, float y);

    public float getWidth();

    public float getHeight();

    public float calculateScale();
}