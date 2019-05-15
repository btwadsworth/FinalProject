package Farkle;

public class Rules {
    /**
     * Ben Wadsworth
     * 5/15/2019
     * This class is only used when showing the rules to the user
     */


    public static final String rules =
            "To win at Farkle you must be the rist player to score 10,000 points\n" +
            "\n" +
            "Each player takes turns rolling the dice. On your turn, you roll all six dice. " +
                    "A 1 or a 5, three of a kind, three pairs, or a six-dice straight earn points.\n" +
                    "You must select at least one scoring die. " +
                    "You can then pass and bank your points, " +
                    "or risk the points earned this turn and roll the remaining dice.\n" +
            "\n" +
            "Scoring is based on selected dice in each roll. " +
                    "You cannot earn points by combining dice from different rolls.\n" +
            "\n" +
            "If none of your dice rolled earn points, you get a Farkle.\n" +
            "\n" +
            "You continue rolling until you either Bank the points or Farkle." +
                    " Then the next player rolls the six dice. " +
                    "Play continues until it is your turn again.\n" +
            "\n" +
            "Example: Your first rolls shows 1, 2, 3, 3, 5, and 6. " +
                    "You keep the 1 and the 5 for 150 points.\n" +
                    "You then opt to roll the remaining four dice. " +
                    "On that roll you get 3, 4, 4, and 5. " +
                    "You select the 5 and decide to Pass and bank your points. ";

}
