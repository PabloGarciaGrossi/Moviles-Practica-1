package ucm.gdv.engine;

public interface Logic {
    public void update(double deltaTime);
    public void render();
    public void handleInput();
}
