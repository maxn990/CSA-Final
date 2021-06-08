import java.awt.*;
import java.util.ArrayList;

public class Collectables implements Collection {
    private ArrayList<CollectableItem> collectables;
    private int max;

    public Collectables(ArrayList<CollectableItem> collectables, int max) {
        this.collectables = collectables;
        this.max = max;
    }

    public void add(GameObject item) {
        if (collectables.size() < max) collectables.add((CollectableItem) item);
    }

    public void drawAll(Graphics window){
        for (CollectableItem c: collectables) c.draw(window);
    }

    public void removeFoundItems(){
        ArrayList<CollectableItem> foundItems = new ArrayList<>();
        for (CollectableItem c: collectables){
            if (c.isFound()) foundItems.add(c);
        }
        for (CollectableItem c: foundItems) collectables.remove(c);
    }

    public void clear(){
        collectables.clear();
    }

    @Override
    public void moveAll() {

    }

    public void changeAllColors(int index){
        for (CollectableItem c: collectables) c.changeColor(index);
    }

    public void changeAllColors(){
        for (CollectableItem c: collectables) c.changeColor();
    }

    public void changeAllSize(){
        for (CollectableItem c: collectables) c.changeSize();
    }

    public ArrayList<CollectableItem> getList(){
        return collectables;
    }
}
