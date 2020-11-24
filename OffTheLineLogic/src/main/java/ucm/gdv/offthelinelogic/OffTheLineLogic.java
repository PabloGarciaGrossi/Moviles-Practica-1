package ucm.gdv.offthelinelogic;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ucm.gdv.engine.Engine;
import ucm.gdv.engine.Logic;
import ucm.gdv.offthelinelogic.Gameobjects.Coin;
import ucm.gdv.offthelinelogic.Gameobjects.GameObject;
import ucm.gdv.offthelinelogic.Gameobjects.Path;
import ucm.gdv.offthelinelogic.Gameobjects.Player;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.json.simple.parser.JSONParser;

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
        JsonArray levelsArray = (JsonArray) levels.get(0);
        //System.out.println(levelsArray);

        JsonObject levelread = (JsonObject)levelsArray.get(level);

        //Loading paths #####################################################
        JsonArray paths = (JsonArray) levelread.get("paths");
        for (int j = 0; j < paths.size(); j++) {
            JsonObject vertex = (JsonObject) paths.get(j);
            JsonArray _v = (JsonArray) vertex.get("vertices");
            Path p = new Path("blue");
            for (int i = 0; i < _v.size(); i++) {
                JsonObject actualVertex = (JsonObject) _v.get(i);

                BigDecimal x = (BigDecimal) actualVertex.get("x");
                BigDecimal y = (BigDecimal) actualVertex.get("y");

                p.addVertex(x.floatValue(), y.floatValue());
            }

            //Comprobamos que vengan indicadas las direcciones
            if ((JsonArray) vertex.get("directions") != null) {

            }
            p.createDirections();
            gameObjects.add(p);
            _paths.add(p);
        }
        // ################################################################

        // Carga de monedas ###############################################
        JsonArray items = (JsonArray) levelread.get("items");
        System.out.println(items);

        for (int i = 0; i < items.size(); i++)
        {
            JsonObject actualItem = (JsonObject) items.get(i);

            BigDecimal x = (BigDecimal) actualItem.get("x");
            BigDecimal y = (BigDecimal) actualItem.get("y");

            Coin nCoin = new Coin(x.floatValue(), y.floatValue(), "yellow", 14f);

            BigDecimal speedBD, angleBD, radiusBD = null;
            if(actualItem.get("speed") != null) {
                speedBD = (BigDecimal) actualItem.get("speed");
                nCoin.set_speed(speedBD.floatValue());
            }
            if(actualItem.get("radius") != null) {
                radiusBD = (BigDecimal) actualItem.get("radius");
                nCoin.set_radius(radiusBD.floatValue());
            }
            if(actualItem.get("angle") != null) {
                angleBD = (BigDecimal) actualItem.get("angle");
                nCoin.set_angle(angleBD.floatValue());
            }
            gameObjects.add(nCoin);
        }

        Player player = new Player(0,0, "yellow", 7f, _paths.get(0));
        gameObjects.add(player);

        //################################################################


    }
    private Engine _engine;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private FileReader reader;
    private JsonArray levels;
    List<Path> _paths = new ArrayList<>();
}