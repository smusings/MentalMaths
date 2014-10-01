package smusings.mentalmaths;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class SetupActivity extends Activity
implements TopScoreDialog.TopScoreDialogListener{

    //declares almost everything we plan on using
    //made public for testing in the future
    public TextView multiplicand_tv;
    public TextView multiplier_tv;
    public TextView countRightAnswer;
    public TextView timer;
    public EditText answer;
    public SeekBar multiplicantSeek;
    public SeekBar multiplierSeek;
    public LinearLayout multiplicandLayout;
    public LinearLayout multiplierLayout;
    public String oldCount;

    //declares these for SharedPreferences
    public static final String intent_count = "smusings.mentalmaths.HIGHSCORE";

    //gives us our pseudo-random numbers
    public static int numSetUp(int min, int max) {
        Random random = new Random();
        int randomInt = random.nextInt(max - min) + min;

        return randomInt;
    }

    //seek bar listener
    SeekBar.OnSeekBarChangeListener seeker = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int progressSeek = progress;

            if (seekBar == multiplicantSeek){
                seekBar_random(multiplicand_tv, multiplicantSeek);
            }
            else if (seekBar == multiplierSeek){
                seekBar_random(multiplier_tv, multiplierSeek);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //listener for EditText Done button
    TextView.OnEditorActionListener result_entered = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                    ((event.isShiftPressed() == false)) &&
                            (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                            (event.getAction() == KeyEvent.ACTION_DOWN)) {

                //set up the ints to work properly
                int result=Integer.valueOf(multiplicand_tv.getText().toString()) *
                        Integer.valueOf(multiplier_tv.getText().toString());

                //make it error proof
                if (answer.getText().toString().matches("")) {
                    answer.setText("");
                    answer.clearFocus();
                }

                //if correct, we say correct, add one to count and restart the countdown
                else if (Integer.valueOf(answer.getText().toString()) == result) {
                    //on correct
                    multiplicandLayout.setVisibility(View.GONE);
                    multiplierLayout.setVisibility(View.GONE);

                    //if correct, say it is, then clear the text, add one to the count, and give two new numbers.
                    Toast.makeText(SetupActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    answer.setText("");
                    setCountPlusOne();
                    seekBar_random(multiplicand_tv, multiplicantSeek);
                    seekBar_random(multiplier_tv, multiplierSeek);
                    //cancel first otherwise we trigger onFinish many times
                    cdt.cancel();
                    cdt.start();
                }

                //if wrong say wrong and do nothign else
                else if (Integer.valueOf(answer.getText().toString()) != result) {
                    Toast.makeText(SetupActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    answer.setText("");
                }
            }
            return false;
        }
    };


    //used to give new numbers if correct
    public void seekBar_random(TextView tv, SeekBar seekBar){
        if (seekBar.getProgress() == 0) {
            tv.setText(Integer.toString(numSetUp(1, 12)));
        }
        else if (seekBar.getProgress() == 1) {
            tv.setText(Integer.toString(numSetUp(13, 99)));
        }
        else if (seekBar.getProgress() == 2) {
            tv.setText(Integer.toString(numSetUp(100, 999)));
        }
        else if (seekBar.getProgress() == 3) {
            tv.setText(Integer.toString(numSetUp(1000, 9999)));
        }
    }



    //the coutndown timer and what to do when when time = 0
    CountDownTimer cdt = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText("Seconds Left: " + millisUntilFinished / 1000);
        }
        @Override
        public void onFinish() {
            timer.setText("Seconds Left: " + "0");
            multiplicandLayout.setVisibility(View.VISIBLE);
            multiplierLayout.setVisibility(View.VISIBLE);
            oldCount = countRightAnswer.getText().toString();
            Toast.makeText(SetupActivity.this,
                    "Your streak ends at: " + oldCount + "!", Toast.LENGTH_LONG).show();
            countRightAnswer.setText("0");

            showDialog();
        }
    };

    //adds one to the count
    public void setCountPlusOne() {
        int oldCount = Integer.valueOf(countRightAnswer.getText().toString());
        int newCount = oldCount + 1;
        countRightAnswer.setText(Integer.toString(newCount));
    }

    //set up for dialog to ask if we want to add a high score or not
    public void showDialog(){
        DialogFragment dialogFragment = new TopScoreDialog();
        dialogFragment.show(getFragmentManager(), "TopScoreDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {
        //laucnhes a new intent of the top score
        Intent intent = new Intent(SetupActivity.this, TopScoreActivity.class);
        intent.putExtra(intent_count, oldCount);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {
    }
}