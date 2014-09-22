package smusings.mentalmaths;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class TopScoreActivity extends SetupActivity {

    //shared preference string
    public static final String NEW_COUNT = "MyNewCount";
    public static final String intent_count = "smusings.mentalmaths.HIGHSCORE";


    //values we plan on using
    public TextView top_score_1;
    public TextView top_score_2;
    public TextView top_score_3;
    public TextView top_score_4;
    public TextView top_score_5;
    public String score1;
    public String score2;
    public String score3;
    public String score4;
    public String score5;
    public String new_score;
    public int intscore1;
    public int intscore2;
    public int intscore3;
    public int intscore4;
    public int intscore5;
    public int new_int_score;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        //define our textviews
        top_score_1 = (TextView) findViewById(R.id.top1);
        top_score_2 = (TextView) findViewById(R.id.top2);
        top_score_3 = (TextView) findViewById(R.id.top3);
        top_score_4 = (TextView) findViewById(R.id.top4);
        top_score_5 = (TextView) findViewById(R.id.top5);

        //get our strings from sharedpreference
        SharedPreferences pref = this.getSharedPreferences(NEW_COUNT, 0);
        score1 = pref.getString("slot1", "0");
        score2 = pref.getString("slot2", "0");
        score3 = pref.getString("slot3", "0");
        score4 = pref.getString("slot4", "0");
        score5 = pref.getString("slot5", "0");

        //set the textviews with the correct score
        top_score_1.setText(score1);
        top_score_2.setText(score2);
        top_score_3.setText(score3);
        top_score_4.setText(score4);
        top_score_5.setText(score5);

        addNewScore();

    }

    public void addNewScore(){
        Intent intent = getIntent();
        //our intent
        new_score = intent.getStringExtra(SetupActivity.intent_count);
        new_int_score = Integer.valueOf(new_score);

        //define ints
        intscore1 = Integer.valueOf(top_score_1.getText().toString());
        intscore2 = Integer.valueOf(top_score_2.getText().toString());
        intscore3 = Integer.valueOf(top_score_3.getText().toString());
        intscore4 = Integer.valueOf(top_score_4.getText().toString());
        intscore5 = Integer.valueOf(top_score_5.getText().toString());

        if (new_int_score >= intscore1) {
            top_score_1.setText(new_score);
            top_score_2.setText(score1);
            top_score_3.setText(score2);
            top_score_4.setText(score3);
            top_score_5.setText(score4);
        }
        else if (new_int_score >= intscore2) {
            top_score_2.setText(new_score);
            top_score_3.setText(score2);
            top_score_4.setText(score3);
            top_score_5.setText(score4);
        }
        else if (new_int_score >= intscore3) {
            top_score_3.setText(new_score);
            top_score_4.setText(score3);
            top_score_5.setText(score4);
        }
        else if (new_int_score >= intscore4) {
            top_score_4.setText(new_score);
            top_score_5.setText(score4);
        }
        else if (new_int_score >= intscore5) {
            top_score_5.setText(new_score);
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        SharedPreferences pref = this.getSharedPreferences(NEW_COUNT, 0);
        SharedPreferences.Editor edt = pref.edit();
        edt.putString("slot1", top_score_1.getText().toString());
        edt.putString("slot2", top_score_2.getText().toString());
        edt.putString("slot3", top_score_3.getText().toString());
        edt.putString("slot4", top_score_4.getText().toString());
        edt.putString("slot5", top_score_5.getText().toString());

        //commits the strings to the shared preferences
        edt.commit();
    }
}
