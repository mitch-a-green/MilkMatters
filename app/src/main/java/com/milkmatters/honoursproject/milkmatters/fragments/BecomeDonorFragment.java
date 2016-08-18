package com.milkmatters.honoursproject.milkmatters.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milkmatters.honoursproject.milkmatters.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BecomeDonorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BecomeDonorFragment extends Fragment {

    public BecomeDonorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BecomeDonorFragment.
     */
    public static BecomeDonorFragment newInstance(String param1, String param2) {
        BecomeDonorFragment fragment = new BecomeDonorFragment();
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
        return inflater.inflate(R.layout.fragment_become_donor, container, false);
    }

}
