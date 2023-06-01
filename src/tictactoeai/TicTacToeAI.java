/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tictactoeai;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
/**
 *
 * @author Ing. Jesus Espinoza
 */
public class TicTacToeAI extends JFrame{
    private JButton[][] buttons;
    private JButton resetButton;
    private JLabel statusLabel;
    private boolean playerTurn;
    private boolean gameFinished;
    private Random random;
    
    public TicTacToeAI(){
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        playerTurn = true;
        gameFinished = false;
        random = new Random();
        
        buttons = new JButton[3][3];
        resetButton = new JButton("Reset");
        statusLabel = new JLabel("Turno del jugador");
        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        JPanel resetPanel = new JPanel(new FlowLayout());
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                buttonPanel.add(buttons[row][col]);
            }
        }
        
        resetButton.addActionListener(new ResetButtonListener());
        resetPanel.add(resetButton);
        
        add(buttonPanel, BorderLayout.CENTER);
        add(resetPanel, BorderLayout.SOUTH);
        add(statusLabel, BorderLayout.NORTH);
    }
    
    private boolean checkWin(String symbol){
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(symbol)
                    && buttons[row][1].getText().equals(symbol)
                    && buttons[row][2].getText().equals(symbol)) {
                return true;
            }
        }
        
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(symbol)
                    && buttons[1][col].getText().equals(symbol)
                    && buttons[2][col].getText().equals(symbol)) {
                return true;
            }
        }
        
        if (buttons[0][0].getText().equals(symbol)
                && buttons[1][1].getText().equals(symbol)
                && buttons[2][2].getText().equals(symbol)) {
            return true;
        }

        if (buttons[0][2].getText().equals(symbol)
                && buttons[1][1].getText().equals(symbol)
                && buttons[2][0].getText().equals(symbol)) {
            return true;
        }
        
        return false;
    }
    
    private boolean isBoardFull(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void resetBoard(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        playerTurn = true;
        gameFinished = false;
        statusLabel.setText("Turno del jugador");
    }
    
    private void computerMove(){
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().isEmpty());

        buttons[row][col].setText("O");

        if (checkWin("O")) {
            statusLabel.setText("La computadora ganó!");
            gameFinished = true;
        } else if (isBoardFull()) {
            statusLabel.setText("Es un empate!");
            gameFinished = true;
        } else {
            playerTurn = true;
            statusLabel.setText("Turno del jugador");
        }
    }
    
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            if (gameFinished) {
                return;
            }

            JButton button = buttons[row][col];

            if (!button.getText().isEmpty()) {
                return;
            }

            button.setText("X");

            if (checkWin("X")) {
                statusLabel.setText("Usted ganó!");
                gameFinished = true;
            } else if (isBoardFull()) {
                statusLabel.setText("Es un empate!");
                gameFinished = true;
            } else {
                playerTurn = false;
                statusLabel.setText("Turno del jugador");
                computerMove();
            }
        }
    }
    
    private class ResetButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            resetBoard();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TicTacToeAI game = new TicTacToeAI();
                game.setVisible(true);
            }
        });
    }
}
