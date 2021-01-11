package edu.gatech.seclass.words6300;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void updateLetterStatistics1() {
        // Works -> "cat" is played.
        // This updates score to 6.
        // Two letters are moved to the "playedLetters" list because
        // and one either 'c' or 'a' replaces 't' on the board.
        // two new Letters are drawn from the pool and used to update the Rack.

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

        Game game = new Game(pool, rack, board);

        String word = "cat";

        try {
            game.playWord(word);
            int score = game.getScore();

            assertEquals(score, 6);

            Player player = new Player();
            player.updateLetterStatistics(game);

            Letter a_letter = LetterStatistics.mapOfLetters.get('a');
            int a_letter_num_of_times_played = a_letter.getNumOfTimesPlayed();

            assertEquals(a_letter_num_of_times_played, 1);

            Letter c_letter = LetterStatistics.mapOfLetters.get('c');
            int c_letter_num_of_times_played = c_letter.getNumOfTimesPlayed();

            assertEquals(c_letter_num_of_times_played, 1);

            Letter t_letter = LetterStatistics.mapOfLetters.get('t');
            int t_letter_num_of_times_played = t_letter.getNumOfTimesPlayed();

            assertEquals(t_letter_num_of_times_played, 1);

        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateLetterStatistics2() {
        // a, b, and c will have 1 traded and 1 drawn.
        // The four letters in the pool will populate the rack, so the pool will be empty.
        // When a,b,c are returned to the pool, they will be picked back up again.

        // Reinitialize LetterStatistics

        HashMap<Character, Letter> mapOfLetters = new HashMap<Character, Letter>();
        String chars = "abcdefghijklmnopqrstuvwxyz";
        for(int i=0; i<chars.length(); i++){
            char c = chars.charAt(i);
            mapOfLetters.put(c, new Letter(c, 0));
        }


        LetterStatistics.setMapOfLetters(mapOfLetters);

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
        System.out.println(pool.getPoolLetters());

        Game newGame = new Game(pool, rack);

        newGame.swapLetters(swapLetters);

        Player player = new Player();

        player.updateLetterStatistics(newGame);

        Letter a_letter = LetterStatistics.mapOfLetters.get('a');
        Letter b_letter = LetterStatistics.mapOfLetters.get('b');
        Letter c_letter = LetterStatistics.mapOfLetters.get('c');

        int a_letter_num_of_times_swapped = a_letter.getNumOfTimesTraded();
        int a_letter_num_of_times_drawn   = a_letter.getTotalTimesDrawn();
        int b_letter_num_of_times_swapped = b_letter.getNumOfTimesTraded();
        int b_letter_num_of_times_drawn   = b_letter.getTotalTimesDrawn();
        int c_letter_num_of_times_swapped = c_letter.getNumOfTimesTraded();
        int c_letter_num_of_times_drawn   = c_letter.getTotalTimesDrawn();

        assertEquals(1, a_letter_num_of_times_swapped);
        assertEquals(1, a_letter_num_of_times_drawn);
        assertEquals(1, b_letter_num_of_times_swapped);
        assertEquals(1, b_letter_num_of_times_drawn);
        assertEquals(1, c_letter_num_of_times_swapped);
        assertEquals(1, c_letter_num_of_times_drawn);

    }

    @Test
    public void updateLetterStatistics3() {
        // Play a word with double letters and confirm
        // statistics are correct.

        // Reinitialize LetterStatistics

        HashMap<Character, Letter> mapOfLetters = new HashMap<Character, Letter>();
        String chars = "abcdefghijklmnopqrstuvwxyz";
        for(int i=0; i<chars.length(); i++){
            char c = chars.charAt(i);
            mapOfLetters.put(c, new Letter(c, 0));
        }


        LetterStatistics.setMapOfLetters(mapOfLetters);

        Letter letterE1 = new Letter('e', 5);
        Letter letterE2 = new Letter('e', 5);

        Letter letterL = new Letter('l', 9);
        Letter letterR = new Letter('r', 10);
        Letter letterW = new Letter('w', 11);
        Letter letterX = new Letter('x', 12);
        Letter letterY = new Letter('y', 13);

        Letter letterT1 = new Letter('t', 14);
        Letter letterT2 = new Letter('t', 14);
        Letter letterZ = new Letter('z', 15);

        // Create ArrayList of letters for Rack

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterL);
        rackLetters.add(letterE1);
        rackLetters.add(letterT1);
        rackLetters.add(letterE2);
        rackLetters.add(letterR);
        rackLetters.add(letterX);
        Rack rack = new Rack(rackLetters);

        // Create ArrayList of letters for Pool

        ArrayList<Letter> poolLetters = new ArrayList<Letter>();
        poolLetters.add(letterW);
        poolLetters.add(letterX);
        poolLetters.add(letterY);
        poolLetters.add(letterZ);

        Pool pool = new Pool();
        pool.setPoolLetters(poolLetters);

        // Create ArrayList of letters for Board

        ArrayList<Letter> boardLetters = new ArrayList<Letter>();

        boardLetters.add(letterX);
        boardLetters.add(letterY);
        boardLetters.add(letterT2);

        Board board = new Board(boardLetters);

        Game game = new Game(pool, rack, board);

        String word = "letter";

        try {
            game.playWord(word);

            Player player = new Player();
            player.updateLetterStatistics(game);

            Letter e_letter = LetterStatistics.mapOfLetters.get('e');
            Letter t_letter = LetterStatistics.mapOfLetters.get('t');

            int e_letter_num_of_times_played = e_letter.getNumOfTimesPlayed();
            int t_letter_num_of_times_played = t_letter.getNumOfTimesPlayed();

            assertEquals(2, e_letter_num_of_times_played);
            assertEquals(2, t_letter_num_of_times_played);

        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
    }
}