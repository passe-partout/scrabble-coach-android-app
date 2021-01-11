package edu.gatech.seclass.words6300;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

//import io.objectbox.Box;

public class ViewStatsActivity extends AppCompatActivity {

    private Button bGameScoreStats;
    private Button bLetterStats;
    private Button bWordBank;

    private WordBankStatistics wordBankStatistics;
    private LetterStatistics letterStatistics;
    private List<Game> gameList;
    private Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);

        currentPlayer = (Player) getIntent().getSerializableExtra("CurrentPlayer");
        gameList = currentPlayer.getGames();
        //letterStatistics = LetterStatistics.mapOfLetters;//.getLetterStatistics();
        wordBankStatistics = currentPlayer.getWordBankStatistics();
        //get statistics - from Player? Game?
        //getWordBankStatistics(this);
        //getLetterStatistics( this );
        //getGameStatistics( this );

        //buttons:
        bGameScoreStats = (Button) findViewById(R.id.bGameScoreStats);
        bGameScoreStats.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked bGameScoreStats.");
                ViewStatsDialog viewStatsDialogFragment = ViewStatsDialog.newInstance("Game Statistics");
                viewStatsDialogFragment.setGameList( gameList );
                viewStatsDialogFragment.show(getSupportFragmentManager(), "game_stats");
            }
        } );

        bLetterStats = (Button) findViewById(R.id.bLetterStats);
        bLetterStats.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked bLetterStats.");
                ViewStatsDialog viewStatsDialogFragment = ViewStatsDialog.newInstance("Letter Statistics");
//                viewStatsDialogFragment.setLetterStatistics(letterStatistics);
                viewStatsDialogFragment.show(getSupportFragmentManager(), "letter_stats");
            }
        });

        bWordBank = (Button) findViewById(R.id.bWordBank);
        bWordBank.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked bWordBank.");
                ViewStatsDialog viewStatsDialogFragment = ViewStatsDialog.newInstance("Word Bank Statistics");
                viewStatsDialogFragment.setWordBankStatistics(wordBankStatistics);
                viewStatsDialogFragment.show(getSupportFragmentManager(), "wordbank_stats");
            }
        });
    }


//    private void getWordBankStatistics(final ViewStatsActivity my_activity){
//        class GetWordBankStatistics extends AsyncTask<Void, Void, WordBankStatistics> {
//            @Override
//            protected WordBankStatistics doInBackground(Void... voids) {
//                WordBankStatistics wordBankStatistics = DatabaseClient.getInstance( getApplicationContext() )
//                        .getAppDatabase().wordBankStatisticsDao().getAll();
//                return wordBankStatistics;
//            }
//
//            @Override
//            protected void onPostExecute(WordBankStatistics wordBankStatistics) {
//                super.onPostExecute(wordBankStatistics);
//                my_activity.setWordBankStatistics(wordBankStatistics);
//            }
//        }
//
//        GetWordBankStatistics gg = new GetWordBankStatistics();
//        gg.execute();
//
//    }

//    public void setWordBankStatistics(WordBankStatistics wordBankStatistics){
//        this.wordBankStatistics = wordBankStatistics;
//    }
//
//    private void getLetterStatistics(final ViewStatsActivity my_activity){
//        class GetLetterStatistics extends AsyncTask<Void, Void, List<LetterStatistics>> {
//            @Override
//            protected List<LetterStatistics> doInBackground(Void... voids) {
//                List<LetterStatistics> letterStatisticsList = DatabaseClient.getInstance( getApplicationContext() )
//                        .getAppDatabase().letterStatisticsDao().getAll();
//                return letterStatisticsList;
//            }
//
//            @Override
//            protected void onPostExecute(List<LetterStatistics> letterStatisticsList) {
//                super.onPostExecute(letterStatisticsList);
//                my_activity.setLetterStatistics(letterStatisticsList);
//            }
//        }
//
//        GetLetterStatistics gg = new GetLetterStatistics();
//        gg.execute();
//
//    }
//    public void setLetterStatistics(List<LetterStatistics> letterStatisticsList){
//        this.letterStatisticsList = letterStatisticsList;
//    }
//
//    private void getGameStatistics(final ViewStatsActivity my_activity){
//        class GetGameStatistics extends AsyncTask<Void, Void, List<Game>> {
//            @Override
//            protected List<Game> doInBackground(Void... voids) {
//                List<Game> gameList = DatabaseClient.getInstance( getApplicationContext() )
//                        .getAppDatabase().gameDao().getAll();
//                return gameList;
//            }
//
//            @Override
//            protected void onPostExecute(List<Game> gameList) {
//                super.onPostExecute(gameList);
//                my_activity.setGameList(gameList);
//            }
//        }
//
//        GetGameStatistics gg = new GetGameStatistics();
//        gg.execute();
//
//    }
//    public void setGameList(List<Game> gameList){
//        this.gameList = gameList;
//    }
}
