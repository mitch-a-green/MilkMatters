package com.milkmatters.honoursproject.milkmatters.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.milkmatters.honoursproject.milkmatters.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {
    private View view;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AboutFragment.
     */
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    /**
     * Overridden onCreate method
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Overridden onCreateView method
     * @param inflater the layout inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the created view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_about, container, false);

        hideFAB(); // Hide the floating action button

        // add functionality to send an automated email
        Button emailUsButton = (Button) this.view.findViewById(R.id.email_us);
        emailUsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] addresses = {"mitch.a.green@gmail.com", "chelseajoyful@gmail.com"};
                String message = "Hi,\n\nI read about you in the Milk Matters app. I am " +
                        "interested in finding out more...";
                emailUs(addresses, "Want to Know More", message);
            }
        });

        return this.view;
    }

    /**
     * Method to show the floating action button
     */
    public void showFAB()
    {
        FloatingActionButton fab = (FloatingActionButton) this.getActivity().findViewById(R.id.fab);
        fab.show();
    }

    /**
     * Method to hide the floating action button
     */
    public void hideFAB()
    {
        FloatingActionButton fab = (FloatingActionButton) this.getActivity().findViewById(R.id.fab);
        fab.hide();
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
}