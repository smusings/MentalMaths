package smusings.mentalmaths;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends SetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //defines everythign we need for this application
        multiplicand_tv = (TextView) findViewById(R.id.num1);
        multiplier_tv = (TextView) findViewById(R.id.num2);
        countRightAnswer = (TextView) findViewById(R.id.count_right);
        timer = (TextView) findViewById(R.id.timer);
        answer = (EditText) findViewById(R.id.num_result);
        multiplicantSeek = (SeekBar) findViewById(R.id.multiplicant_seek);
        multiplierSeek = (SeekBar) findViewById(R.id.multiplier_seek);
        multiplicandLayout = (LinearLayout) findViewById(R.id.multiplicant_layout);
        multiplierLayout = (LinearLayout) findViewById(R.id.multiplier_layout);

        //initial setup
        countRightAnswer.setText("0");
        multiplicantSeek.setProgress(0);
        multiplierSeek.setProgress(0);
        seekBar_random(multiplicand_tv, multiplicantSeek);
        seekBar_random(multiplier_tv, multiplierSeek);

        //assigns listeners to interactable elements
        multiplicantSeek.setOnSeekBarChangeListener(seeker);
        multiplierSeek.setOnSeekBarChangeListener(seeker);
        answer.setOnEditorActionListener(result_entered);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_top_scores){
            cdt.cancel();
            Intent topScoreIntent = new Intent(MainActivity.this, TopScoreActivity.class);
            this.startActivity(topScoreIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
