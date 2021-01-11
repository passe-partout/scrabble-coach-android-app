package edu.gatech.seclass.words6300;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.content.Intent;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Words6300Activity extends AppCompatActivity {

    private Button bPlayGame;
    private Button bViewStats;
    private Button bAdjustSettings;

    private static Words6300Activity myContext;

    //private Game currentGame;
    private Player currentPlayer;

    public Words6300Activity()
    { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        setContentView(R.layout.activity_main);

        //getPlayer
        if( currentPlayer == null ) {
            loadPlayer( );//Words6300Activity.this); //right now there is only 1 player.
            currentPlayer = getCurrentPlayer();//inserts player.
            //insertPlayer( currentPlayer, Words6300Activity.this );
        }
        //getGames
//        if( currentGame == null ) {
//            try
//            {
//                currentGame = getCurrentPlayer().getGames().get(0);
//            } catch (Exception e) {
//                currentGame = new Game();
//                getCurrentPlayer().getGames().add(0, currentGame );//player will be set above.
//                insertGame( currentGame, Words6300Activity.this );
//                insertPlayer( currentPlayer, Words6300Activity.this );
//            }
//        }

        //button handlers:
        bPlayGame=(Button)findViewById(R.id.bPlayGame);
        bPlayGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Game currentGame;
                if( getCurrentPlayer().getGameInProgress() != null ) {
                    currentGame = getCurrentPlayer().getGameInProgress();
                }
                else {
                    currentGame = new Game();
                    getCurrentPlayer().setGameInProgress( currentGame );
                }
                if( currentGame.getGameOver() )
                {
                    //we need a new game:
                    getCurrentPlayer().handleGameOver( currentGame );
                    currentGame = new Game();
                    getCurrentPlayer().setGameInProgress( currentGame );
                }

                Intent intent=new Intent(Words6300Activity.this, PlayGameActivity.class);
                intent.putExtra("CurrentGame", currentGame );
                startActivityForResult(intent, 1);
            }
        });

        bViewStats=(Button)findViewById(R.id.bViewStats);
        bViewStats.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( getCurrentPlayer() == null )
                {
                    loadPlayer();//Words6300Activity.this); //right now there is only 1 player.
                    currentPlayer = getCurrentPlayer();
                }
                Intent intent = new Intent(Words6300Activity.this, ViewStatsActivity.class);
                intent.putExtra("CurrentPlayer", getCurrentPlayer());
                startActivity(intent);
            }
        });

        bAdjustSettings=(Button)findViewById(R.id.bAdjustGameSettings);
        bAdjustSettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Game currentGame;
                if( getCurrentPlayer().getGameInProgress() != null ) {
                    currentGame = getCurrentPlayer().getGameInProgress();
                }
                else {
                    currentGame = new Game();
                    getCurrentPlayer().setGameInProgress( currentGame );
                }
                if( currentGame.getGameOver() )
                {
                    //we need a new game:
                    getCurrentPlayer().handleGameOver( currentGame );
                    currentGame = new Game();
                    getCurrentPlayer().setGameInProgress( currentGame );
                }

                Intent intent=new Intent(Words6300Activity.this, AdjustSettingsActivity.class);
                intent.putExtra("CurrentGame", currentGame);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Words6300Activity.super.onActivityResult( requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            System.out.println("setting values in onActivityResult in Words6300.");
            Game currentGame = (Game)data.getSerializableExtra("CurrentGame");

            System.out.println("in onActivityResult - maxTurns = " + currentGame.getMaxNumberOfTurns());
            System.out.println( "and the # letters: " + currentGame.getPool().getPoolLetters().size());
            //insertGame( currentGame ,Words6300Activity.this );

            if( currentGame.getGameOver() )
            {
                //player needs to handle game over:
                getCurrentPlayer().handleGameOver( currentGame );
                getCurrentPlayer().setGameInProgress( null );
            }
            else //make sure this game from Intent is the most recent one.
            {
                getCurrentPlayer().setGameInProgress( currentGame );
            }
            //insertPlayer( currentPlayer, Words6300Activity.this );
            insertPlayer( getCurrentPlayer() );
            insertStatistics();
        }
    }
///////////////
// Accessors //
///////////////

    public void setCurrentPlayer(Player player ){this.currentPlayer = player;}

    public static Context getContext() {
        return myContext;
    }

    public Player getCurrentPlayer()
    {
        if( currentPlayer == null ) {
            //no player yet - create one.
            currentPlayer = new Player();
            insertPlayer( currentPlayer );//, Words6300Activity.this );
        }
        return currentPlayer;
    }

/////////////////////
// Database Access //
////////////////////


    private void insertPlayer( Player player )
    {
        System.out.println( " In executor");
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
//            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
//                .playerDao().insert(player);
            AppDatabase.getAppDatabase(getApplicationContext()).playerDao().insert(player);
        });

    }



    private void loadPlayer( ) {
        System.out.println("In executor to load player");
//        Executor myExecutor = Executors.newSingleThreadExecutor();
//        myExecutor.execute(() -> {
//            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
//                .playerDao().insert(player);
            Player player =
                    AppDatabase.getAppDatabase(getApplicationContext()).playerDao().getLatestPlayer();
            setCurrentPlayer(player);
//        });
    }

    private void insertStatistics()
    {
        System.out.println( "Inserting letter statistics test.");
        LetterStatistics ls = new LetterStatistics();
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
//            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
//                .playerDao().insert(player);
            AppDatabase.getAppDatabase(getApplicationContext()).letterStatisticsDao().insert(ls);
        });
    }
//    public void loadPlayer( Player player, Words6300Activity my_activity){
//        class LoadPlayer extends AsyncTask<Void, Void, Player> {
//            @Override
//            protected Player doInBackground(Void... voids) {
//                Player player = DatabaseClient.getInstance( getApplicationContext() )
//                        .getAppDatabase().playerDao().getLatestPlayer();
//                return player;
//            }
//
//            @Override
//            protected void onPostExecute(Player player) {
//                super.onPostExecute(player);
//                my_activity.setCurrentPlayer(player);
//            }
//        }
//
//        LoadPlayer gg = new LoadPlayer();
//        gg.execute();
//
//    }

/////////////////////
// DEBUG PRINTOUTS //
/////////////////////
//    private void printPlayer()
//    {
//        //first try to print current player:
//        if( currentPlayer != null )
//        {
//            System.out.println( "Debugging database, current player in Words6300: " + currentPlayer.toString());
//        }
//        //now pull what's in the database.
//        System.out.println("database is at: " + this.getContext().getDatabasePath("Words6300"));
//        getPlayer( this );
//        System.out.println("updated player from database, player = " + playerList.toString());
//
//    }
}
