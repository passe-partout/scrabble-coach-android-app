package edu.gatech.seclass.words6300;

import android.os.Build;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.*;

@Entity
public class Rack implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int rackid;

    //@TypeConverters(Converter.class)
    //@ColumnInfo(name = "rackLetters")
    @Embedded
    private ArrayList<Letter> rackLetters;

    @ColumnInfo(name = "empty")
    private boolean empty = false;

    public Rack(){}
    @Ignore
    public Rack(ArrayList<Letter> rackLetters) {
        this.rackLetters = rackLetters;
    }

    public boolean getEmpty() { return empty; }
    public void setEmpty(boolean empty) { this.empty = empty; }

    public int getRackid() { return rackid; }

    public void setRackid(int rackid) { this.rackid = rackid; }

    public ArrayList<Letter> getLetters() { return this.rackLetters; }
    public void setLetters(ArrayList<Letter> newRackLetters) { this.rackLetters = newRackLetters; }

    public ArrayList<Letter> getRackLetters() { return rackLetters; }
    public void setRackLetters(ArrayList<Letter> rackLetters) { this.rackLetters = rackLetters; }

    public Boolean validateWord(String word) {

        ArrayList<Letter> rackLetterTemp = this.rackLetters;

        // This method checks if two letters exist in the word that are not on the rack.
        // (one letter is acceptable because that is the letter on the board.

        Integer nonExistentLetters = 0;

        for (Integer i = 0; i < word.length(); i++) {
            Integer rackLetterLength = rackLetterTemp.size();
            Boolean letterInRack = Boolean.FALSE;

            Character wordCharacter = word.charAt(i);

            for (Integer j = 0; j < rackLetterLength; j++) {
                Letter rackLetter = rackLetterTemp.get(j);
                Character rackLetterCharacter = rackLetter.getLetter();

                if (wordCharacter == rackLetterCharacter) {
                    letterInRack = Boolean.TRUE;
                    rackLetterTemp.remove(j);
                    break;
                }
            }

            if (letterInRack)  {
                continue;
            } else {
                nonExistentLetters += 1;

                if (nonExistentLetters > 1) {
                    return Boolean.FALSE;
                } else {
                    continue;
                }

            }
        }

        return Boolean.TRUE;
    }

    public Boolean validateLettersExistInRack(ArrayList<Letter> letters) {

        Boolean isValid = Boolean.FALSE;

        for (int i = 0; i < letters.size(); i++) {
            isValid = Boolean.FALSE;
            Letter letter = letters.get(i);
            char letterChar = letter.getLetter();

            for (int j = 0; j < this.rackLetters.size(); j++) {
                Letter rackLetter = this.rackLetters.get(j);
                char rackLetterChar = rackLetter.getLetter();

                System.out.println("LETTERS");
                System.out.println(letterChar);
                System.out.println(rackLetterChar);

                if (letterChar == rackLetterChar) {
                    isValid = Boolean.TRUE;
                    break;
                }
            }

            if (isValid == Boolean.FALSE) {
                return Boolean.FALSE;
            }

        }
        return Boolean.TRUE;
    }

    public String toString()
    {
        String returnString = "";
        for(int i = 0; i < rackLetters.size(); i++)
        {
            returnString += rackLetters.get(i).toString() + "   ";
        }
        return returnString;
    }

}

