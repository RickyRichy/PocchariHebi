/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Snake;

/**
 *
 * @author LENOVO
 */
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Game extends JPanel {

    private Timer timer;
    private Snake snake;
    private Point food; // food location
    private int points = 0;
    private int best = 0;
    private BufferedImage img;
    private GameStatus status;
    private boolean loadFoodImg = true;
    private boolean isPaused = false;

    private static final Font FONT_M = new Font("MV Boli", Font.PLAIN, 24);
    private static final Font FONT_M_ITALIC = new Font("MV Boli", Font.ITALIC, 24);
    private static final Font FONT_L_ITALIC = new Font("MV Boli", Font.ITALIC, 84);
    private static final int W = 780;
    private static final int H = 520;
    private static final int DELAY = 100;

    public Game() {
        try {
            img = ImageIO.read(new File("src/assets/img/food.png"));
        } catch (IOException e) {
            loadFoodImg = false;
        }

        addKeyListener(new KeyListener());
        setFocusable(true);
        setBackground(Color.gray); // set bg-color for game window
        setDoubleBuffered(true); // sử dụng bộ đệm kép

        snake = new Snake(W / 2, H / 2);
        status = GameStatus.NOT_STARTED;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void action() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
        snake.move();

        if (food != null && snake.getHead().intersects(food, 8)) {
            snake.addTail();
            food = null;
            points++;
        }

        if (food == null) {
            spawnFood();
        }

        checkForGameOver();
    }

    private void reset() {
        points = 0;
        food = null;
        snake = new Snake(W / 2, H / 2);
        setStatus(GameStatus.RUNNING);
    }

    private void setStatus(GameStatus newStatus) {
        switch (newStatus) {
            case RUNNING:
                timer = new Timer();
                timer.schedule(new GameLoop(), 0, DELAY);
                break;
            case PAUSED:
                timer.cancel();
            case GAME_OVER:
                timer.cancel();
                best = points > best ? points : best;
                break;
        }

        status = newStatus;
    }

    private void togglePause() {
        if (status == GameStatus.PAUSED) {
            setStatus(GameStatus.RUNNING);
            isPaused = false;
        } else {
            setStatus(GameStatus.PAUSED);
            isPaused = true;
        }
        repaint();
    }

    private void checkForGameOver() {
        Point head = snake.getHead();

        boolean hitBoundary = head.getX() < 10 // hit leftside boundary
                || head.getX() > W - 20// hit rightside boundary
                || head.getY() <= 30 // hit upper edge boundary
                || head.getY() >= H + 35; // hit lower edge boundary

        boolean ateItself = false;

        for (Point t : snake.getTail()) {
            ateItself = ateItself || head.equals(t); // Check if the head hit the body
        }

        if (hitBoundary || ateItself) {
            setStatus(GameStatus.GAME_OVER);
        }
    }

    public void drawCenteredString(Graphics g, String text, Font font, int y) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (W - metrics.stringWidth(text)) / 2;

        g.setFont(font);
        g.drawString(text, x, y);
    }

    private void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(0, 255, 0));
        g2d.setFont(FONT_M);

        if (status == GameStatus.NOT_STARTED) {
            drawCenteredString(g2d, "CHUBBY SNAKE", FONT_L_ITALIC, 200);
            drawCenteredString(g2d, "Press  any  key  to  begin", FONT_M_ITALIC, 330);

            return;
        }

        Point p = snake.getHead();

        g2d.drawString("SCORE: " + String.format("%04d", points), 20, 30);
        g2d.drawString("BEST: " + String.format("%04d", best), 600, 30);

        if (food != null) {
            if (loadFoodImg) {
                g2d.drawImage(img, food.getX() + 2, food.getY() + 2, 10, 10, null);
            } else {
                g2d.setColor(Color.RED);
                g2d.fillOval(food.getX() + 2, food.getY() + 2, 12, 12); // set color, size for food
                g2d.setColor(new Color(0, 255, 0));
            }
        }

        if (isPaused) {
            drawCenteredString(g2d, "PAUSE", FONT_L_ITALIC, 300);
            drawCenteredString(g2d, "Press  P  to  continue", FONT_M_ITALIC, 330);
        }

        if (status == GameStatus.GAME_OVER) {
            drawCenteredString(g2d, "GAME OVER", FONT_L_ITALIC, 300);
            drawCenteredString(g2d, "Press  enter  to  start  again", FONT_M_ITALIC, 330);
        }

        // set color for snake
        g2d.setColor(new Color(0, 255, 0));
        g2d.fillRect(p.getX(), p.getY(), 10, 10);

        for (int i = 0, size = snake.getTail().size(); i < size; i++) {
            Point t = snake.getTail().get(i);

            g2d.fillRect(t.getX(), t.getY(), 10, 10);
        }

        // set color for boundary
        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(10, 40, (W - 20), H);
    }

    // spawn new food
    public void spawnFood() {
        food = new Point((new Random()).nextInt(W - 50) + 20,
                (new Random()).nextInt(H - 50) + 40);
    }

    private class KeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (status == GameStatus.RUNNING) {
                switch (key) {
                    case KeyEvent.VK_LEFT ->
                        snake.turn(Direction.LEFT);
                    case KeyEvent.VK_RIGHT ->
                        snake.turn(Direction.RIGHT);
                    case KeyEvent.VK_UP ->
                        snake.turn(Direction.UP);
                    case KeyEvent.VK_DOWN ->
                        snake.turn(Direction.DOWN);
                }
            }

            if (status == GameStatus.NOT_STARTED) {
                setStatus(GameStatus.RUNNING);
            }

            if (status == GameStatus.GAME_OVER && key == KeyEvent.VK_ENTER) {
                reset();
            }

            if (key == KeyEvent.VK_P) {
                togglePause(); // Pause or Continue
            }
        }
    }

    private class GameLoop extends java.util.TimerTask {
        @Override
        public void run() {
            action();
            repaint();
        }
    }
}
