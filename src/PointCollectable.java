import java.awt.*;
import java.util.ArrayList;

public class PointCollectable extends CollectableItem {

    public PointCollectable(int x, int y, int width, int height, Color color, int pointVal, ArrayList<Color> colors, int flexSize) {
        super(x, y, width, height, color, pointVal, colors, flexSize);
    }

    public PointCollectable(int x, int y, ArrayList<Color> colors) {
        super(x, y, colors);
    }

    @Override
    public void setToFound() {
        setFound(true);
        addToScore();
    }

    @Override
    void addToScore() {
        Board.score++;
    }
}
