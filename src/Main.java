import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int SIZE_BOARD = 5;
    private static final String CASTLE = "\uD83C\uDFF0";
    private static final String NOTHING = " ";
    private static final String LEFT_BLOCK = "| ";
    private static final String RIGHT_BLOCK = "|";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");
        String answer = sc.nextLine();

        if (answer.equals("НЕТ")) {
            System.out.println("Жаль");
            return;
        }
        if (!answer.equals("ДА")) {
            System.out.println("Данные введены некорректно");
            return;
        }

        System.out.println("Выбери сложность игры(от 1 до 5):");
        int difficultGame = sc.nextInt();
        while (difficultGame > 5 || difficultGame < 1) {
            System.out.println("Вбейте число от 1 до 5 включительно:");
            difficultGame = sc.nextInt();
        }
        System.out.println("Выбранная сложность:\t" + difficultGame);

        playGame(difficultGame, sc);
    }

    private static void playGame(int difficultGame, Scanner sc) {
        int step = 0;

        String[][] board = new String[SIZE_BOARD][SIZE_BOARD];
        fillBoard(board);

        Random r = new Random();

        Person person = new Person(SIZE_BOARD);
        board[person.getY()][person.getX()] = person.getImage();

        int castleX = r.nextInt(SIZE_BOARD);
        int castleY = 0;
        board[castleY][castleX] = CASTLE;

        int countMonster = SIZE_BOARD * SIZE_BOARD - SIZE_BOARD - 5;
        Monster[] arrMonster = new Monster[countMonster];
        int count = 0;
        while (count < countMonster) {
            boolean isBig = (count % 3 == 0);
            Monster el = new Monster(SIZE_BOARD, isBig);

            if (board[el.getY()][el.getX()].equals(NOTHING)) {
                board[el.getY()][el.getX()] = el.getImage();
                arrMonster[count] = el;
                count++;
            }
        }

        String wall = "+ —— ".repeat(SIZE_BOARD) + "+";

        while (true) {
            board[person.getY()][person.getX()] = person.getImage();
            printBoard(board, wall);
            System.out.println("Введите куда будет ходить персонаж (ход возможен только по вертикали и горизонтали на одну клетку)");
            System.out.println("Координаты персонажа - (x: " + (person.getX() + 1) + ", y: " + (person.getY() + 1) + "))");

            int x = sc.nextInt() - 1;
            int y = sc.nextInt() - 1;

            boolean sameCell = (x == person.getX() && y == person.getY());
            boolean outOfBoard = !(0 <= x && x < SIZE_BOARD && 0 <= y && y < SIZE_BOARD);

            if (sameCell || outOfBoard) {
                System.out.println("Неккоректный ход");
                continue;
            }
            if (!person.moveCorrect(x, y)) {
                System.out.println("Координаты не изменены");
                continue;
            }

            String target = board[y][x];

            if (target.equals(NOTHING)) {
                board[person.getY()][person.getX()] = NOTHING;
                person.move(x, y);
                step++;
            } else if (target.equals(CASTLE)) {
                System.out.println("Вы прошли игру!");
                printResults(step, person);
                break;
            } else {
                Monster monst = findMonster(arrMonster, x, y);

                if (monst != null && monst.taskMonster(difficultGame)) {
                    board[person.getY()][person.getX()] = NOTHING;
                    person.move(x, y);
                    step++;
                    System.out.println("Виктория!");
                } else if (monst != null) {
                    person.downLive();
                }

                if (person.getLive() <= 0) {
                    System.out.println("Гамовер");
                    printResults(step, person);
                    break;
                } else if (person.getLive() == 1) {
                    System.out.println("Вы наткнулись на монстра. Ваши координаты: "
                            + (person.getX() + 1) + ", " + (person.getY() + 1)
                            + "\nУ вас осталось: " + person.getLive() + " жизнь");
                } else {
                    System.out.println("Вы наткнулись на монстра. Ваши координаты: "
                            + (person.getX() + 1) + ", " + (person.getY() + 1)
                            + "\nУ вас осталось: " + person.getLive() + " жизней");
                }
            }

            System.out.println("Ход корректный; Новые координаты: "
                    + (person.getX() + 1) + ", " + (person.getY() + 1)
                    + "\nХод номер: " + step);
        }
    }

    private static void fillBoard(String[][] board) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = NOTHING;
            }
        }
    }

    private static void printBoard(String[][] board, String wall) {
        for (String[] row : board) {
            System.out.println(wall);
            for (String cell : row) {
                System.out.print(LEFT_BLOCK + cell + " ");
            }
            System.out.println(RIGHT_BLOCK);
        }
        System.out.println(wall);
    }

    private static Monster findMonster(Monster[] monsters, int x, int y) {
        for (Monster m : monsters) {
            if (m.conflictPerson(x, y)) {
                return m;
            }
        }
        return null;
    }

    private static void printResults(int step, Person person) {
        System.out.println("Итоги:\n"
                + "Сделано " + step + " шагов\n"
                + "Последняя координата " + (person.getX() + 1) + "," + (person.getY() + 1));
    }
}