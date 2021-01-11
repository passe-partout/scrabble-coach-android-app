package edu.gatech.seclass.words6300;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.*;

@Entity
public class Board implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int boardID;

    //@TypeConverters(Converter.class)
    //@ColumnInfo(name = "boardLetters")
    @Embedded
    private ArrayList<Letter> boardLetters;

    @ColumnInfo(name = "boardSize")
    private int boardSize = 4;

    public Board(){}
    public Board(ArrayList<Letter> letters) {
        this.boardLetters = letters;
    }

    public void setBoardSize(int boardSize) { this.boardSize = boardSize; }
    public int getBoardSize(){ return boardSize; }

    public int getBoardID() { return boardID; }
    public void setBoardID(int boardID) { this.boardID = boardID; }

    public ArrayList<Letter> getBoardLetters() {
        return this.boardLetters;
    }
    public void setBoardLetters(ArrayList<Letter> boardLetters) { this.boardLetters = boardLetters; }

    public Boolean validateWord(String word){

        System.out.println("WORD");
        System.out.println(word);

        System.out.println("BOARD LETTERS");

        for (Integer i = 0; i < boardLetters.size(); i++) {
            Letter l = boardLetters.get(i);

            System.out.println(l.getLetter());
        }

        boolean inBoard = false;
        for(int i=0;i < word.length();i++){
            for(int j=0;j<this.boardLetters.size();j++){
                if(word.charAt(i)==Character.toLowerCase(this.boardLetters.get(j).getLetter())){
                    inBoard = true;
                }
            }
        }
        return inBoard;
    }

    public void updateLetter(ArrayList<Letter> letters){
        if(boardLetters.size()<boardSize){
            boardLetters.addAll(letters);
            String actualBoardSize = Integer.toString(boardLetters.size());
            if(boardLetters.size()<boardSize){
                System.out.println(String.format("Board only has %s letters",actualBoardSize));
            }
            else if(boardLetters.size()>boardSize){
                boardLetters = new ArrayList<Letter>(boardLetters.subList(0, boardSize));
            }
        }
        else{
            System.out.println("Board is already full, cannot add additional letters");
        }

    }

    public String toString()
    {
        String returnString = "";
        for(int i = 0; i < boardLetters.size(); i++)
        {
            returnString += boardLetters.get(i).toString() + "   ";
        }
        return returnString;
    }

    public Integer getNumOfLetterInBoard(){
        return boardLetters.size();
    }


}
