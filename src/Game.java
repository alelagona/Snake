import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;
import java.lang.Thread;


public class Game extends JPanel implements KeyListener {
    
    public final static int WIDTH = 600, HEIGHT = 600;
    public final static int PIXEL_FOR_SIDE = 25;
    private Pixel snakeHead;
    private ArrayList<Pixel> snakeBody;
    private char direction;


    public Game() {
        snakeHead = new Pixel(4, PIXEL_FOR_SIDE/2);
        snakeBody = new ArrayList<Pixel>();
        direction = 'R';

        int x = 1;
        int y = 12;
        for(int i = 0; i < 3; i ++){
            snakeBody.add(new Pixel(x, y));
            x ++;
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
    }


    public void paint(Graphics g) {
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
    }

    public void play() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(int i = 0; i < (snakeBody.size() - 1); i ++) {
                snakeBody.get(i).setX(snakeBody.get(i + 1).getX());
                snakeBody.get(i).setY(snakeBody.get(i + 1).getY());
            }

            snakeBody.get(snakeBody.size() - 1).setX(snakeHead.getX());
            snakeBody.get(snakeBody.size() - 1).setY(snakeHead.getY());
            
            if(direction == 'R')
                snakeHead.increaseX();
            else if(direction == 'U')
                snakeHead.decreaseY();
            else if(direction == 'L')
                snakeHead.decreaseX();
            else if(direction == 'D')
                snakeHead.increaseY();

            repaint();
        }
    }

    public void keyTyped(KeyEvent e) {}


    public void keyPressed(KeyEvent e) {

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


    public void keyReleased(KeyEvent e) {}

}
