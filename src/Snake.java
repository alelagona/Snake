import java.util.ArrayList;
import java.util.Random;

public class Snake extends ArrayList<Pixel> {

    public void moveHead(char direction, boolean forward) {
        switch(direction) {
            case 'R':
                if(forward)
                    get(0).increaseX();
                else
                    get(0).decreaseX();
                break;
            case 'U':
                if(forward)
                    get(0).decreaseY();  
                else
                    get(0).increaseY();
                break;
            case 'L':
                if(forward)
                    get(0).decreaseX();
                else
                    get(0).increaseX();
                break;
            case 'D':
                if(forward)
                    get(0).increaseY();
                else
                    get(0).decreaseY();
        }
    }


    public void move(char direction) {
        for(int i = size() - 1; i > 0; i --) {
            get(i).setX(get(i - 1).getX());
            get(i).setY(get(i - 1).getY());
        }
        
        moveHead(direction, true);
    }


    public boolean gameOver(char direction) {
        moveHead(direction, true);

        if(get(0).getX() < 0 || get(0).getY() < 0) {
            moveHead(direction, false);
            return true;
        }

        if(get(0).getX() > (Game.PIXEL_FOR_SIDE - 1) || get(0).getY() > (Game.PIXEL_FOR_SIDE - 1)) {
            moveHead(direction, false);
            return true;
        }

        for(int i = 1; i < size(); i ++)
            if(get(0).hasSameCoordinatesOf(get(i))) {
                moveHead(direction, false);
                return true;
            }

        moveHead(direction, false);
        return false;
    }


    public boolean eatApple(Pixel apple, char direction) {
        moveHead(direction, true);

        if(get(0).hasSameCoordinatesOf(apple)) {
            Random r = new Random();
            moveHead(direction, false);
            addFirst(new Pixel(apple.getX(), apple.getY()));
            
            if(size() != Math.pow(Game.PIXEL_FOR_SIDE, 2)) {
                int valid = 0;

                while(valid != size()) {
                    valid = 0;
                    apple.setX(r.nextInt(Game.PIXEL_FOR_SIDE));
                    apple.setY(r.nextInt(Game.PIXEL_FOR_SIDE));
    
                    for(int i = 0; i < size(); i ++)
                        if(!apple.hasSameCoordinatesOf(get(i)))
                            valid ++;
                }
            }

            return true;
        }

        moveHead(direction, false);
        return false;
    }


    public boolean win() {
        if(size() == Math.pow(Game.PIXEL_FOR_SIDE, 2))
            return true;
        else
            return false;
    }

}
