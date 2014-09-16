package smusings.mentalmaths;

import android.app.Activity;
import android.content.SharedPreferences;
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


public class SetupActivity extends Activity {

    //declares almost everything we plan on using
    //made public for unit testing in the future
    public TextView num1;
    public TextView num2;
    public TextView countRight;
    public TextView timer;
    public TextView high_score;
    public EditText num_answer;
    public SeekBar multiplicantSeek;
    public SeekBar multiplierSeek;
    public LinearLayout multiplicandLayout;
    public LinearLayout multiplierLayout;

    //declares these for SharedPreferences
    public static final String OLD_COUNT = "MyOldCount";
    public static final String NEW_COUNT = "MyNewCount";


    //gives us our pseudo-random numbers
    public static int numSetUp(int min, int max) {
        Random random = new Random();

        int randomInt = random.nextInt(max - min) + min;

        return randomInt;
    }

    //a listener for seekbar
    SeekBar.OnSeekBarChangeListener seeker1 = new SeekBar.OnSeekBarChangeListener() {
        int progressOne = 0;

        //if progress changes we do one of four things
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            progressOne = progress;

            if (progressOne == 0) {
                num1.setText(Integer.toString(numSetUp(1, 9)));
            }
            else if (progressOne == 1) {
                num1.setText(Integer.toString(numSetUp(10, 99)));
            }
            else if (progressOne == 2) {
                num1.setText(Integer.toString(numSetUp(100, 999)));
            }
            else if (progressOne == 3) {
                num1.setText(Integer.toString(numSetUp(100, 9999)));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //TODO: Auto-generated
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //TODO: Auto-generated
        }
    };

    SeekBar.OnSeekBarChangeListener seeker2 = new SeekBar.OnSeekBarChangeListener() {
        int progressTwo = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            progressTwo = progress;

            if (progressTwo == 0) {
                num2.setText(Integer.toString(numSetUp(1, 9)));
            }
            else if (progressTwo == 1) {
                num2.setText(Integer.toString(numSetUp(10, 99)));
            }
            else if (progressTwo == 2) {
                num2.setText(Integer.toString(numSetUp(100, 999)));
            }
            else if (progressTwo == 3) {
                num2.setText(Integer.toString(numSetUp(100, 9999)));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //TODO: Auto-generated
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //TODO: Auto-generated
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


                //on done we hide the seekbars
                multiplicandLayout.setVisibility(View.GONE);
                multiplierLayout.setVisibility(View.GONE);


                //set up the ints to work properly
                int result=Integer.valueOf(num1.getText().toString()) *
                        Integer.valueOf(num2.getText().toString());

                if (num_answer.getText().toString().matches("")) {
                    num_answer.setText("");
                    num_answer.clearFocus();
                }

                //if correct, we say correct, add one to count and restart the countdown
                else if (Integer.valueOf(num_answer.getText().toString()) == result) {
                    Toast.makeText(SetupActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    num_answer.setText("");
                    setCountPlusOne();
                    seekBar_1_call();
                    seekBar_2_call();
                    cdt.start();
                }

                //if wrong say wrong and do nothign else
                else if (Integer.valueOf(num_answer.getText().toString()) != result) {
                    Toast.makeText(SetupActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    num_answer.setText("");
                }
            }
            return false;
        }
    };


    //used to give new numbers if correct
    public void seekBar_1_call() {
        if (multiplicantSeek.getProgress() == 0) {
            num1.setText(Integer.toString(numSetUp(1, 9)));
        }
        else if (multiplicantSeek.getProgress() == 1) {
            num1.setText(Integer.toString(numSetUp(10, 99)));
        }
        else if (multiplicantSeek.getProgress() == 2) {
            num1.setText(Integer.toString(numSetUp(100, 999)));
        }
        else if (multiplicantSeek.getProgress() == 3) {
            num1.setText(Integer.toString(numSetUp(1000, 9999)));
        }
    }

    public void seekBar_2_call() {
        if (multiplierSeek.getProgress() == 0) {
            num2.setText(Integer.toString(numSetUp(1, 9)));
        }
        else if (multiplierSeek.getProgress() == 1) {
            num2.setText(Integer.toString(numSetUp(10, 99)));
        }
        else if (multiplierSeek.getProgress() == 2) {
            num2.setText(Integer.toString(numSetUp(100, 999)));
        }
        else if (multiplierSeek.getProgress() == 3) {
            num2.setText(Integer.toString(numSetUp(1000, 9999)));
        }
    }

    //the coutndown timer and what to do when when time = 0
    CountDownTimer cdt = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText("Seconds Left: " + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            timer.setText("Seconds Left: " + "0");
            multiplicandLayout.setVisibility(View.VISIBLE);
            multiplierLayout.setVisibility(View.VISIBLE);
            String oldCount = countRight.getText().toString();
            Toast.makeText(SetupActivity.this,
                    "Your streak ends at: " + oldCount + "!", Toast.LENGTH_LONG).show();
            countRight.setText("0");

            //sets up the preference and adds the string
            SharedPreferences pref = SetupActivity.this.getSharedPreferences(OLD_COUNT, 0);
            SharedPreferences.Editor edt = pref.edit();
            edt.putString("oldCount", oldCount);

            //commits the string to memory
            edt.commit();

            //triggers a method to set the high score
            setHighScore();

        }
    };

    //adds one to the count
    public void setCountPlusOne() {
        int oldCount = Integer.valueOf(countRight.getText().toString());
        int newCount = oldCount + 1;
        countRight.setText(Integer.toString(newCount));
    }

    //sets the high score
    public void setHighScore() {
        SharedPreferences pref = this.getSharedPreferences(OLD_COUNT, 0);
        String new_score = pref.getString("oldCount", "0");

        if (high_score.getText().toString().matches("")){
            high_score.setText("0");
        }

        //logic for integers
        int initial = Integer.valueOf(high_score.getText().toString());
        int newcount = Integer.valueOf(new_score);
        if (newcount > initial){
            high_score.setText(new_score);
        }
        else if (initial > newcount){
            high_score.setText(Integer.toString(initial));
        }

        //this preference needs to be called upon somewhere else
        SharedPreferences prefCount = SetupActivity.this.getSharedPreferences(NEW_COUNT, 0);
        SharedPreferences.Editor edtCount = prefCount.edit();
        edtCount.putString("newCount", high_score.getText().toString());

        //commits the newcount to memory
        edtCount.commit();
    }
}