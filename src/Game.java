import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public Game(){
        // Code from Starfighter
        super("Space Squares");
        setSize(WIDTH, HEIGHT);
        Board gameBoard = new Board();
        ((Component) gameBoard).setFocusable(true);
        getContentPane().add(gameBoard);
        setVisible(true);
    }

    public static void main(String[] args) {
        Game g = new Game();
    }
}
