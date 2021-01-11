package edu.gatech.seclass.words6300;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.*;
@SuppressWarnings({RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED, RoomWarnings.MISSING_INDEX_ON_FOREIGN_KEY_CHILD})
@Entity(foreignKeys = {
        @ForeignKey(
                entity = WordBank.class,
                parentColumns = "id",
                childColumns = "wordbank_id"
        ),
        @ForeignKey(
                entity = Pool.class,
                parentColumns = "id",
                childColumns = "pool_id"
        )})
public class Game implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = -1;
    @ColumnInfo(name = "score")
    private int score;
    @ColumnInfo(name = "gameOver")
    private boolean gameOver;
    @ColumnInfo(name = "maxNumberOfTurns")
    private int maxNumberOfTurns;
    @ColumnInfo(name = "currentTurn")
    private int currentTurn;

    @Embedded
    private ArrayList<Letter> playedLetters;

    @Embedded
    private Board board;
    @Embedded
    private Rack rack;

    @ColumnInfo(name = "wordbank_id")
    private int wordbank_id;
    @ColumnInfo(name = "pool_id")
    private int pool_id;

    @Ignore
    private WordBank wordBank;
    @Ignore
    private Pool pool;

    public Game() {
        this(20);
    }

    @Ignore
    public Game(Pool pool, Rack rack) {
        this(0,
                0,
                false,
                20,
                0,
                pool,
                new Board(pool.getLetters(4)),
                rack,
                new WordBank());

        this.playedLetters = new ArrayList<Letter>();
    }


    @Ignore
    public Game(Pool pool, Rack rack, Board board) {
        this(0,0,false, 20, 0, pool, board, rack, new WordBank());
        this.playedLetters = new ArrayList<Letter>();
    }

    @Ignore
    public Game(int maxNumberOfTurns) {
        Pool pool;
        if( id != -1 ) {
            List<Pool> poolList = DatabaseClient.getInstance(Words6300Activity.getContext())
                    .getAppDatabase().poolDao().getPoolForGameID(id);
            if (poolList.size() == 0) {
                pool = new Pool();
            } else {
                pool = (Pool) poolList.get(0);
            }
        }
        else {
            pool = new Pool();
        }
        this.id = 0;
        this.score = 0;
        this.gameOver = false;
        this.maxNumberOfTurns = maxNumberOfTurns;
        this.currentTurn = 0;
        this.pool = pool;
        this.board = new Board( pool.getLetters(4));
        this.rack = new Rack( pool.getLetters(7));
        this.wordBank = new WordBank();
        this.playedLetters = new ArrayList<Letter>();
    }

    @Ignore
    public Game(Pool pool) {
        this(0,0,false, 20, 0, pool, new Board(pool.getLetters(4)),
                new Rack(pool.getLetters(7)), new WordBank());
        this.playedLetters = new ArrayList<Letter>();
    }

    @Ignore
    public Game(int maxNumberOfTurns, Pool pool) {
        this(0,0,false, maxNumberOfTurns, 0, pool, new Board(pool.getLetters(4)),
                new Rack(pool.getLetters(7)), new WordBank());
        this.playedLetters = new ArrayList<Letter>();
    }

    @Ignore
    public Game(int id,
                int score,
                boolean gameOver,
                int maxNumberOfTurns,
                int currentTurn,
                Pool pool,
                Board board,
                Rack rack,
                WordBank wordBank) {
        this.id = id;
        this.score = score;
        this.gameOver = gameOver;
        this.maxNumberOfTurns = maxNumberOfTurns;
        this.currentTurn = currentTurn;
        this.pool = pool;
        this.board = board;
        this.rack = rack;
        this.wordBank = wordBank;
        this.playedLetters = new ArrayList<Letter>();
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }

    public void setScore(int score) { this.score = score; }
    public int getScore() { return this.score; }

    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    public boolean getGameOver() { return this.gameOver; }

    public void setMaxNumberOfTurns(int input) { this.maxNumberOfTurns = input; }
    public int getMaxNumberOfTurns() { return this.maxNumberOfTurns; }

    public void setPool(Pool pool) { this.pool = pool; }
    public Pool getPool() { return this.pool; }

    public void setBoard(Board board) { this.board = board; }
    public Board getBoard() { return this.board; }

    public void setRack(Rack rack) { this.rack = rack; }
    public Rack getRack() { return this.rack; }

    public void setWordBank(WordBank wordBank) { this.wordBank = wordBank; }
    public WordBank getWordBank() { return this.wordBank; }

    public ArrayList<Letter> getPlayedLetters() { return playedLetters; }
    public void setPlayedLetters(ArrayList<Letter> playedLetters) { this.playedLetters = playedLetters; }

    public int getWordbank_id() { return wordbank_id; }
    public void setWordbank_id(int wordbank_id) {
        this.wordbank_id = wordbank_id;
        WordBank bank = DatabaseClient.getInstance( Words6300Activity.getContext())
                .getAppDatabase().wordBankDao().getWordBankViaID( wordbank_id );
        if ( bank != null){
            setWordBank(bank);
        }
    }

    public int getPool_id() { return pool_id; }
    public void setPool_id(int pool_id) {
        this.pool_id = pool_id;
        Pool pool = DatabaseClient.getInstance( Words6300Activity.getContext())
                .getAppDatabase().poolDao().getPoolViaID( pool_id );
        if ( pool != null){
            setPool(pool);
        }
    }

    public int getCurrentTurn() { return currentTurn; }
    public void setCurrentTurn(Integer currentTurn) { this.currentTurn = currentTurn; }

    public void playGame() {

        // Loop for number of turns
        // How to persist the game by exiting???
        while (this.currentTurn < this.maxNumberOfTurns || this.gameOver) {

            // Display Board and Rack

            // Prompt the player what to do -> swapLetters or createWord

            // Execute command and update Board and Rack appropriately

            // Increment Turn if command returns true, else try again
            this.currentTurn += 1;

            // Persist current state in memory

            // Leave Game?

        }
    }

    public boolean swapLetters(ArrayList<Letter> rackLettersToSwap){
        // check if letters to be swapped exist in Rack
        Boolean lettersExistInRack = this.rack.validateLettersExistInRack(rackLettersToSwap);

        if (lettersExistInRack) {

            System.out.println("Rack Letters to Swap");

            for (int i = 0; i < rackLettersToSwap.size(); i++) {
                Letter l = rackLettersToSwap.get(i);

                System.out.println(l.getLetter());
            }

            // Increment numOfTimesTraded for each letter

            for (int i = 0; i < rackLettersToSwap.size(); i++) {
                Letter letter = rackLettersToSwap.get(i);
                letter.incrementNumOfTimesTraded();
                rackLettersToSwap.set(i, letter);
            }

//            for (int i = 0; i < rackLettersToSwap.size(); i++) {
//                Letter l = rackLettersToSwap.get(i);
//
//                System.out.println(l.getNumOfTimesTraded());
//            }

            // if letters exist, remove those letters and return to pool
            ArrayList<Letter> originalRackLetters = this.rack.getLetters();

            for (int i = 0; i < rackLettersToSwap.size(); i++) {
                int rackLetterLength = originalRackLetters.size();

                Letter swapLetter = rackLettersToSwap.get(i);
                Character swapLetterCharacter = swapLetter.getLetter();

                for (int j = 0; j < rackLetterLength; j++) {
                    Letter rackLetter = originalRackLetters.get(j);
                    Character rackLetterCharacter = rackLetter.getLetter();

                    if (swapLetterCharacter == rackLetterCharacter) {
                        originalRackLetters.remove(j);
                        break;
                    }
                }
            }

            //////////// RETURN SWAP LETTERS TO POOL //////////////

            this.pool.returnLetters(rackLettersToSwap);


//            System.out.println("OLD LETTERS");
//
//            for (int i = 0; i < originalRackLetters.size(); i++) {
//                Letter l = originalRackLetters.get(i);
//
//                System.out.println(l.getLetter());
//            }



            //////////// GET NEW LETTERS FROM POOL //////////////

            // Get new letters from the pool
            ArrayList<Letter> newLetters = this.pool.getLetters(rackLettersToSwap.size());

            // Increment incrementTotalTimesDrawn then insert back into array
            for (int i = 0; i < newLetters.size(); i++) {
                Letter letter = newLetters.get(i);
                letter.incrementTotalTimesDrawn();
            }

            System.out.println("NEW LETTERS");

            for (int i = 0; i < newLetters.size(); i++) {
                Letter l = newLetters.get(i);

                System.out.println(l.getLetter());
            }

            // Append new letters to old letters that were kept
            newLetters.addAll(originalRackLetters);

            System.out.println("NEW RACK OF LETTERS");

            for (int i = 0; i < newLetters.size(); i++) {
                Letter l = newLetters.get(i);

                System.out.println(l.getLetter());
            }

            // Insert new letters into rack
            this.rack.setLetters(newLetters);

            return Boolean.TRUE;
        } else {
            System.out.println("Letters not in Rack");
            return Boolean.FALSE;
        }
    }

    public Boolean playWord( String word ) throws InvalidObjectException {
        // validate word through rack, board and word bank.
        Boolean boardValidWord    = this.board.validateWord(word);
        Boolean rackValidWord     = this.rack.validateWord(word);
        Boolean wordBankValidWord = this.wordBank.validateWord(word);

        if ( boardValidWord & rackValidWord & wordBankValidWord) {

            System.out.println("Word is: ");
            System.out.println(word);

            System.out.println("ORIGINAL RACK OF LETTERS");

            for (Integer i = 0; i < this.rack.getLetters().size(); i++) {
                Letter l = this.rack.getLetters().get(i);

                System.out.println(l.getLetter());
            }

            System.out.println("BOARD LETTERS");

            for (int i = 0; i < this.board.getBoardLetters().size(); i++) {
                Letter l = this.board.getBoardLetters().get(i);

                System.out.println(l.getLetter());
            }

            // GET LETTER TO REPLACE ON BOARD
            char boardReplacement = new Character('?');

            Boolean foundBoardLetter = Boolean.FALSE;

            for (int i = 0; i < word.length(); i++) {
                char wordLetter = word.charAt(i);
                for (int j = 0; j < this.board.getNumOfLetterInBoard(); j++) {
                    ArrayList<Letter> boardLetters = this.board.getBoardLetters();
                    Letter boardLetter = boardLetters.get(j);
                    char boardLetterChar = boardLetter.getLetter();

                    if (wordLetter == boardLetterChar) {
                        // IncrementNumberOfTimesPlayed for boardLetter
                        // Store Board Letter in lettersPlayed
                        boardLetter.incrementNumOfTimesPlayed();

//                        System.out.println("BOARD LETTER");
//                        System.out.println(boardLetter.getLetter());

                        this.playedLetters.add(boardLetter);

                        // add to score
                        score += boardLetter.getPointValue();

//                        System.out.println("PLAYED LETTERS");
//
//                        for (int k = 0; k < this.playedLetters.size(); k++) {
//                            Letter letter = this.playedLetters.get(k);
//                            System.out.println(letter.getLetter());
//                        }

                        boardReplacement = boardLetterChar;
                        foundBoardLetter = Boolean.TRUE;
                        break;
                    }

                if (foundBoardLetter) {
                    break;
                }
                }
            }

            System.out.println("BOARD REPLACEMENT LETTER");
            System.out.println(boardReplacement);

            StringBuilder updated_word = new StringBuilder(word);

            // Remove board letter from word!
            for (int i = 0; i < word.length(); i++) {
                char wordChar = word.charAt(i);

                if (boardReplacement == wordChar) {
                    updated_word.deleteCharAt(i);
                    break;
                }
            }



            // update Rack to remove letters played
            // Also, update score for rack letters!!!
            System.out.println("NEW UPDATED WORD WADDUP!!!!");
            System.out.println(updated_word);

            ArrayList<Letter> originalRackLetters = this.rack.getLetters();

            for (int i = 0; i < updated_word.length(); i++) {
                int rackLetterLength = originalRackLetters.size();

                Character wordCharacter = updated_word.charAt(i);

                for (int j = 0; j < rackLetterLength; j++) {
                    Letter rackLetter = originalRackLetters.get(j);
                    Character rackLetterCharacter = rackLetter.getLetter();

                    if (wordCharacter == rackLetterCharacter) {
                        // Increment the score
                        this.score += rackLetter.getPointValue();

                        // IncrementNumberOfTimesPlayed for rackLetter
                        rackLetter.incrementNumOfTimesPlayed();

                        // Add rack letter to playedLetters
                        playedLetters.add(rackLetter);

                        // Remove rackLetter from originalRackLetters
                        originalRackLetters.remove(j);
                        break;
                    }
                }
            }

            System.out.println("SCORE");
            System.out.println(score);

            System.out.println("ORIGINAL RACK OF LETTERS");
            for (Integer i = 0; i < originalRackLetters.size(); i++) {
                Letter l = originalRackLetters.get(i);

                System.out.println(l.getLetter());
            }

            // Get new letters for rack based on number of letters used for word
            // Note: rackLetters.length - 1 because one letter is used from the Board.
            ArrayList<Letter> newRackLetters = this.pool.getLetters(word.length() - 1);

            // IncrementTotalTimesDrawn
            for (int i = 0; i < newRackLetters.size(); i++ ) {
                Letter letter = newRackLetters.get(i);
                letter.incrementTotalTimesDrawn();
            }

            // Append new letters to old letters that were kept
            newRackLetters.addAll(originalRackLetters);

            // Insert new letters into rack
            this.rack.setLetters(newRackLetters);

            // UPDATE THE BOARD //

            Random rnd = new Random();

            int indexToPick = rnd.nextInt(word.length());
            char newBoardCharacter = word.charAt(indexToPick);

            while ( boardReplacement ==  newBoardCharacter) {
                indexToPick = rnd.nextInt(word.length());
                newBoardCharacter = word.charAt(indexToPick);
            }

            // Get the Letter object for newBoardCharacter from the rack!!!!!!!

            System.out.println("NEW BOARD CHARACTER");
            System.out.println(newBoardCharacter);

            // Replace the first letter with the new letter on board.

            ArrayList<Letter> boardLetters = this.board.getBoardLetters();

            for (int i = 0; i < boardLetters.size(); i++) {
                Letter boardLetter = boardLetters.get(i);
                char letter = boardLetter.getLetter();

                if (letter == boardReplacement) {
                    boardLetters.remove(i);
                    break;
                }
            }

            // Get Letter object with same letter character from playedLetters
            // Pull playedLetters list, and retrieve a letter and pass to board. Remove from playedLetters arraylist.
            for (int i = 0; i < this.playedLetters.size(); i++ ) {
                Letter letter = this.playedLetters.get(i);
                char letter_char = letter.getLetter();

                if ( letter_char == newBoardCharacter ) {
                    // Set newBoardLetter = to letter
                    boardLetters.add(letter);
                    this.playedLetters.remove(i);
                    break;
                }
            }

            // SET NEW BOARD LETTERS
            this.board.setBoardLetters(boardLetters);

            // add to word bank an instance of Word;
            this.wordBank.addWord(new Word(word));

            // CHECK IF POOL IS EMPTY!!!!
            Boolean poolIsEmpty = this.pool.isPoolEmpty();

            if (poolIsEmpty) {
                this.score += 20;
                this.gameOver = Boolean.TRUE;
                return Boolean.TRUE;
            }

            return Boolean.TRUE;
        } else {
            System.out.println("NOT A VALID WORD");
//            PlayGameActivity.EditText.setError("NOT A VALID WORD");
            throw new InvalidObjectException( "Not a valid word.");
            //return Boolean.FALSE;
        }

    }

    public void incrementTurn()
    {
        this.currentTurn++;
        if( currentTurn == maxNumberOfTurns )
            gameOver = true;
    }

    public Letter[] getTileValues() {

        Letter[] letterArray;
        letterArray = new Letter[1];

        return letterArray;
    }

    public void setTileValues(Letter[] input) {
        System.out.println("setTileValues");
    }
}