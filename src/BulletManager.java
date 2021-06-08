import java.awt.*;
import java.util.ArrayList;

public class BulletManager implements Collection {
    private ArrayList<Bullet> bullets;
    private boolean areStopped;

    public BulletManager(){
        bullets = new ArrayList<>();
        areStopped = false;
    }

    public void shoot(Bullet b){
        bullets.add(b);
    }

    public void add(GameObject item){
        bullets.add((Bullet) (item));
    }

    public ArrayList<Bullet> getList(){
        return bullets;
    }

    public void moveAll(){
        for (Bullet b: bullets) b.move();
    }

    public void drawAll(Graphics window){
        for (Bullet b: bullets)
            b.draw(window);
    }

    public void removeHitBullets(){
        ArrayList<Bullet> hitBullets = new ArrayList<>();
        for (Bullet b: bullets) if (b.hasBeenHit()) hitBullets.add(b);
        for (Bullet b: hitBullets) bullets.remove(b);
    }

    public boolean checkCollision(GameObject other){
        for (Bullet b: bullets)
            if (b.getX() >= other.getX() && b.getX() <= other.getX() + other.getWidth() && b.getY() >= other.getY() && b.getY() <= other.getY() + other.getHeight()) {
                b.setToHit();
                return true;
            }
        return false;
    }

    public boolean getAreStopped(){
        return areStopped;
    }

    public void setAreStopped(boolean areStopped){
        this.areStopped = areStopped;
    }

    public void resetSpeed(int speed){
        for (Bullet bullet: bullets) bullet.setSpeed(speed);
    }

    public void stop(){
        for (Bullet bullet: bullets) bullet.setSpeed(0);
        setAreStopped(true);
    }

    public void clear(){
        bullets.clear();
    }
}
