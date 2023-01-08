package tictactoe;
import java.util.Iterator;

import javax.swing.JOptionPane;
import boardgame.Grid;

public class TictactoeGame extends boardgame.BoardGame implements boardgame.Saveable{

    private char playerTurn = 'X';
    private int depth = 0;
    private char[] board = {'0','|','1','|','2','\n','-','+','-','+','-','\n','3','|','4','|','5',
  '\n','-','+','-','+','-','\n','6','|','7','|','8','\n'};

    private int[] posToIndex = {0, 2, 4, 12, 14, 16, 24, 26, 28};
    private boolean flag;

    private int winnerState = -1;

    
    public TictactoeGame(int wide, int high) {
        super(wide,high);
        setGrid(new TictactoeGrid(wide,high));
    }

    @Override
    public void newGame(){
        super.newGame();
        playerTurn = 'X';
        depth = 0;
        char[] resettedBoard = {'0','|','1','|','2','\n','-','+','-','+','-','\n','3','|','4','|','5',
        '\n','-','+','-','+','-','\n','6','|','7','|','8','\n'};
        board = resettedBoard;
        winnerState = -1;
    }

    @Override
    public boolean takeTurn(int across, int down, int input){
        //check that input is a digit between 1-9
        setValue(across,down,String.valueOf(input));
        return true;
    }

    @Override
    public boolean takeTurn(int across, int down, String input){
        //check that input is a digit between 1-9
        setValue(across,down,input);
        return true;
    }
  
    @Override
    public boolean isDone(){
        if (depth >= 9 || winnerState != -1){
            return true;
        }
        return false;
    }
  
    @Override
    public String getGameStateMessage(){
        return "You Won!"; //should be a message based on the state of the game
    }

    @Override
    public String getStringToSave(){
        return "This should be a csv delimited string that could be written to file";
    }

    @Override
    public void loadSavedString(String saved){
        //here there should be code to parse the saved string into a board.
        //I would probably write a method in my KakuroBoard class that did the parsing
        // and just pass it the string/

        /* must cast it to get a reference that can use Kakuro grid
        methods*/
        TictactoeGrid myGrid = (TictactoeGrid)getGrid();  
        myGrid.parseStringIntoBoard(saved);
    }

    
    // Old game from this point on 
    public void setTurn() { // switch turns
        if (playerTurn == 'X') {
            playerTurn = 'O';
        } else {
            playerTurn = 'X';
        }
    }

    public void presetTurn(char turn) {
        playerTurn = turn;
    }
    
    public char getTurn() {
        return playerTurn;
    }
    
    public void setDepth() { // depth of game
        depth++;
    }

    public void presetDepth(int gameDepth) { // depth of game
        depth = gameDepth;
    }
    
    public int getDepth() {
        return depth;
    }
    
    public void displayBoard() {
        System.out.println(board);
    }
    
    public void setBoard(int index) {
        board[index] = playerTurn; // pass index from gameplay class to get posistion on board
    }

    public void setEntireBoard(char[] newBoard) {
        board = newBoard;
    }
    
    public char[] getBoard() {
        return board;
    }
    
    public boolean getCheckBoard(int pos, char[] b) { // update the board throughout the game
        flag = false;
        if (b[posToIndex[pos]] == 'X'
        || b[posToIndex[pos]] == 'O') {
            flag = true;
        }
        return flag;
    }
    
    public int getPosition(int index) { // pass index and return value of that array
        return posToIndex[index];
    }
    
    public void setWinner(int gameDepth, char[] gameBoard, boolean choice) {
        char winner = '?';
        for (int i = 0; i < 3; i++) { // checking winner for vertical coloumns 3 times
            if (board[posToIndex[i]] == board[posToIndex[i + 3]] 
            && board[posToIndex[i + 3]] == board[posToIndex[i + 6]]) {
                winner = board[posToIndex[i]];
                break;
            }
        }
        if (winner == '?') { //check diagonal winner (only 2 cases)
            if (board[posToIndex[0]] == board[posToIndex[4]] && board[posToIndex[4]] == board[posToIndex[8]]) {
                winner = board[posToIndex[0]];
            }
            if (board[posToIndex[2]] == board[posToIndex[4]] && board[posToIndex[4]] == board[posToIndex[6]]) {
                winner = board[posToIndex[2]];
            }
        }
        if (winner == '?') { // checking winner for coloumns 3 times 
            for (int i = 0; i <= 6; i += 3) {
                if (board[posToIndex[i]] == board[posToIndex[i + 1]] 
                && board[posToIndex[i + 1]] == board[posToIndex[i + 2]]) {
                    winner = board[posToIndex[i]];
                    break;
                }
            }
        }
        gameType(depth, winner, choice);
    }
    
    private void checkTie(int tieDepth, char winner, boolean gameChoice) {
        // checking the case for a tie
        if (depth == 9 && winner == '?') {
            if (!gameChoice) {
                System.out.println("The Game was a Tie!");
                System.exit(0);   
            }
            winnerState = 0;
        } else if (winner != '?') { // output the winner
            if (winner == 'X') {
                winnerState = 1;
            } else if (winner == 'O') {
                winnerState = 2;
            }
            if (!gameChoice) {
                System.out.println("The winner is " + winner);
                System.exit(0);  
            }
        } 
    }

    private void gameType(int d, char w, boolean choice) {
        if (choice) {
            checkTie(d, w, true);
        } else {
            checkTie(d, w, false);
        }
    }

    @Override
    public int getWinner() {
        return winnerState;  //there is only one player in kakuro
    }

    public void setNewGrid(Grid g) {
        setGrid(g);
    }

    public Grid getNewGrid() {
        return getGrid();
    }
}