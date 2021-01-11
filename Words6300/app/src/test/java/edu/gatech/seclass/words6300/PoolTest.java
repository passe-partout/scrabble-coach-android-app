package edu.gatech.seclass.words6300;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class PoolTest extends TestCase {

    private Pool pool;
    @Before
    public void setUp() throws Exception {
        this.pool = new Pool();
    }

    @After
    public void tearDown() throws Exception {
        this.pool = null;
    }

    @Test
    public void testGetLetters() {
        ArrayList<Letter> letters = this.pool.getLetters(5);
        Integer actualSize = letters.size();
        Integer expectedGetNumOfLetter = 5;

        assertEquals(expectedGetNumOfLetter, actualSize);
    }

    @Test
    public void testReturnLetters() {

        ArrayList<Letter> letterToAdd = new ArrayList<Letter>();
        int expectedNumOfLetterReturned = 10;
        for(int i=0;i<expectedNumOfLetterReturned;i++){
            Character c = 'a';
            Letter temp = new Letter(c, 5);
            letterToAdd.add(temp);
        }
        int numOfLettersInPoolBefore = this.pool.getNumOfLetterInPool();
        this.pool.returnLetters(letterToAdd);
        int numOfLettersInPoolAfter = this.pool.getNumOfLetterInPool();

        assertEquals(expectedNumOfLetterReturned,
                numOfLettersInPoolAfter-numOfLettersInPoolBefore);

    }

    @Test
    public void testIsPoolEmpty() {
        Boolean actualResult = this.pool.isPoolEmpty();
        Boolean expectedResult = false;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetNumOfLetterInPool() {
        int numOfLetterInPool = 98;
        int actualNumOfLetterInPool = this.pool.getNumOfLetterInPool();

        assertEquals(numOfLetterInPool, actualNumOfLetterInPool);
    }

    @Test
    public void testSetPoolLetters() {
    }

    @Test
    public void testGetPoolLetters() {
    }
}