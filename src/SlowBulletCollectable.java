import java.awt.Color;
import java.util.ArrayList;

public class SlowBulletCollectable extends CollectableItem{
    BulletManager bullets;

    public SlowBulletCollectable(int x, int y, int width, int height, Color color, int pointVal, ArrayList<Color> colors, int luxDistance, BulletManager bullets) {
        super(x, y, width, height, color, pointVal, colors, luxDistance);
        this.bullets = bullets;
    }

    @Override
    public void setToFound() {
        setFound(true);
        bullets.stop();
        Board.markedTime = Board.time;
    }

    @Override
    void addToScore() {

    }
}
