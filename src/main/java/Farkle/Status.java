package Farkle;

public class Status {
    /**
     * Ben Wadsworth
     * 5/15/2019
     * This class handles the Status Object for the program
     */

    // Declare variables
    private boolean valid_dice;
    private int roll_total;

    // Constructor
    public Status(boolean valid, int total){
        this.valid_dice = valid;
        this.roll_total = total;
    }

    // Getters and Setters
    public boolean isValid_dice() { return valid_dice; }

    public void setValid_dice(boolean valid_dice) { this.valid_dice = valid_dice; }

    public int getRoll_total() { return roll_total; }

    public void setRoll_total(int roll_total) { this.roll_total = roll_total; }

}
