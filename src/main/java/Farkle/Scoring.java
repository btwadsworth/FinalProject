package Farkle;

import javax.swing.*;

public class Scoring {

    public static Status RunningTotal(Dice[] dice){

        for (Dice die : dice){
            JCheckBox box = die.getCheckbox();
            if (box.isSelected())
                die.setSelected(true);
            else
                die.setSelected(false);
        }

        int[] values = getDiceValues(dice);

        if (straight(values))
            return new Status(true, 1200);

        if (threePairs(values))
            return new Status(true, 1500);

        int total = countTotal(values);

        if (checkForInvalidSelection(values))
            return new Status(false, total);
        else
            return new Status(true, total);
    }


    private static int countTotal(int[] values) {
        int total = 0;

        if (values[1] >= 3)
            total += 200 * (values[1] - 2);
        if (values[2] >= 3)
            total += 300 * (values[2] - 2);
        if (values[3] >= 3)
            total += 400 * (values[3] - 2);
        if (values[5] >= 3)
            total += 600 * (values[5] - 2);

        if (values[0] < 3)
            total += 100 * values[0];
        else
            total += 1000 * (values[0] - 2);

        if (values[4] < 3)
            total += 50 * values[4];
        else
            total += 500 * (values[4] - 2);

        return total;
    }

    private static boolean checkForInvalidSelection(int[] values) {

        return (0 < values[1] && values[1] < 3 ||
                0 < values[2] && values[2] < 3 ||
                0 < values[3] && values[3] < 3 ||
                0 < values[5] && values[5] < 3 );
    }


    private static boolean straight(int[] values) {
        for (int val : values) {
            if (val != 1)
                return false;
        }
        return true;
    }

    private static boolean threePairs(int[] values) {
        int pairs = 0;
        for (int val : values){
            if (val == 2)
                pairs++;
        }
        return (pairs == 3);
    }

    // Count the values of
    private static int[] getDiceValues(Dice[] dice){
        int[] values = {0,0,0,0,0,0};

        for (Dice die : dice)
        {
            if (die.isInPlay() && die.isSelected())
            {
                int value = die.getValue();
                values[value-1]++;
            }
        }
        return values;
    }

    public static boolean checkForFarkle(Dice[] dice){

        int[] values = {0,0,0,0,0,0};

        for (Dice die : dice){
            if (die.getCheckbox().isEnabled()){
                int value = die.getValue();
                values[value-1]++;
            }
        }
        return (values[0] == 0 && values[1] < 3 && values[2] < 3 && values[3] < 3 && values[4] == 0 && values[5] < 3);
    }

    public static boolean allSelected(Dice[] dice){
        for (Dice die : dice){
            if (!die.isSelected())
                return false;
        }
        return true;
    }

}
