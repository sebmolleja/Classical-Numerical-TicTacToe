package numerical;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import boardgame.Saveable;
import boardgame.Grid;

public class LoadSaveNumerical implements Saveable {

    private char playerTurn;
    private int[] board;
    private int gameDepth = 0;
    private int[] odd = {1,3,5,7,9};
    private int[] even = {0,2,4,6,8};
    private int[] used = {-50,-50,-50,-50,-50,-50,-50,-50,-50,-50,-50,-50,-50, -50};
    private String presetTurn;

    private Scanner read;

    private static FileReader fr;

    public void setTurn(char turn) {
        playerTurn = turn;
    }

    private char getTurn() {
        return playerTurn;
    }

    public String getPresetTurn() {
        return presetTurn;
    }

    public void setBoard(int[] newBoard) {
        board = newBoard;
    }

    public int[] getBoard() {
        return board;
    }

    public int getGameDepth() {
        return gameDepth;
    }

    public int[] getOddNums() {
        return odd;
    } 

    public int[] getEvenNums() {
        return even;
    }

    @Override
    public String getStringToSave(){
        String saving = "";
        int endLine = 0;

        saving += getTurn() + "\n";

        for (int i = 0; i < board.length; i++) {
            endLine++;

            if (board[i] != -100) {
                saving += board[i];
            } else {
                saving += "";
            }

            if (endLine == 3) {
                saving += "\n";
                endLine = 0;
            } else {
                saving += ",";
            }
        }

        return saving;
    }

    public static void saveToFile(String savingBoard) {
        int input = 0;
        JFileChooser fileChooser = new JFileChooser();
        input = fileChooser.showSaveDialog(null);

        if (input == JFileChooser.APPROVE_OPTION) {
            try {
                File f = new File(fileChooser.getSelectedFile().getAbsolutePath().toString());
                FileWriter fileWriter = new FileWriter(f);
                fileWriter.write(savingBoard);
                fileWriter.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error with that file",
                 "Invalid", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static String loadFromFile() {
        int input = 0;
        String stringToLoad = "";
        JFileChooser fileChooser = new JFileChooser();
        input = fileChooser.showOpenDialog(null);

        if (input == JFileChooser.APPROVE_OPTION) {
            try {
                File f = new File(fileChooser.getSelectedFile().getAbsolutePath());
                fr = new FileReader(f);
                int i;
                while ((i = fr.read()) != -1) {
                    stringToLoad += (char) i;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error with that file",
                 "Invalid", JOptionPane.ERROR_MESSAGE);
            }
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringToLoad;
    }

    @Override
    public void loadSavedString(String toLoad) {
        int i = 0;
        int[] loadedBoard = {-100, -100, -100, -100, -100, -100, -100, -100, -100};
        try {
            read = new Scanner(toLoad);
            read.useDelimiter(",|\n");
            playerTurn = read.next().charAt(0);
            presetHelper(playerTurn);
            while (read.hasNext()) {
                toLoad = read.next();
                if (!toLoad.equals("")) {
                    loadedBoard[i] = Character.getNumericValue(toLoad.charAt(0));
                    gameDepth++;
                } else if (toLoad.equals("")) { 
                    loadedBoard[i] = -100;
                } else {
                    throw new FileNotFoundException("Error - this file is valid");
                }
                i++;
                if (i >= 9) {
                    throw new FileNotFoundException("Error - file too long");
                }
            }
        } catch (FileNotFoundException e) {
        }
        read.close();
        board = loadedBoard;
    }

    public Grid setNewGame() {
        NumericalGrid newGrid = new NumericalGrid(3, 3);
        int[] loadedBoard = {-100, -100, -100, -100, -100, -100, -100, -100, -100};
        int posX = 1;
        int posY = 1;
        int index = 0;

        for (int i = 0; i < loadedBoard.length; i++) {
            if (board[i] != -100) {
                newGrid.setValue(posY, posX, String.valueOf(board[i]));
                used[index] = board[i];
                index++;
            } 
            posY++;
            if (posY == 4) {
                posY = 1;
                posX++;
            }
        }
        setOddAndEven();
    
        return newGrid;
    }
    
    public void setOddAndEven() {
        for (int i = 0; i < used.length; i++){
            if (used[i] > -10) {

                if (used[i] % 2 == 0) {
                    for (int k = 0; k < even.length; k++) {
                        if (even[k] == used[i]) {
                            even[k] = -50;
                        }
                    }
                } else {
                    for (int j = 0; j < odd.length; j++) {
                        if (odd[j] == used[i]) {
                            odd[j] = -50;
                        }
                    }
                }
            }
        }
    }

    private void presetHelper(char currentTurn) {
        if (currentTurn == 'O') {
            presetTurn = "Odd";
        } else if (currentTurn == 'E') {
            presetTurn = "Even";
        }
    }
}