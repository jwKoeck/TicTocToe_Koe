import java.util.Random;
import java.util.Scanner;

public class TicTacToe_Koeck {
    static final char X = 'X';
    static final char O = 'O';
    static final char empty = ' ';

    public static void main(String[] args) {
        final char player1 = X;
        final char player2 = O;
        final boolean player1Human = true;
        final boolean player2Human = false;

        final char[][] field = new char[3][3];
        initalizeField(field);
        int countEmpty = field.length * field[0].length;
        boolean gameOver = false;
        char nextPlayer = player1;
        boolean nextPlayerHuman = player1Human;
        char winner = empty;

        while (countEmpty > 0 && ! gameOver) {
            printField(field);

            System.out.println("Next move of player " + nextPlayer);

            if (nextPlayerHuman) {
                moveHumanPlayer(field, nextPlayer);
            } else {
                moveComputerPlayer(field, nextPlayer);
            }

            winner = whoWins(field);
            if ( winner!= empty) {
                System.out.println(winner + " wins! Congratulations!");
                gameOver = true;
            } else {
                // swap player
                nextPlayer = (nextPlayer == player1) ? player2 : player1;
                nextPlayerHuman = (nextPlayer == player1) ? player1Human : player2Human;
            }
        }

        if (!gameOver) {
            System.out.println("Draw! No winner!");
        }
        // final print
        printField(field);
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

    public static void moveComputerPlayer(char[][] field, char player) {
        int strategie = 0;
        int cell = 0;

        switch (strategie){
            case 0: // random move
                cell = getField_RandomStrategie(field);
                break;
            default:
                cell = getField_RandomStrategie(field);
        }

        field[getRow(field, cell)][getColumn(field, cell)] = player;
    }

    public static int getField_RandomStrategie(char[][] field) {
        Random rand = new Random();

        int cell = 0;

        do {
            cell = rand.nextInt(field.length * field[0].length);
        } while (field[getRow(field, cell)][getColumn(field, cell)] != empty);
        return cell;
    }

    public static int getRow(char[][] field, int cell) {
        return cell / field[0].length;
    }

    public static int getColumn(char[][] field, int cell) {
        return cell % field[0].length;
    }

    public static char whoWins(char[][] field) {
        char winsRow = empty;
        char winsColumn = empty;
        char winsXDown = empty;
        char winsXUp = empty;

        for (int i = 0; i < field.length; i++) {
            winsRow = field[i][0];
            winsColumn = field[0][i];
            winsXDown = field[0][0];
            winsXUp = field[field.length-1][0];
            for (int j = 1; j < field.length; j++) {
                if (field[i][j] != winsRow) winsRow = empty;
                if (field[j][i] != winsColumn) winsColumn = empty;
                if (field[j][j] != winsXDown) winsXDown = empty;
                if (field[field.length-1-j][j] != winsXUp) winsXUp = empty;
            }
            if (winsRow != empty) return winsRow;
            if (winsColumn != empty) return winsColumn;
            if (winsXDown != empty) return winsXDown;
            if (winsXUp != empty) return winsXUp;
        }
        return empty;
    }
}
