import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.lang.Thread;


public class Game extends JPanel implements KeyListener, Runnable {
    
    public final static int WIDTH = 600, HEIGHT = 600;
    public final static int PIXEL_FOR_SIDE = 3;
    public final int speed = 500;
    private Pixel apple;
    private Snake snake;
    private char direction;
    private boolean gameOver, win;
    private boolean repainted;
    private int score;

    Game() {
        // inizializzo le variabili di gioco
        direction = 'R';
        gameOver = false;
        win = false;
        repainted = false;

        // inizializzo il serpente
        /*
        snake = new Snake();
        int x = 3, y = PIXEL_FOR_SIDE / 2;

        for(int i = 0; i < 4; i ++){
            snake.add(new Pixel(x, y));
            x --;
        }
        */

        snake = new Snake();
        int x = 0, y = PIXEL_FOR_SIDE / 2;

        for(int i = 0; i < 1; i ++){
            snake.add(new Pixel(x, y));
            x --;
        }

        // inizializzo la mela
        apple = new Pixel(PIXEL_FOR_SIDE / 3 * 2, PIXEL_FOR_SIDE / 2);

        // impostazioni varie del pannello di gioco
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
    }


    @Override
    public void run() {
        // gioco eseguito in loop finchè non si perde o si vince
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

            //delay per ogni spostamento
            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        // ridisegno il pannello di gioco
        super.paintComponent(g);
        this.draw(g);
    }


    private void draw(Graphics g) {
        // disegno le linee della griglia di gioco
        int x = Pixel.SIZE, y = Pixel.SIZE;
        g.setColor(new Color(51, 51, 51));
        for(int i = 0; i < (PIXEL_FOR_SIDE - 1); i ++) {
            g.drawLine(x, 0, x, WIDTH);
            g.drawLine(0, y, WIDTH, y);

            x += Pixel.SIZE;
            y += Pixel.SIZE;
        }

        // disegno la testa del serpente
        g.setColor(Color.WHITE);
        g.fillRect((Pixel.SIZE * snake.get(0).getX()), (Pixel.SIZE * snake.get(0).getY()), Pixel.SIZE, Pixel.SIZE);
    
        // disegno il corpo del serpente
        g.setColor(new Color(40, 200, 150));
        for(int i = 1; i < snake.size(); i ++)
            g.fillRect((Pixel.SIZE * snake.get(i).getX()), (Pixel.SIZE * snake.get(i).getY()), Pixel.SIZE, Pixel.SIZE);
    
        // disegno la mela
        if(!win) {
            g.setColor(new Color(200, 10, 10));
            g.fillOval((Pixel.SIZE * apple.getX()), (Pixel.SIZE * apple.getY()), Pixel.SIZE, Pixel.SIZE);
        }
    
        // disegno il punteggio
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
        g.drawString("Score: " + String.valueOf(score), 245, 40);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // ascoltatore della tastiera che cambia la direzione del serpente
        if(repainted) {
            if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
