import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.lang.Thread;


public class Game extends JPanel implements KeyListener {
    
    public final static int WIDTH = 600, HEIGHT = 600;
    public final static int PIXEL_FOR_SIDE = 30;
    public final int speed = 100;
    private Pixel apple;
    private Snake snake;
    private char direction;
    private boolean gameOver;
    private int score;

    Game() {
        // inizializzo le variabili di gioco
        direction = 'R';
        gameOver = false;

        // inizializzo il serpente
        snake = new Snake();
        int x = 4, y = PIXEL_FOR_SIDE / 2;

        for(int i = 0; i < 4; i ++){
            snake.add(new Pixel(x, y));
            x --;
        }

        // inizializzo la mela
        apple = new Pixel(PIXEL_FOR_SIDE / 5 * 4, PIXEL_FOR_SIDE / 2);

        // impostazioni varie del pannello di gioco
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
    }


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
        g.setColor(new Color(200, 10, 10));
        g.fillOval((Pixel.SIZE * apple.getX()), (Pixel.SIZE * apple.getY()), Pixel.SIZE, Pixel.SIZE);
    
        // disegno il punteggio
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


    public void play() {
        // gioco eseguito in loop finchè non si perde o si vince
        while(!gameOver) {
            gameOver = snake.gameOver();
            
            if(snake.eatApple(apple, direction))
                score ++;
            else
                snake.move(direction);

            if(!gameOver)
                repaint();
            else
                System.out.println("game over");

            //delay per ogni spostamento
            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void keyPressed(KeyEvent e) {
        // ascoltatore della tastiera che cambia la direzione del serpente
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(direction != 'L')
                direction = 'R';
        } else if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            if(direction != 'D')
                direction = 'U';
        } else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(direction != 'R')
                direction = 'L';                    
        } else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(direction != 'U')
                direction = 'D';
        }
    }
            

    public void keyTyped(KeyEvent e) {}
            

    public void keyReleased(KeyEvent e) {}

}
