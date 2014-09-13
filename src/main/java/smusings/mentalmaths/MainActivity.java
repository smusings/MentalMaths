package smusings.mentalmaths;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends SetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        countRight = (TextView) findViewById(R.id.count_right);
        num_answer = (EditText) findViewById(R.id.num_result);
        multiplicantSeek = (SeekBar) findViewById(R.id.multiplicant_seek);
        multiplierSeek = (SeekBar) findViewById(R.id.multiplier_seek);

        countRight.setText("0");
        multiplicantSeek.setProgress(0);
        multiplierSeek.setProgress(0);
        seekBar_1_call();
        seekBar_2_call();

        multiplicantSeek.setOnSeekBarChangeListener(seeker1);
        multiplierSeek.setOnSeekBarChangeListener(seeker2);

        num_answer.setOnEditorActionListener(result_entered);
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
