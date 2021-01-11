package edu.gatech.seclass.words6300;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings({RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED, RoomWarnings.MISSING_INDEX_ON_FOREIGN_KEY_CHILD})
@Entity(foreignKeys = {@ForeignKey(
        entity = Word.class,
        parentColumns = "id",
        childColumns = "word_id")})
public class WordBank implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "word_id")
    private int wordId;

    @TypeConverters(Converter.class)
    @ColumnInfo(name = "words")
    static ArrayList<Word> words;

    public WordBank() {
        this.words = new ArrayList();
    }

    // validate if String w exists in WB
    // return true if such word is not in WB (can be added to WB)
    public Boolean validateWord(String candidateWord){
        for (Word w: words) {
            if (w.getWord().equals(candidateWord))
                return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getWordId() { return wordId; }
    public void setWordId(int wordId) { this.wordId = wordId; }

    public static void setWords(ArrayList<Word> words) { WordBank.words = words; }

    // A Word instance may be added to WB after being validated and created
    // new words (most recently played) added to the beginning of the list
    public void addWord(Word w){ //or addWord(String w)
        if (validateWord(w.getWord()))
            words.add(w);
    }

    // returns an array of Word object
    // or an array of Strings: public ArrayList<String> getWords(){
    public ArrayList<Word> getWords() {
        if (words.isEmpty())
            System.out.println("Word Bank is empty!");
        return words;
    }

    //Option 2: return an ArrayList of Strings
    /*
    public ArrayList<String> getWords(){
        ArrayList<String> wordStrings = new ArrayList<String>();
        for (Word w: words) {
           	wordStrings.add(w.getWord());
        }
        return wordStrings;
    }
    */
}
