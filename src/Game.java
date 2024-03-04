import java.awt.*;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel {
    
    private final int width = 600, height = 600;
    private final int numOfPixels = 25;
    private final int pixelSize = width / numOfPixels;
    private int snakeHeadX, snakeHeadY;


    public Game() {
        snakeHeadX = 3;
        snakeHeadY = 12;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
    }


    public void paint(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }


    private void draw(Graphics g) {
        // disegno le linee della griglia di gioco
        int x = pixelSize, y = pixelSize;
        g.setColor(new Color(51, 51, 51));

        for(int i = 0; i < (numOfPixels - 1); i ++) {
            g.drawLine(x, 0, x, width);
            g.drawLine(0, y, width, y);

            x += pixelSize;
            y += pixelSize;
        }

        //disegno il serpente
        g.setColor(Color.GREEN);
        g.fillRect((pixelSize * snakeHeadX), (pixelSize * snakeHeadY), pixelSize, pixelSize);
    }


    public void increaseSnakeHeadX() {
        snakeHeadX ++;
    }

    
    public void decreaseSnakeHeadX() {
        snakeHeadX --;
    }


    public void increaseSnakeHeadY() {
        snakeHeadY ++;
    }


    public void decreaseSnakeHeadY() {
        snakeHeadY --;
    }

}
