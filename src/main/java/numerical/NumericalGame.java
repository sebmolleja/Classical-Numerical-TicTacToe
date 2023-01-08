package numerical;
import java.util.Iterator;

import javax.swing.JOptionPane;

import boardgame.Grid;


public class NumericalGame extends boardgame.BoardGame implements boardgame.Saveable{

    private String playerTurn = "Odd";
    private int depth = 0;
    private int[] oddValues = {1, 3, 5, 7, 9};
    private int[] evenValues = {0, 2, 4, 6, 8};
//     private char[] board = {'0','|','1','|','2','\n','-','+','-','+','-','\n','3','|','4','|','5',
//   '\n','-','+','-','+','-','\n','6','|','7','|','8','\n'};
    private int[] board = {-100, -100, -100, -100, -100, -100, -100, -100, -100};
    // private int[] posToIndex = {0, 2, 4, 12, 14, 16, 24, 26, 28};
    private boolean flag;

    private int winnerState = -1;

                
    public NumericalGame(int wide, int high) {
        super(wide,high);
        setGrid(new NumericalGrid(wide,high));
    }

    @Override
    public void newGame(){
        super.newGame();
        playerTurn = "Odd";
        depth = 0;
        winnerState = -1;
        int[] newBoard = {-100, -100, -100, -100, -100, -100, -100, -100, -100};
        board = newBoard;
        int[] resetOdd = {1, 3, 5, 7, 9};
        int[] resetEven = {0, 2, 4, 6, 8};
        oddValues = resetOdd;
        evenValues = resetEven;
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
        NumericalGrid myGrid = (NumericalGrid)getGrid();  
        myGrid.parseStringIntoBoard(saved);
    }

    
    // Old gmae from this point on 
    public void setTurn() { // switch turns
        if (playerTurn.equals("Odd")) {
            playerTurn = "Even";
        } else {
            playerTurn = "Odd";
        }
    }

    public void presetTurn(String turn) {
        playerTurn = turn;
    }
    
    public String getTurn() {
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

    public void setBoard(int index, int input) { 
        board[index] = input; // pass index from gameplay class to get posistion on board
    }

    public void setEntireBoard(int[] newBoard) {
        board = newBoard;
    }
    
    public int[] getBoard() {
        return board;
    }
    
    public int getPosition(int index) { // pass index and return value of that array
        return index;
    }

    public void setWinnerV(int gameDepth, int[] gameBoard, String turn) {
        char winner = '?';
       
        for (int i = 0; i < 3; i++) { // checking winner for vertical coloumns 3 times
            if (board[i] + board[i + 3]  + board[i + 6] == 15) {
                if (turn.equals("Odd")) {
                    winner = 'O';
                } else {
                    winner = 'E';
                }
                break;
            }
        }
        checkTie(depth, winner);
    }

    public void setWinnerD(int gameDepth, int[] gameBoard, String turn) {
        char winner = '?';

        if (winner == '?') {//check diagonal winner (only 2 cases)
            if (board[0] + board[4] + board[8] == 15) {
                if (getTurn().equals("Odd")) {
                    winner = 'O';
                } else {
                    winner = 'E';
                }
            }
            if (board[2] + board[4] + board[6] == 15) {
                if (getTurn().equals("Odd")) {
                    winner = 'O';
                } else {
                    winner = 'E';
                }
            }
        }
        checkTie(depth, winner);
    }
    
    public void setWinnerH(int gameDepth, int[] gameBoard, String turn) {
        char winner = '?';
       
        if (winner == '?') { // checking horizontal winner for coloumns 3 times 
            for (int i = 0; i <= 6; i += 3) {
                if (board[i] + board[i + 1] +  board[i + 2] == 15) {
                    if (getTurn().equals("Odd")) {
                        winner = 'O';
                    } else {
                        winner = 'E';
                    }
                    break;
                }
            }
        }
        
        checkTie(depth, winner);
    }
    
    private void checkTie(int tieDepth, char winner) {
        // checking the case for a tie
        if (depth == 9 && winner == '?') {
            winnerState = 0;
        } else if (winner != '?') { // output the winner
            if (winner == 'O') {
                winnerState = 1;
            } else if (winner == 'E') {
                winnerState = 2;
            }
        } 
    }

    public boolean checkInput(int playerInput) {
        if (playerInput > 0 && playerInput < 9) {
            if (getTurn().equals("Odd")) {
                for (int i = 0; i < oddValues.length; i++) {
                    if (oddValues[i] == playerInput) {
                        oddValues[i] = -100;
                        return true;
                    }
                }
                return false;
            } else {
                for (int i = 0; i < evenValues.length; i++) {
                    if (evenValues[i] == playerInput) {
                        evenValues[i] = -100;
                        return true;
                    }
                }
                return false;
            }
        } 
        return false;
    }
    
    @Override
    public int getWinner(){
        return winnerState;  //there is only one player in kakuro
    }
    
    public void setNewGrid(Grid g) {
        setGrid(g);
    }

    public Grid getNewGrid() {
        return getGrid();
    }

    public void setOddValues(int[] oddNums) {
        oddValues = oddNums;
    }

    public void setEvenValues(int[] evenNums) {
        evenValues = evenNums;
    }
}