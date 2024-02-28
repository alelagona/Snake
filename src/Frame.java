import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private Container c;
    private JPanel mainPanel;
    private JPanel[][] caselle;
    private final int nCaselle = 15;
    
    Frame () {
        super("Snake");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 600);
        
        c = getContentPane();     
        
        /* Creazione del campo di gioco:
        * il campo di gioco è formato da una matrice di
        * pannelli disposti nel contentPane per mezzo
        * di un GridLayout
        */
        mainPanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(5, 5, 5, 5);
            }
        };
        mainPanel.setBackground(Color.decode("#ffbb33"));   
        mainPanel.setLayout(new GridLayout(nCaselle, nCaselle));
        c.add(mainPanel);
        caselle = new JPanel[nCaselle][nCaselle];
        Boolean light = true;
        for(int i = 0; i < nCaselle; i ++) {
            for(int j = 0; j < nCaselle; j ++) {
                caselle[i][j] = new JPanel();
                if(light)
                    caselle[i][j].setBackground(Color.decode("#ffcc66"));
                else
                    caselle[i][j].setBackground(Color.decode("#ffbb33"));

                mainPanel.add(caselle[i][j]);
                light = !light;
            }
        }
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
