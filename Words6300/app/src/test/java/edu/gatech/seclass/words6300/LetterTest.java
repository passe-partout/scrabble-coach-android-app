package edu.gatech.seclass.words6300;

import junit.framework.TestCase;

public class LetterTest extends TestCase {

    private Letter letter;
    public void setUp() throws Exception {
        this.letter = new Letter('a',5);
    }

    public void tearDown() throws Exception {
        this.letter = null;
    }

    public void testIncrementNumOfTimesPlayed() {
        this.letter.incrementNumOfTimesPlayed();
        int actualNumOfTimesPlayed = this.letter.getNumOfTimesPlayed();
        int expectNumOfTimesPlayed = 1;

        assertEquals(expectNumOfTimesPlayed, actualNumOfTimesPlayed);

    }

    public void testIncrementTotalTimesDrawn() {
        this.letter.incrementTotalTimesDrawn();
        int actualNumOfTimesDrawn = this.letter.getTotalTimesDrawn();
        int expectNumOfTimesDrawn = 1;

        assertEquals(expectNumOfTimesDrawn, actualNumOfTimesDrawn);
    }

    public void testGetLetter() {
        Character actualLetter = this.letter.getLetter();
        Character expectedLetter = 'a';
        assertEquals(expectedLetter, actualLetter);
    }

    public void testGetPointValue() {
        int actualPv = this.letter.getPointValue();
        int expectedPv = 5;
        assertEquals(expectedPv, actualPv);
    }

    public void testGetNumOfTimesPlayed() {
        int actualNumOfTimesPlayed = letter.getNumOfTimesPlayed();
        int expectNumOfTimesPlayed = 0;
        assertEquals(expectNumOfTimesPlayed, actualNumOfTimesPlayed);

    }

    public void testGetTotalTimesDrawn() {
        int actualNumOfTimesDrawn = letter.getTotalTimesDrawn();
        int expectNumOfTimesDrawn = 0;
        assertEquals(expectNumOfTimesDrawn, actualNumOfTimesDrawn);
    }

    public void testGetNumOfTimesTraded() {
        int actualNumOfTimesTraded = letter.getNumOfTimesTraded();
        int expectNumOfTimesTraded = 0;
        assertEquals(expectNumOfTimesTraded, actualNumOfTimesTraded);
    }

    public void testGetPercentageOfTime() {
        double actualPercentage = letter.getPercentageOfTime();
        double expectPercentage = 1;
        assertEquals(expectPercentage, actualPercentage);

    }
}