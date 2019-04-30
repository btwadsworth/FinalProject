package Farkle;


import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scoring extends FarkleGUI {

    private HashMap<Integer, ImageIcon> images = FarkleGUI.images;
    private HashMap<JCheckBox, JLabel> checkBoxes = FarkleGUI.checkBoxes;

    private static int ONE = 100;
    private static int FIVE = 50;
    private static int THREEONES = 1000;
    private static int THREEOFAKIND = 100;
    private static int THREEPAIRS = 1500;
    private static int STRAIGHT = 1500;

    private ArrayList<Integer> straight = new ArrayList<>() {
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
        }
    };

    public int RunningTotal(){

        int total = 0;

        ArrayList<Integer> diceValues = new ArrayList<>();

        for (JCheckBox box : checkBoxes.keySet()){
            if (box.isEnabled() && box.isSelected()){
                String temp = checkBoxes.get(box).getIcon().toString();
                switch (temp){
                    case "one":
                        diceValues.add(1);
                        break;
                    case "two":
                        diceValues.add(2);
                        break;
                    case "three":
                        diceValues.add(3);
                        break;
                    case "four":
                        diceValues.add(4);
                        break;
                    case "five":
                        diceValues.add(5);
                        break;
                    case "six":
                        diceValues.add(6);
                        break;
                }
            }
        }


        if (diceValues.containsAll(straight))
            return STRAIGHT;
        else {

            for (Integer num : diceValues){

                switch (num){
                    case 1:

                }



            }


        }



        return -1;
    }





}
