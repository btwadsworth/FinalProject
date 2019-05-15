package Farkle;

import javax.swing.*;

public class Validation {
    /**
     * Ben Wadsworth
     * 5/15/2019
     * This class handles some of the validation for the application
     */

    // This checks to see if none of the dice are selected
    public boolean checkNoneSelected(Dice[] dice){

        for (Dice die : dice){
            JCheckBox box = die.getCheckbox();
            if (box.isEnabled() && box.isSelected())
                return false;
        }

        return true;
    }

    // This checks to see if all of the dice are selected
    public boolean checkAllSelected(Dice[] dice){
        for (Dice die : dice){
            JCheckBox box = die.getCheckbox();
            if (box.isEnabled() && !box.isSelected())
                return false;
        }
        return true;
    }
}
