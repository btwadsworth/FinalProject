package Farkle;

import java.util.ArrayList;
import java.util.Random;

public class Roll {

    public static ArrayList<Integer> RollDice(Integer dice){

        Random rnd = new Random();
        ArrayList<Integer> rolls = new ArrayList<>();

        for (int i = 0; i < dice; i++) {
            Integer num = rnd.nextInt(6)+1;
            rolls.add(num);
        }

        return rolls;
    }

}
