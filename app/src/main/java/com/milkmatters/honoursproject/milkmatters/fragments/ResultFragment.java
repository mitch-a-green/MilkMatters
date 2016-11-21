package com.milkmatters.honoursproject.milkmatters.fragments;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.milkmatters.honoursproject.milkmatters.R;

/**
 * Fragment to display the result of the 'Become a Donor' quiz.
 */
public class ResultFragment extends Fragment{
    // Context
    private Context context;
    // Data
    private int score;
    // View
    private View view;

    /**
     * Required empty public constructor
     */
    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AboutFragment.
     */
    public static ResultFragment newInstance(int score) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putInt("score", score);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Overridden onCreate method
     * Accept the user's score as an argument.
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this.getActivity().getApplicationContext();
        if (getArguments() != null) {
            this.score = getArguments().getInt("score");
        }
    }

    /**
     * Overridden onCreateView method.
     * @param inflater the layout inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_become_donor_result, container, false);

        hideFAB();

        //get rating bar object
        RatingBar bar=(RatingBar)this.view.findViewById(R.id.ratingBar1);
        bar.setNumStars(5);
        bar.setStepSize(0.5f);
        //get text view
        TextView t=(TextView)this.view.findViewById(R.id.textResult);
        //display score
        bar.setRating(score);
        if(score>4){
            //Successful result
            t.setText("Congratulations, you are eligible to be a potential donor. \n\n" +
                    "Please get in contact with us if you are willing to make a difference.");
        }
        else {
            //unsuccessful result
            t.setText("Unfortunately you may be ineligible to become a donor because you answered 'no' to a question. \n\n" +
                    "However, please still get in contact with us and consider donating equipment or money (which can be done through myschool/myvillage). \n\n"+
                        "Or tell us what you answered 'no' to, and maybe we can still accommodate you");
        }

        // add functionality to send an automated email
        Button emailUsButton = (Button) this.view.findViewById(R.id.email_us);
        emailUsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] addresses = {"info@milkmatters.org"};
                if(score>4){
                    String message = "Hi,\n\nI took the become a donor quiz on the app. I am " +
                            "interested in becoming a donor...";
                    emailUs(addresses, "Eligible donor", message);
                }
                else {
                    String message = "Hi,\n\nI took the become a donor quiz on the app. " +
                            "Unfortunately, I answered 'no' to a question (Insert question here) and may not be eligible to become a donor. " +
                            "However, I am " +
                            "interested in donating equipment/money and joining your mailing list...";
                    emailUs(addresses, "Helping out", message);
                }
            }
        });

        return this.view;
    }

    /**
     * Method to send an email to Milk Matters.
     * @param addresses the email addresses
     * @param subject the subject of the email
     */
    public void emailUs(String[] addresses, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(emailIntent);
    }

    /**
     * Method to show the floating action button
     */
    public void showFAB()
    {
        Button fab = (Button) this.getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
    }

    /**
     * Method to hide the floating action button
     */
    public void hideFAB()
    {
        Button fab = (Button) this.getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
    }

    /**
     * Overridden onDetach method
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
