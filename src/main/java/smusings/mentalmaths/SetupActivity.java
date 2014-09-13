package smusings.mentalmaths;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class SetupActivity extends Activity {

    public TextView num1;
    public TextView num2;
    public TextView countRight;
    public EditText num_answer;
    public SeekBar multiplicantSeek;
    public SeekBar multiplierSeek;


    public static int numSetUp(int min, int max)
    {
        Random random = new Random();

        int randomInt=random.nextInt(max - min) + min;

        return randomInt;
    }

    SeekBar.OnSeekBarChangeListener seeker1 = new SeekBar.OnSeekBarChangeListener()
    {
        int progressOne = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            progressOne = progress;

            if (progressOne == 0)
            {
                num1.setText(Integer.toString(numSetUp(1, 9)));
            }
            else if (progressOne == 1)
            {
                num1.setText(Integer.toString(numSetUp(10, 99)));
            }
            else if (progressOne == 2)
            {
                num1.setText(Integer.toString(numSetUp(100, 999)));
            }
            else if (progressOne == 3)
            {
                num1.setText(Integer.toString(numSetUp(100, 9999)));
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

    SeekBar.OnSeekBarChangeListener seeker2 = new SeekBar.OnSeekBarChangeListener()
    {
        int progressTwo = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            progressTwo = progress;

            if (progressTwo == 0)
            {
                num2.setText(Integer.toString(numSetUp(1, 9)));
            }
            else if (progressTwo == 1)
            {
                num2.setText(Integer.toString(numSetUp(10, 99)));
            }
            else if (progressTwo == 2)
            {
                num2.setText(Integer.toString(numSetUp(100, 999)));
            }
            else if (progressTwo == 3)
            {
                num2.setText(Integer.toString(numSetUp(100, 9999)));
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

    TextView.OnEditorActionListener result_entered = new TextView.OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
        {
            if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                    ((event.isShiftPressed() == false)) &&
                            (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                            (event.getAction() == KeyEvent.ACTION_DOWN))
            {

                int result=Integer.valueOf(num1.getText().toString()) *
                        Integer.valueOf(num2.getText().toString());

                if (num_answer.getText().toString().matches(""))
                {
                    num_answer.setText("");
                    num_answer.clearFocus();
                }
                else if (Integer.valueOf(num_answer.getText().toString()) == result)
                {
                    Toast.makeText(SetupActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    num_answer.setText("");
                    setCountPlusOne();
                    seekBar_1_call();
                    seekBar_2_call();
                }
                else if (Integer.valueOf(num_answer.getText().toString()) != result)
                {
                    Toast.makeText(SetupActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    num_answer.setText("");
                    countRight.setText("0");
                }
            }
            return false;
        }
    };


    public void seekBar_1_call()
    {
        if (multiplicantSeek.getProgress() == 0)
        {
            num1.setText(Integer.toString(numSetUp(1, 9)));
        }
        else if (multiplicantSeek.getProgress() == 1)
        {
            num1.setText(Integer.toString(numSetUp(10, 99)));
        }
        else if (multiplicantSeek.getProgress() == 2)
        {
            num1.setText(Integer.toString(numSetUp(100, 999)));
        }
        else if (multiplicantSeek.getProgress() == 3)
        {
            num1.setText(Integer.toString(numSetUp(1000, 9999)));
        }
    }

    public void seekBar_2_call()
    {
        if (multiplierSeek.getProgress() == 0)
        {
            num2.setText(Integer.toString(numSetUp(1, 9)));
        }
        else if (multiplierSeek.getProgress() == 1)
        {
            num2.setText(Integer.toString(numSetUp(10, 99)));
        }
        else if (multiplierSeek.getProgress() == 2)
        {
            num2.setText(Integer.toString(numSetUp(100, 999)));
        }
        else if (multiplierSeek.getProgress() == 3)
        {
            num2.setText(Integer.toString(numSetUp(1000, 9999)));
        }
    }



    public void setCountPlusOne()
    {
        int oldCount = Integer.valueOf(countRight.getText().toString());
        int newCount = oldCount + 1;
        countRight.setText(Integer.toString(newCount));
    }
}
