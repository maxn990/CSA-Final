import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Board extends Canvas implements KeyListener, Runnable, MouseListener {
    private final Hero hero;
    private Collectables gameItems;
    public static int time;
    private final ArrayList<Color> itemColors;
    private final EnemyCollection enemies;
    private final BulletManager heroBullets;
    public static int score;
    public final static int INTRO = 0;
    public final static int SCORES = 1;
    public final static int GAME = 2;
    public final static int END = 3;
    public static int MODE = INTRO;
    private final BulletManager enemyBullets;
    public static int lives;
    private boolean scoreSaved;
    private final Walls wallManager;
    private final int enemySpeed = 2;
    public static int markedTime = 0;

    public Board(){
        setBackground(Color.BLACK);
        hero = new Hero(400, 300, 3);
        ArrayList<CollectableItem> ci = new ArrayList<>();
        itemColors = new ArrayList<>();
        itemColors.add(Color.PINK);
        itemColors.add(Color.ORANGE);
        itemColors.add(Color.MAGENTA);
        itemColors.add(Color.YELLOW);
        for (int i = 0 ; i < 5; i++) ci.add(new PointCollectable((int)(Math.random()*800), (int)(Math.random()*600), 10, 10, Color.BLUE, 5, itemColors, 15));
        gameItems = new Collectables(ci, 5);
        time = 0;
        enemies = new EnemyCollection(new ArrayList<>(), 5);
        enemies.add(new Enemy((int)(Math.random()*770), (int)(Math.random()*570), 30, 30, Color.RED, enemySpeed, (int)(Math.random()*4)));
        heroBullets = new BulletManager();
        score = 0;
        enemyBullets = new BulletManager();
        lives = 5;
        scoreSaved = false;
        wallManager = new Walls();
        wallManager.add(new MovingWall((int) (Math.random() * 750), (int) (Math.random() * 550), 35, 75, Color.GRAY));
        wallManager.add(new StandardWall((int) (Math.random() * 750), (int) (Math.random() * 550), 200, 35, Color.GRAY));
        this.addKeyListener(this);
        this.addMouseListener(this);
        new Thread(this).start();
        setVisible(true);
    }

    public void update(Graphics window){
        paint(window);
    }

    public void paint(Graphics window){

        // Lines 66-70 from gridworld (edited):
        Graphics2D twoDGraph = (Graphics2D) window;
        BufferedImage back = (BufferedImage) (createImage(getWidth(), getHeight()));
        Graphics graphToBack = back.createGraphics();
        graphToBack.setColor(Color.BLACK);
        graphToBack.fillRect(0, 0, 800, 600);


        if (MODE == INTRO) drawIntro(graphToBack);
        if (MODE == SCORES) drawScores(graphToBack);
        if (MODE == GAME) drawGame(graphToBack);
        if (MODE == END) drawEnd(graphToBack);

        twoDGraph.drawImage(back, null, 0, 0);
    }

    private void drawIntro(Graphics window){
        window.setColor(new Color(0, 255, 205));
        window.setFont(new Font("Serif", Font.PLAIN, 25));
        window.drawString("Space Squares", 325, 100);
        window.setFont(new Font("Serif", Font.PLAIN, 18));
        window.drawString("By Max Norman", 335, 150);
        String instructions = "Move your character (the green square) with the arrow keys. Try to collect as many" +
                " items as possible. Items will change in ";
        String instructions1 = "in area and color. A new one appears once every few seconds. For each item you collect " +
                "your score will be incremented by ";
        String instructions2 = "1 point. Additionally, enemies (red blocks) will fire at you. Each time your hit your lives will decrease by 1. If you collide ";
        String instructions3 = "with an enemy you will lose all of your remaining lives. You can fire at enemies by aiming with your mouse and clicking, ";
        String instructions4 = "but you won't get points for hitting them. At the beginning of the game you will have 5 lives. Walls randomly transport you.";
        String instructions5 = "Moving walls will take one point from your score. Other collectables will appear throughout. Some increase your speed,";
        String instructions6 = "one decreases enemy speed, and one pauses enemy bullets. When bullets stop, collect them to gain lives and points.";
        window.setFont(new Font("Serif", Font.PLAIN, 15));
        window.setColor(new Color(208, 0, 255));
        window.drawString(instructions, 12, 175);
        window.drawString(instructions1, 12, 188);
        window.drawString(instructions2, 12, 201);
        window.drawString(instructions3, 12, 214);
        window.drawString(instructions4, 12, 227);
        window.drawString(instructions5, 12, 240);
        window.drawString(instructions6, 12, 253);
        window.setColor(new Color(191, 0, 0));
        window.drawRect(25, 350, 350, 100);
        window.drawRect(425, 350, 350, 100);
        window.setColor(new Color(0, 255, 205));
        window.setFont(new Font("Serif", Font.PLAIN, 35));
        window.drawString("Play!", 175, 400);
        window.drawString("View past scores", 475, 400);
    }

    private void drawScores(Graphics window){
        window.setColor(new Color(0, 255, 205));
        window.setFont(new Font("Serif", Font.PLAIN, 30));
        window.drawString("Past scores: ", 75, 75);
        window.setColor(new Color(232, 86, 86));
        window.setFont(new Font("Serif", Font.PLAIN, 15));
        int line = 0;
        try {
            Scanner scores = new Scanner(new File("scores.txt"));
            while (scores.hasNextLine()){
                window.drawString(scores.nextLine(), 100, line + 125);
                line += 15;
            }
        } catch (FileNotFoundException e) {
            window.drawString("No scores to display", 75, 125);
        }
        window.setColor(new Color(191, 0, 0));
        window.drawRect(425, 400, 300, 100);
        window.setFont(new Font("Serif", Font.PLAIN, 25));
        window.setColor(new Color(23, 71, 161));
        window.drawString("Return home", 510, 450);
    }

    private void drawGame(Graphics window){
        manageBullets(window);
        manageEnemies(window);
        manageCollectables(window);
        manageWalls(window);
        manageHero(window);
        drawScore(window);
        if (time % 1000 == 0) lives++;
        if (lives == 0) MODE = END;
        time++;
    }

    private void drawEnd(Graphics window){
        window.setFont(new Font("Serif", Font.PLAIN, 30));
        window.setColor(new Color(168, 33, 33));
        window.drawString("Game over!", 325, 50);
        window.setFont(new Font("Serif", Font.PLAIN, 20));
        window.setColor(new Color(0, 99, 255, 133));
        window.drawString("You scored " + score + " points", 315, 100);
        window.setColor(new Color(191, 0, 0));
        window.drawRect(25, 350, 350, 100);
        if (scoreSaved)
            window.setColor(new Color(109, 109, 109));
        window.drawRect(425, 350, 350, 100);
        window.setFont(new Font("Serif", Font.PLAIN, 35));
        window.setColor(new Color(17, 179, 156));
        window.drawString("Play again", 125, 400);
        if (scoreSaved)
            window.setColor(new Color(109, 109, 109));
        window.drawString("Save score", 525, 400);
        window.setColor(new Color(191, 0, 0));
        window.drawRect(307, 485, 215, 50);
        window.drawString("Return home", 325, 525);
    }

    private void manageHero(Graphics window){
        if (hero.moving()) hero.move();
        hero.draw(window);
    }

    private void manageWalls(Graphics window){
        wallManager.hitAll(hero);
        wallManager.checkAllForBulletCollision(enemyBullets);
        wallManager.checkAllForBulletCollision(heroBullets);
        wallManager.moveAllMovableWalls();
        wallManager.drawAll(window);
    }

    private void manageCollectables(Graphics window){
        for (CollectableItem c: gameItems.getList()) {
            if (c.getX() >= hero.getX() && c.getX() <= hero.getX() + hero.getWidth() && c.getY() >= hero.getY() && c.getY() <= hero.getY() + hero.getHeight())
                c.setToFound();
            if (hero.getX() >= c.getX() && hero.getX() <= c.getX() + c.getWidth() && hero.getY() >= c.getY() && hero.getY() <= c.getY() + c.getHeight())
                c.setToFound();
        }

        if (time % 50 == 0) gameItems.changeAllColors();
        if (time % 10 == 0) gameItems.changeAllSize();
        if (time % 300 == 0) gameItems.add(new PointCollectable((int)(Math.random()*800), (int)(Math.random()*600), 10, 10, Color.BLUE, 5, itemColors, 15));
        if (time % 2500 == 0) {
            ArrayList<Color> otherColor = new ArrayList<>();
            otherColor.add(new Color(255, 0, 188));
            gameItems.add(new SpeedCollectable((int) (Math.random() * 770), (int) (Math.random() * 570), 25, 25, new Color(0, 255, 234), 2, otherColor, 5, 1, hero));
        }
        if (time % 3000 == 0){
            ArrayList<Color> otherColor = new ArrayList<>();
            otherColor.add(new Color(255, 206, 0));
            gameItems.add(new SlowCollectable((int) (Math.random() * 770), (int) (Math.random() * 570), 25, 25, new Color(255, 91, 0), 3, otherColor, 10, enemies));
        }
        if (time % 1000 == 0){
            ArrayList<Color> otherColor = new ArrayList<>();
            otherColor.add(new Color(73, 255, 0));
            gameItems.add(new SlowBulletCollectable((int) (Math.random() * 770), (int) (Math.random() * 570), 25, 25, new Color(73, 225, 0), 1, otherColor, 10, enemyBullets));
        }
        gameItems.removeFoundItems();
        gameItems.drawAll(window);
    }

    private void manageEnemies(Graphics window){
        if (enemies.getList().size() < enemies.getMax())
            enemies.add(new Enemy((int)(Math.random()*770), (int)(Math.random()*570), 30, 30, Color.RED, enemySpeed, (int)(Math.random()*4)));
        for (Enemy enemy: enemies.getList())
            if (hero.getX() >= enemy.getX() && hero.getX() <= enemy.getX() + enemy.getWidth() && hero.getY() >= enemy.getY() && hero.getY() <= enemy.getY() + enemy.getHeight()) {
                lives = 0;
                break;
            }
        if (time % 2000 == 0) enemies.incrementAllSpeed();
        enemies.removeDeadEnemies();
        enemies.moveAll();
        enemies.changeDirection();
        enemies.drawAll(window);
    }

    private void manageBullets(Graphics window){
        heroBullets.moveAll();
        for (Enemy enemy: enemies.getList())
            if (heroBullets.checkCollision(enemy)) enemy.kill();
        heroBullets.removeHitBullets();
        heroBullets.drawAll(window);
        if (time % 100 == 0) enemyBullets.shoot(new Bullet(enemies.getRandomEnemy(), hero.getX(), hero.getY(), 3));
        enemyBullets.moveAll();
        if (enemyBullets.checkCollision(hero) && !enemyBullets.getAreStopped()) lives--;
        if (enemyBullets.checkCollision(hero) && enemyBullets.getAreStopped()) {
            lives++;
            score++;
        }
        if (markedTime != 0){
            enemyBullets.stop();
        }
        if (time - markedTime == 150){
            markedTime = 0;
            enemyBullets.resetSpeed(3);
            enemyBullets.setAreStopped(false);
        }
        enemyBullets.removeHitBullets();
        enemyBullets.drawAll(window);
    }

    private void drawScore(Graphics window){
        window.setColor(new Color(0, 255, 135));
        if (MODE == GAME) {
            window.drawString("Score: " + score, 15, 25);
            window.drawString("Lives remaining: " + lives, 15, 50);
        }
    }

    private void moveOnKeyEvent(KeyEvent e){
        hero.startMoving();
        if (e.getKeyCode() == KeyEvent.VK_UP) hero.setDirection(0);
        if (e.getKeyCode() == KeyEvent.VK_DOWN) hero.setDirection(1);
        if (e.getKeyCode() == KeyEvent.VK_LEFT) hero.setDirection(2);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) hero.setDirection(3);
    }

    private void resetGame(){
        score = 0;
        lives = 5;
        scoreSaved = false;
        hero.moveTo(400, 300);
        enemyBullets.clear();
        hero.setSpeed(3);
        heroBullets.clear();
        wallManager.clear();
        enemies.clear();
        gameItems.clear();
        ArrayList<CollectableItem> ci = new ArrayList<>();
        for (int i = 0 ; i < 5; i++) ci.add(new PointCollectable((int)(Math.random()*800), (int)(Math.random()*600), 10, 10, Color.BLUE, 5, itemColors, 15));
        gameItems = new Collectables(ci, 5);
        wallManager.add(new MovingWall((int) (Math.random() * 750), (int) (Math.random() * 550), 35, 75, Color.GRAY));
        wallManager.add(new StandardWall((int) (Math.random() * 750), (int) (Math.random() * 550), 200, 35, Color.GRAY));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        moveOnKeyEvent(e);
        repaint(); // this line is from starfighter
    }

    @Override
    public void keyReleased(KeyEvent e) {
        hero.stopMoving();
        repaint(); // this line is from starfighter
    }

    @Override
    public void run() {
        // code from starfighter
        try {
            while (true){
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (MODE == INTRO){
            if (e.getX() >= 425 && e.getX() <= 775 && e.getY() >= 350 && e.getY() <= 450) MODE = SCORES;
            if (e.getX() >= 25 && e.getX() <= 375 && e.getY() >= 350 && e.getY() <= 450) MODE = GAME;
        }
        if (MODE == END){
            if (e.getX() >= 25 && e.getX() <= 375 && e.getY() >= 350 && e.getY() <= 450) {
                MODE = GAME;
                resetGame();
            }
            if (e.getX() >= 425 && e.getX() <= 775 && e.getY() >= 350 && e.getY() <= 450  && !scoreSaved){
                scoreSaved = true;
                String name = JOptionPane.showInputDialog(null, "What's your name?");
                try {
                    FileWriter scores = new FileWriter("scores.txt", true);
                    scores.write(name + ": " + score + "\n");
                    scores.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (e.getX() >= 307 && e.getX() <= 522 && e.getY() >= 485 && e.getY() <= 535) {
                MODE = INTRO;
                resetGame();
            }
        }
        if (MODE == SCORES)
            if (e.getX() >= 425 && e.getX() <= 725 && e.getY() >= 400 && e.getY() <= 500)
                MODE = INTRO;
        if (MODE == GAME) heroBullets.shoot(new Bullet(hero, e.getX(), e.getY(), 5));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
