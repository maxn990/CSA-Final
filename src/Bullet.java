import java.awt.*;

public class Bullet extends GameObject{
    private boolean hit;
    private double xSpeed;
    private double ySpeed;
    private boolean goLeft;
    private double angle;
    private int initSpeed;


    public Bullet(GameObject o, int targetX, int targetY, int speed){
        super(o.getX(), o.getY(), 5, 5, new Color(255, 109, 0, 215));
        hit = false;

        // Trig code inspired by stack overflow:
        // https://stackoverflow.com/questions/27554412/java-shoot-towards-mouse

        double tan = (((double) (targetY-o.getY())/(targetX-o.getX())));
        double ang = Math.atan(tan);
        this.xSpeed = speed * Math.cos(ang);
        this.ySpeed = speed * Math.sin(ang);
        angle = ang;

        speed = initSpeed;

        if (targetX < o.getX()) goLeft = true;
    }

    public void setSpeed(int speed){
        this.xSpeed = speed * Math.cos(angle);
        this.ySpeed = speed * Math.sin(angle);
        initSpeed = speed;
    }

    public int getSpeed(){
        return initSpeed;
    }

    public void setToObjectX(GameObject o){
        setX(o.getX());
    }

    public void setToObjectY(GameObject o){
        setY(o.getY());
    }

    public void setLocToItem(GameObject o){
        moveTo(o.getX(), o.getY());
    }

    public boolean hasBeenHit(){
        return hit;
    }

    public void setToHit(){
        hit = true;
    }

    public void move(){
        if (!goLeft)
            moveTo((int) (getX() + xSpeed), (int) (getY() + ySpeed));
        else
            moveTo((int) (getX() - xSpeed), (int) (getY() - ySpeed));
    }
}
