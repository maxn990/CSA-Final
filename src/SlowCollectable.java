import java.awt.*;
import java.util.ArrayList;

public class SlowCollectable extends CollectableItem{
    Collection enemies;
    public SlowCollectable(int x, int y, int width, int height, Color color, int pointVal, ArrayList<Color> colors, int luxDistance, EnemyCollection enemies) {
        super(x, y, width, height, color, pointVal, colors, luxDistance);
        this.enemies = enemies;
    }

    private void slowGroup(){
        for (Enemy enemy: ((EnemyCollection) enemies).getList()) enemy.setSpeed(enemy.getSpeed() - 3);
    }

    @Override
    public void setToFound() {
        setFound(true);
        slowGroup();
    }

    @Override
    void addToScore() {
        Board.score += 3;
    }
}
