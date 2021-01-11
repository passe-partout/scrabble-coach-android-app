package edu.gatech.seclass.words6300;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class BoardTest extends TestCase {

    private Pool pool;
    private Board board;

    @Before
    public void setUp() throws Exception {

        pool = new Pool();
        this.board = new Board(pool.getLetters(4));
    }

    @After
    public void tearDown() throws Exception {
        this.pool = null;
    }

    @Test
    public void testValidateWord() {
        Boolean actualResult = this.board.validateWord("123");
        Boolean expectedResult = false;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testUpdateLetter() {
        ArrayList<Letter> lettersToAdd = new ArrayList<>();
        int numOfLetterToAdd = 1;

        int numOfLetterInBoardBefore = this.board.getNumOfLetterInBoard();
        for(int i=0;i<numOfLetterToAdd;i++){
            Letter l = new Letter('a', 5);
            lettersToAdd.add(l);
        }
        this.board.updateLetter(lettersToAdd);
        int numOfLetterInBoardAfter = this.board.getNumOfLetterInBoard();
        int actualLetterAdded = numOfLetterInBoardAfter - numOfLetterInBoardBefore;
        int expectedLetterAdded = 0;

        assertEquals(expectedLetterAdded, actualLetterAdded);

    }

    @Test
    public void testGetBoardSize() {
        int expectedBoardSize = 4;
        int actualBoardSize = this.board.getBoardSize();

        assertEquals(expectedBoardSize, actualBoardSize);
    }

    @Test
    public void testGetNumOfLetterInBoard() {
        int expectedBoardSize = 4;
        int actualBoardSize = this.board.getNumOfLetterInBoard();

        assertEquals(expectedBoardSize, actualBoardSize);
    }

    @Test
    public void testGetBoardLetters() {
    }

    @Test
    public void testSetBoardLetters() {
    }
}