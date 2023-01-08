package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import numerical.NumericalView;
import tictactoe.TictactoeView;

public class GameUI extends JFrame {

    private JMenuBar menuBar;
    private JLabel label;
    private JPanel gamePanel;

    public GameUI() {

        setTitle("CIS*2430 Assignment Three - Sebastian Mollejas");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 600);
        this.getContentPane().setBackground(new Color(0x10b16));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        createMenu();
        setJMenuBar(menuBar);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setBackground(Color.white);
        rightPanel.setBackground(Color.white);

        leftPanel.setPreferredSize(new Dimension(100, 100));
        rightPanel.setPreferredSize(new Dimension(100, 100));

        this.add(menuText1(), BorderLayout.NORTH);
        this.add(makeGamePanel(), BorderLayout.CENTER);
        this.add(menuText2(), BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private JMenuBar createMenu() {
        menuBar = new JMenuBar();

        Border menuBorder = BorderFactory.createLineBorder(Color.white, 3);
        menuBar.setBorder(menuBorder);
        menuBar.setBackground(new Color(0x10b16));

        menuBar.add(optionsMenu());
        menuBar.add(profileMenu());
        
        return menuBar;
    }
    
    private JMenu optionsMenu() {
        JMenu optionsMenu = new JMenu("Options");
        
        optionsMenu.setFont(new Font("MV Boli", Font.PLAIN, 17));
        optionsMenu.setForeground(Color.white);
        
        JMenuItem exitItem = new JMenuItem("Quit Game");
        
        exitItem.setFont(new Font("MV Boli", Font.PLAIN, 17));

        exitItem.addActionListener(e -> exitGame());
        
        optionsMenu.add(exitItem);
        
        return optionsMenu;
    }
    
    private JMenu profileMenu() {
        JMenu profileMenu = new JMenu("Profiles");
        
        profileMenu.setFont(new Font("MV Boli", Font.PLAIN, 17));
        profileMenu.setForeground(Color.white);
        
        JMenuItem playerItem = new JMenuItem("Player Statistics");
        playerItem.setFont(new Font("MV Boli", Font.PLAIN, 17));
        
        profileMenu.add(playerItem);
        
        return profileMenu;
    }
    
    private JLabel menuText1() {
        label = new JLabel();

        Border border = BorderFactory.createLineBorder(Color.white, 3);

        label.setText("Tic Tac Toe Database");
        label.setFont(new Font("MV Boli", Font.BOLD, 60));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setBorder(border);
        label.setForeground(Color.white);

        return label;
    }

    private JLabel menuText2() {
        label = new JLabel();

        Border border = BorderFactory.createLineBorder(Color.white, 3);

        label.setText("Developed by Yours Truly");
        label.setFont(new Font("MV Boli", Font.BOLD, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setBorder(border);
        label.setForeground(Color.white);

        return label;
    }

    private JButton makeTTTButton() {
        JButton buttonOne = new JButton("Play Tic Tac Toe");
        buttonOne.setFont(new Font("MV Boli", Font.BOLD, 40));
        buttonOne.setBackground(new Color(0x10b16));
        buttonOne.setForeground(Color.white);
        buttonOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonOne.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonOne.setBounds(200, 100, 500, 150);

        //action listener
        buttonOne.addActionListener(e -> tictactoe());

        return buttonOne;
    }

    private JButton makeNTTTButton() {
        JButton buttonTwo = new JButton("Play Numerical Tic Tac Toe");
        buttonTwo.setFont(new Font("MV Boli", Font.BOLD, 40));
        buttonTwo.setBackground(new Color(0x10b16));
        buttonTwo.setForeground(Color.white);
        buttonTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonTwo.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonTwo.setBounds(200, 100, 500, 150);

        // action listener
        buttonTwo.addActionListener(e -> numerical());

        return buttonTwo;
    }

    private JPanel makeGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setBackground(new Color(0x10b16));
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        
        Border frameBorder = BorderFactory.createLineBorder(Color.white, 2);
        gamePanel.setBorder(frameBorder);

        gamePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        gamePanel.add(makeTTTButton());

        gamePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        gamePanel.add(makeNTTTButton());

        return gamePanel;
    }

    public void start(){
        gamePanel.setVisible(true);
        getContentPane().repaint();
        getContentPane().revalidate();
    }


    protected void tictactoe() {
        gamePanel.setVisible(false);
        this.add(new TictactoeView(3, 3, this), BorderLayout.CENTER);
        getContentPane().repaint();
        getContentPane().revalidate();
    }

    protected void numerical() {
        gamePanel.setVisible(false);
        this.add(new NumericalView(3, 3, this), BorderLayout.CENTER);
        getContentPane().repaint();
        getContentPane().revalidate();
    }

    protected void exitGame() {
        System.exit(0);
    }

    
    public static void main(String[] args) {
        GameUI frame = new GameUI();
    } 
}