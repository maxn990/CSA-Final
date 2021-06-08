import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class CollectableItem extends GameObject {
    private ArrayList<Color> colors;
    private int pointVal;
    private boolean found;
    private int fluxDistance;
    private boolean isIncreasing;
    private int initialWidth;

    public CollectableItem(int x, int y, int width, int height, Color color, int pointVal, ArrayList<Color> colors, int luxDistance) {
        super(x, y, width, height, color);
        this.pointVal = pointVal;
        this.colors = colors;
        found = false;
        this.fluxDistance = fluxDistance;
        isIncreasing = true;
        initialWidth = width;
    }

    public CollectableItem(int x, int y, ArrayList<Color> colors) {
        super(x, y);
        pointVal = 5;
        this.colors = colors;
        found = false;
        fluxDistance = 10;
        isIncreasing = true;
    }

    public int getPointVal(){
        return pointVal;
    }

    public void setPointVal(int pointVal){
        this.pointVal = pointVal;
    }

    public boolean isFound(){
        return found;
    }

    public abstract void setToFound();

    public int getFluxDistance(){
        return fluxDistance;
    }

    public void setFluxDistance(int fluxDistance){
        this.fluxDistance = fluxDistance;
    }

    public void changeColor(int indexOfColor){
        setColor(colors.get(indexOfColor));
    }

    public void changeColor(){
        int indexOfColor = (int) (Math.random() * colors.size());
        setColor(colors.get(indexOfColor));
    }


    public void changeSize(){
        if (isIncreasing){
            setWidth(getWidth()+1);
            if (getWidth() >= fluxDistance + initialWidth) isIncreasing = false;
        }
        else{
            setWidth(getWidth()-1);
            if (getWidth() <= initialWidth - fluxDistance) isIncreasing = true;
        }
    }

    public void setFound(boolean found){
        this.found = found;
    }

    abstract void addToScore();
}
