import java.awt.*;

public class Enemy extends GameObject{
    private int speed;
    private int direction;
    private boolean isAlive;

    public Enemy(int x, int y, int width, int height, Color color, int speed, int direction) {
        super(x, y, width, height, color);
        this.speed = speed;
        this.direction = direction;
        isAlive = true;
    }

    public Enemy(int x, int y) {
        super(x, y);
        speed = 5;
        direction = 1;
        isAlive = true;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getSpeed(){
        return speed;
    }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public void kill(){
        isAlive = false;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void move(){
        if (direction == 0) setY(getY() - speed); // up
        if (direction == 1) setY(getY() + speed); // down
        if (direction == 2) setX(getX() - speed); // left
        if (direction == 3) setX(getX() + speed); // right
    }
}
