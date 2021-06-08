import java.awt.*;
import java.util.ArrayList;

public class SpeedCollectable extends CollectableItem{
    private int speedBump;
    private GameObject target;

    public SpeedCollectable(int x, int y, int width, int height, Color color, int pointVal, ArrayList<Color> colors, int luxDistance, int speedBump, GameObject target) {
        super(x, y, width, height, color, pointVal, colors, luxDistance);
        this.speedBump = speedBump;
        this.target = target;
    }

    public SpeedCollectable(int x, int y, ArrayList<Color> colors) {
        super(x, y, colors);
    }

    public void setSpeedBump(int speedBump){
        this.speedBump = speedBump;
    }

    private void increaseSpeedOf(Hero hero){
        hero.setSpeed(hero.getSpeed() + speedBump);
    }

    @Override
    public void setToFound() {
        setFound(true);
        increaseSpeedOf((Hero) target);
    }

    @Override
    void addToScore() {
        Board.score += 2;
    }
}
