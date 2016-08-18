package com.milkmatters.honoursproject.milkmatters.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milkmatters.honoursproject.milkmatters.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DepotLocatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DepotLocatorFragment extends Fragment {
    private View view;

    public DepotLocatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DepotLocatorFragment.
     */
    public static DepotLocatorFragment newInstance(String param1, String param2) {
        DepotLocatorFragment fragment = new DepotLocatorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_depot_locator, container, false);

        hideFAB();

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
}
