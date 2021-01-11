package edu.gatech.seclass.words6300;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RackTest {

    @Test
    public void validateWord() {
        // Word has one letter that does not exist in Rack.
        // Expect return TRUE.

        // First, create all the letters that will be used

        Letter letterA = new Letter('a', 2);
        Letter letterC = new Letter('c', 2);

        // Create ArrayList of letters for Rack

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterA);
        rackLetters.add(letterC);

        Rack rack = new Rack(rackLetters);

        String word = "cat";

        Boolean output = rack.validateWord(word);

        assertEquals(output, Boolean.TRUE);
    }

    @Test
    public void validateWord2() {
        // Word has more than 1 letter that does not exist in rack.
        // Expect return FALSE.

        // First, create all the letters that will be used

        Letter letterA = new Letter('a', 2);
        Letter letterC = new Letter('c', 2);

        // Create ArrayList of letters for Rack

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterA);
        rackLetters.add(letterC);

        Rack rack = new Rack(rackLetters);

        String word = "catnip";

        Boolean output = rack.validateWord(word);

        assertEquals(output, Boolean.FALSE);
    }

    @Test
    public void validateLettersExistInRack() {
        // Swap letters 'a', 'b', and 'c'.
        // All letters exist in Rack.
        // Expect return TRUE

        // First, create all the letters that will be used

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

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterA2);
        rackLetters.add(letterB2);
        rackLetters.add(letterC2);

        Rack rack = new Rack(rackLetters);

        Boolean output = rack.validateLettersExistInRack(swapLetters);

        assertEquals(output, Boolean.TRUE);
    }

    @Test
    public void validateLettersExistInRack2() {
        // Swap letter 'd', but 'd' does not exist in Rack.
        // Expect return FALSE

        // First, create all the letters that will be used

        Letter letterA = new Letter('a', 2);
        Letter letterB = new Letter('b', 2);
        Letter letterC = new Letter('c', 2);
        Letter letterD = new Letter('d', 2);

        // Create ArrayList of letters to swap

        ArrayList<Letter> swapLetters = new ArrayList<Letter>();
        swapLetters.add(letterD);

        // Create ArrayList of letters for Rack

        ArrayList<Letter> rackLetters = new ArrayList<Letter>();
        rackLetters.add(letterA);
        rackLetters.add(letterB);
        rackLetters.add(letterC);

        Rack rack = new Rack(rackLetters);

        Boolean output = rack.validateLettersExistInRack(swapLetters);

        assertEquals(output, Boolean.FALSE);
    }
}