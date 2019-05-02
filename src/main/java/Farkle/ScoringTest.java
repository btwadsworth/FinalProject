package Farkle;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoringTest {

    @Test
    public void runningTotal() {

    }

    @Test
    public void diceOK() {



    }

    @Test
    public void calcScore() {

        int score = Scoring.calcScore(4, 2,1,1,1,5);
        assertEquals(100, score);

    }
}