package Farkle;

import javax.swing.*;

public class Player {
    /**
     * Ben Wadsworth
     * 5/15/2019
     * This class handles the Player Objects
     */

    // Declare variables
    private String name;
    private int score;
    private boolean turn;
    private JLabel score_label;

    // Constructors
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

    // Change players turns and set the player rolling label
    public static void changeTurns(Player[] players, JLabel label){
        for (Player player : players){
            if (player.isTurn())
                player.setTurn(false);
            else {
                player.setTurn(true);
                label.setText(player.name);
            }
        }
    }


    // This is used for adding players at the beginning of the game
    // The user enters the name and only creates/returns a new player if they enter some text
    public static Player addPlayer(String message, boolean turn, JLabel label){
        String name = "";
        while (name.equals("")) {

            JOptionPane pane = new JOptionPane(null);
            pane.setWantsInput(true);
            JDialog dialog = pane.createDialog(null, message);
            dialog.setContentPane(pane);
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
            if (!pane.getInputValue().equals("uninitializedValue"))
                name = (String) pane.getInputValue();
        }
        return new Player(name, 0, turn, label);
    }
}
