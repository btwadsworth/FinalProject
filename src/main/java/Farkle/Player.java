package Farkle;

import javax.swing.*;

public class Player {

    private String name;
    private int score;
    private boolean turn;

    // Constructor
    public Player(String name, int score, boolean turn){
        this.name = name;
        this.score = score;
        this.turn = turn;
    }

    // Getters and Setters
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public boolean isTurn() { return turn; }

    public void setTurn(boolean turn) { this.turn = turn; }


    // This is used for adding players at the beginning of the game
    // The user enters the name and only creates/returns a new player if they enter some text
    public static Player addPlayer(String message, boolean turn){
        while (true) {
            String name = JOptionPane.showInputDialog(message);

            if (!name.equals(""))
                return new Player(name, 0, turn);
        }
    }


}
