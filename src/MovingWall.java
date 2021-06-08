import java.awt.Color;

public class MovingWall extends Wall{
    private boolean forward;

    public MovingWall(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        forward = true;
    }

    public void move(int x, int y){
        moveTo(x, y);
    }

    @Override
    public void hit(GameObject o) {
        if (o.getX() >= getX() && o.getX() <= getX() + getWidth() && o.getY() >= getY() && o.getY() <= getY() + getHeight()){
            o.moveTo((int) (Math.random() * 700), (int) (Math.random() * 600));
            Board.score--;
        }
    }

    @Override
    public boolean doesMove() {
        return true;
    }

    @Override
    public void move() {
        if (forward){
            moveTo(getX(), getY() + 1);
            if (getY() + (getHeight() + (getHeight() / 2)) >= Game.HEIGHT) forward = false;
        }
        if (!forward){
            moveTo(getX(), getY() - 1);
            if (getY() <= 0) forward = true;
        }
    }
}
