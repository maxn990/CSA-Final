import java.util.ArrayList;
import java.awt.Graphics;

public class Walls implements Collection {
    private final ArrayList<Wall> walls;

    public Walls(){
        walls = new ArrayList<>();
    }

    public void add(GameObject item){
        walls.add((Wall) item);
    }

    public void drawAll(Graphics window){
        for (Wall wall: walls) wall.draw(window);
    }

    public void moveAllMovableWalls(){
        for (Wall wall: walls)
            if (wall.doesMove())
                wall.move();
    }

    public void clear(){
        walls.clear();
    }

    public void checkAllForBulletCollision(BulletManager manager){
        for (Wall wall: walls)
            wall.checkBulletCollision(manager);
    }

    public void hitAll(GameObject other){
        for(Wall wall: walls)
            wall.hit(other);
    }

    @Override
    public void moveAll() {

    }
}
