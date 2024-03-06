import java.util.ArrayList;
import java.util.Random;

public class Snake extends ArrayList<Pixel> {

    public void moveHead(char direction, boolean forward) {
        // sposto la testa
        switch(direction) {
            case 'R':
                if(forward)
                    this.get(0).increaseX();
                else
                    this.get(0).decreaseX();
                break;
            case 'U':
                if(forward)
                    this.get(0).decreaseY();  
                else
                    this.get(0).increaseY();
                break;
            case 'L':
                if(forward)
                    this.get(0).decreaseX();
                else
                    this.get(0).increaseX();
                break;
            case 'D':
                if(forward)
                    this.get(0).increaseY();
                else
                    this.get(0).decreaseY();
        }
    }


    public void move(char direction) {
        // sposto tutti i pixel del corpo sul pixel successivo
        for(int i = this.size() - 1; i > 0; i --) {
            this.get(i).setX(this.get(i - 1).getX());
            this.get(i).setY(this.get(i - 1).getY());
        }
        // sposto la testa
        this.moveHead(direction, true);
    }


    public boolean gameOver() {
        // controllo se il serpente collide con i bordi della finestra
        if(this.get(0).getX() < 0 || this.get(0).getY() < 0)
            return true; 
        if(this.get(0).getX() > (Game.PIXEL_FOR_SIDE - 1) || this.get(0).getY() > (Game.PIXEL_FOR_SIDE - 1))
            return true;

        // controllo se il serpente collide su se stesso
        for(int i = 1; i < this.size(); i ++)
            if(this.get(0).hasSameCoordinatesOf(this.get(i)))
                return true;

        return false;
    }        


    public boolean eatApple(Pixel apple, char direction) {
        // sposto la testa per vedere se si incontrerà una mela
        this.moveHead(direction, true);

        // controllo se si incontra una mela con la testa
        if(this.get(0).hasSameCoordinatesOf(apple)) {
            Random r = new Random();
            // riporto la testa indietro e aggiungo un pixel al serpente
            // con le coordinate della mela
            this.moveHead(direction, false);
            this.addFirst(new Pixel(apple.getX(), apple.getY()));
            // controllo che le coordinate della mela non coincidano
            // con quelle dei pixel del serpente
            int valid = 0;
            while(valid != this.size()) {
                valid = 0;
                apple.setX(r.nextInt(Game.PIXEL_FOR_SIDE));
                apple.setY(r.nextInt(Game.PIXEL_FOR_SIDE));

                for(int i = 0; i < this.size(); i ++)
                    if(!apple.hasSameCoordinatesOf(this.get(i)))
                        valid ++;
            }
            return true;
        }
        moveHead(direction, false);
        return false;
    }

}
