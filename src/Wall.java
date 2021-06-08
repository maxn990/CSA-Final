import java.awt.Color;
import java.util.ArrayList;

public abstract class Wall extends GameObject{
    public Wall(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    public void checkBulletCollision(BulletManager bm){
        for (Bullet bullet: bm.getList())
            if (bullet.getX() >= getX() && bullet.getX() <= getX() + getWidth() && bullet.getY() >= getY() && bullet.getY() <= getY() + getHeight())
                bullet.setToHit();
    }

    public abstract void hit(GameObject other);

    public abstract boolean doesMove();

    public abstract void move();
}
