package Farkle;

import javax.swing.*;

public class Dice {
    private JLabel label;
    private JCheckBox checkbox;
    private int value;
    private boolean inPlay;
    private boolean selected;

    // Constructor
    Dice(JLabel label, JCheckBox box, Integer value, Boolean in_play, Boolean selected){
        this.label = label;
        this.checkbox = box;
        this.value = value;
        this.inPlay = in_play;
        this.selected = selected;
    }

    // Getters and Setters
    public JLabel getLabel() { return label; }

    public void setLabel(JLabel label) { this.label = label; }

    public JCheckBox getCheckbox() { return checkbox; }

    public void setCheckbox(JCheckBox checkbox) { this.checkbox = checkbox; }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public boolean isInPlay() { return inPlay; }

    public void setInPlay(boolean inPlay) { this.inPlay = inPlay; }

    public boolean isSelected() { return selected; }

    public void setSelected(boolean selected) { this.selected = selected; }


}
