package ucm.gdv.offthelinelogic;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import ucm.gdv.engine.Engine;
import ucm.gdv.engine.Font;
import ucm.gdv.engine.Input;
import ucm.gdv.engine.Logic;
import ucm.gdv.offthelinelogic.Gameobjects.Coin;
import ucm.gdv.offthelinelogic.Gameobjects.Enemy;
import ucm.gdv.offthelinelogic.Gameobjects.GameObject;
import ucm.gdv.offthelinelogic.Gameobjects.Path;
import ucm.gdv.offthelinelogic.Gameobjects.Player;
import ucm.gdv.offthelinelogic.Gameobjects.UI;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class OffTheLineLogic implements Logic{
    public OffTheLineLogic(Engine e){
        _engine = e;
        //gameObjects.add(new Square(0,0, 40, "blue", 10));
        try{
            reader = new InputStreamReader(_engine.openInputStream("levels.json"));
            levels = Jsoner.deserializeMany(reader);
        } catch(Exception exc){
            System.err.println("Error cargando los niveles: " + e);
        }
        Font f = _engine.getGraphics().newFont("Bangers-Regular.ttf", 32, false);
        loadLevel(actLVL);
        _level._info = new UI(50, 170, 10, false, lives);

        pila.push(_menu);
    }

    private boolean AreColliding(GameObject o1, GameObject o2){
        if(o1 != o2){
            Point o1_p = new Point(o1.getPos().x - o1.getSize()/2, o1.getPos().y + o1.getSize()/2);

            Point o2_p = new Point(o2.getPos().x - o2.getSize()/2, o2.getPos().y + o2.getSize()/2);

            if(o1_p.x >= o2_p.x && o1_p.x<=o2_p.x+o2.getSize()
                    && o1_p.y>=o2_p.y && o1_p.y<=o2_p.y+o2.getSize()){
                return true;
            }
            else if(o2_p.x >= o1_p.x && o2_p.x<=o1_p.x+o1.getSize()
                    && o2_p.y>=o1_p.y && o2_p.y<=o1_p.y+o1.getSize()){
                return true;
            }
        }
        return false;
    }

    private void checkCollisions(){
        for(GameObject o1: _level.getGameobjects()){
            for(GameObject o2: _level.getGameobjects()){
                if(AreColliding(o1, o2)){
                    o1.OnCollision(o2);
                    o2.OnCollision(o1);
                };
            }
        }
    }

    public void update(double deltaTime){
        pila.peek().update(deltaTime);
    }


    public void render(){
        pila.peek().render(_engine);
    }

    public void handleInput(){
        pila.peek().handleInput(_engine);
    }

    public void playerDeath(){
        _level._player.playerDeath(_level._paths.get(0));
        lives -= 1;
        _level._info.lives = lives;
        if(lives == 0)
            pila.pop();
    }

    public void checkPlayerOutofBounds(){
        if (_level._player.getPos().x > _engine.getGraphics().getWidth()/2 ||
                _level._player.getPos().y > _engine.getGraphics().getHeight()/2 ||
                _level._player.getPos().x < -_engine.getGraphics().getWidth()/2 ||
                _level._player.getPos().y <  -_engine.getGraphics().getHeight()/2)
            playerDeath();
    }
    public boolean pathCollision(Segment s1, Segment s2){
        return Utils.segmentCollition(s1.p1, s1.p2, s2.p1, s2.p2);
    }

    public void checkPathCollision(){
        int i = 0;
        boolean found = false;
        Segment col = _level._player.get_collisionSegment();
        if(_level._player.jumping) {
            while (!found && i < _level._paths.size()) {
                int j = 0;
                while (!found && j < _level._paths.get(i).getSegments().size()) {
                    if (pathCollision(col, _level._paths.get(i).getSegments().get(j)) && _level._player.getDistance() > 5f) {
                        Segment s = _level._paths.get(i).getSegments().get(j);
                        if (s != _level._player.get_actualSegment()) {
                            Path p = _level._paths.get(i);
                            _level._player.setNewDirSegment(s, p);
                            found = true;
                        }
                    }
                    j++;
                }
                i++;
            }
        }
    }

    public void checkEnemyCollision(){
            Segment col = _level._player.get_collisionSegment();
            int i = 0;
            boolean found = false;
            while (!found && i < _level._enemies.size()) {
                if (pathCollision(col, _level._enemies.get(i).get_segment())) {
                    playerDeath();
                    found = true;
                }
                i++;
            }
    }

    public void checkCoinCollision(){
        for (int m = 0; m < _level._coins.size(); m++){
            if(Utils.sqrDistancePointPoint(_level._coins.get(m).getPos(), _level._player.getPos()) < 15f)
            {
                _level._coins.get(m).initDeath();
            }
        }
    }

    public void removeCoins(){
        for (int m = 0; m < _level._coins.size(); m++){
            if(_level._coins.get(m).isDead())
                _level._coins.remove(_level._coins.get(m));
        }
    }

    public void lvlFinished(){
        if(_level._coins.isEmpty()) {
            actLVL += 1;
            if(actLVL > 19)
            {
                working = false;
            }
            else {
                endLevel();
                loadLevel(actLVL);
            }
        }
    }

    public void loadLevel(int level){
        JsonArray levelsArray = (JsonArray) levels.get(0);

        JsonObject levelread = (JsonObject)levelsArray.get(level);

        String name = levelread.get("name").toString();
        _level.lvlName = Integer.toString(level) + " " + name;
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

            p.createSegments();

            //Comprobamos que vengan indicadas las direcciones
            if ((JsonArray) vertex.get("directions") != null) {
                JsonArray dirs = (JsonArray) vertex.get("directions");
                for (int i = 0; i < _v.size(); i++) {
                    JsonObject actualDir = (JsonObject) dirs.get(i);

                    BigDecimal x = (BigDecimal) actualDir.get("x");
                    BigDecimal y = (BigDecimal) actualDir.get("y");

                    p.addDirection(x.floatValue(), y.floatValue());
                }
            }
            _level._paths.add(p);
        }
        // ################################################################

        // Carga de monedas ###############################################
        JsonArray items = (JsonArray) levelread.get("items");

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
            _level._coins.add(nCoin);
        }

        try {
            JsonArray enemies = (JsonArray) levelread.get("enemies");
            for (int i = 0; i < enemies.size(); i++){
                JsonObject actualEnemy = (JsonObject) enemies.get(i);

                BigDecimal x = (BigDecimal) actualEnemy.get("x");
                BigDecimal y = (BigDecimal) actualEnemy.get("y");
                BigDecimal l = (BigDecimal) actualEnemy.get("length");


                Enemy e = new Enemy(x.floatValue(), y.floatValue(), l.floatValue(),"red");

                BigDecimal speedBD, angleBD, offset1, offset2, time1, time2 = null;
                if(actualEnemy.get("speed") != null) {
                    speedBD = (BigDecimal) actualEnemy.get("speed");
                    e.set_speed(speedBD.floatValue());
                }
                if(actualEnemy.get("angle") != null) {
                    angleBD = (BigDecimal) actualEnemy.get("angle");
                    e.set_angle(angleBD.floatValue());
                }
                if(actualEnemy.get("offset") != null) {
                    JsonObject _v = (JsonObject) actualEnemy.get("offset");
                    offset1 = (BigDecimal) _v.get("x");
                    offset2 = (BigDecimal) _v.get("y");
                    e.set_offset(offset1.floatValue(),offset2.floatValue());

                    time1 = (BigDecimal) actualEnemy.get("time1");
                    e.set_time1(time1.floatValue());

                    time2 = (BigDecimal) actualEnemy.get("time2");
                    e.set_time2(time2.floatValue());
                }
                _level._enemies.add(e);
            }
        } catch (Exception e){
            System.out.println("No hay enemigos en este nivel");
        }
        //################################################################

        _level._player = new Player(0,0, "white", 12f, _level._paths.get(0));

    }

    void endLevel(){
        _level._enemies.clear();
        _level._paths.clear();
        _level._coins.clear();
    }

    public boolean isWorking(){
        return  working;
    }
    
    private Engine _engine;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private InputStreamReader reader;
    private JsonArray levels;
    private Level _level = new Level();
    private Menu _menu = new Menu();

    public int lives = 10;
    public int actLVL = 0;
    public boolean working = true;

    Stack<State> pila = new Stack<>();

    interface State{
        public void update(double deltaTime);
        public void render(Engine e);
        public void handleInput(Engine e);
    }

    class Level implements  State
    {
        public List<Path> _paths = new ArrayList<>();
        public List<Coin> _coins = new ArrayList<>();
        public List <Enemy> _enemies = new ArrayList<>();
        public Player _player;
        public String lvlName;
        public UI _info;

        public void update(double deltaTime){
            if(isWorking()) {
                for (GameObject o : getGameobjects()) {
                    o.update(deltaTime);
                }
                checkEnemyCollision();
                checkPathCollision();
                checkCoinCollision();
                removeCoins();

                checkPlayerOutofBounds();
                lvlFinished();
            }
        }

        public void render (Engine e){
            e.getGraphics().setColor("White");
            e.getGraphics().drawText(_level.lvlName, -300, -150);
            for (GameObject o : getGameobjects())
            {
                o.render(e);
            }
        }

        public void handleInput(Engine e){
            for (GameObject o : getGameobjects())
            {
                o.handleInput(e);
            }
        }
        public List<GameObject> getGameobjects(){
            List<GameObject> l = new ArrayList<>();
            l.addAll(_paths);
            l.addAll(_coins);
            l.addAll(_enemies);
            l.add(_info);
            l.add(_player);
            return l;
        }

        public void clearLevel(){
            _paths.clear();
            _coins.clear();
            _enemies.clear();
        }
    };

    class Menu implements State{
        public void update(double deltaTime){

        }

        public void render(Engine e){
            e.getGraphics().setColor("White");
            e.getGraphics().drawText("Bienvenido al menu", -130, 0);
            e.getGraphics().drawText("Pulsa para empezar", -130, 100);
        }

        public void handleInput(Engine e){
            for(Input.TouchEvent event: e.getInput().getTouchEvents()){
                if(event.typeEvent==Input.type.PULSAR){
                    lives = 10;
                    _level._info.lives = lives;
                    actLVL = 0;
                    _level.clearLevel();
                    loadLevel(0);
                    pila.push(_level);
                }
            }
        }
    }

}