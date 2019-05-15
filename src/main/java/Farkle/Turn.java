package Farkle;

import javax.swing.*;

public class Turn {
    /**
     * Ben Wadsworth
     * 5/15/2019
     * This class handles actions relating to player turns
     */

    // Setup the dice for the start of a player turn
    public void startOfTurn(Dice[] dice){

        for (Dice die : dice){
            die.getCheckbox().setSelected(false);
            die.getCheckbox().setEnabled(true);
            die.setValue(0);
            die.setInPlay(true);
            die.setSelected(false);
        }
    }

    // This handles when the player keeps dice and rolls again
    public void keepDice(Dice[] dice){
        for (Dice die : dice){
            if (die.isSelected()){
                die.setInPlay(false);
                die.getCheckbox().setEnabled(false);
            }
        }
    }

    // This handles when all dice score and are valid
    public void keepRolling(Dice[] dice){
        for (Dice die : dice){
            die.getCheckbox().setEnabled(true);
            die.getCheckbox().setSelected(false);
            die.setInPlay(true);
        }
    }

    // This handles when it is the end of a players turn
    public void endTurn(Dice[] dice, ImageIcon[] images){
        for (Dice die : dice){
            die.getLabel().setIcon(images[0]);
            die.getCheckbox().setSelected(false);
            die.getCheckbox().setEnabled(false);
        }
    }
}
