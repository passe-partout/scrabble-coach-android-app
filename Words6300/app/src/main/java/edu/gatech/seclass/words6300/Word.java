package edu.gatech.seclass.words6300;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Word implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "word")
    private String word;
    @ColumnInfo(name = "pointValue")
    private int pointValue;
    @ColumnInfo(name = "numOfTimesPlayed")
    private int numOfTimesPlayed;

    public Word()
    {
        pointValue = 0;
        numOfTimesPlayed = 0;
        word = "";
    }


    //Construct after validation
    @Ignore
    public Word(String w){
        word = w;
        numOfTimesPlayed = 1;

        // get the points of this word by
        // using character as Key to access HashMap mapOfLetters
        // to get the corresponding Letter object and its pointValue

        LetterStatistics letterStatistics = new LetterStatistics();

        for (int i = 0; i < w.length(); i++){
            Letter letter = letterStatistics.mapOfLetters.get(w.charAt(i));
            int value = letter.getPointValue();
            pointValue += value;
        }
    }


    public Integer getPointValue(){ return pointValue; }
    public void setPointValue( int pointValue ){ this.pointValue = pointValue; }
    //added
    public String getWord(){
        return word;
    }
    public void setWord( String word ){ this.word = word; }

    //added
    public int getNumOfTimesPlayed(){ return numOfTimesPlayed; }
    public void setNumOfTimesPlayed( int numOfTimesPlayed ){ this.numOfTimesPlayed = numOfTimesPlayed; }

    public void incrementNumOfTimesPlayed() { numOfTimesPlayed += 1; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

}
