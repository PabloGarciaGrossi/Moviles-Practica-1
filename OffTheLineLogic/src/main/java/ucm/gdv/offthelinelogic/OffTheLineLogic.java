package ucm.gdv.offthelinelogic;

import java.util.ArrayList;
import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.engine.Logic;
import ucm.gdv.offthelinelogic.Gameobjects.GameObject;
import ucm.gdv.offthelinelogic.Gameobjects.Square;

public class OffTheLineLogic implements Logic{
    public OffTheLineLogic(Engine e){
        _engine = e;
        gameObjects.add(new Square(0,0, 40, "blue", 10));
    }

    public void update(double deltaTime){
        for (GameObject o : gameObjects)
        {
            o.update(deltaTime);
        }
    }


    public void render(){
        for (GameObject o : gameObjects)
        {
            o.render(_engine);
        }
    }

    public void handleInput(){

    }

    public void loadLevel(){

    }
    private Engine _engine;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
}