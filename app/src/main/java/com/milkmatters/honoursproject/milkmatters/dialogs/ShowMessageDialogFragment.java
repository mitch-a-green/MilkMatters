package com.milkmatters.honoursproject.milkmatters.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Generic dialog to display a message to the user.
 */
public class ShowMessageDialogFragment extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogClose(boolean logDonation);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    /**
     * Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
     * @param activity the activity that created the dialog
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    /**
     * Overridden onCreateDialog method.
     * Sets up the dialog.
     * @param savedInstanceState the saved instance state
     * @return the dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Bundle args = this.getArguments();
        builder.setMessage(args.getString("message"))
                .setTitle(args.getString("title"))
                .setPositiveButton(args.getString("button"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (args.getBoolean("logDonation", false))
                            mListener.onDialogClose(true);
                        else
                            mListener.onDialogClose(false);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
