import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import java.lang.Thread;


public class Game extends JPanel implements KeyListener {
    
    public final static int WIDTH = 600, HEIGHT = 600;
    public final static int PIXEL_FOR_SIDE = 25;
    private Pixel snakeHead, apple;
    private ArrayList<Pixel> snakeBody;
    private char direction;
    private boolean gameOver;


    public Game() {
        // inizializzo le variabili di gioco
        direction = 'R';
        gameOver = false;

        // inizializzo il pixel della testa
        snakeHead = new Pixel(4, PIXEL_FOR_SIDE / 2);
        
        // inizializzo i pixel del corpo
        snakeBody = new ArrayList<Pixel>();
        int x = 1, y = PIXEL_FOR_SIDE / 2;
        for(int i = 0; i < 3; i ++){
            snakeBody.add(new Pixel(x, y));
            x ++;
        }

        // inizializzo il pixel della mela
        apple = new Pixel(PIXEL_FOR_SIDE - 3, PIXEL_FOR_SIDE / 2);

        // impostazioni varie del pannello di gioco
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
    }


    public void paint(Graphics g) {
        // ridisegna il pannello di gioco
        super.paintComponent(g);
        draw(g);
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
        g.fillRect((Pixel.SIZE * snakeHead.getX()), (Pixel.SIZE * snakeHead.getY()), Pixel.SIZE, Pixel.SIZE);
    
        // disegno il corpo del serpente
        g.setColor(new Color(40, 200, 150));
        for(int i = 0; i < snakeBody.size(); i ++)
            g.fillRect((Pixel.SIZE * snakeBody.get(i).getX()), (Pixel.SIZE * snakeBody.get(i).getY()), Pixel.SIZE, Pixel.SIZE);
    
        // disegno la mela
        g.setColor(Color.RED);
        g.fillOval((Pixel.SIZE * apple.getX()), (Pixel.SIZE * apple.getY()), Pixel.SIZE, Pixel.SIZE);
    }


    public void play() {
        while(true) {
            //delay per ogni spostamento
            try {
                Thread.sleep(60);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //sposto le caselle dall'ultima alla terza su quella successiva
            for(int i = 0; i < (snakeBody.size() - 1); i ++) {
                snakeBody.get(i).setX(snakeBody.get(i + 1).getX());
                snakeBody.get(i).setY(snakeBody.get(i + 1).getY());
            }

            // sposto la seconda casella sulla testa
            snakeBody.get(snakeBody.size() - 1).setX(snakeHead.getX());
            snakeBody.get(snakeBody.size() - 1).setY(snakeHead.getY());
            
            // sposto la testa
            if(direction == 'R')
                snakeHead.increaseX();
            else if(direction == 'U')
                snakeHead.decreaseY();
            else if(direction == 'L')
                snakeHead.decreaseX();
            else if(direction == 'D')
                snakeHead.increaseY();

            // se il serpente mangia la mela, aggiungo un pixel al corpo
            // e ricreo la mela con coordinate randm
            if(snakeHead.hasSameCoordinatesOf(apple)) {
                Random r = new Random();
                snakeBody.add(new Pixel(snakeBody.get(snakeBody.size() - 1).getX(), snakeBody.get(snakeBody.size() - 1).getY()));
                
                // controllo che le coordinate della mella non coincidano
                // con quelle dei pixel del serpente
                int valid = 0;
                while(valid != (snakeBody.size() + 1)) {
                    valid = 0;
                    apple.setX(r.nextInt(PIXEL_FOR_SIDE));
                    apple.setY(r.nextInt(PIXEL_FOR_SIDE));

                    if(!apple.hasSameCoordinatesOf(snakeHead))
                        valid ++;

                    for(int i = 0; i < snakeBody.size(); i ++)
                        if(!apple.hasSameCoordinatesOf(snakeBody.get(i)))
                            valid ++;
                }
            }

            // se il serpente collide con i bordi della finestra => gameover
            if(snakeHead.getX() < 0 || snakeHead.getY() < 0)
                gameOver = true;   
            if(snakeHead.getX() > (PIXEL_FOR_SIDE - 1) || snakeHead.getY() > (PIXEL_FOR_SIDE - 1))
                gameOver = true;

            // se il serpente collide su se stesso => gameover
            for(int i = 0; i < snakeBody.size(); i ++)
                if(snakeHead.hasSameCoordinatesOf(snakeBody.get(i)))
                    gameOver = true;

            if(!gameOver)
                repaint();
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
