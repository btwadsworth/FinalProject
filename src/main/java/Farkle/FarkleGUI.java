package Farkle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FarkleGUI extends JFrame {
    /**
     * Ben Wadsworth
     * 5/14/2019
     * This is the main class for the program, handling all the gui components and their listeners
     */

    private JPanel mainPanel;
    private JLabel lblPlayerOne;
    private JLabel lblPlayerTwo;
    private JLabel lblPlayerOneScore;
    private JLabel lblPlayerTwoScore;
    private JLabel dice1;
    private JLabel dice2;
    private JLabel dice3;
    private JLabel dice4;
    private JLabel dice5;
    private JLabel dice6;
    private JCheckBox chkBoxDiceOne;
    private JCheckBox chkBoxDiceTwo;
    private JCheckBox chkBoxDiceThree;
    private JCheckBox chkBoxDiceFour;
    private JCheckBox chkBoxDiceFive;
    private JCheckBox chkBoxDiceSix;
    private JButton btnRollDice;
    private JLabel lblRunningTotal;
    private JButton btnBankPoints;
    private JLabel lblPlayerRolling;
    private JButton btnSaveGame;
    private JButton btnRules;
    private JButton btnSelectAll;

    // Declare variables
    private Dice[] dice = new Dice[6];
    private JLabel[] labels = {dice1, dice2, dice3, dice4, dice5, dice6};
    private JCheckBox[] checkBoxes = {chkBoxDiceOne, chkBoxDiceTwo, chkBoxDiceThree, chkBoxDiceFour,
                                      chkBoxDiceFive, chkBoxDiceSix};
    private ImageIcon[] images = new ImageIcon[7];
    private JLabel[] playerScoreLabels = {lblPlayerOneScore, lblPlayerTwoScore};
    private boolean first_roll_of_turn = true;
    private Status status = new Status(true, 0);
    private int roll_total;
    private Player[] players = new Player[2];
    private int WINNING_SCORE = 10000;


    private Validation validation = new Validation();
    private Turn turn = new Turn();
    private FarkleDB farkleDB = new FarkleDB();

    public FarkleGUI() {

        setContentPane(mainPanel);

        setup(); // setup the images and Arrays for the game

        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Get names for both players and create new Player Objects for them
        setupPlayers();

        // Add listeners for GUI form
        addListeners();
        
    }

    private void addListeners() { // Add listeners to the form

         // The listener for when the "Roll Dice" button is clicked
         // Check if it is the first roll of the turn or not to reset the dice or keep selected dice
         // Then call Roll.rollDice to roll the dice
        btnRollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (first_roll_of_turn) {
                    turn.startOfTurn(dice);  // Sets the dice up for the roll
                    first_roll_of_turn = false;
                    Roll.rollDice(dice, images);
                }

                else {
                    // Can't roll again again without having at least one dice selected
                    if (validation.checkNoneSelected(dice))
                        showMessage("You must keep at least one dice to roll again.");

                    else {

                        // Checks the Status of the selected dice
                        if (status.isValid_dice()) {
                            roll_total += status.getRoll_total();

                            // If all dice are selected and valid the program rolls the dice again for you
                            if (validation.checkAllSelected(dice)){
                                turn.keepRolling(dice);
                                Roll.rollDice(dice, images);
                            }

                            else {
                                turn.keepDice(dice);
                                Roll.rollDice(dice, images);
                            }
                        }
                        else
                            showMessage("One or more of the selected dice can't be kept.");
                    }
                }

                // Immediately Checks if none of the dice rolled can score AKA "FARKLE"
                if (Scoring.checkForFarkle(dice)) {
                    showMessage("FARKLE! End of turn");
                    nextTurn();
                }

                else
                    btnRollDice.setText("Roll Again");
            }
        });

        // Bank points ends the rolling players turn, saves their score and changes the active rolling player
        //   to the other player.  Once a player scores equal to or more than the WINNING_SCORE, they win
        btnBankPoints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validation.checkNoneSelected(dice))
                    showMessage("You must select a dice in order to bank your points.");
                else{

                    for (Player player : players){

                        if (player.isTurn()){
                            int total = roll_total + status.getRoll_total();
                            player.addToScore(total);
                            player.changeScoreLabel();

                            if (player.getScore() >= WINNING_SCORE){
                                showMessage(player.getName() + " is the Winner!");
                                farkleDB.dropTable();
                                System.exit(0);
                            }
                        }
                    }

                    turn.endTurn(dice, images);
                    nextTurn();
                }
            }
        });

        // Generate ActionListeners for all checkboxes
        for (JCheckBox box : checkBoxes){
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    status = Scoring.RunningTotal(dice); // Generate the current Status for the dice selected

                    int currentTotal = status.getRoll_total();
                    int visibleTotal = currentTotal + roll_total;
                    String total = Integer.toString(visibleTotal);

                    lblRunningTotal.setText(total);  // Display running total

                    // If all dice are selected and scoring, roll again
                    if (Scoring.allSelected(dice) && status.isValid_dice()){
                        showMessage("The dice are hot! Keep 'em rolling!");
                        btnRollDice.doClick();
                    }
                }
            });
        }

        btnSaveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (showConfirm("Are you sure you want to save and quit?", "SAVE & QUIT")){
                    farkleDB.saveGame(players);
                    System.exit(0);
                }
            }
        });

        // Show the rules
        btnRules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showMessage(Rules.rules);
            }
        });

        // Select all dice
        btnSelectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox box : checkBoxes){
                    box.doClick();
                }
            }
        });

        // Triggered when the user tries to close the application
        // Asks the user if they want to save before closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Object[] options = { "Yes", "No" };
                JOptionPane pane = new JOptionPane(null);
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                pane.setMessage("Do you want to save the game before closing?");
                pane.setOptions(options);
                JDialog dialog = pane.createDialog(null, "Save Game?");
                dialog.setContentPane(pane);
                dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                dialog.setVisible(true);
                Object choice = pane.getValue();

                if (choice.equals("Yes"))
                    farkleDB.saveGame(players);

                System.exit(0);

            }
        });
    }

    // Handles when a player banks their points and setup for the next players turn
    private void nextTurn(){

        Player.changeTurns(players, lblPlayerRolling);
        turn.endTurn(dice, images);
        first_roll_of_turn = true;
        btnRollDice.setText("Roll Dice");
        status.setRoll_total(0);
        roll_total = 0;
        lblRunningTotal.setText("0");
    }

    private void setup() {
        // Get all images and add them to the images Array
        try {
            images[0] = new ImageIcon(new ImageIcon("rollDice.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[1] = new ImageIcon(new ImageIcon("faceOne.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[2] = new ImageIcon(new ImageIcon("faceTwo.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[3] = new ImageIcon(new ImageIcon("faceThree.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[4] = new ImageIcon(new ImageIcon("faceFour.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[5] = new ImageIcon(new ImageIcon("faceFive.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[6] = new ImageIcon(new ImageIcon("faceSix.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

            // Set all dice icons to the 'roll' picture
            for (JLabel label : labels)
                label.setIcon(images[0]);

            // Create 6 new Dice
            for (int i = 0; i < 6; i++) {
                Dice die = new Dice(labels[i], checkBoxes[i], 0, true, false);
                dice[i] = die;
            }
        } catch (Exception ex) {  // Catch if the images fail
            System.out.println(ex.toString());
        }
    }

    // Call Player to get players names and create new Player Objects
    private void setupPlayers() {

        boolean loadGame = loadGameConfirm();

        players = farkleDB.loadGame(playerScoreLabels, loadGame);

        lblPlayerOne.setText(players[0].getName());
        lblPlayerTwo.setText(players[1].getName());

        lblPlayerOneScore.setText(Integer.toString(players[0].getScore()));
        lblPlayerTwoScore.setText(Integer.toString(players[1].getScore()));

        lblPlayerRolling.setText(players[0].getName());
    }

    // Dialog Box
    private void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    // Confirm boc
    private boolean showConfirm(String message, String title) {
        return (JOptionPane.showConfirmDialog(this, message, title,
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
    }

    // Asks if the user wants to load a game or start a new game
    private boolean loadGameConfirm(){
        Object[] options = { "Load Game", "Start New Game" };
        int result = JOptionPane.showOptionDialog(
                        mainPanel,
                        "Would you like to load the saved game?",
                        "Load Game or Start New",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        null);

        return (result == JOptionPane.YES_NO_OPTION);
    }

}

