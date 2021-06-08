import java.awt.Color;

public class StandardWall extends Wall {
    public StandardWall(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void hit(GameObject o){
        if (o.getX() >= getX() && o.getX() <= getX() + getWidth() && o.getY() >= getY() && o.getY() <= getY() + getHeight()){
            int xChange = ((int) (Math.random() * 75)) - 50;
            int yChange = ((int) (Math.random() * 75)) - 50;
            o.moveTo(o.getX() + xChange, o.getY() + yChange);
        }
    }

    @Override
    public boolean doesMove() {
        return false;
    }

    @Override
    public void move() {

    }
}
