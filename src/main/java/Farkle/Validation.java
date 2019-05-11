package Farkle;

import javax.swing.*;

public class Validation {

    public boolean checkNoneSelected(Dice[] dice){
        for (Dice die : dice){
            JCheckBox box = die.getCheckbox();
            if (box.isEnabled() && box.isSelected())
                return false;
        }
        return true;
    }

    public boolean checkAllSelected(Dice[] dice){
        for (Dice die : dice){
            JCheckBox box = die.getCheckbox();
            if (box.isEnabled() && !box.isSelected())
                return false;
        }
        return true;
    }
}
