package edu.gatech.seclass.words6300;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

@Entity
public class LetterStatistics implements Statistics, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int lsId;

    //to store a record of all letters in the form of <Key:letter char, Value:Letter object> pairs
    // changed the original design
    //variable name changed
    @TypeConverters(Converter.class)
    @ColumnInfo(name = "mapOfLetters")
    static HashMap<Character, Letter> mapOfLetters = new HashMap<Character, Letter>();
    static {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        for(int i=0; i<chars.length(); i++){
            char c = chars.charAt(i);
            mapOfLetters.put(c, new Letter(c, 0));
        }
    }

    public LetterStatistics() {
    }

    @Override
    public String viewStatistics(String stringName) { //input String? //return type float?

        String returnvalue = "";
        // view letter stats
        if (stringName.equals("2")) {

            // use TreeMap as a sorted Map to store letter stats result
            // put() in sorted order by Key: Integer: numOfTimesPlayed
            TreeMap<Integer, Letter> letterStats = new TreeMap<Integer, Letter>();
            for(Character key : mapOfLetters.keySet()) {
                letterStats.put(mapOfLetters.get(key).getNumOfTimesPlayed(), mapOfLetters.get(key));
            }

            // display letter stats
            returnvalue = "------------------------ Start of Letter Statistics -----------------------\n";
            for(Integer key : letterStats.keySet()) {
                returnvalue += "\nNumber of Times Played In A Word: " + key + "\n";
                returnvalue +="Number of Times Traded Back Into The Pool: " +
                                    letterStats.get(key).getNumOfTimesTraded() +"\n";
                returnvalue += "Percentage of Times Used: " +
                                   letterStats.get(key).getPercentageOfTime() + "\n\n";
                //print two decimal places
            }
            returnvalue +=("\n------------------------ End of Letter Statistics ----------------------");

        }
        return returnvalue;
    }

    public int getLsId() { return lsId; }

    public void setLsId(int lsId) { this.lsId = lsId; }

    public HashMap<Character, Letter> getMapOfLetters() { return mapOfLetters; }

    public static void setMapOfLetters(HashMap<Character, Letter> mapOfLetters) { LetterStatistics.mapOfLetters = mapOfLetters; }
}
