package Farkle;

import javax.swing.*;

public class Dice {
    private JLabel label;
    private int dice_value;
    private boolean is_in_play;
    private boolean is_selected;

    Dice(JLabel label, Integer value, Boolean in_play, Boolean selected){
        this.label = label;
        this.dice_value = value;
        this.is_in_play = in_play;
        this.is_selected = selected;
    }

    public JLabel getLabel() { return label; }
    public void setLabel(JLabel label) { this.label = label; }

    public int getDice_value() { return dice_value; }
    public void setDice_value(int dice_value) { this.dice_value = dice_value; }

    public boolean isIs_in_play() { return is_in_play; }
    public void setIs_in_play(boolean is_in_play) { this.is_in_play = is_in_play; }

    public boolean isIs_selected() { return is_selected; }
    public void setIs_selected(boolean is_selected) { this.is_selected = is_selected; }


}
