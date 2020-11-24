package ucm.gdv.offthelinelogic.Gameobjects;

import java.sql.Struct;

public class Enemy extends GameObject {
    public Enemy(int x, int y, int size, String color, float angle, float speed){
        super(x,y,size,color);
        _angle = angle;
        _speed = speed;
    }

    private float _angle;
    private float _speed;
}
