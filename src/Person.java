import java.util.Random;

public class Person {
    private static final String IMAGE = "\uD83E\uDDD1";
    private static final int START_LIVES = 3;

    private final Random r = new Random();
    private int x;
    private int y;
    private int live = START_LIVES;

    Person(int sizeBoard) {
        this.x = r.nextInt(sizeBoard);
        this.y = r.nextInt(sizeBoard);
    }

    public String getImage() { return IMAGE; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getLive() { return live; }

    public void downLive() { live--; }

    public boolean moveCorrect(int newX, int newY) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return dx + dy == 1;
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}