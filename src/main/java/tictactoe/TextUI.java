package tictactoe;

import java.util.Scanner;

public class TextUI {

    public static void main(String[] args) {
        TictactoeGame game = new TictactoeGame(0, 0);
        Scanner input = new Scanner(System.in);
        int position;
        char[] board = game.getBoard();
        game.displayBoard();
        System.out.println("Turn = X");
        while (game.getWinner() == -1) {
            game.setWinner(game.getDepth(), board, false);
            game.getWinner();
            System.out.println("Enter Position between 0 and 8:");
            position = input.nextInt();
            if (position > 8 || position < 0) {
                System.out.println("Error - Out of Bounds");
            } else {
                if (game.getCheckBoard(position, board)) {
                    System.err.println("Illegal Move! Try Again");
                } else {
                    game.setDepth();
                    game.setBoard(game.getPosition(position));
                    game.displayBoard();
                    game.setTurn();
                    if (game.getDepth() != 9) {
                        System.out.println("Turn = " + game.getTurn());
                    }
                }
            }
        }
        input.close();
    }
}
