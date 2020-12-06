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
import ucm.gdv.offthelinelogic.Gameobjects.Callback;
import ucm.gdv.offthelinelogic.Gameobjects.Coin;
import ucm.gdv.offthelinelogic.Gameobjects.Enemy;
import ucm.gdv.offthelinelogic.Gameobjects.GameObject;
import ucm.gdv.offthelinelogic.Gameobjects.Path;
import ucm.gdv.offthelinelogic.Gameobjects.Player;
import ucm.gdv.offthelinelogic.Gameobjects.TextButton;
import ucm.gdv.offthelinelogic.Gameobjects.UI;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import javax.xml.soap.Text;


public class OffTheLineLogic implements Logic{
    //booleano para indicar si es el modo difícil
    boolean hardMode=false;

    /*Inicialización de la lógica:
    * Se intenta leer el archivo de niveles y guardarlos para su posterior carga.
    * Inicialización de la fuente que vamos a utilizar
    * Carga del primer nivel
    * Inicialización de la interfaz
    * Se mete en la pila de estados el estado del menú*/
    public OffTheLineLogic(Engine e){
        _engine = e;
        try{
            reader = new InputStreamReader(_engine.openInputStream("levels.json"));
            levels = Jsoner.deserializeMany(reader);
        } catch(Exception exc){
            System.err.println("Error cargando los niveles: " + e);
        }
        Font f = _engine.getGraphics().newFont("Bangers-Regular.ttf", 32, false);
        loadLevel(actLVL);
        _level._info = new UI(50, 170, 10, hardMode, lives);

        pila.push(_menu);
    }


    //Se llama al update del elemento superior de la pila de estados
    public void update(double deltaTime){
        pila.peek().update(deltaTime);
    }

    //Se llama al render del elemento superior de la pila de estados
    public void render(){
        pila.peek().render(_engine);
    }

    //Se llama al handleInput del elemento superior de la pila de estados
    public void handleInput(){
        pila.peek().handleInput(_engine);
    }

    /*Finaliza nivel
    * Reinicia el nivel actual
    * Reduce vidas y si el jugador se ha quedado sin vidas lo devuelve de nuevo al menú principal eliminando el estado de nivel de la pila de estados*/
    public void playerDeath(){
        endLevel();
        loadLevel(actLVL);
        lives -= 1;
        _level._info.lives = lives;
        if(lives == 0)
            pila.pop();
    }

    //Comprobación de que el jugador no se haya salido de los límites del canvas para matarlo en caso de que ocurra
    public void checkPlayerOutofBounds(){
        if (_level._player.getPos().x > _engine.getGraphics().getWidth()/2 ||
                _level._player.getPos().y > _engine.getGraphics().getHeight()/2 ||
                _level._player.getPos().x < -_engine.getGraphics().getWidth()/2 ||
                _level._player.getPos().y <  -_engine.getGraphics().getHeight()/2)
            playerDeath();
    }

    //Comprobación de la colisión entre dos segmentos
    public Point pathCollision(Segment s1, Segment s2){
        return Utils.segmentCollition(s1.p1, s1.p2, s2.p1, s2.p2);
    }

    /*Comprueba la colisión entre un path y el jugador*/
    public void checkPathCollision(){
        int i = 0;
        boolean found = false;
        //Segmento de colisión del jugador: posición en el frame anterior y posición actual
        Segment col = _level._player.get_collisionSegment();
        //Solo comprueba la colisión si está saltando
        if(_level._player.jumping) {
            /*Se recorren cada uno de los paths del nivel y se comprueba
            * la colisión con cada uno de los segmentos de dicho path.
            * En caso de haber colisión, se comprueba que ese path no sea el mismo que el actual
            * y se actualiza la posición del jugador con la nueva posición de colisión con el path,
            * se asigna su nueva dirección y segmento en el que se encuentra.
            * Tras haber encontrado la primera colisión, se termina la búsqueda.*/
            while (!found && i < _level._paths.size()) {
                int j = 0;
                while (!found && j < _level._paths.get(i).getSegments().size()) {
                    Point corte = pathCollision(col, _level._paths.get(i).getSegments().get(j));
                    if (corte != null && _level._player.getDistance() > 5f) {
                        Segment s = _level._paths.get(i).getSegments().get(j);
                        if (s != _level._player.get_actualSegment()) {
                            Path p = _level._paths.get(i);
                            _level._player.setNewDirSegment(s, p);
                            _level._player.setPos(corte);
                            found = true;
                        }
                    }
                    j++;
                }
                i++;
            }
        }
    }

    /*Similar a la colisión con los paths, solo que llama a la muerte del jugador y reinicia el nivel*/
    public void checkEnemyCollision(){
            Segment col = _level._player.get_collisionSegment();
            int i = 0;
            boolean found = false;
            while (!found && i < _level._enemies.size()) {
                if (pathCollision(col, _level._enemies.get(i).get_segment()) != null) {
                    playerDeath();
                    found = true;
                }
                i++;
            }
    }

    /*Comprueba únicamente cuando el jugador está saltando si este se encuentra cerca del centro de una de las otras monedas.
    * En caso de que sea así, inicia la animación de muerte de las monedas.*/
    public void checkCoinCollision(){
        if(_level._player.jumping) {
            for (int m = 0; m < _level._coins.size(); m++) {
                if (Utils.sqrDistancePointPoint(_level._coins.get(m).getPos(), _level._player.getPos()) < 22f) {
                    _level._coins.get(m).initDeath();
                }
            }
        }
    }

    //Para cada una de las monedas que ya haya terminado su tiempo de animación de muerte, las elimina de la lista de monedas
    public void removeCoins(){
        for (int m = 0; m < _level._coins.size(); m++){
            if(_level._coins.get(m).isDead())
                _level._coins.remove(_level._coins.get(m));
        }
    }

    /*Comprueba que el nivel se ha finalizado.
    * Este habrá finalizado si la lista de monedas se encuentra vacía.
    * Incrementará el nivel actual y si este es mayor que el último nivel, en este caso el 19, finaliza la partida.
    * Sino, vaciará las listas de paths y de enemies y cargará el siguiente nivel.*/
    public void lvlFinished(){
        if(_level._coins.isEmpty()) {
            actLVL += 1;
            if(actLVL > 19)
            {
                pila.pop();
            }
            else {
                endLevel();
                loadLevel(actLVL);
            }
        }
    }

    //Lectura del json
    public void loadLevel(int level){
        //Selecciona el primer archivo del json que corresponde con el archivo entero en este caso, por eso siempre se selecciona el 0
        JsonArray levelsArray = (JsonArray) levels.get(0);

        //Selección del nivel actual a cargar
        JsonObject levelread = (JsonObject)levelsArray.get(level);


        String name = levelread.get("name").toString();
        _level.lvlName = Integer.toString(level) + " " + name;

        //Loading paths #####################################################
        JsonArray paths = (JsonArray) levelread.get("paths");

        /*Para cada uno de los paths del nivel se leen sus vértices*/
        for (int j = 0; j < paths.size(); j++) {
            JsonObject vertex = (JsonObject) paths.get(j);
            JsonArray _v = (JsonArray) vertex.get("vertices");
            Path p = new Path(0xFFFFFFFF);
            for (int i = 0; i < _v.size(); i++) {
                JsonObject actualVertex = (JsonObject) _v.get(i);

                BigDecimal x = (BigDecimal) actualVertex.get("x");
                BigDecimal y = (BigDecimal) actualVertex.get("y");

                p.addVertex(x.floatValue(), y.floatValue());
            }

            //Inicialización de los segmentos según los vértices leídos
            p.createSegments();

            //Comprobamos que vengan indicadas las direcciones y se añaden a la lista de direcciones del path
            if ((JsonArray) vertex.get("directions") != null) {
                JsonArray dirs = (JsonArray) vertex.get("directions");
                for (int i = 0; i < _v.size(); i++) {
                    JsonObject actualDir = (JsonObject) dirs.get(i);

                    BigDecimal x = (BigDecimal) actualDir.get("x");
                    BigDecimal y = (BigDecimal) actualDir.get("y");

                    p.addDirection(x.floatValue(), y.floatValue());
                }
            }
            //se añade el path a la lista de paths del nivel
            _level._paths.add(p);
        }
        // ################################################################

        // Carga de monedas ###############################################
        JsonArray items = (JsonArray) levelread.get("items");

        //Para cada una de las monedas del nivel se lee su posición, radio, velocidad y ángulo y se le asignan dependiendo de si vienen indicados o no.
        for (int i = 0; i < items.size(); i++)
        {
            JsonObject actualItem = (JsonObject) items.get(i);

            BigDecimal x = (BigDecimal) actualItem.get("x");
            BigDecimal y = (BigDecimal) actualItem.get("y");

            Coin nCoin = new Coin(x.floatValue(), y.floatValue(), 0xFFFFFF00, 14f);

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
        // ################################################################

        // Carga de enemigos ###############################################
        try {
            JsonArray enemies = (JsonArray) levelread.get("enemies");
            //Igual que con las monedas, se inicializan cada uno de los valores de cada enemigo del nivel.
            for (int i = 0; i < enemies.size(); i++){
                JsonObject actualEnemy = (JsonObject) enemies.get(i);

                BigDecimal x = (BigDecimal) actualEnemy.get("x");
                BigDecimal y = (BigDecimal) actualEnemy.get("y");
                BigDecimal l = (BigDecimal) actualEnemy.get("length");


                Enemy e = new Enemy(x.floatValue(), y.floatValue(), l.floatValue(),0xFFFF0000);

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

        //Se crea el jugador pasándole el primer path del nivel y la velocidad correspondiente según la dificultad seleccionada
        float speed=250f;
        if(hardMode) speed = 400f;
        _level._player = new Player(0,0, 0xFF1E90FF, 12f, _level._paths.get(0), speed);

    }

    //Limpieza de cada lista de cada uno de los distintos tipos de objetos del nivel
    void endLevel(){
        _level._enemies.clear();
        _level._paths.clear();
        _level._coins.clear();
    }
    
    private Engine _engine;
    private InputStreamReader reader;
    private JsonArray levels;
    private Level _level = new Level();
    private Menu _menu = new Menu();

    public int lives = 10;
    public int actLVL = 0;

    Stack<State> pila = new Stack<>();

    //Interfaz estado para cada uno de los estados de la pila
    interface State{
        public void update(double deltaTime);
        public void render(Engine e);
        public void handleInput(Engine e);
    }

    //Clase nivel, contiene cada uno de los objetos del nivel actual
    class Level implements  State
    {
        public List<Path> _paths = new ArrayList<>();
        public List<Coin> _coins = new ArrayList<>();
        public List <Enemy> _enemies = new ArrayList<>();
        public Player _player;
        public String lvlName;
        public UI _info;

        //Realiza el update de cada uno de los objetos y comprueba colisiones y si el nivel se ha finalizado
        public void update(double deltaTime){
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

        //muestra el nombre del nivel y llama al render de cada uno de los objetos del nivel
        public void render (Engine e){
            e.getGraphics().setColor(0xFFFFFFFF);
            e.getGraphics().drawText(_level.lvlName, -300, -150);
            for (GameObject o : getGameobjects())
            {
                o.render(e);
            }
        }

        //Llama al handleInputde cada uno de los objetos
        public void handleInput(Engine e){
            for (GameObject o : getGameobjects())
            {
                o.handleInput(e);
            }
        }
        //Devuelve una única lista con todos los objetos
        public List<GameObject> getGameobjects(){
            List<GameObject> l = new ArrayList<>();
            l.addAll(_paths);
            l.addAll(_coins);
            l.addAll(_enemies);
            l.add(_info);
            l.add(_player);
            return l;
        }

        //Limpia cada una de las listas de objetos
        public void clearLevel(){
            _paths.clear();
            _coins.clear();
            _enemies.clear();
        }
    };

    //Callback del botón de modo fácil que inicializa el juego cargando el primer nivel con 10 vidas y la velocidad del jugador adecuada
    class easyModeCallback implements Callback{
        public void callfunction(){
            lives = 10;
            _level._info.lives = lives;
            actLVL = 0;
            _level.clearLevel();
            hardMode = false;
            loadLevel(actLVL);
            _level._info = new UI(50, 170, 10, hardMode, lives);
            pila.push(_level);
        }
    }

    //Callback del botón de modo fácil que inicializa el juego cargando el primer nivel con 5 vidas y la velocidad del jugador adecuada
    class hardModeCallback implements Callback{
        public void callfunction(){
            lives = 5;
            _level._info.lives = lives;
            actLVL = 0;
            _level.clearLevel();
            hardMode = true;
            loadLevel(0);
            _level._info = new UI(50, 170, 10, hardMode, lives);
            pila.push(_level);
        }
    }

    //Estado del menú
    class Menu implements State{
        TextButton easyModeButton;
        TextButton hardModeButton;
        //Inicialización de los dos botones del menú
        public Menu(){
            easyModeButton = new TextButton(-200, 50, 0, 0xFFFFFFFF, 100, 50, "EASY MODE",  new easyModeCallback());
            hardModeButton = new TextButton(-200, 100, 0, 0xFFFFFFFF, 100, 50, "HARD MODE", new hardModeCallback());
        }

        //no se requiere hacer el update de ningun objeto en el menú
        public void update(double deltaTime){

        }

        //Renderizado de ambos botones del menú
        public void render(Engine e){
            e.getGraphics().setColor(0xFF1E90FF);
            e.getGraphics().drawText("OFF THE LINE", -250, -100);
            e.getGraphics().drawText("A GAME COPIED TO BRYAN PERFETTO", -250, -50);
            easyModeButton.render(e);
            hardModeButton.render(e);
        }

        //comprobación del click sobre los botones
        public void handleInput(Engine e){
            easyModeButton.handleInput(e);
            hardModeButton.handleInput(e);
        }
    }

}