import javax.security.auth.kerberos.KeyTab;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame {
    
    private Game game;


    Frame () {
        super("Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        game = new Game();
        add(game);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addKeyListener(new K());
    }

    private class K implements KeyListener {

        public void keyTyped(KeyEvent e) {}


        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
                game.increaseSnakeHeadX();
            else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
                game.decreaseSnakeHeadX();
            else if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
                game.decreaseSnakeHeadY();
            else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
                game.increaseSnakeHeadY();
            
            game.repaint();
        }


        public void keyReleased(KeyEvent e) {}

    }

}
