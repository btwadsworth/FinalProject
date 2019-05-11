package Farkle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FarkleGUI extends JFrame {
    private JPanel mainPanel;
    private JLabel lblPlayerOne;
    private JLabel lblPlayerTwo;
    private JLabel lblPlayerOneScore;
    private JLabel lblPlayerTwoScore;
    protected JLabel dice1;
    protected JLabel dice2;
    protected JLabel dice3;
    protected JLabel dice4;
    protected JLabel dice5;
    protected JLabel dice6;
    protected JCheckBox chkBoxDiceOne;
    protected JCheckBox chkBoxDiceTwo;
    protected JCheckBox chkBoxDiceThree;
    protected JCheckBox chkBoxDiceFour;
    protected JCheckBox chkBoxDiceFive;
    protected JCheckBox chkBoxDiceSix;
    private JButton btnRollDice;
    private JLabel lblRunningTotal;
    private JButton btnBankPoints;

    private Dice[] dice = new Dice[6];
    private JLabel[] labels = {dice1, dice2, dice3, dice4, dice5, dice6};
    private JCheckBox[] checkBoxes = {chkBoxDiceOne, chkBoxDiceTwo, chkBoxDiceThree, chkBoxDiceFour,
                                      chkBoxDiceFive, chkBoxDiceSix};
    private ImageIcon[] images = new ImageIcon[7];

    private boolean first_roll_of_turn = true;
    private Status status = new Status(true, 0);
    private int roll_total;

    private Validation validation = new Validation();
    private Turn turn = new Turn();

    private Player[] players = new Player[2];

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

                if (first_roll_of_turn)
                {
                    turn.startOfTurn(dice);
                    first_roll_of_turn = false;
                    Roll.rollDice(dice, images);
                }
                else
                {
                    if (validation.checkNoneSelected(dice))
                        showMessage("You must keep at least one dice to roll again.");
                    else {
                        if (status.isValid_dice())
                        {
                            roll_total += status.getRoll_total();

                            if (validation.checkAllSelected(dice)){
                                turn.keepRolling(dice);
                                Roll.rollDice(dice, images);
                            }
                            else{
                                turn.keepDice(dice);
                                Roll.rollDice(dice, images);
                            }

                        }
                        else
                            showMessage("One or more of the selected dice can't be kept.");
                    }

                }

                if (Scoring.checkForFarkle(dice))
                {
                    showMessage("FARKLE! End of turn");
                    nextTurn();
                }
                else
                    btnRollDice.setText("Roll Again");
            }
        });

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
//                            player.setTurn(false);
                        }
//                        else
//                            player.setTurn(true);
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
                    status = Scoring.RunningTotal(dice);
                    int currentTotal = status.getRoll_total();
                    int visibleTotal = currentTotal + roll_total;
                    String total = Integer.toString(visibleTotal);
                    lblRunningTotal.setText(total);
                }
            });
        }
    }

//    private void startOfTurn() {
//
//        for (Dice die : dice){
//            die.getLabel().setIcon(images[0]);
//            die.getCheckbox().setSelected(false);
//            die.getCheckbox().setEnabled(true);
//            die.setValue(0);
//            die.setInPlay(true);
//            die.setSelected(false);
//        }
//    }

//    private void keepDice() {
//        for (Dice die : dice){
//            if (die.isSelected()){
//                die.setInPlay(false);
//                die.getCheckbox().setEnabled(false);
//            }
//        }
//    }

    private void changeTurn(){
        for (Player player : players){
            if (player.isTurn())
                player.setTurn(false);
            else
                player.setTurn(true);
        }
    }

    private void nextTurn(){
        changeTurn();
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
        players[0] = Player.addPlayer("Enter name for Player 1:", true, lblPlayerOneScore);
        players[1] = Player.addPlayer("Enter name for Player 2:", false, lblPlayerTwoScore);

        lblPlayerOne.setText(players[0].getName());
        lblPlayerTwo.setText(players[1].getName());
    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

}

