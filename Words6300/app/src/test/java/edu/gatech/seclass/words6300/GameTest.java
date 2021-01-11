package edu.gatech.seclass.words6300;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.InvalidObjectException;
import java.util.*;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        this.game = new Game();
    }

    @After
    public void tearDown() throws Exception {
        this.game = null;
    }

    @Test
    public void setId() {
        this.game.setId(5);
        Integer id = this.game.getId();
        Integer expectedOutput = 5;

        assertEquals(expectedOutput, id);

    }

    @Test
    public void getId() {
        this.game.setId(5);
        Integer id = this.game.getId();
        Integer expectedOutput = 5;

        assertEquals(expectedOutput, id);
    }

    @Test
    public void setScore() {
        this.game.setScore(5);
        Integer score = this.game.getScore();
        Integer expectedOutput = 5;

        assertEquals(expectedOutput, score);
    }

    @Test
    public void getScore() {
        this.game.setScore(5);
        Integer score = this.game.getScore();
        Integer expectedOutput = 5;

        assertEquals(expectedOutput, score);
    }

    @Test
    public void setGameOver() {
        this.game.setGameOver(Boolean.TRUE);
        Boolean gameOver = this.game.getGameOver();
        Boolean expectedOutput = Boolean.TRUE;

        assertEquals(expectedOutput, gameOver);
    }

    @Test
    public void getGameOver() {
        this.game.setGameOver(Boolean.TRUE);
        Boolean gameOver = this.game.getGameOver();
        Boolean expectedOutput = Boolean.TRUE;

        assertEquals(expectedOutput, gameOver);
    }

    @Test
    public void setMaxNumberOfTurns() {
        this.game.setMaxNumberOfTurns(30);
        Integer maxNumberOfTurns = this.game.getMaxNumberOfTurns();
        Integer expectedOutput = 30;

        assertEquals(expectedOutput, maxNumberOfTurns);
    }

    @Test
    public void getMaxNumberOfTurns() {
        this.game.setMaxNumberOfTurns(30);
        Integer maxNumberOfTurns = this.game.getMaxNumberOfTurns();
        Integer expectedOutput = 30;

        assertEquals(expectedOutput, maxNumberOfTurns);
    }

    @Test
    public void setPool() {

        Pool pool = new Pool();

        this.game.setPool(pool);
        Pool newPool = this.game.getPool();

        assertEquals(pool, newPool);

    }

    @Test
    public void getPool() {

        Pool pool = new Pool();

        this.game.setPool(pool);
        Pool newPool = this.game.getPool();

        assertEquals(pool, newPool);

    }

    @Test
    public void swapLetters1() {
        // EXPECT TRUE because all letters being swapped are in rack

        // First, create the letters that will be used

        Letter letterA1 = new Letter('a', 2);
        Letter letterB1 = new Letter('b', 2);
        Letter letterC1 = new Letter('c', 2);

        // Create ArrayList of letters to swap

        ArrayList<Letter> swapLetters = new ArrayList<Letter>();
        swapLetters.add(letterA1);
        swapLetters.add(letterB1);
        swapLetters.add(letterC1);

        // Create ArrayList of letters for Rack

        Letter letterA2 = new Letter('a', 2);
        Letter letterB2 = new Letter('b', 2);
        Letter letterC2 = new Letter('c', 2);
        Letter letterD2 = new Letter('d', 2);

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterA2);
        rackLetters.add(letterB2);
        rackLetters.add(letterC2);
        rackLetters.add(letterD2);

        Rack rack = new Rack(rackLetters);

        // Create ArrayList of letters for Pool

        Letter letterE = new Letter('e', 2);
        Letter letterF = new Letter('f', 2);
        Letter letterG = new Letter('g', 2);
        Letter letterH = new Letter('h', 2);

        ArrayList<Letter> poolLetters = new ArrayList<Letter>();
        poolLetters.add(letterE);
        poolLetters.add(letterF);
        poolLetters.add(letterG);
        poolLetters.add(letterH);

        Pool pool = new Pool();
        pool.setPoolLetters(poolLetters);

        Game newGame = new Game(pool, rack);

        Boolean output = newGame.swapLetters(swapLetters);

        assertEquals(output, Boolean.TRUE);
    }

    @Test
    public void swapLetters2() {
        // Expect false because not all letters to be swapped are in rack.

        // Create ArrayList of letters to swap

        Letter letterA1 = new Letter('a', 2);
        Letter letterB1 = new Letter('b', 2);
        Letter letterC1 = new Letter('c', 2);

        ArrayList<Letter> swapLetters = new ArrayList<Letter>();
        swapLetters.add(letterA1);
        swapLetters.add(letterB1);
        swapLetters.add(letterC1);

        // Create ArrayList of letters for Rack

        Letter letterA2 = new Letter('a', 2);
        Letter letterB2 = new Letter('b', 2);

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterA2);
        rackLetters.add(letterB2);

        Rack rack = new Rack(rackLetters);

        // Create ArrayList of letters for Pool

        Letter letterE = new Letter('e', 2);
        Letter letterF = new Letter('f', 2);
        Letter letterG = new Letter('g', 2);
        Letter letterH = new Letter('h', 2);

        ArrayList<Letter> poolLetters = new ArrayList<Letter>();
        poolLetters.add(letterE);
        poolLetters.add(letterF);
        poolLetters.add(letterG);
        poolLetters.add(letterH);

        Pool pool = new Pool();
        pool.setPoolLetters(poolLetters);

        Game newGame = new Game(pool, rack);

        Boolean output = newGame.swapLetters(swapLetters);

        assertEquals(output, Boolean.FALSE);

    }

    @Test
    public void playWord() {
        // Play the word "cat".  Return true because acceptable.

        // First, create all the letters that will be used

        Letter letterA = new Letter('a', 2);
        Letter letterB = new Letter('b', 2);
        Letter letterC = new Letter('c', 2);
        Letter letterD = new Letter('d', 2);
        Letter letterE = new Letter('e', 2);
        Letter letterF = new Letter('f', 2);
        Letter letterG = new Letter('g', 2);
        Letter letterH = new Letter('h', 2);

        // Create ArrayList of letters for Rack

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterA);
        rackLetters.add(letterB);
        rackLetters.add(letterC);
        rackLetters.add(letterD);

        Rack rack = new Rack(rackLetters);

        // Create ArrayList of letters for Pool

        ArrayList<Letter> poolLetters = new ArrayList<Letter>();
        poolLetters.add(letterE);
        poolLetters.add(letterF);
        poolLetters.add(letterG);
        poolLetters.add(letterH);

        Pool pool = new Pool();
        pool.setPoolLetters(poolLetters);

        // Create ArrayList of letters for Board

        ArrayList<Letter> boardLetters = new ArrayList<Letter>();
        Letter letterX = new Letter('x', 2);
        Letter letterY = new Letter('y', 2);
        Letter letterT = new Letter('t', 2);

        boardLetters.add(letterX);
        boardLetters.add(letterY);
        boardLetters.add(letterT);

        Board board = new Board(boardLetters);

        Game newGame = new Game(pool, rack, board);

        String word = "cat";

        try {
            Boolean output = newGame.playWord(word);
            assertEquals(output, Boolean.TRUE);
        }catch( Exception e ){}


    }

    @Test
    public void calculateScore() {
        // Play the word "cat".  Return true because acceptable.

        // First, create all the letters that will be used

        Letter letterA = new Letter('a', 2);
        Letter letterB = new Letter('b', 2);
        Letter letterC = new Letter('c', 2);
        Letter letterD = new Letter('d', 2);
        Letter letterE = new Letter('e', 2);
        Letter letterF = new Letter('f', 2);
        Letter letterG = new Letter('g', 2);
        Letter letterH = new Letter('h', 2);
        Letter letterX = new Letter('x', 2);
        Letter letterY = new Letter('y', 2);
        Letter letterT = new Letter('t', 2);

        // Create ArrayList of letters for Rack

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterA);
        rackLetters.add(letterB);
        rackLetters.add(letterC);
        rackLetters.add(letterD);
        Rack rack = new Rack(rackLetters);

        // Create ArrayList of letters for Pool

        ArrayList<Letter> poolLetters = new ArrayList<Letter>();
        poolLetters.add(letterE);
        poolLetters.add(letterF);
        poolLetters.add(letterG);
        poolLetters.add(letterH);
        Pool pool = new Pool();
        pool.setPoolLetters(poolLetters);

        // Create ArrayList of letters for Board

        ArrayList<Letter> boardLetters = new ArrayList<Letter>();
        boardLetters.add(letterX);
        boardLetters.add(letterY);
        boardLetters.add(letterT);
        Board board = new Board(boardLetters);

        Game newGame = new Game(pool, rack, board);

        String word = "cat";

        try {
            Boolean output = newGame.playWord(word);
            int score = newGame.getScore();

            assertEquals(score, 6);
        }
        catch( Exception e) {}
    }

    @Test
    public void getTileValues() {
    }

    @Test
    public void setTileValues() {

    }
}