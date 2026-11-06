import java.util.Random;
import java.util.Scanner;

public class Monster {
    private static final String SMALL_IMAGE = "\uD83E\uDDDF\u200D";
    private static final String BIG_IMAGE = "\uD83D\uDC79";

    private final String image;
    private final int x;
    private final int y;
    private final boolean isBig;
    private final Random r = new Random();

    Monster(int sizeBoard, boolean isBig) {
        this.y = r.nextInt(sizeBoard);
        this.x = r.nextInt(sizeBoard);
        this.isBig = isBig;
        this.image = isBig ? BIG_IMAGE : SMALL_IMAGE;
    }

    public String getImage() { return image; }
    public int getX() { return x; }
    public int getY() { return y; }

    public boolean conflictPerson(int perX, int perY) {
        return perY == y && perX == x;
    }

    public boolean taskMonster(int difficultGame) {
        if (isBig) {
            return solveReverseTask(difficultGame);
        }
        return solveMathTask(difficultGame);
    }

    private boolean solveMathTask(int difficultGame) {
        int a = r.nextInt(100 * difficultGame);
        int b = r.nextInt(100 * difficultGame);
        int trueAnswer = a - b;

        System.out.println("Реши пример: " + a + " - " + b + " = ?");
        Scanner sc = new Scanner(System.in);
        int ans = sc.nextInt();

        if (trueAnswer == ans) {
            System.out.println("Победа! Или нет?");
            return true;
        }
        System.out.println("Гамоверталити!");
        return false;
    }

    private boolean solveReverseTask(int difficultGame) {
        System.out.println("Напишите данную строку наоборот:");

        int length = difficultGame + 1;
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = (char) (r.nextInt(42) + 48);
            b.append(ch);
        }
        String test = b.toString();
        System.out.println(test);

        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine().strip();

        // Переворачиваем ответ и сравниваем с исходной строкой
        String reversed = new StringBuilder(answer).reverse().toString();

        if (reversed.equals(test)) {
            System.out.println("Верно! Ты победил монстра");
            return true;
        }
        System.out.println("Game over");
        System.out.println("Please deposit 5 coins");
        return false;
    }
}