package edu.gatech.seclass.words6300;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Embedded;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;


public class AdjustSettingsActivity extends AppCompatActivity {

    private Button bSetLetterPoints;
    private Button bSetMaxTurns;

    private String sMaxTurns;
    private Game currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_settings);

        if( currentGame == null ) {
            currentGame = (Game) getIntent().getSerializableExtra("CurrentGame");
        }

        //buttons:
        bSetMaxTurns = (Button) findViewById(R.id.bSetMaxTurns);
        bSetMaxTurns.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked bSetMaxTurns.");

                AlertDialog.Builder alert = new AlertDialog.Builder(AdjustSettingsActivity.this);

                final EditText editText = new EditText(AdjustSettingsActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                //set the maximum turns from the saved game:
                String sMaxTurns = (new Integer(currentGame.getMaxNumberOfTurns())).toString();
                editText.setText(sMaxTurns);

                alert.setTitle("Set Max Turns:");
                alert.setView( editText );

                alert.setNeutralButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sMaxTurns = editText.getText().toString();
                        System.out.println("user entered max turns = " + sMaxTurns);
                        currentGame.setMaxNumberOfTurns( Integer.parseInt(sMaxTurns) );
                        System.out.println("set game max turns = " + currentGame.getMaxNumberOfTurns());

                        updateGame( currentGame, AdjustSettingsActivity.this );
                    }
                });

                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        updateGame( currentGame, AdjustSettingsActivity.this );

                        dialogInterface.dismiss();
                    }
                });

                alert.show();

            }
        });

        bSetLetterPoints = (Button) findViewById(R.id.bSetLetterPoints);
        bSetLetterPoints.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked bSetLetterPoints.");

                AlertDialog.Builder alert = new AlertDialog.Builder(AdjustSettingsActivity.this);

                ScrollView scroll = new ScrollView(AdjustSettingsActivity.this);

                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setBackgroundColor( Color.parseColor("#FFFFF0")); //IVORY

                for(int i= 0; i< currentGame.getPool().getPoolLetters().size(); i++)
                {
                    //Add some buttons.
                    Letter letter = currentGame.getPool().getPoolLetters().get(i);
                    Button btnTag = new Button(v.getContext());
                    btnTag.setBackgroundColor(Color.parseColor("#FFE4B5"));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(30, 20, 30, 0);
                    btnTag.setLayoutParams(layoutParams);

                    btnTag.setText(letter.toString());
                    btnTag.setId(i);
                    layout.addView(btnTag);
                }
                scroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                scroll.addView( layout );
                scroll.setSmoothScrollingEnabled(true);
                alert.setView( scroll );
                alert.setTitle("Set Pool:");

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddTileDialog addTileDialogFragment = AddTileDialog.newInstance("Add Tile");
                        addTileDialogFragment.show(getSupportFragmentManager(), "add_tile");
                    }
                });

                alert.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddTileDialog addTileDialogFragment = AddTileDialog.newInstance("Remove Tile");
                        addTileDialogFragment.show(getSupportFragmentManager(), "remove_tile");
                    }
                });

                alert.setNeutralButton("Done",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateGame( currentGame, AdjustSettingsActivity.this );
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        System.out.println("onOptionsItemSelected in AdjustSettingsActivity.");
        Intent intent = new Intent( AdjustSettingsActivity.this,Words6300Activity.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("CurrentGame", currentGame);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    public void addLetter( Letter letter )
    {
        currentGame.getPool().addLetter( letter );
        System.out.println("added a letter.  current game now has " + currentGame.getPool().getPoolLetters().size() + " letters.");
        updateGame(currentGame, this);
        Intent intent = new Intent();
        intent.putExtra("CurrentGame", currentGame);
        setResult(RESULT_OK, intent);
        /*
        ViewGroup vg = findViewById (R.id.adjSettingsLayout);
//      vg.invalidate();
         */
    }
    public void removeLetter( Letter letter )
    {
        System.out.println( "removing letter: " + letter.toString() + ", pool size: " + currentGame.getPool().getPoolLetters().size());
        currentGame.getPool().removeLetterByCharAndPoint( letter.getLetter(), letter.getPointValue() );
        System.out.println( "removed letter, pool size: " + currentGame.getPool().getPoolLetters().size());
        updateGame(currentGame, this);
        /*
        ViewGroup vg = findViewById (R.id.adjSettingsLayout);
//      vg.invalidate();
         */
    }

    private void updateGame(final Game game, final AdjustSettingsActivity my_activity) {
        class UpdateGame extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                System.out.println("AdjustSettingsActivity update game, pool size " +
                                    game.getPool().getPoolLetters().size());
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .gameDao()
                        .update(game);
                System.out.println("AdjustSettingsActivity update game completed.");
                return null;
            }
        }

        UpdateGame ug = new UpdateGame();
        ug.execute();
    }

}
