package com.milkmatters.honoursproject.milkmatters.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.milkmatters.honoursproject.milkmatters.R;

/**
 * 'About Milk Matters' fragment.
 * Provides the user with information about Milk Matters, and contact details.
 */
public class AboutFragment extends Fragment {
    private View view;
    private AboutFragment.OnFragmentInteractionListener mListener;

    /**
     * Required empty public constructor
     */
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

        Button becomeDonorButton = (Button) this.view.findViewById(R.id.about_link);
        becomeDonorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBecomeDonorButtonPressed(null);
            }
        });

        // add functionality to send an automated email
        Button emailUsButton = (Button) this.view.findViewById(R.id.email_us);
        emailUsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] addresses = {"info@milkmatters.org"};
                String message = "Hi,\n\nI read about you in the Milk Matters app. I am " +
                        "interested in finding out more...";
                emailUs(addresses, "Want to Know More", message);
            }
        });

        TextView officeNumberTextView = (TextView) this.view.findViewById(R.id.office_number);
        TextView cellNumberTextView = (TextView) this.view.findViewById(R.id.cell_number);
        TextView emailAddressTextView = (TextView) this.view.findViewById(R.id.email_address);

        // add on click functionality to automatically dial the Milk Matters office number
        officeNumberTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callUs(getString(R.string.office));
            }
        });

        // add on click functionality to automatically dial the Milk Matters cellphone number
        cellNumberTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callUs(getString(R.string.cell));
            }
        });

        // add functionality to send an automated email
        emailAddressTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] addresses = {"info@milkmatters.org"};
                String message = "Hi,\n\nI read about you in the Milk Matters app. I am " +
                        "interested in finding out more...";
                emailUs(addresses, "Want to Know More", message);
            }
        });

        return this.view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onBecomeDonorButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Overridden onAttach method
     * @param context the context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AboutFragment.OnFragmentInteractionListener) {
            mListener = (AboutFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Overridden onDetach method
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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
     * Method to dial a number that was clicked on
     * @param number the number to dial
     */
    public void callUs(String number)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
