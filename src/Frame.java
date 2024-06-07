import javax.swing.*;

public class Frame extends JFrame implements Runnable {
    
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

        Thread frameThread = new Thread(this);
        frameThread.start();
    }


    @Override
    public void run() {
        Thread gameThread = new Thread(game);
        gameThread.start();

        while(gameThread.isAlive()) {
            continue;
        }

        if(game.getGameOver()) {
            int choice = JOptionPane.showConfirmDialog(null,
                "Would you like to start a new game?",
                "You loose!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
            );

            if(choice == JOptionPane.YES_OPTION) {
                dispose();
                new Frame();
            } else {
                System.exit(choice);
            }
        } else if(game.getWin()) {
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Would you like to start a new game?",
                "You win!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
            );

            if(choice == JOptionPane.YES_OPTION) {
                dispose();
                new Frame();
            } else {
                System.exit(0);
            }
        }
    }

}
