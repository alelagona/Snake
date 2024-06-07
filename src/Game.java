import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.lang.Thread;


public class Game extends JPanel implements KeyListener, Runnable {
    
    public final static int WIDTH = 640, HEIGHT = 640;
    public final static int PIXEL_FOR_SIDE = 16;
    public final int speed = 120;
    private Pixel apple;
    private Snake snake;
    private char direction;
    private boolean gameOver, win;
    private boolean repainted;
    private int score;

    Game() {
        direction = 'R';
        gameOver = false;
        win = false;
        repainted = false;
        score = 0;

        snake = new Snake();
        int x = 3, y = PIXEL_FOR_SIDE / 2;

        for(int i = 0; i < 3; i ++){
            snake.add(new Pixel(x, y));
            x --;
        }

        apple = new Pixel(PIXEL_FOR_SIDE / 3 * 2, PIXEL_FOR_SIDE / 2);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
    }


    @Override
    public void run() {
        while(!gameOver && !win) {

            gameOver = snake.gameOver(direction);
            
            if(snake.eatApple(apple, direction)) {
                score ++;
                win = snake.win();
            }
            else
                snake.move(direction);

            if(!gameOver) {
                repaint();
                repainted = true;
            }

            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }


    private void draw(Graphics g) {
        int x = Pixel.SIZE, y = Pixel.SIZE;
        g.setColor(new Color(51, 51, 51));
        for(int i = 0; i < (PIXEL_FOR_SIDE - 1); i ++) {
            g.drawLine(x, 0, x, WIDTH);
            g.drawLine(0, y, WIDTH, y);

            x += Pixel.SIZE;
            y += Pixel.SIZE;
        }

        g.setColor(Color.WHITE);
        g.fillRect((Pixel.SIZE * snake.get(0).getX()), (Pixel.SIZE * snake.get(0).getY()), Pixel.SIZE, Pixel.SIZE);
    
        g.setColor(new Color(40, 200, 150));
        for(int i = 1; i < snake.size(); i ++)
            g.fillRect((Pixel.SIZE * snake.get(i).getX()), (Pixel.SIZE * snake.get(i).getY()), Pixel.SIZE, Pixel.SIZE);
    
        if(!win) {
            g.setColor(new Color(200, 10, 10));
            g.fillOval((Pixel.SIZE * apple.getX()), (Pixel.SIZE * apple.getY()), Pixel.SIZE, Pixel.SIZE);
        }
    
        if(snake.get(0).hasSameCoordinatesOf(new Pixel(1, 0)))
            g.setColor(Color.BLACK);
        else
            g.setColor(Color.WHITE);
            
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/digital-7.ttf"));
            g.setFont(font.deriveFont(Font.PLAIN,38));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(score < 10)
            g.drawString("Score: " + String.valueOf(score), 240, 38);
        else
            g.drawString("Score: " + String.valueOf(score), 230, 38);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(!repainted) {
            e.consume();
        } else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(direction != 'L') {
                direction = 'R';
                repainted = false;
            }
        } else if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            if(direction != 'D') {
                direction = 'U';
                repainted = false;
            }
        } else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(direction != 'R') {
                direction = 'L';                    
                repainted = false;
            }
        } else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(direction != 'U') {
                direction = 'D';
                repainted = false;
            }
        }
    }
            

    public boolean getGameOver() {
        return gameOver;
    }


    public boolean getWin() {
        return win;
    }


    @Override
    public void keyTyped(KeyEvent e) {}
            

    @Override
    public void keyReleased(KeyEvent e) {}

}
