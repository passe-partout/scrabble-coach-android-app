package edu.gatech.seclass.words6300;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WordBankStatisticsTest {

    private WordBankStatistics wordBankStats;

    @Before
    public void setUp() throws Exception {
        this.wordBankStats = new WordBankStatistics();
    }

    @After
    public void tearDown() throws Exception {
        this.wordBankStats = null;
    }

    @Test
    public void viewStatistics() {
        String expectedOutput = "~~~~~~~~~~~~~~~~~~~~~~~~~ Start of Word Bank Statistics ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        this.wordBankStats.viewStatistics("3");
        assertEquals(expectedOutput, "~~~~~~~~~~~~~~~~~~~~~~~~~ Start of Word Bank Statistics ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
