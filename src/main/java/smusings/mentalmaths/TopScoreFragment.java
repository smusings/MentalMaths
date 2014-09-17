package smusings.mentalmaths;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

public class TopScoreFragment extends Fragment {

    public ExpandableListView top_scores;
    String[] highscores = {"1", "2", "3"};


    public static final String OLD_COUNT = "MyOldCount";
    public static final String NEW_COUNT = "MyNewCount";



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences pref = getActivity().getSharedPreferences(NEW_COUNT, 0);
        String top_score = pref.getString("newCount", "0");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActivity(),
                android.R.layout.simple_list_item_1, highscores);

        top_scores = (ExpandableListView) getView().findViewById(R.id.expand_top_score);
        top_scores.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflates the xml layout
        View view = inflater.inflate(R.layout.fragment_top_score, container, false);

        return view;
    }

    /*
    @Override
    public void onPause(){
        super.onPause();

        //this preference needs to be called upon somewhere else
        SharedPreferences prefCount = getActivity().getSharedPreferences(NEW_COUNT, 0);
        SharedPreferences.Editor edtCount = prefCount.edit();
        edtCount.putStringSet("newCount", top_scores.toString());

        //commits the newcount to memory
        edtCount.commit();
    }
    */
}
