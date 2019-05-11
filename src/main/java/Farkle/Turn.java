package Farkle;

import javax.swing.*;

public class Turn {

    public void startOfTurn(Dice[] dice){
        for (Dice die : dice){
            die.getCheckbox().setSelected(false);
            die.getCheckbox().setEnabled(true);
            die.setValue(0);
            die.setInPlay(true);
            die.setSelected(false);
        }
    }

    public void keepDice(Dice[] dice){
        for (Dice die : dice){
            if (die.isSelected()){
                die.setInPlay(false);
                die.getCheckbox().setEnabled(false);
            }
        }
    }

    public void keepRolling(Dice[] dice){
        for (Dice die : dice){
            die.getCheckbox().setEnabled(true);
            die.getCheckbox().setSelected(false);
            die.setInPlay(true);
        }
    }

    public void endTurn(Dice[] dice, ImageIcon[] images){
        for (Dice die : dice){
            die.getLabel().setIcon(images[0]);
            die.getCheckbox().setSelected(false);
            die.getCheckbox().setEnabled(false);
        }
    }
}
