package Farkle;


import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Scoring extends FarkleGUI {

    private static HashMap<Integer, ImageIcon> images = FarkleGUI.images;
    private static HashMap<JCheckBox, JLabel> checkBoxes = FarkleGUI.checkBoxes;

    private static ArrayList<Integer> straight = new ArrayList<>() {
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
        }
    };

    public static int RunningTotal(){

        int total = 0;

        String ONE = images.get(1).toString();

        ArrayList<Integer> diceValues = new ArrayList<>();

        for (JCheckBox box : checkBoxes.keySet()){
            if (box.isEnabled() && box.isSelected()){
                ImageIcon temp = (ImageIcon) checkBoxes.get(box).getIcon();

                if (temp == images.get(1))
                    diceValues.add(1);
                else if (temp == images.get(2))
                    diceValues.add(2);
                else if (temp == images.get(3))
                    diceValues.add(3);
                else if (temp == images.get(4))
                    diceValues.add(4);
                else if (temp == images.get(5))
                    diceValues.add(5);
                else if (temp == images.get(6))
                    diceValues.add(6);
            }
        }

        if (diceValues.containsAll(straight))
            return 1500;
        else {
            int one = 0;
            int two = 0;
            int three = 0;
            int four = 0;
            int five = 0;
            int six = 0;

            for (Integer num : diceValues){

                switch (num){
                    case 1:
                        one++;
                        break;
                    case 2:
                        two++;
                        break;
                    case 3:
                        three++;
                        break;
                    case 4:
                        four++;
                        break;
                    case 5:
                        five++;
                        break;
                    case 6:
                        six++;
                        break;
                }
            }

            if ((0 < two && two < 3) || (0 < three && three < 3) || (0 < four && four < 3) || (0 < six && six < 3))
                return -1;
            else {
                if (two >= 3)
                    total += 200 * (two - 2);
                if (three >= 3)
                    total += 300 * (three - 2);
                if (four >= 3)
                    total += 400 * (four - 2);
                if (six >= 3)
                    total += 600 * (six - 2);

                if (one < 3)
                    total += 100 * one;
                else
                    total += 1000 * (one - 2);

                if (five < 3)
                    total += 50 * five;
                else
                    total += 500 * (five - 2);

                return total;
            }
        }
    }





}
