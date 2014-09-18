package smusings.mentalmaths;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TopScoreActivity extends SetupActivity {


    public static final String OLD_COUNT = "MyOldCount";
    public static final String NEW_COUNT = "MyNewCount";

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

        top_score_1 = (TextView) findViewById(R.id.top1);
        top_score_2 = (TextView) findViewById(R.id.top2);
        top_score_3 = (TextView) findViewById(R.id.top3);
        top_score_4 = (TextView) findViewById(R.id.top4);
        top_score_5 = (TextView) findViewById(R.id.top5);

        score1 = top_score_1.getText().toString();
        score2 = top_score_2.getText().toString();
        score3 = top_score_3.getText().toString();
        score4 = top_score_4.getText().toString();
        score5 = top_score_5.getText().toString();

        Intent intent = getIntent();
        new_score = intent.getStringExtra(MainActivity.intent_count);

        new_int_score = Integer.valueOf(new_score);

        getIntScores();
        setIntScores();
    }

    public void getIntScores(){
        if (score1.matches("")){
            top_score_1.setText("0");
            top_score_2.setText("0");
            top_score_3.setText("0");
            top_score_4.setText("0");
            top_score_5.setText("0");
        }
        else if (score2.matches("")){
            top_score_2.setText("0");
            top_score_3.setText("0");
            top_score_4.setText("0");
            top_score_5.setText("0");
        }
        else if (score3.matches("")){
            top_score_3.setText("0");
            top_score_4.setText("0");
            top_score_5.setText("0");
        }
        else if (score4.matches("")){
            top_score_4.setText("0");
            top_score_5.setText("0");
        }
        else if (score5.matches("")){
            top_score_5.setText("0");
        }

        intscore1 = Integer.valueOf(top_score_1.getText().toString());
        intscore2 = Integer.valueOf(top_score_2.getText().toString());
        intscore3 = Integer.valueOf(top_score_3.getText().toString());
        intscore4 = Integer.valueOf(top_score_4.getText().toString());
        intscore5 = Integer.valueOf(top_score_5.getText().toString());
    }

    public void setIntScores() {
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
}
