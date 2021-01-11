package edu.gatech.seclass.words6300;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Letter implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "numOfTimesPlayed")
    private int numOfTimesPlayed = 0;

    @ColumnInfo(name = "totalTimesDrawn")
    private int totalTimesDrawn = 0;

    @ColumnInfo(name = "numOfTimesTraded")
    private int numOfTimesTraded = 0; //added to track Num Of Times Traded Into Pool

    @ColumnInfo(name = "letter")
    private char letter;

    @ColumnInfo(name = "pointValue")
    private int pointValue;

    @ColumnInfo(name = "isPlayable")
    private boolean isPlayable;

    public Letter(){}

    @Ignore
    public Letter(Character l, Integer pv){
        letter = l;
        pointValue = pv;
        isPlayable = true;
    }

    public boolean validate(){
        return false;
    }

    public void incrementNumOfTimesPlayed(){ numOfTimesPlayed += 1; }

    public void incrementTotalTimesDrawn(){ totalTimesDrawn += 1; }

    public void incrementNumOfTimesTraded() { numOfTimesTraded += 1; }

    public char getLetter(){ return letter; }
    public void setLetter(char letter) { this.letter = letter;}

    public int getPointValue(){ return pointValue; }
    public void setPointValue(int pointValue) { this.pointValue = pointValue; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumOfTimesPlayed(){ return numOfTimesPlayed; }
    public void setNumOfTimesPlayed(int numOfTimesPlayed) { this.numOfTimesPlayed = numOfTimesPlayed; }

    public int getTotalTimesDrawn(){ return totalTimesDrawn; }
    public void setTotalTimesDrawn(int totalTimesDrawn) { this.totalTimesDrawn = totalTimesDrawn; }

    public void setNumOfTimesTraded(int numOfTimesTraded) { this.numOfTimesTraded = numOfTimesTraded; }
    public int getNumOfTimesTraded(){ return numOfTimesTraded; }

    public boolean getPlayable() { return isPlayable; }
    public void setPlayable(boolean playable) { isPlayable = playable; }

    // added
    // % of Times the letter is used in a word, out of the total number of times it has been drawn
    public Float getPercentageOfTime(){
        if (totalTimesDrawn == 0)
            return 0.00f;
        else
            return (float)numOfTimesPlayed / totalTimesDrawn;
    }

    public String toString()
    {
        return letter + " (" + pointValue + ")";
    }

}