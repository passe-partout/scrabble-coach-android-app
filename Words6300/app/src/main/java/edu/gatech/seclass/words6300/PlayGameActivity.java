package edu.gatech.seclass.words6300;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class PlayGameActivity extends AppCompatActivity {

    private Game currentGame;

    private EditText tEntry;
    private TextView tScore;
    private TextView tTurns;
    //private Button bSubmit;

    //board:
    private Button board_letter1;
    private Button board_letter2;
    private Button board_letter3;
    private Button board_letter4;

    //rack:
    private Button rack_letter1;
    private Button rack_letter2;
    private Button rack_letter3;
    private Button rack_letter4;
    private Button rack_letter5;
    private Button rack_letter6;
    private Button rack_letter7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        if( currentGame == null ) {
            currentGame = (Game) getIntent().getSerializableExtra("CurrentGame");
        }
//        if( player == null ) {
//            player = (Player) getIntent().getSerializableExtra("Player");
//        }
        tEntry=(EditText)findViewById(R.id.tEntry);
        tScore=(TextView)findViewById(R.id.tScore);
        tTurns=(TextView)findViewById(R.id.tTurns);

        board_letter1 = (Button)findViewById(R.id.board_letter1);
        board_letter2 = (Button)findViewById(R.id.board_letter2);
        board_letter3 = (Button)findViewById(R.id.board_letter3);
        board_letter4 = (Button)findViewById(R.id.board_letter4);

        rack_letter1 = (Button)findViewById(R.id.rack_letter1);
        rack_letter2 = (Button)findViewById(R.id.rack_letter2);
        rack_letter3 = (Button)findViewById(R.id.rack_letter3);
        rack_letter4 = (Button)findViewById(R.id.rack_letter4);
        rack_letter5 = (Button)findViewById(R.id.rack_letter5);
        rack_letter6 = (Button)findViewById(R.id.rack_letter6);
        rack_letter7 = (Button)findViewById(R.id.rack_letter7);

        //set the values:
        updateScoreAndTurn();

        //update board:
        updateBoard();
        //update rack:
        updateRack();


        Button bSubmit = (Button) findViewById(R.id.bSubmit);
        bSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked Submit.");
                handleClick();
            }
        });

        Button bSwap = (Button) findViewById( R.id.bSwap );
        bSwap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked Submit.");
                handleSwap();
            }
        });


    }

    private void handleSwap()
    {
        try {
            //enter the word:
            String word = tEntry.getText().toString();
            if (word.equals("")) {
                throw new InvalidObjectException("empty string entered.");
            }//find out which letters are selected:

            //get the current Rack:
            ArrayList<Letter> lettersToSwap = new ArrayList<Letter>();
            ArrayList<Letter> rack = currentGame.getRack().getLetters();
            String sletters = tEntry.getText().toString();
            for (int i = 0; i < sletters.length(); i++) {
                char character = sletters.charAt(i);
                for (int j = 0; j < rack.size(); j++) {
                    if (character == rack.get(j).getLetter()) {
                        lettersToSwap.add(rack.get(j));
                        break;
                    }
                }
            }
            if (currentGame.swapLetters(lettersToSwap)) {
                //increment turn:
                currentGame.incrementTurn();

                //save state:
                updateGame(currentGame);

                //update viewer:
                updateScoreAndTurn();

                //update board and rack:
                if (currentGame.getGameOver()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayGameActivity.this);

                    final TextView gameOverDialog = new TextView(PlayGameActivity.this);
                    gameOverDialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    gameOverDialog.setText("Game Over!");
                    builder.setView(gameOverDialog);

                    builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //player.handleGameOver( currentGame );
                            Intent intent = new Intent(PlayGameActivity.this, Words6300Activity.class);
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra("CurrentGame", currentGame);
                            setResult(RESULT_OK, intent);
                            finish();
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    updateBoard();
                    updateRack();

                    //clear the Entry text:
                    tEntry.setText("");

                    //force repaint:
                    repaintBoard();
                }
            } else {
                throw new InvalidObjectException( "Trying to use letters not on the rack.");
            }
        }
        catch(InvalidObjectException ioe ){
            //trying to swap letters that are not on the rack.  Notify and leave it all the same.
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayGameActivity.this);

            final TextView badSwapDialog = new TextView(PlayGameActivity.this);
            badSwapDialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            badSwapDialog.setText("You cannot swap letters that are not on the rack.  Please try again.");
            builder.setView(badSwapDialog);

            builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.show();
        }
    }

    private void handleClick()
    {
        try {
            //enter the word:
            String word = tEntry.getText().toString();
            if( word.equals(""))
            {
                throw new InvalidObjectException("empty string entered.");
            }
            currentGame.playWord(word); //throws EXE if invalid.
            currentGame.incrementTurn();

            //save state:
            updateGame( currentGame );

            //update viewer:
            updateScoreAndTurn();

            //update board and rack:
            if( currentGame.getGameOver() )
            {
                AlertDialog.Builder builder = new AlertDialog.Builder( PlayGameActivity.this );

                final TextView gameOverDialog = new TextView(PlayGameActivity.this);
                gameOverDialog.setTextAlignment( View.TEXT_ALIGNMENT_CENTER );
                gameOverDialog.setText("Game Over!");
                builder.setView( gameOverDialog );

                builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //player.handleGameOver( currentGame );
                        Intent intent = new Intent( PlayGameActivity.this,Words6300Activity.class);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra("CurrentGame", currentGame);
                        setResult(RESULT_OK, intent);
                        finish();
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
            else {
                updateBoard();
                updateRack();

                //clear the Entry text:
                tEntry.setText("");

                //force repaint:
                repaintBoard();
            }
        }
        catch(InvalidObjectException ioe ){
            AlertDialog.Builder builder = new AlertDialog.Builder( PlayGameActivity.this );

            final TextView errorDialog = new TextView(PlayGameActivity.this);
            errorDialog.setTextAlignment( View.TEXT_ALIGNMENT_CENTER );
            errorDialog.setText("Invalid word.  Please try again.");
            builder.setView( errorDialog );

            builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
                }
            });

            builder.show();
        }
    }

    private void repaintBoard()
    {
        tEntry.invalidate();
        board_letter1.invalidate();
        board_letter2.invalidate();
        board_letter3.invalidate();
        board_letter4.invalidate();
        rack_letter1.invalidate();
        rack_letter2.invalidate();
        rack_letter3.invalidate();
        rack_letter4.invalidate();
        rack_letter5.invalidate();
        rack_letter6.invalidate();
        rack_letter7.invalidate();
    }

    private void updateScoreAndTurn()
    {
        tScore.setText( "Score: " + ((Integer)currentGame.getScore()).toString());
        tTurns.setText( "Turns: " + ((Integer)currentGame.getCurrentTurn()).toString() +
                "/" + ((Integer)currentGame.getMaxNumberOfTurns()).toString());
    }

    private void updateBoard()
    {
        ArrayList boardLetters = currentGame.getBoard().getBoardLetters();
        try {
            board_letter1.setVisibility(View.GONE);
            board_letter2.setVisibility(View.GONE);
            board_letter3.setVisibility(View.GONE);
            board_letter4.setVisibility(View.GONE);

            board_letter1.setText( boardLetters.get(0).toString() );
            board_letter1.setVisibility(View.VISIBLE);
            board_letter2.setText( boardLetters.get(1).toString() );
            board_letter2.setVisibility(View.VISIBLE);
            board_letter3.setText( boardLetters.get(2).toString() );
            board_letter3.setVisibility(View.VISIBLE);
            board_letter4.setText( boardLetters.get(3).toString() );
            board_letter4.setVisibility(View.VISIBLE);
        }
        catch( Exception e ){
            //silently ignore - only show what we are able.
        }
    }

    private void updateRack()
    {
        ArrayList rackLetters = currentGame.getRack().getRackLetters();
        try {
            Letter letter = (Letter)rackLetters.get( 0 );
            rack_letter1.setText( letter.toString() );
            rack_letter1.setVisibility(View.VISIBLE);
        } catch ( IndexOutOfBoundsException e ) {
            rack_letter1.setVisibility(View.GONE);
        }
        try {
            Letter letter = (Letter)rackLetters.get( 1 );
            rack_letter2.setText( letter.toString() );
            rack_letter2.setVisibility(View.VISIBLE);
        } catch ( IndexOutOfBoundsException e ) {
            rack_letter2.setVisibility(View.GONE);
        }
        try {
            Letter letter = (Letter)rackLetters.get( 2 );
            rack_letter3.setText( letter.toString() );
            rack_letter3.setVisibility(View.VISIBLE);
        } catch ( IndexOutOfBoundsException e ) {
            rack_letter3.setVisibility(View.GONE);
        }
        try {
            Letter letter = (Letter)rackLetters.get( 3 );
            rack_letter4.setText( letter.toString() );
            rack_letter4.setVisibility(View.VISIBLE);
        } catch ( IndexOutOfBoundsException e ) {
            rack_letter4.setVisibility(View.GONE);
        }
        try {
            Letter letter = (Letter)rackLetters.get( 4 );
            rack_letter5.setText( letter.toString() );
            rack_letter5.setVisibility(View.VISIBLE);
        } catch ( IndexOutOfBoundsException e ) {
            rack_letter5.setVisibility(View.GONE);
        }
        try {
            Letter letter = (Letter)rackLetters.get( 5 );
            rack_letter6.setText( letter.toString() );
            rack_letter6.setVisibility(View.VISIBLE);
        } catch ( IndexOutOfBoundsException e ) {
            rack_letter6.setVisibility(View.GONE);
        }
        try {
            Letter letter = (Letter)rackLetters.get( 6 );
            rack_letter7.setText( letter.toString() );
            rack_letter7.setVisibility(View.VISIBLE);
        } catch ( IndexOutOfBoundsException e ) {
            rack_letter7.setVisibility(View.GONE);
        }
    }


    private void updateGame(final Game game) {
        class UpdateGame extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .gameDao()
                        .update(game);
                return null;
            }

        }

        UpdateGame ug = new UpdateGame();
        ug.execute();
    }

    private void updatePlayer(final Player player) {
        class UpdatePlayer extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .playerDao()
                        .update(player);
                return null;
            }

        }

        UpdatePlayer up = new UpdatePlayer();
        up.execute();
    }

//    public Player getPlayer() { return player; }
//    public void setPlayer(Player player) { this.player = player; }

    public boolean onOptionsItemSelected(MenuItem item){
        System.out.println("onOptionsItemSelected in AdjustSettingsActivity.");
        Intent intent = new Intent( PlayGameActivity.this,Words6300Activity.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("CurrentGame", currentGame);
//        intent.putExtra("CurrentPlayer", player);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }


}
