package ucm.gdv.offthelinelogic;

import java.util.ArrayList;
import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.offthelinelogic.Gameobjects.GameObject;
import ucm.gdv.offthelinelogic.Gameobjects.Square;

public class OffTheLineLogic {
    public OffTheLineLogic(Engine e){
        _engine = e;
        gameObjects.add(new Square(0,0, 40, "blue"));
    }

    public void update(){
        for (GameObject o : gameObjects)
        {
            o.update();
        }
    }


    public void render(){
        for (GameObject o : gameObjects)
        {
            o.render(_engine);
        }
    }

    public void loadLevel(){

    }
    private Engine _engine;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
}