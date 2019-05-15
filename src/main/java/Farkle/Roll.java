package Farkle;

import javax.swing.*;
import java.util.Random;

public class Roll {
    /**
     * Ben Wadsworth
     * 5/15/2019
     * This class handles rolling the dice
     *
     *
     *
     * Roll the dice using a random number generator
     * Check if the dice is in play and roll if it is
     * Then set the label as the correct image
     * and set the new Dice value
    **/
    public static void rollDice(Dice[] dice, ImageIcon[] images){

        Random random = new Random();

        for (Dice die : dice) {
            if (die.isInPlay()) {
                int roll = random.nextInt(6) + 1;

                JLabel die_label = die.getLabel();
                ImageIcon image = images[roll];
                die_label.setIcon(image);

                die.setValue(roll);
            }
        }
    }
}
