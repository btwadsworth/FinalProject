package Farkle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class FarkleGUI extends JFrame {
    private JPanel mainPanel;
    private JLabel lblPlayerOne;
    private JLabel lblPlayerTwo;
    private JLabel lblPlayerOneScore;
    private JLabel lblPlayerTwoScore;
    protected JLabel dice2;
    protected JLabel dice3;
    protected JLabel dice1;
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

    public static HashMap<Integer, ImageIcon> images = new HashMap<>();
    public static HashMap<JCheckBox, JLabel> checkBoxes = new HashMap<>();
    private int turn = 1;
    private int TOTAL = 0;
    private int ROLLTOTAL = 0;
    private int PLAYERONESCORE = 0;
    private int PLAYERTWOSCORE = 0;

    private static final int INVALID_SCORE = -1;
    private static final int TOTAL_DICE = 6;


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
                System.out.println(VALID_DICE);
                if (VALID_DICE) {
                    int count = TOTAL_DICE;
                    KeepDice();
                    for (JCheckBox box : checkBoxes.keySet()) {
                        if (box.isSelected())
                            count--;
                    }
                    SetDice(Roll.RollDice(count));
                }
                else {
                    JOptionPane.showMessageDialog(null, "One or more of the selected dice can't be kept.",
                            "Invalid Move",JOptionPane.WARNING_MESSAGE );
                }
            }
        });

        btnBankPoints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (VALID_DICE){

                    ResetDice();
                    if (turn == 1)
                        turn = 2;
                    else
                        turn = 1;
                    for (JCheckBox box : checkBoxes.keySet()){  // Disable checkboxes because dice have not been rolled yet
                        box.setEnabled(false);
                    }
                    for (JLabel dice : checkBoxes.values()) {
                        dice.setIcon(images.get(0));
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "One or more of the selected dice can't be kept.",
                            "Invalid Move",JOptionPane.WARNING_MESSAGE );
                }


            }
        });

        for (JCheckBox box : checkBoxes.keySet()){

            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { SetRunningTotal(); }
            });
        }
    }

    private void ResetDice() {
        for (JCheckBox box : checkBoxes.keySet()){
            box.setEnabled(true);
            box.setSelected(false);
        }
    }

    private void KeepDice() {
        for (JCheckBox box : checkBoxes.keySet()){
            if (box.isSelected())
                box.setEnabled(false);
            else
                box.setEnabled(true);
        }
    }

    private void Setup() {
        // Get all images, set images HashMap and set icons

        try {
            ImageIcon one = new ImageIcon(new ImageIcon("faceOne.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            ImageIcon two = new ImageIcon(new ImageIcon("faceTwo.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            ImageIcon three = new ImageIcon(new ImageIcon("faceThree.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            ImageIcon four = new ImageIcon(new ImageIcon("faceFour.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            ImageIcon five= new ImageIcon(new ImageIcon("faceFive.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            ImageIcon six = new ImageIcon(new ImageIcon("faceSix.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            ImageIcon roll = new ImageIcon(new ImageIcon("rollDice.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

            images.put(0, roll);
            images.put(1, one);
            images.put(2, two);
            images.put(3, three);
            images.put(4, four);
            images.put(5, five);
            images.put(6, six);

            dice1.setIcon(roll);
            dice2.setIcon(roll);
            dice3.setIcon(roll);
            dice4.setIcon(roll);
            dice5.setIcon(roll);
            dice6.setIcon(roll);

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        checkBoxes.put(chkBoxDiceOne, dice1);
        checkBoxes.put(chkBoxDiceTwo, dice2);
        checkBoxes.put(chkBoxDiceThree, dice3);
        checkBoxes.put(chkBoxDiceFour, dice4);
        checkBoxes.put(chkBoxDiceFive, dice5);
        checkBoxes.put(chkBoxDiceSix, dice6);
    }


    private void AddPlayers() {
        // Get player names using JOptionPane.showInputDialog
        // Set player name labels using input

        String playerOne = JOptionPane.showInputDialog("Player One:");
        lblPlayerOne.setText(playerOne);
        String playerTwo = JOptionPane.showInputDialog("Player Two:");
        lblPlayerTwo.setText(playerTwo);
    }


    private void SetDice(ArrayList<Integer> rolls) {

        int roll = 0;

        for (JCheckBox dice : checkBoxes.keySet()){
            if (!dice.isSelected()){
                checkBoxes.get(dice).setIcon(images.get(rolls.get(roll)));
                roll++;
            }
        }
    }

    private void SetRunningTotal(){

        Status scoreStatus = Scoring.runningTotal();

        VALID_DICE = scoreStatus.diceValid;
        ROLLTOTAL = scoreStatus.score;
        lblRunningTotal.setText(Integer.toString(TOTAL + ROLLTOTAL));

    }

}

