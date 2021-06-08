import java.awt.Graphics;

public interface Collection {
    void add(GameObject item);
    void clear();
    void drawAll(Graphics window);
    void moveAll();
}
