import java.util.Random;
import java.util.Scanner;

public class TicTacToe_Koeck {
    static final char X = 'X';
    static final char O = 'O';
    static final char empty = ' ';

    public static void main(String[] args) {

        final Player player1 = new Player("Human", X, true);
        final Player player2 = new Player("Computer", O, false);

        final char[][] field = new char[3][3];
        initalizeField(field);
        int countEmpty = field.length * field[0].length;
        Player nextPlayer = player1;
        Player otherPlayer = player2;
        Player winner = null;

        while (countEmpty > 0 && winner == null ) {
            printField(field);

            System.out.println("Next move of player " + nextPlayer.getName());

            if (nextPlayer.isHuman()) {
                moveHumanPlayer(field, nextPlayer);
            } else {
                moveComputerPlayer(field, nextPlayer, otherPlayer);
            }
            countEmpty--;

            winner = whoWins(field, player1, player2);
            if ( winner != null) {
                System.out.println(winner.getName() + " wins! Congratulations!");
            } else {
                // swap player
                nextPlayer = (nextPlayer == player1) ? player2 : player1;
                otherPlayer = (nextPlayer == player1) ? player2 : player1;
            }
        }

        if (winner == null) {
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

    public static void moveHumanPlayer(char[][] field, Player player){
        Scanner scanner = new Scanner(System.in);
        boolean validMove = false;

        while (!validMove) {
            System.out.print("Row: ");
            int row = scanner.nextInt() - 1; // correct to zero-based index
            System.out.print("Column: ");
            int column = scanner.nextInt() - 1; // correct to zero-based index
            if (row < field.length && column < field[row].length && field[row][column] == empty) {
                field[row][column] = player.getFigure();
                validMove = true;
            }
        }
    }

    public static void moveComputerPlayer(char[][] field, Player me, Player other) {
        int strategy = 1;
        int cell = -1;

        switch (strategy){
            case 0: // random move
                cell = getField_RandomStrategy(field);
                break;
            case 1:
                cell = getField_MakeWinMoveStrategy(field,me);
                if (cell == -1) {
                    cell = getField_RandomStrategy(field);
                }
                break;
            default:
                cell = getField_RandomStrategy(field);
        }

        field[getRow(field, cell)][getColumn(field, cell)] = me.getFigure();
    }

    public static int getField_RandomStrategy(char[][] field) {
        Random rand = new Random();

        int cell = 0;

        do {
            cell = rand.nextInt(field.length * field[0].length);
        } while (field[getRow(field, cell)][getColumn(field, cell)] != empty);
        return cell;
    }

    public static int getField_MakeWinMoveStrategy(char[][] field, Player player) {
        for (int i = 0; i < field.length * field[0].length; i++) {
            int row = getRow(field, i);
            int column = getColumn(field, i);
            if (field[row][column] == empty) {
                // test for a winning move
                char[][] simulation = simulateMove(field, player, i);

//                System.out.println("Simulation " + i);
//                printField(simulation);
//                System.out.println("Winner " + whoWins(simulation, player, player));

                if (whoWins(simulation, player, player) == player) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static char[][] simulateMove(char[][] field, Player player, int cell) {
        // copy field to simulation-field
        char[][] simulation = new char[field.length][];
        for (int i = 0; i < field.length; i++) {
            simulation[i] = field[i].clone();
        }
        // perform move in simulation and return it
        simulation[getRow(field, cell)][getColumn(field, cell)] = player.getFigure();
        return simulation;
    }

    public static int getRow(char[][] field, int cell) {
        return cell / field[0].length;
    }

    public static int getColumn(char[][] field, int cell) {
        return cell % field[0].length;
    }

    public static Player whoWins(char[][] field, Player player1, Player player2) {
        char winsRow = empty;
        char winsColumn = empty;
        char winsXDown = empty;
        char winsXUp = empty;
        char winner = empty;

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
            if (winsRow != empty) {
                winner = winsRow;
                break;
            } else if (winsColumn != empty) {
                winner = winsColumn;
                break;
            } else if (winsXDown != empty) {
                winner = winsXDown;
                break;
            } else if (winsXUp != empty) {
                winner = winsXUp;
                break;
            }
        }
        if (winner == empty) {
            return null;
        } else if (winner == player1.getFigure()) {
            return player1;
        } else {
            return player2;
        }

    }
}
