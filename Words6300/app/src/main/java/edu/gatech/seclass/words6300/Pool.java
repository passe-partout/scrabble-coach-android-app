package edu.gatech.seclass.words6300;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.*;
@SuppressWarnings({RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED, RoomWarnings.MISSING_INDEX_ON_FOREIGN_KEY_CHILD})
@Entity(foreignKeys = {
        @ForeignKey(
        entity = Letter.class,
        parentColumns = "id",
        childColumns = "letter_id"),
        @ForeignKey(
        entity = Game.class,
        parentColumns = "id",
        childColumns = "game_id"
    )})
public class Pool implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    //@TypeConverters(Converter.class)
    //@ColumnInfo(name = "poolLetters")
    @Embedded
    private ArrayList<Letter> poolLetters;

    @ColumnInfo(name = "game_id")
    private int gameId;

    @ColumnInfo(name = "letter_id")
    private int letterId;

    public Pool( ArrayList poolLetters ){
        this.poolLetters = poolLetters;
    }

    @Ignore
    public Pool() {
        HashMap<Character, Integer> inputPoints = new HashMap<>();
        HashMap<Character, Integer> letterDist = new HashMap<>();
        initializeLetters(inputPoints, letterDist);
    }

    @Ignore
    public Pool(HashMap<Character, Integer> inputPoints,
                HashMap<Character, Integer> letterDist) {
        initializeLetters(inputPoints, letterDist);
    }

    public Integer getLetterPoint(char letter){
        /*
         *   Letter                           Value
         *   a, e, i, o, u, l, n, r, s, t       1
         *   d, g                               2
         *   b, c, m, p                         3
         *   f, h, v, w, y                      4
         *   k                                  5
         *   j, x                               8
         *   q, z                               10
         * */
        switch (letter){
            case 'g':
            case 'd': return 2;

            case 'b':
            case 'c':
            case 'm':
            case 'p': return 3;

            case 'f':
            case 'h':
            case 'v':
            case 'w':
            case 'y': return 4;

            case 'k': return 5;

            case 'j':
            case 'x': return 8;

            case 'q':
            case 'z': return 10;

            default: return 1;
        }
    }

    private Integer getLetterDist(char letter) {
        /*
         *   Letter                           Value
         *   b, c, m, p, f, h, v, w             2
         *   k, j, x, q, z                      1
         *   g                                  3
         *   l, s, u, d                         4
         *   n, r ,t                            6
         *   o                                  8
         *   a, i                               9
         *   e                                  12
         * */
        switch (letter) {
            case 'k':
            case 'j':
            case 'x':
            case 'q':
            case 'z':
                return 1;

            case 'g':
                return 3;

            case 'l':
            case 's':
            case 'u':
            case 'd':
                return 4;

            case 'n':
            case 'r':
            case 't':
                return 6;

            case 'o':
                return 8;

            case 'a':
            case 'i':
                return 9;

            case 'e':
                return 12;

            default:
                return 2;
        }
    }

    private void initializeLetters(HashMap<Character, Integer> inputPoints,
                                   HashMap<Character, Integer> inputDist){
        String chars = "abcdefghijklmnopqrstuvwxyz";

        HashMap<Character, Integer> letterPoints = new HashMap<>();
        HashMap<Character, Integer> letterDist = new HashMap<>();

        for(int i=0;i<chars.length();i++){
            char c = chars.charAt(i);
            char upperCaseC = Character.toUpperCase(c);
            //Initialize letterDist
            if(inputPoints.containsKey(c)||inputPoints.containsKey(upperCaseC)){
                if (inputPoints.containsKey(upperCaseC)){
                    letterPoints.put(c, inputPoints.get(upperCaseC));
                }
                else{
                    letterPoints.put(c, inputPoints.get(c));
                }
            }
            else{
                letterPoints.put(c, getLetterPoint(c));
            }
            //Initialize letterPoints
            if(inputDist.containsKey(c)||inputDist.containsKey(upperCaseC)){
                if (inputDist.containsKey(upperCaseC)){
                    letterDist.put(c, inputDist.get(upperCaseC));
                }
                else{
                    letterDist.put(c, inputDist.get(c));
                }
            }
            else{
                letterDist.put(c, getLetterDist(c));
            }
        }

        ArrayList<Letter> poolLetters = new ArrayList<Letter>();
        for(int i=0; i<chars.length(); i++){
            char c = chars.charAt(i);
            Integer letterPoint = letterPoints.get(c);
            Integer noOfTimeCreated = letterDist.get(c);
            for(int j=0; j<noOfTimeCreated;j++){
                Letter temp = new Letter(c, letterPoint);
                poolLetters.add(temp);
            }
        }
        this.poolLetters = poolLetters;
    }

    public ArrayList<Letter> getLetters(Integer numberOfPoolLetters){
        ArrayList<Letter> letters = new ArrayList<Letter>();
        Random rnd = new Random(System.currentTimeMillis());
        for(int i=0; i<numberOfPoolLetters; i++){
            int noOfLetterRemaining = this.poolLetters.size();

            if (noOfLetterRemaining == 0) {
                break;
            }

            int indexToPick = rnd.nextInt(noOfLetterRemaining);
            Letter c = this.poolLetters.get(indexToPick);
            this.poolLetters.remove(indexToPick);
            letters.add(c);
        }
        return letters;
    }

    public void returnLetters(ArrayList<Letter> returnedPoolLetters){
        this.poolLetters.addAll(returnedPoolLetters);
    }

    public boolean isPoolEmpty(){
        if(poolLetters.size()==0){
            return true;
        }
        else{
            return false;
        }

    }

    public Integer getNumOfLetterInPool(){
        return poolLetters.size();
    }

    public void setPoolLetters(ArrayList poolLetters) {
        this.poolLetters = poolLetters;
    }
    public ArrayList<Letter> getPoolLetters() {
        return this.poolLetters;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public int getLetterId() { return letterId; }
    public void setLetterId(int letterId) { this.letterId = letterId; }

    public String toString()
    {
        String toReturn = "";
        for(int i=0; i< poolLetters.size(); i++)
        {
            if( i == 0 ) {
                toReturn = poolLetters.get(i).toString();
            }
            else {
                toReturn = ", " + poolLetters.get(i).toString();
            }
        }
        return toReturn;
    }

    public void addLetter( Letter letter )
    {
        poolLetters.add( letter );
    }

    public void removeLetter( Letter letter )
    {
        poolLetters.remove( letter );
    }

    public void removeLetterByCharAndPoint( char character, int pointValue )
    {
        //loop thru the pool of letters to find a match, and remove the first instance:

        for( int i = 0; i< poolLetters.size(); i++){
            Letter letter = poolLetters.get(i);
            Character charValueInLowerCase = Character.toLowerCase( character );

            if( letter.getPointValue() == pointValue && letter.getLetter() == charValueInLowerCase.charValue() ){
                poolLetters.remove( i );
                break;
            }
        }
    }
}
