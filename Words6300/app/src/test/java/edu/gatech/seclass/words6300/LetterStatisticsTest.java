package edu.gatech.seclass.words6300;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LetterStatisticsTest {

    private LetterStatistics letterStats;

    @Before
    public void setUp() throws Exception {
        this.letterStats = new LetterStatistics();
    }

    @After
    public void tearDown() throws Exception {
        this.letterStats = null;
    }

    @Test
    public void viewStatistics() {
        String expectedOutput = "------------------------ Start of Letter Statistics -----------------------";
        this.letterStats.viewStatistics("2");
        assertEquals(expectedOutput,"------------------------ Start of Letter Statistics -----------------------");
    }
}
