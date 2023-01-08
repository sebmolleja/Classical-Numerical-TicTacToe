package tictactoe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import boardgame.Grid;
import boardgame.Saveable;

public class LoadSaveClassic implements Saveable {

    private char playerTurn;
    private char[] board;
    private Scanner read;
    private static FileReader fr;
    private int gameDepth = 0;

    public void setTurn(char turn) {
        playerTurn = turn;
    }

    public char getTurn() {
        return playerTurn;
    }

    public void setBoard(char[] newBoard) {
        board = newBoard;
    }

    public char[] getBoard() {
        return board;
    }

    public int getGameDepth() {
        return gameDepth;
    }

    private int[] getIndices() {
        int[] indices = {0, 2, 4, 12, 14, 16, 24, 26, 28};
        return indices;
    }

    @Override
    public String getStringToSave(){
        String saving = "";
        int[] index = getIndices();
        int endLine = 0;

        saving += getTurn() + "\n";

        for (int i = 0; i < getIndices().length; i++) {
            endLine++;
            if (board[index[i]] == 'X') {
                saving += board[index[i]];
            } else if (board[index[i]] == 'O') {
                saving += board[index[i]];
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
        char[] loadedBoard = {'0','|','1','|','2','\n','-','+','-','+','-','\n','3','|','4','|','5',
        '\n','-','+','-','+','-','\n','6','|','7','|','8','\n'};
        int[] index = getIndices();
        try {
            read = new Scanner(toLoad);
            read.useDelimiter(",|\n");
            playerTurn = read.next().charAt(0); 
            for (int i = 0; read.hasNext(); i++) {
                toLoad = read.next();
                if (toLoad.equals("X")) {
                    loadedBoard[index[i]] = toLoad.charAt(0); 
                    gameDepth++;
                } else if (toLoad.equals("O")) {
                    loadedBoard[index[i]] = toLoad.charAt(0);
                    gameDepth++;
                } else if (!toLoad.equals("X") && !toLoad.equals("O")) {
                    loadedBoard[index[i]] = Character.forDigit(i, 10);
                } else {
                    throw new FileNotFoundException("Error - this file is valid");
                }
                if (i >= 9) {
                    throw new FileNotFoundException("Error - file too long");
                }
            }
        } catch (FileNotFoundException e) {}
        read.close();
        board = loadedBoard;
    }

    public Grid setNewGame() {
        TictactoeGrid newGrid = new TictactoeGrid(3, 3);
        int[] index = getIndices();
        int posX = 1;
        int posY = 1;

        for (int i = 0; i < getIndices().length; i++) {
            if (board[index[i]] == 'X') {
                newGrid.setValue(posY, posX, String.valueOf(board[index[i]]));
            } else if (board[index[i]] == 'O') {
                newGrid.setValue(posY, posX, String.valueOf(board[index[i]]));
            } else {
                newGrid.setValue(posY, posX, " ");
            }
            posY++;
            if (posY == 4) {
                posY = 1;
                posX++;
            }
        }

        return newGrid;
    }
    
}