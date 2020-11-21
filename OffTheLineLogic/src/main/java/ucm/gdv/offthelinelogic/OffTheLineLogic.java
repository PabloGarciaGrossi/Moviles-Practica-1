package ucm.gdv.offthelinelogic;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.engine.Logic;
import ucm.gdv.offthelinelogic.Gameobjects.GameObject;
import ucm.gdv.offthelinelogic.Gameobjects.Path;
import ucm.gdv.offthelinelogic.Gameobjects.Square;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class OffTheLineLogic implements Logic{
    public OffTheLineLogic(Engine e){
        _engine = e;
        //gameObjects.add(new Square(0,0, 40, "blue", 10));
        try{
            reader = new FileReader("Assets/levels.json");
            levels = Jsoner.deserializeMany(reader);
        } catch(Exception exc){
            System.err.println("Error cargando los niveles: " + e);
        }
        loadLevel(0);
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

    public void loadLevel(int level){
        JsonArray o = (JsonArray) levels.get(level);
        System.out.println(o);
        JsonObject levelread = (JsonObject)o.get(0);
        System.out.println(levelread);

        //Loading paths
        JsonArray paths = (JsonArray) levelread.get("paths");
        System.out.println(paths);

        JsonObject vertex = (JsonObject) paths.get(0);
        JsonArray _v = (JsonArray) vertex.get("vertices");
        System.out.println(_v);
        Path p = new Path(0,0,0, "yellow");

        for (int i = 0; i < _v.size(); i++)
        {
            JsonObject actualVertex = (JsonObject) _v.get(i);

            BigDecimal x = (BigDecimal) actualVertex.get("x");
            BigDecimal y = (BigDecimal) actualVertex.get("y");

            p.addVertex(x.intValue(), y.intValue());
        }

        gameObjects.add(p);
        /*for (int i = 0; i < paths.size(); i++) {
            JsonObject v = (JsonObject) paths.get(i);
            for (int j = 0; j < v.size(); j++) {
                JsonObject vertex1 = (JsonObject) v.get(j);
                JsonObject vertex2 = null;
                if ((j + 1) != v.size())
                    vertex2 = (JsonObject) v.get(j + 1);
                else
                    vertex2 = (JsonObject) v.get(0);
                BigDecimal x1 = (BigDecimal) vertex1.get("x");
                BigDecimal y1 = (BigDecimal) vertex1.get("y");
                BigDecimal x2 = (BigDecimal) vertex2.get("x");
                BigDecimal y2 = (BigDecimal) vertex2.get("y");
                p.addLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
            }
        }
        gameObjects.add(p);*/
    }
    private Engine _engine;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private FileReader reader;
    private JsonArray levels;
}