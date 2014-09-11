package smusings.mentalmaths;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends SetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        countRight = (TextView) findViewById(R.id.count_right);
        num_answer = (EditText) findViewById(R.id.num_result);
        seek1 = (SeekBar) findViewById(R.id.seek1);

        countRight.setText("0");
        seek1.setProgress(0);
        seekBar_1_call();

        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;

                if (progressChanged == 0){
                    singleDigit();
                }
                else if (progressChanged == 1){
                    doubleDigit();
                }
                else if (progressChanged == 2){
                    tripleDigit();
                }
                else if (progressChanged == 3){
                    quadDigit();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }


        });

        num_answer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                ((event.isShiftPressed() == false)) &&
                        (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                        (event.getAction() == KeyEvent.ACTION_DOWN)){
                    int result=Integer.valueOf(num1.getText().toString()) *
                            Integer.valueOf(num2.getText().toString());

                    if (num_answer.getText().toString().matches("")){
                        num_answer.setText("");
                        num_answer.clearFocus();
                    }
                    else if (Integer.valueOf(num_answer.getText().toString()) == result){
                        Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                        num_answer.setText("");
                        setCountPlusOne();
                        seekBar_1_call();
                    }
                    else if (Integer.valueOf(num_answer.getText().toString()) != result){
                        Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                        num_answer.setText("");
                        resetCount();
                    }
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
