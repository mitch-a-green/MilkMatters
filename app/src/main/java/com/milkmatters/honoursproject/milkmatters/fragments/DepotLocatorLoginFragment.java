package com.milkmatters.honoursproject.milkmatters.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.milkmatters.honoursproject.milkmatters.R;

import java.util.ArrayList;

/**
 * The depot locator login fragment.
 * Provides a login screen for mothers the first time they try use the depot locator.
 */
public class DepotLocatorLoginFragment extends Fragment implements View.OnClickListener{
    private final String PASSWORD = "supermom2016!";
    private View view;
    private DepotLocatorLoginFragment.OnLoginListener mListener;

    /**
     * Required empty public constructor
     */
    public DepotLocatorLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DepotLocatorFragment.
     */
    public static DepotLocatorLoginFragment newInstance() {
        DepotLocatorLoginFragment fragment = new DepotLocatorLoginFragment();
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
     * Overridden onCreateView method.
     * @param inflater the layout inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.view = inflater.inflate(R.layout.fragment_depot_locator_login, container, false);

        hideFAB();
        Button loginButton = (Button) this.view.findViewById(R.id.login);
        loginButton.setOnClickListener(this);

        return this.view;
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Button loginButton = (Button) this.view.findViewById(R.id.login);
        /* If the login button was clicked, try login.
           Performs a callback to the main activity, which switches to the DepotLocatorFragment.
         */
        if (v.equals(loginButton)) {
            TextView errorTextView = (TextView) this.view.findViewById(R.id.error);
            EditText passwordEditText = (EditText) this.view.findViewById(R.id.passwordEditText);
            String password = passwordEditText.getText().toString().trim();
            if (password.equals(PASSWORD)) {
                passwordEditText.clearFocus();
                onLoginPressed();
            } else {
                errorTextView.setVisibility(View.VISIBLE);
                passwordEditText.setText("");
            }
        }
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

    public void onLoginPressed() {
        if (mListener != null) {
            mListener.onLogin();
        }
    }

    /**
     * Overridden onAttach method
     * @param context the context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DepotLocatorLoginFragment.OnLoginListener) {
            mListener = (DepotLocatorLoginFragment.OnLoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
    public interface OnLoginListener {
        void onLogin();
    }
}
