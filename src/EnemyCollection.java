import java.awt.*;
import java.util.ArrayList;

public class EnemyCollection implements Collection {
    private ArrayList<Enemy> enemies;
    private int max;

    public EnemyCollection(ArrayList<Enemy> enemies, int max) {
        this.enemies = enemies;
        this.max = max;
    }

    public ArrayList<Enemy> getList(){
        return enemies;
    }

    public void setMax(int max){
        this.max = max;
    }

    public int getMax(){
        return max;
    }

    public void add(GameObject item){
        if (enemies.size() < max) enemies.add((Enemy) item);
    }

    public void drawAll(Graphics window){
        for (Enemy e: enemies) e.draw(window);
    }

    public void moveAll(){
        for (Enemy e: enemies) e.move();
    }

    public void clear(){
        enemies.clear();
    }

    public void removeDeadEnemies(){
        ArrayList<Enemy> dead = new ArrayList<>();
        for (Enemy e: enemies) if (!e.isAlive()) dead.add(e);
        for (Enemy e: dead) enemies.remove(e);
    }

    public void changeDirection(){
        for (Enemy e: enemies){
            if (e.getX() <= 0) {
                e.setX(1);
                e.setDirection((int) (Math.random() * 4));
            }
            else if (e.getX() + e.getWidth() >= Game.WIDTH) {
                e.setX(Game.WIDTH - 1 - e.getWidth());
                e.setDirection((int) (Math.random() * 4));
            }
            else if (e.getY() <= 0) {
                e.setY(1);
                e.setDirection((int) (Math.random() * 4));
            }
            else if (e.getY() + 2 * e.getHeight() >= Game.HEIGHT) {
                e.setY(Game.HEIGHT - 1 - 2 * e.getHeight());
                e.setDirection((int) (Math.random() * 4));
            }
            if (((int) (Math.random() * 30)) == 4) e.setDirection((int) (Math.random() * 4));
        }
    }

    public Enemy getRandomEnemy(){
        int num = (int) (Math.random() * enemies.size());
        return enemies.get(num);
    }

    public void incrementAllSpeed(){
        for (Enemy enemy: enemies) enemy.setSpeed(enemy.getSpeed() + 1);
    }
}
