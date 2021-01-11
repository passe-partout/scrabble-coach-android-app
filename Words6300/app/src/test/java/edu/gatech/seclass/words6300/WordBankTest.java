package edu.gatech.seclass.words6300;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordBankTest {

    private WordBank wordBank;

    @Before
    public void setUp() throws Exception {
        this.wordBank = new WordBank();
    }

    @After
    public void tearDown() throws Exception {
        this.wordBank = null;
    }

    @Test
    public void validateWord() {
        assertEquals(true,  wordBank.validateWord("Hello"));
        wordBank.addWord(new Word("Hello"));
        assertEquals(false, wordBank.validateWord("Hello"));
    }

    @Test
    public void addWord() {
        wordBank.addWord(new Word("Bonjour"));
        assertEquals(wordBank.words.get(0).getWord(),"Bonjour");
    }

    @Test
    public void getWords() {
        assertEquals("Bonjour", wordBank.getWords().get(0));
        assertEquals("Hello", wordBank.getWords().get(1));
    }
}
