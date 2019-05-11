package Farkle;

import javax.swing.*;

public class Player {

    private String name;
    private int score;
    private boolean turn;
    private JLabel score_label;

    // Constructor
    public Player(String name, int score, boolean turn, JLabel label){
        this.name = name;
        this.score = score;
        this.turn = turn;
        this.score_label = label;
    }

    // Getters and Setters
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public boolean isTurn() { return turn; }

    public void setTurn(boolean turn) { this.turn = turn; }

    public JLabel getScore_label() { return score_label; }

    public void setScore_label(JLabel score_label) { this.score_label = score_label; }


    // Add to player score
    public void addToScore(int score) { this.score += score; }

    // Change players score label
    public void changeScoreLabel() {
        this.score_label.setText(Integer.toString(this.score));
    }


    // This is used for adding players at the beginning of the game
    // The user enters the name and only creates/returns a new player if they enter some text
    public static Player addPlayer(String message, boolean turn, JLabel label){
        while (true) {
            String name = JOptionPane.showInputDialog(message);

            if (!name.equals(""))
                return new Player(name, 0, turn, label);
        }
    }


}
