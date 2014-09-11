package smusings.mentalmaths;

import android.app.Activity;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;


public class SetupActivity extends Activity {

    public TextView num1;
    public TextView num2;
    public TextView countRight;
    public EditText num_answer;
    public SeekBar seek1;

    public static int numSetUp(int min, int max){
        Random random = new Random();

        int randomInt=random.nextInt(max - min) + min;

        return randomInt;
    }

    public void singleDigit(){
        num1.setText(Integer.toString(numSetUp(0, 9)));
        num2.setText(Integer.toString(numSetUp(0, 9)));
    }

    public void doubleDigit(){
        num1.setText(Integer.toString(numSetUp(10, 99)));
        num2.setText(Integer.toString(numSetUp(10, 99)));
    }

    public void tripleDigit(){
        num1.setText(Integer.toString(numSetUp(100, 999)));
        num2.setText(Integer.toString(numSetUp(100, 999)));
    }

    public void quadDigit(){
        num1.setText(Integer.toString(numSetUp(1000, 9999)));
        num2.setText(Integer.toString(numSetUp(1000, 9999)));
    }

    public void setCountPlusOne(){
        int oldCount = Integer.valueOf(countRight.getText().toString());
        int newCount = oldCount + 1;
        countRight.setText(Integer.toString(newCount));
    }

    public void resetCount(){
        countRight.setText("0");
    }
}
