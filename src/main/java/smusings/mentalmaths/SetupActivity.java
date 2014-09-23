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
    //made public for unit testing in the future
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

    //a listener for seekbar
    SeekBar.OnSeekBarChangeListener seeker1 = new SeekBar.OnSeekBarChangeListener() {
        int progressOne = 0;

        //if progress changes we do one of four things
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            progressOne = progress;

            if (progressOne == 0) {
                multiplicand_tv.setText(Integer.toString(numSetUp(1, 10)));
            }
            else if (progressOne == 1) {
                multiplicand_tv.setText(Integer.toString(numSetUp(11, 99)));
            }
            else if (progressOne == 2) {
                multiplicand_tv.setText(Integer.toString(numSetUp(100, 999)));
            }
            else if (progressOne == 3) {
                multiplicand_tv.setText(Integer.toString(numSetUp(1000, 9999)));
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
                multiplier_tv.setText(Integer.toString(numSetUp(1, 9)));
            }
            else if (progressTwo == 1) {
                multiplier_tv.setText(Integer.toString(numSetUp(10, 99)));
            }
            else if (progressTwo == 2) {
                multiplier_tv.setText(Integer.toString(numSetUp(100, 999)));
            }
            else if (progressTwo == 3) {
                multiplier_tv.setText(Integer.toString(numSetUp(100, 9999)));
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
                int result=Integer.valueOf(multiplicand_tv.getText().toString()) *
                        Integer.valueOf(multiplier_tv.getText().toString());

                //make it error proof
                if (answer.getText().toString().matches("")) {
                    answer.setText("");
                    answer.clearFocus();
                }

                //if correct, we say correct, add one to count and restart the countdown
                else if (Integer.valueOf(answer.getText().toString()) == result) {
                    Toast.makeText(SetupActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    answer.setText("");
                    setCountPlusOne();
                    seekBar_1_call();
                    seekBar_2_call();
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
    public void seekBar_1_call() {
        if (multiplicantSeek.getProgress() == 0) {
            multiplicand_tv.setText(Integer.toString(numSetUp(1, 9)));
        }
        else if (multiplicantSeek.getProgress() == 1) {
            multiplicand_tv.setText(Integer.toString(numSetUp(10, 99)));
        }
        else if (multiplicantSeek.getProgress() == 2) {
            multiplicand_tv.setText(Integer.toString(numSetUp(100, 999)));
        }
        else if (multiplicantSeek.getProgress() == 3) {
            multiplicand_tv.setText(Integer.toString(numSetUp(1000, 9999)));
        }
    }

    public void seekBar_2_call() {
        if (multiplierSeek.getProgress() == 0) {
            multiplier_tv.setText(Integer.toString(numSetUp(1, 9)));
        }
        else if (multiplierSeek.getProgress() == 1) {
            multiplier_tv.setText(Integer.toString(numSetUp(10, 99)));
        }
        else if (multiplierSeek.getProgress() == 2) {
            multiplier_tv.setText(Integer.toString(numSetUp(100, 999)));
        }
        else if (multiplierSeek.getProgress() == 3) {
            multiplier_tv.setText(Integer.toString(numSetUp(1000, 9999)));
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

    /*
        set up for dialog to ask if we want to add a high score or not
     */

    public void showDialog(){
        DialogFragment dialogFragment = new TopScoreDialog();
        dialogFragment.show(getFragmentManager(), "TopScoreDialog");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {

        Intent intent = new Intent(SetupActivity.this, TopScoreActivity.class);
        intent.putExtra(intent_count, oldCount);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {

    }

}