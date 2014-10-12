package smusings.mentalmaths;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ResetConfirmDialog extends DialogFragment {

    public static ResetConfirmDialog newInstance(){
        return new ResetConfirmDialog();
    }

    public interface ResetConfirmListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    ResetConfirmListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Reset Scores")
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(ResetConfirmDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(ResetConfirmDialog.this);
                    }
                })
                .create();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mListener = (ResetConfirmListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
        + " must implement ResterConfirmListener");
        }
    }
}
