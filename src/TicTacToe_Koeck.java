import java.util.Scanner;

public class TicTacToe_Koeck {
    static final char X = 'X';
    static final char O = 'O';
    static final char empty = ' ';

    public static void main(String[] args) {
        final char player1 = X;
        final char player2 = O;

        final char[][] field = new char[3][3];
        initalizeField(field);
        int countEmpty = field.length * field[0].length;
        boolean gameOver = false;
        char nextPlayer = player1;

        while (countEmpty > 0 && ! gameOver) {
            printField(field);
            moveHumanPlayer(field, nextPlayer);

            // swap player
            nextPlayer = (nextPlayer == player1) ? player2 : player1;
        }
    }

    public static void initalizeField (char[][] field){
        for (int r= 0; r < field.length; r++) {
            for (int c= 0; c < field[r].length; c++) {
                field[r][c] = empty;
            }
        }
    }

    public static void printField(char[][] field) {

        for (int r= 0; r < field.length; r++) {
            System.out.println("-".repeat(field.length * 4 + 1));

            for (int c= 0; c < field[r].length; c++) {
                System.out.print("| " + field[r][c] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-".repeat(field.length * 4 + 1));
    }

    public static void moveHumanPlayer(char[][] field, char player){
        Scanner scanner = new Scanner(System.in);
        boolean validMove = false;

        System.out.println("Next move of player " + player);

        while (!validMove) {
            System.out.print("Row: ");
            int row = scanner.nextInt() - 1; // correct to zero-based index
            System.out.print("Column: ");
            int column = scanner.nextInt() - 1; // correct to zero-based index
            if (row < field.length && column < field[row].length && field[row][column] == empty) {
                field[row][column] = player;
                validMove = true;
            }
        }
    }
}
