package tictactoe;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import boardgame.ui.PositionAwareButton;
import game.GameUI;

public class TictactoeView extends JPanel {

    private JLabel label;
    private TictactoeGame game;
    private PositionAwareButton[][] buttons;
    private JPanel buttonPanel;
    private GameUI root;
    private JFrame savingFrame;
    

    public TictactoeView(int sizeX, int sizeY, GameUI myFrame) {
        // call the superclass constructor
        super();    
        setLayout(new BorderLayout());
        root = myFrame;

        // instantiate the controller
        setGameController(new TictactoeGame(sizeX, sizeY));   


        // make a new label to store messages
        add(gameText(), BorderLayout.NORTH);  // Messages go on top   
        add(buttonsPanel(), BorderLayout.SOUTH);
        add(makeButtonGrid(sizeX, sizeY), BorderLayout.CENTER);
    }

    public void setGameController(TictactoeGame controller){
        this.game = controller;
        }

    private JLabel gameText() {
        label = new JLabel();

        label.setText("Currently playing Tic Tac Toe");
        label.setFont(new Font("MV Boli", Font.BOLD, 25));
        label.setHorizontalAlignment(JLabel.LEFT);

        return label;
    }

    private JButton makeMenuButton(){
        JButton button = new JButton("Menu");
        button.setFont(new Font("MV Boli", Font.BOLD, 25));
        button.setFocusable(false);
        button.addActionListener(e->goBack());
        return button;
    }

    private JButton makeSaveButton(){
        JButton button = new JButton("Save");
        button.setFont(new Font("MV Boli", Font.BOLD, 25));
        button.setFocusable(false);
        button.addActionListener(e-> saveGame());
        return button;
    }

    private JButton makeLoadButton(){
        JButton button = new JButton("Load");
        button.setFont(new Font("MV Boli", Font.BOLD, 25));
        button.setFocusable(false);
        button.addActionListener(e-> loadGame());
        return button;
    }

    private JPanel buttonsPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0x10b16));

        Border frameBorder = BorderFactory.createLineBorder(Color.white, 2);
        buttonPanel.setBorder(frameBorder);

        buttonPanel.add(makeMenuButton());
        buttonPanel.add(makeSaveButton());
        buttonPanel.add(makeLoadButton());

        return buttonPanel;
    }

    private JPanel makeButtonGrid(int tall, int wide){
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(wide, tall));
        for (int y = 0; y < wide; y++){
            for (int x = 0; x < tall; x++){ 
                //Create buttons and link each button back to a coordinate on the grid
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x + 1); //made the choice to be 1-based
                buttons[y][x].setDown(y + 1);
                buttons[y][x].addActionListener(e->{
                                            enterNumber(e);
                                            checkGameState();
                                            });
                    panel.add(buttons[y][x]);
                }
            }
        return panel;
    }

/* controller methods start here */

    private void checkGameState() { 
        int selection = 0;

        if (game.isDone()){
            if (game.getWinner() == 0) {
                JOptionPane.showMessageDialog(null, "The Game was a Tie!",
            "Tie!", JOptionPane.INFORMATION_MESSAGE);
            } else if (game.getWinner() == 1 || game.getWinner() == 2) {
                JOptionPane.showMessageDialog(null, "The winner is Player " + game.getWinner() + "!",
            "Winner!", JOptionPane.INFORMATION_MESSAGE);
            }
            selection = JOptionPane.showConfirmDialog(null,
             "Would you like to play again?", "PlayAgain?", JOptionPane.YES_NO_OPTION);
            if(selection == JOptionPane.NO_OPTION) {
                this.setVisible(false);
                root.start();
            } else {
                newGame();
            }
        }
    }   

    protected void updateView(){ // update board or view
        //update the labels on the buttons according to the model
        for (int y = 0; y < game.getHeight(); y++){
            for (int x = 0; x < game.getWidth(); x++){  
                buttons[y][x].setText(game.getCell(buttons[y][x].getAcross(),buttons[y][x].getDown())); 
            }
        }

    }

    private void enterNumber(ActionEvent e) { // prompt input via dialog box
        //get input from user
        String playerInput = "";
        String message = "Player " + game.getTurn() + " turn: ";

        do {
            playerInput = JOptionPane.showInputDialog(message);
            if (playerInput == null) {
                break;
            }
            message = "<html><b style = 'color:red'>Player " 
            + game.getTurn() + " turn: </b><br>" 
            + "Please enter correct turn.";
        } while (!playerInput.equals(Character.toString(game.getTurn())));
        
        //send input to game and update view
        if (playerInput != null && playerInput.equals(Character.toString(game.getTurn()))) {
            PositionAwareButton clicked = ((PositionAwareButton)(e.getSource()));
            if (game.takeTurn(clicked.getAcross(), clicked.getDown(), playerInput)) {
                int originalIndex = (clicked.getAcross() - 1) + (clicked.getDown() - 1) * 3;
                game.setDepth();
                game.setBoard(game.getPosition(originalIndex));
                clicked.setText(game.getCell(clicked.getAcross(),clicked.getDown()));
                game.setWinner(game.getDepth(), game.getBoard(), true);
                game.setTurn();
            }
        }
    }

    protected void newGame(){ 
        game.newGame();
        updateView();
    }

    protected void goBack() {
        this.setVisible(false);
        root.start();
    }

    protected void saveGame() {
        LoadSaveClassic saveClassicFile = new LoadSaveClassic();
        saveClassicFile.setBoard(game.getBoard());
        saveClassicFile.setTurn(game.getTurn());
        LoadSaveClassic.saveToFile(saveClassicFile.getStringToSave());
    }

    protected void loadGame() {
        LoadSaveClassic saveClassicFile = new LoadSaveClassic();
        saveClassicFile.loadSavedString(LoadSaveClassic.loadFromFile());
        game.setEntireBoard(saveClassicFile.getBoard());
        game.presetTurn(saveClassicFile.getTurn());
        game.presetDepth(saveClassicFile.getGameDepth());
        this.game.setNewGrid(saveClassicFile.setNewGame());
        updateView();
    }
}
