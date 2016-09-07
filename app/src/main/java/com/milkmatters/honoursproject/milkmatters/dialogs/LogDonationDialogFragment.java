package com.milkmatters.honoursproject.milkmatters.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.milkmatters.honoursproject.milkmatters.R;

/**
 * Dialog to allow users to log donations
 * Extends DialogFragment
 * Takes in a title and strings for the buttons
 * Has a callback method that returns the user's input
 * Created by mitchell on 2016/08/19.
 */
public class LogDonationDialogFragment extends DialogFragment {

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
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
     * Overridden onCreate method
     * @param savedInstanceState the saved instance state
     * @return the dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get the arguments
        Bundle b = getArguments();
        final String title = b.getString("title"); // The title
        final String hint = b.getString("hint");
        final String positiveButton = b.getString("positiveButton"); // The positive button text
        final String negativeButton = b.getString("negativeButton"); // The negative button text

        // Build the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate the view and set the edit text values
        View view = inflater.inflate(R.layout.dialog_log_donation, null);
        final EditText quantityEditText = (EditText) view.findViewById(R.id.quantityEditText);
        // Set the dialog title
        builder.setTitle(title)
                .setView(view)
                // Specify the message, define the call back listener and handle the button clicks
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    /**
                     * Overridden positive button onClick
                     * Makes a call to the callback interface indicating that the positive button was clicked
                     *
                     * @param dialog the dialog
                     * @param id     the ID
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText quantityEditText = (EditText)
                                getDialog().findViewById(R.id.quantityEditText);
                        DatePicker datePicker = (DatePicker)
                                getDialog().findViewById(R.id.datePicker);
                        NoticeDialogListener activity = (NoticeDialogListener) getActivity();
                        String date = datePicker.getDayOfMonth() + "-" + datePicker.getMonth()
                                + "-" + datePicker.getYear();
                        String quantity = quantityEditText.getText().toString().trim();
                        if ((quantity.equals("")) || (quantity == null))
                        {
                            LogDonationDialogFragment.this.getDialog().cancel();
                        }
                        else
                        {
                            activity.onReturnValue(Integer.valueOf(quantity), date);
                            mListener.onDialogPositiveClick(LogDonationDialogFragment.this);
                        }
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    /**
                     * Overridden negative button onClick
                     * Doesn't do anything
                     * @param dialog the dialog
                     * @param id the ID
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        LogDonationDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);

        void onReturnValue(int quantity, String date);
    }
}
