package smusings.mentalmaths;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

public class TopScoresActivity extends SetupActivity {

    /*
    so what we are going to do is have the list generated here
    and each entry will be compared from entry 10 up
    then onstop we save the list as is
    and thats the new one

    eventually something will happen here
    i need to figure out what
     */

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ListView high_score = (ListView) findViewById(R.id.high_scores_list);

        SharedPreferences pref = this.getSharedPreferences(OLD_COUNT, 0);
        String new_score = pref.getString("oldCount", "0");


    }


}
