package edu.gatech.seclass.words6300;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashMap;

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity
public class Player implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int playerId;

    @TypeConverters(Converter.class)
    @ColumnInfo(name = "game_list")
    private List<Game> games;

    @Embedded
    private WordBankStatistics wordBankStatistics;

    @Embedded
    private Game gameInProgress;

    public Player() {
        games = new ArrayList<Game>();
        wordBankStatistics = new WordBankStatistics();
        wordBankStatistics.setWordBankMapping(new LinkedHashMap<Word, Integer>());
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public WordBankStatistics getWordBankStatistics() {
        return wordBankStatistics;
    }

    public void setWordBankStatistics(WordBankStatistics wordBankStatistics) {
        this.wordBankStatistics = wordBankStatistics;
    }

    public Game getGameInProgress() {
        return gameInProgress;
    }

    public void setGameInProgress(Game gameInProgress) {
        this.gameInProgress = gameInProgress;
    }

    //    public LetterStatistics getLetterStatistics() { return letterStatistics; }
//    public void setLetterStatistics(LetterStatistics letterStatistics) { this.letterStatistics = letterStatistics; }

    public void handleGameOver(Game currentGame) {
        //add to games[]
        if (!games.contains(currentGame))
            games.add(currentGame);
        //add to word bank stats

        //add to letter stats
        this.updateLetterStatistics(currentGame);

        //add to word bank stats
        this.updateWordBankStatistics(currentGame);
    }

    //also changed to update WordBank.
    public void updateLetterStatistics(Game currentGame) {
        // Get a list of all letters from Board, Rack, Pool and playedLetters

        ArrayList<Letter> allLetters = new ArrayList<Letter>();

        Board board = currentGame.getBoard();
        Rack rack = currentGame.getRack();
        Pool pool = currentGame.getPool();


        ArrayList<Letter> boardLetters = board.getBoardLetters();

        allLetters.addAll(boardLetters);

        System.out.println("BOARD");

        for (int i = 0; i < boardLetters.size(); i++) {
            Letter letter = boardLetters.get(i);
            System.out.println(letter.getLetter());
        }

        ArrayList<Letter> rackLetters = rack.getLetters();

        allLetters.addAll(rackLetters);

        System.out.println("RACK");

        for (int i = 0; i < rackLetters.size(); i++) {
            Letter letter = rackLetters.get(i);
            System.out.println(letter.getLetter());
        }

        int numberOfPoolLetters = pool.getNumOfLetterInPool();
        ArrayList<Letter> poolLetters = pool.getLetters(numberOfPoolLetters);
        allLetters.addAll(poolLetters);

        System.out.println("POOL");

        for (int i = 0; i < poolLetters.size(); i++) {
            Letter letter = poolLetters.get(i);
            System.out.println(letter.getLetter());
        }

        ArrayList<Letter> playedLetters = currentGame.getPlayedLetters();

        allLetters.addAll(playedLetters);

        System.out.println("PLAYED LETTERS");

        for (int i = 0; i < playedLetters.size(); i++) {
            Letter letter = playedLetters.get(i);
            System.out.println(letter.getLetter());
        }

        // Now for all letters, iterate through each and update the HashMap with number of times
        // played, traded and drawn.

        HashMap<Character, Letter> mapOfLetters = LetterStatistics.mapOfLetters;

        for (int i = 0; i < allLetters.size(); i++) {
            Letter gameLetter = allLetters.get(i);

            char gameCharacter = gameLetter.getLetter();

            Letter hashMapLetter = mapOfLetters.get(gameCharacter);

            char hashMapChar = hashMapLetter.getLetter();

            if (gameCharacter == hashMapChar) {
                // Get previous count values
                int previousNumberOfTimesPlayed = hashMapLetter.getNumOfTimesPlayed();
                int previousNumberOfTimesTraded = hashMapLetter.getNumOfTimesTraded();
                int previousNumberOfTimesDrawn = hashMapLetter.getTotalTimesDrawn();

                // Get count values for game
                int gameNumberOfTimesPlayed = gameLetter.getNumOfTimesPlayed();
                int gameNumberOfTimesTraded = gameLetter.getNumOfTimesTraded();
                int gameNumberOfTimesDrawn = gameLetter.getTotalTimesDrawn();

                // Add previous count values to game values
                int totalNumberOfTimesPlayed = previousNumberOfTimesPlayed + gameNumberOfTimesPlayed;
                int totalNumberOfTimesTraded = previousNumberOfTimesTraded + gameNumberOfTimesTraded;
                int totalNumberOfTimesDrawn = previousNumberOfTimesDrawn + gameNumberOfTimesDrawn;

                // Store updated values in hashMapLetter
                hashMapLetter.setNumOfTimesPlayed(totalNumberOfTimesPlayed);
                hashMapLetter.setNumOfTimesTraded(totalNumberOfTimesTraded);
                hashMapLetter.setTotalTimesDrawn(totalNumberOfTimesDrawn);

                // Store updated hashMapLetter in mapOfLetters.
                mapOfLetters.put(gameCharacter, hashMapLetter);
            }
        }
        LetterStatistics.mapOfLetters = mapOfLetters;
    }

    public void updateWordBankStatistics(Game currentGame) {
//
//        if(!WordBank.words.isEmpty()){
//
//            //Iterate through the list of words played in currentGame and wordBankMapping
//            for (Word w: WordBank.words){
//                for (Map.Entry<Word, Integer> entry : this.getWordBankStatistics().getWordBankMapping().entrySet()){
//
//                    //If a word does not exist in wordBankMapping then put it in the map
//                    if(!entry.getKey().getWord().equals(w.getWord())){
//                        this.getWordBankStatistics().getWordBankMapping().put(w, w.getNumOfTimesPlayed());
//                    }
//                    //If a word already exists in the mapping, then increment its numberOfTimesPlayed
//                    else {
//                        entry.setValue(entry.getValue() + w.getNumOfTimesPlayed());
//                    }
//    }
//}
        if( wordBankStatistics == null )
        {
            wordBankStatistics = new WordBankStatistics();
        }

        for (Word gameWord : currentGame.getWordBank().getWords()) {
            //see if its already in the mapping:

            if (wordBankStatistics.getWordBankMapping().containsKey(gameWord)) {
                Integer numTimesPlayed = wordBankStatistics.getWordBankMapping().get(gameWord);
                //increase the value by 1.
                wordBankStatistics.getWordBankMapping().put(gameWord, numTimesPlayed++);
            } else {
                //add it:
                wordBankStatistics.getWordBankMapping().put(gameWord, 1);
            }
            //add it to the global wordbank
            if( ! WordBank.words.contains( gameWord )) {
                WordBank.words.add(gameWord);
            }
        }

    }
}
