import java.awt.*;

public class Hero extends GameObject{
    private int speed;
    private int direction;
    private boolean isMoving;

    public Hero(){
        super(0, 0);
        speed = 10;
        direction = 0;
        isMoving = false;
    }

    public Hero(int x, int y, int speed){
        super(x, y);
        this.speed = speed;
        direction = 0;
        isMoving = false;
    }

    public Hero(int x, int y, int width, int height, Color color, int speed){
        super(x, y, width, height, color);
        this.speed = speed;
    }

    public void startMoving(){
        isMoving = true;
    }

    public void stopMoving(){
        isMoving = false;
    }

    public boolean moving(){
        return isMoving;
    }

    public void setDirection(int direction){
        this.direction = direction;
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

    public void move(){
        this.direction = direction;
        if (direction == 0) setY(getY() - speed); // up
        if (direction == 1) setY(getY() + speed); // down
        if (direction == 2) setX(getX() - speed); // left
        if (direction == 3) setX(getX() + speed); // right
        if (getX() + getWidth() > Game.WIDTH) setX(0);
        if (getX() < 0) setX(Game.WIDTH - getWidth());
        if (getY() < 0) setY(Game.HEIGHT - getHeight());
        if (getY() > Game.HEIGHT - getHeight()) setY(0);
    }
}
