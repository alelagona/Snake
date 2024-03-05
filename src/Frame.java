import javax.swing.*;

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
        game.run();
    }

}
