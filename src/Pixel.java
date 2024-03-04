public class Pixel {

    public final static int SIZE = Game.WIDTH / Game.NUM_OF_PIXELS;
    private int x;
    private int y;


    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void increaseX() {
        x ++;
    }


    public void decreaseX() {
        x --;
    }


    public void increaseY() {
        y ++;
    }


    public void decreaseY() {
        y --;
    }

}
