package edu.gatech.seclass.words6300;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
public class WordBankStatistics implements Statistics, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int wbsId;

    @TypeConverters(Converter.class)
    @ColumnInfo(name = "wordBankMapping")
    // a map to store <Key: Word object, Value: numOfTimesPlayed> pairs
    private Map<Word, Integer> wordBankMapping;


    public WordBankStatistics()
    {
        wordBankMapping = new HashMap<Word, Integer>();
    }
    // changed Statistics Interface return type to void
    public String viewStatistics(String stringName){
        String returnString = "";
        //view WordBank stats
        if (stringName.equals("3")){

            //display word bank stats
            returnString += "~~~~~~~~~~~~~~~~~~~~~~~~~ Start of Word Bank Statistics ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
            for (Word w: WordBank.words){
                returnString += "\nWord Played: " + w.getWord() + "\n";
                returnString += w.getNumOfTimesPlayed() + "\n\n";
            }
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~ End of Word Bank Statistics ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
        return returnString;
    }

    public int getWbsId() { return wbsId; }
    public void setWbsId(int wbsId) { this.wbsId = wbsId; }

    public Map<Word, Integer> getWordBankMapping() { return wordBankMapping; }
    public void setWordBankMapping(Map<Word, Integer> wordBankMapping) { this.wordBankMapping = wordBankMapping; }
}
