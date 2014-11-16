package smusings.mentalmaths;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SetupActivity extends Activity {

    //declares these for SharedPreferences
    public static final String intent_count = "smusings.mentalmaths.HIGHSCORE";
    //declares almost everything we plan on using
    //made public for testing in the future
    public TextView     multiplicand_tv;
    public TextView     multiplier_tv;
    public TextView     countRightAnswer;
    public TextView     timer;
    public TextView     latestScore;
    public TextView     operation_symbol;
    public TextView     scoreTextView;
    public EditText     answer;
    public SeekBar      multiplicantSeek;
    public SeekBar      multiplierSeek;
    public LinearLayout multiplicandLayout;
    public LinearLayout multiplierLayout;
    public LinearLayout scoreLayout;
    public LinearLayout buttonLayout;
    public String       oldCount;
    public Button       saveScoreButton;
    public Button       saveCancelButton;

    //seek bar listener
    SeekBar.OnSeekBarChangeListener seeker = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            if (seekBar == multiplicantSeek)
            {
                seekBar_random(multiplicand_tv, multiplicantSeek);
            } else if (seekBar == multiplierSeek)
            {
                seekBar_random(multiplier_tv, multiplierSeek);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }
    };


    //listener for EditText Done button
    TextView.OnEditorActionListener result_entered = new TextView.OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
        {
            if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                    ((!event.isShiftPressed())) &&
                            (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                            (event.getAction() == KeyEvent.ACTION_DOWN))
            {

                //set up the ints to work properly
                int result = 0;
                if (operation_symbol.getText() == "x")
                {
                    result = Integer.valueOf(multiplicand_tv.getText().toString()) *
                            Integer.valueOf(multiplier_tv.getText().toString());
                }
                else if (operation_symbol.getText() == "÷")
                {
                    result = Integer.valueOf(multiplicand_tv.getText().toString()) +
                            Integer.valueOf(multiplier_tv.getText().toString());
                }
                else if (operation_symbol.getText() == "+")
                {
                    result = Integer.valueOf(multiplicand_tv.getText().toString()) +
                            Integer.valueOf(multiplier_tv.getText().toString());
                }
                else if (operation_symbol.getText() == "-")
                {
                    result = Integer.valueOf(multiplicand_tv.getText().toString()) -
                            Integer.valueOf(multiplier_tv.getText().toString());
                }

                //make it error proof
                if (answer.getText().toString().matches(""))
                {
                    answer.setText("");
                    answer.clearFocus();
                }
                //if correct, we say correct, add one to count and restart the countdown
                else if (Integer.valueOf(answer.getText().toString()) == result)
                {
                    //on correct
                    multiplicandLayout.setVisibility(View.GONE);
                    multiplierLayout.setVisibility(View.GONE);

                    //if correct, say it is, then clear the text, add one to the count, and give two new numbers.
                    answer.setText("");
                    seekBar_random(multiplicand_tv, multiplicantSeek);
                    seekBar_random(multiplier_tv, multiplierSeek);
                    //cancel first otherwise we trigger onFinish many times
                    cdt.cancel();
                    cdt.start();

                    //could we put all of the above into the method?
                    setCountPlusOne();
                }

                //if wrong say wrong and do nothign else
                else if (Integer.valueOf(answer.getText().toString()) != result)
                {
                    Toast.makeText(SetupActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    answer.setText("");
                }
            }
            return false;
        }
    };

    //the coutndown timer and what to do when when time = 0
    CountDownTimer cdt = new CountDownTimer(10000, 1000)
    {
        @Override
        public void onTick(long millisUntilFinished)
        {
            timer.setText("" + millisUntilFinished /    1000);
        }

        @Override
        public void onFinish()
        {
            timer.setText("TIMES UP");
            answer.clearFocus();    //focus not clearing for some reason?
            multiplicandLayout.setVisibility(View.VISIBLE);
            multiplierLayout.setVisibility(View.VISIBLE);
            oldCount = countRightAnswer.getText().toString();
            latestScore.setText(oldCount);
            countRightAnswer.setText("0");
            scoreTextView.setText(R.string.spree_count);

            //show the score buttons
            buttonLayout.setVisibility(View.VISIBLE);
            scoreLayout.setVisibility(View.VISIBLE);
        }
    };

    //catchall button listener.
    View.OnClickListener buttonClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (v == saveScoreButton)
            {
                //laucnhes a new intent of the top score
                Intent intent = new Intent(SetupActivity.this, TopScoreActivity.class);
                intent.putExtra(intent_count, oldCount);
                startActivity(intent);
            }
            //hides everything
            buttonLayout.setVisibility(View.GONE);
            scoreLayout.setVisibility(View.GONE);
        }
    };

    //gives us our pseudo-random numbers
    public static int numSetUp(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1) + min);
    }

    //used to give new numbers if correct
    public void seekBar_random(TextView tv, SeekBar seekBar)
    {
        if (seekBar.getProgress() == 0)
        {
            tv.setText(Integer.toString(numSetUp(1, 12)));
        }
        else if (seekBar.getProgress() == 1)
        {
            tv.setText(Integer.toString(numSetUp(13, 99)));
        }
        else if (seekBar.getProgress() == 2)
        {
            tv.setText(Integer.toString(numSetUp(100, 999)));
        }
        else if (seekBar.getProgress() == 3)
        {
            tv.setText(Integer.toString(numSetUp(1000, 9999)));
        }
    }

    //adds one to the count
    public void setCountPlusOne()
    {
        int oldCount = Integer.valueOf(countRightAnswer.getText().toString());
        int newCount = oldCount + 1;
        countRightAnswer.setText(Integer.toString(newCount));
        if (newCount == 5)
        {
            Toast.makeText(this, R.string.five_spree, Toast.LENGTH_SHORT).show();
            scoreTextView.setText(R.string.five_spree);
        }
        if (newCount == 10)
        {
            Toast.makeText(this, R.string.ten_spree, Toast.LENGTH_SHORT).show();
            scoreTextView.setText(R.string.ten_spree);
        }
        if (newCount == 15)
        {
            Toast.makeText(this, R.string.fiften_spree, Toast.LENGTH_SHORT).show();
            scoreTextView.setText(R.string.fiften_spree);
        }
        if (newCount == 20)
        {
            Toast.makeText(this, R.string.twenty_spree, Toast.LENGTH_SHORT).show();
            scoreTextView.setText(R.string.twenty_spree);
        }
        if (newCount == 25)
        {
            Toast.makeText(this, R.string.twenty_five_spree, Toast.LENGTH_SHORT).show();
            scoreTextView.setText(R.string.twenty_five_spree);
        }
        else
        {
            Toast.makeText(SetupActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        }
    }
}