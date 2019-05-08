package Farkle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

    // NEW!!!
    private Dice[] dice = new Dice[6];

    private JLabel[] labels = {dice1, dice2, dice3, dice4, dice5, dice6};
    private JCheckBox[] checkBoxes = {chkBoxDiceOne, chkBoxDiceTwo, chkBoxDiceThree, chkBoxDiceFour,
            chkBoxDiceFive, chkBoxDiceSix};
    private ImageIcon[] images = new ImageIcon[7];

    private boolean first_roll_of_turn = true;

    private Random random = new Random();


    ////////////////
    //public static HashMap<Integer, ImageIcon> images = new HashMap<>();
    //public static HashMap<JCheckBox, JLabel> checkBoxes = new HashMap<>();
    private int turn = 1;
    private int TOTAL = 0;
    private int ROLLTOTAL = 0;
    private int PLAYERONESCORE = 0;
    private int PLAYERTWOSCORE = 0;

    protected static boolean VALID_DICE = true;


    public FarkleGUI() {

        setContentPane(mainPanel);

        Setup();

        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        AddPlayers();

        AddListeners();
        
    }

    private void AddListeners() { // Add listeners to the form

        btnRollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (first_roll_of_turn)
                    ResetDice();
                    RollDice();
            }
        });

        btnBankPoints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                if (VALID_DICE){
//
//                    ResetDice();
//                    if (turn == 1)
//                        turn = 2;
//                    else
//                        turn = 1;
//                    for (JCheckBox box : checkBoxes.keySet()){  // Disable checkboxes because dice have not been rolled yet
//                        box.setEnabled(false);
//                    }
//                    for (JLabel dice : checkBoxes.values()) {
//                        dice.setIcon(images.get(0));
//                    }
//                }
//                else {
//                    JOptionPane.showMessageDialog(null, "One or more of the selected dice can't be kept.",
//                            "Invalid Move",JOptionPane.WARNING_MESSAGE );
//                }
            }
        });

        // Generate ActionListeners for all checkboxes
        for (JCheckBox box : checkBoxes){
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { SetRunningTotal(); }
            });
        }
    }

    private void RollDice() {

        for (Dice die : dice)
        {
            if (die.isIs_in_play())
            {
                int roll = random.nextInt(6) + 1;

                JLabel die_label = die.getLabel();
                ImageIcon image = images[roll];
                die_label.setIcon(image);

                die.setDice_value(roll);
            }
        }
    }

    private void ResetDice() {
        for (JCheckBox box : checkBoxes){
            box.setEnabled(true);
            box.setSelected(false);
        }
        for (Dice die : dice){
            die.getLabel().setIcon(images[0]);
            die.setDice_value(0);
            die.setIs_in_play(true);
            die.setIs_selected(false);
        }
    }

    private void KeepDice() {
        for (JCheckBox box : checkBoxes){
            if (box.isSelected())
                box.setEnabled(false);
            else
                box.setEnabled(true);
        }
    }

    private void Setup() {
        // Get all images and add them to the images Array
        try {
            images[0] = new ImageIcon(new ImageIcon("rollDice.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[1] = new ImageIcon(new ImageIcon("faceOne.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[2] = new ImageIcon(new ImageIcon("faceTwo.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[3] = new ImageIcon(new ImageIcon("faceThree.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[4] = new ImageIcon(new ImageIcon("faceFour.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[5] = new ImageIcon(new ImageIcon("faceFive.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            images[6] = new ImageIcon(new ImageIcon("faceSix.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

            for (JLabel label : labels)  // Set all dice icons to the 'roll' picture
                label.setIcon(images[0]);

            // Create 6 new Dice
            for (int i = 0; i < 6; i++) {
                Dice die = new Dice(labels[i], 0, false, false);
                dice[i] = die;
            }
        } catch (Exception ex) {  // Catch if the images fail
            System.out.println(ex.toString());
        }
    }


    private void AddPlayers() {
        // Get player names using JOptionPane.showInputDialog
        // Set player name labels using input

        String playerOne = JOptionPane.showInputDialog("Player One:");
        lblPlayerOne.setText(playerOne);
        String playerTwo = JOptionPane.showInputDialog("Player Two:");
        lblPlayerTwo.setText(playerTwo);
    }

    private void SetRunningTotal(){

//        int score = Scoring.RunningTotal();
//
//        ROLLTOTAL = score;
//        lblRunningTotal.setText(Integer.toString(TOTAL + ROLLTOTAL));

    }

}

