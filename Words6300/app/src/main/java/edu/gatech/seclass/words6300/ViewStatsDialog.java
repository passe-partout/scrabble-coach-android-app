package edu.gatech.seclass.words6300;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ListIterator;

public class ViewStatsDialog extends DialogFragment {

    private LetterStatistics letterStatistics;
    private WordBankStatistics wordBankStatistics;
    private List<Game> gameList;

    //This sorted map is created for displaying Letter Statistics
    private Map<Integer, ArrayList<Letter>> letterStats = new TreeMap<Integer, ArrayList<Letter>>();

//    public void setLetterStatistics(LetterStatistics letterStatistics) { this.letterStatistics = letterStatistics; }
    public void setGameList( List<Game> gameList ){ this.gameList = gameList; }
    public void setWordBankStatistics(WordBankStatistics wordBankStatistics) { this.wordBankStatistics = wordBankStatistics; }

    //required.
    public ViewStatsDialog()
    { }

    public static ViewStatsDialog newInstance(String title) {
        ViewStatsDialog frag = new ViewStatsDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("creating stats dialog.");

        String title = getArguments().getString("title");
        View view = getActivity().getLayoutInflater().inflate(R.layout.statitics_view_dialog, null);
        TableLayout vsTableLayout = (TableLayout)view.findViewById(R.id.vsTableLayout);
        vsTableLayout.setStretchAllColumns( true );
        //title row:
        TableRow tr_head = new TableRow(this.getContext());
        tr_head.setBackgroundColor(Color.GRAY);

        if ( title.equals("Game Statistics"))
        {
            TextView label_score = new TextView(this.getContext());
            label_score.setText("Final Score");
            label_score.setTextColor(Color.WHITE);
            label_score.setPadding(5, 5, 5, 5);
            tr_head.addView(label_score);

            TextView label_numTurns = new TextView(this.getContext());
            label_numTurns.setText("Number of Turns");
            label_numTurns.setTextColor(Color.WHITE);
            label_numTurns.setPadding(5, 5, 5, 5);
            tr_head.addView(label_numTurns);

            TextView label_avgScore = new TextView(this.getContext());
            label_avgScore.setText("Average Score per Turn");
            label_avgScore.setTextColor(Color.WHITE);
            label_avgScore.setPadding(5, 5, 5, 5);
            tr_head.addView(label_avgScore);

            vsTableLayout.addView(tr_head, new TableLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            if(gameList != null || gameList.size() != 0) {
                //create a row for every )statistics entry.
                for (int i = gameList.size()-1; i >= 0; i--) {

                    Game game = gameList.get(i);

                    if (game != null && game.getGameOver() ){
                        TableRow tableRow = new TableRow(this.getContext());
                        tableRow.setId(i);
                        tableRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        tableRow.setClickable(true);
                        tableRow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //create a dialog for the game details:
                                handleClick(v.getId());
                            }
                        });

                        TextView textView1 = new TextView(this.getContext());
                        textView1.setText(Integer.toString(game.getScore()));
                        tableRow.addView(textView1);

                        TextView textView2 = new TextView(this.getContext());
                        textView2.setText(Integer.toString( game.getCurrentTurn()));
                        tableRow.addView(textView2);

                        TextView textView3 = new TextView(this.getContext());
                        double d = 0d;
                        if (game.getCurrentTurn() != 0) {
                            d = (double) game.getScore() / (double) game.getCurrentTurn();
                        }
                        textView3.setText(Double.toString(d));
                        tableRow.addView(textView3);

                        vsTableLayout.addView(tableRow);
                    }
                }
            }
        }
        else if(title.equals( "Letter Statistics"))
        {
            TextView label_letter = new TextView(this.getContext());
            label_letter.setText("Letter");
            label_letter.setTextColor(Color.WHITE);
            label_letter.setPadding(5, 5, 5, 5);
            tr_head.addView(label_letter);

            TextView label_timesplayed = new TextView(this.getContext());
            label_timesplayed.setText("Times Played");
            label_timesplayed.setTextColor(Color.WHITE);
            label_timesplayed.setPadding(5, 5, 5, 5);
            tr_head.addView(label_timesplayed);

            TextView label_timestraded = new TextView(this.getContext());
            label_timestraded.setText("Times Traded");
            label_timestraded.setTextColor(Color.WHITE);
            label_timestraded.setPadding(5, 5, 5, 5);
            tr_head.addView(label_timestraded);

            TextView label_percentused = new TextView(this.getContext());
            label_percentused.setText("Percentage Used");
            label_percentused.setTextColor(Color.WHITE);
            label_percentused.setPadding(5, 5, 5, 5);
            tr_head.addView(label_percentused);

            vsTableLayout.addView(tr_head, new TableLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            if(LetterStatistics.mapOfLetters != null ) {

                // Add <Integer, Letter> pairs to sorted Map "letterStats"
                // by using a helper method "addEntries" defined at the end of this class
                for(Character key : LetterStatistics.mapOfLetters.keySet()) {
                    addEntries(LetterStatistics.mapOfLetters.get(key).getNumOfTimesPlayed(),
                               LetterStatistics.mapOfLetters.get(key));
                }

//                ListIterator it = new ArrayList(letterStats.entrySet()).listIterator();
                //int rowCounter = 1;
//                while (it.hasPrevious()) {
                ArrayList keyset = new ArrayList(letterStats.keySet());
                for( int i = keyset.size()-1; i >= 0; i--){

                    Integer key = (Integer)(keyset.get(i));
                    ArrayList<Letter> tempList = letterStats.get(key);

                    if (tempList != null) {

                        for (Letter value: tempList) {

                            //create a row for every statistics entry.
                            TableRow tableRow = new TableRow(this.getContext());
                            tableRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                         LinearLayout.LayoutParams.WRAP_CONTENT));

                            TextView textView1 = new TextView(this.getContext());
                            textView1.setText(String.valueOf(value.getLetter()).toUpperCase());
                            tableRow.addView(textView1);

                            TextView textView2 = new TextView(this.getContext());
                            textView2.setText(String.valueOf(value.getNumOfTimesPlayed()));
                            tableRow.addView(textView2);

                            TextView textView3 = new TextView(this.getContext());
                            textView3.setText(String.valueOf(value.getNumOfTimesTraded()));
                            tableRow.addView(textView3);

                            TextView textView4 = new TextView(this.getContext());
                            textView4.setText(String.valueOf(value.getPercentageOfTime()));
                            tableRow.addView(textView4);

                            vsTableLayout.addView(tableRow);
                        }
                    }

                }
            }

        }

        else if (title.equals("Word Bank Statistics"))
        {
            TextView label_word = new TextView(this.getContext());
            label_word.setText("Word");
            label_word.setTextColor(Color.WHITE);
            label_word.setPadding(5, 5, 5, 5);
            tr_head.addView(label_word);

            TextView label_timesplayed = new TextView(this.getContext());
            label_timesplayed.setText("Times Played");
            label_timesplayed.setTextColor(Color.WHITE);
            label_timesplayed.setPadding(5, 5, 5, 5);
            tr_head.addView(label_timesplayed);

            vsTableLayout.addView(tr_head, new TableLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            if( wordBankStatistics.getWordBankMapping() != null && wordBankStatistics.getWordBankMapping().size() > 0) {

                // Iterate thru wordBankMapping in reversed order to get the latest played words first
//                ListIterator<Map.Entry<Word, Integer>> iterator =
//                        new ArrayList<Map.Entry<Word, Integer>>(wordBankStatistics.getWordBankMapping().entrySet()).listIterator(wordBankStatistics.getWordBankMapping().size());

                //int rowCounter = 1;
//                while (iterator.hasNext()) {
                ArrayList entryset = new ArrayList(wordBankStatistics.getWordBankMapping().entrySet());
                for( int i = entryset.size()-1; i>= 0; i--){

//                    Integer key = (Integer)(entryset.get(i));
                    Map.Entry<Word, Integer> entry = (Map.Entry<Word, Integer>)entryset.get(i);

                        TableRow tableRow = new TableRow(this.getContext());
                        tableRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        TextView textView1 = new TextView(this.getContext());
                        textView1.setText(entry.getKey().getWord());
                        tableRow.addView(textView1);

                        TextView textView2 = new TextView(this.getContext());
                        textView2.setText(entry.getValue().toString());
                        tableRow.addView(textView2);

                        vsTableLayout.addView(tableRow);//, rowCounter);
                        //rowCounter++;
                }
            }

        }


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Clicked neutral button.");
                dialog.dismiss();
            }

        });

        return alertDialogBuilder.create();
    }

    public void handleClick( int gameID ){
        //get the game:
        try {
            Game game = gameList.get(gameID);

            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

            ScrollView scroll = new ScrollView( getContext() );

            LinearLayout layout = new LinearLayout( getContext() );
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setBackgroundColor( Color.parseColor("#FFFFF0")); //IVORY

            TextView textView = new TextView( getContext() );
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );
            textView.setBackgroundColor(Color.parseColor("#FFE4B5"));

            StringBuffer sb = new StringBuffer();
            sb.append("Max Number of Turns: ");
            sb.append( game.getMaxNumberOfTurns() );

            textView.setText( sb.toString() );
            scroll.addView( textView );

            View divider = new View(getContext());
            divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,5 ));
            divider.setBackgroundColor(Color.parseColor("#B3B3B3"));
            scroll.addView ( divider );

            TextView letterLabel = new TextView( getContext() );
            letterLabel.setText( "Letters: ");
            scroll.addView( letterLabel );

            for(int i= 0; i< game.getPool().getPoolLetters().size(); i++)
            {
                //Add some buttons.
                Letter letter = game.getPool().getPoolLetters().get(i);
                Button btnTag = new Button(getContext());
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
            alert.setTitle("Game Settings:");


//            alert.setNeutralButton("Done",  new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    alert.dismiss();
//                })
//            };


            alert.show();
        }
        catch(Exception e)
        {
            //no games found - do nothing - just return:
        }

    }


    //A helper function to add Letter instances in sorted map
    private void addEntries(Integer key, Letter value) {
        ArrayList tempList = null;
        if (letterStats.containsKey(key)) {
            tempList = letterStats.get(key);
            if(tempList == null)
                tempList = new ArrayList();
            tempList.add(value);
        } else {
            tempList = new ArrayList();
            tempList.add(value);
        }
        letterStats.put(key,tempList);
    }

}