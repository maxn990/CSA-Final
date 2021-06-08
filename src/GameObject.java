import java.awt.*;

public abstract class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    public GameObject(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
        width = 25;
        height = 25;
        color = Color.GREEN;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Color getColor(){
        return color;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void moveTo(int x, int y){
        setX(x);
        setY(y);
    }

    public void changeDimentions(int width, int height){
        setWidth(width);
        setHeight(height);
    }

    public void draw(Graphics window){
        window.setColor(color);
        window.fillRect(x, y, width, height);
    }
}
