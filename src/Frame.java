import javax.swing.*;

public class Frame extends JFrame {
    
    private Game game;


    Frame() {
        super("Snake");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        
        game = new Game();
        this.add(game);
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        game.play();
    }

}
