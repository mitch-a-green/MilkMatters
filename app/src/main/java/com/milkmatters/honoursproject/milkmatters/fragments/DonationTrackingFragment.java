package com.milkmatters.honoursproject.milkmatters.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.activities.DonationGraphActivity;
import com.milkmatters.honoursproject.milkmatters.adapters.DonationsAdapter;
import com.milkmatters.honoursproject.milkmatters.adapters.FeedAdapter;
import com.milkmatters.honoursproject.milkmatters.database.DonationsTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.FeedTableHelper;
import com.milkmatters.honoursproject.milkmatters.decoration.DividerItemDecoration;
import com.milkmatters.honoursproject.milkmatters.model.Donation;
import com.milkmatters.honoursproject.milkmatters.model.FeedItem;

import java.util.ArrayList;

/**
 * The donation tracking fragment.
 * Provides a summary data and a textual list of fragments.
 */
public class DonationTrackingFragment extends Fragment {
    // Context
    private Context context;
    // Data
    private ArrayList<ArrayList<Donation>> donations;
    private final int AMOUNT_CONSUMED_DAILY = 50;
    // Helper
    private DonationsTableHelper donationsTableHelper;
    // View
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View view;

    public DonationTrackingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DonationTrackingFragment.
     */
    public static DonationTrackingFragment newInstance(String param1, String param2) {
        DonationTrackingFragment fragment = new DonationTrackingFragment();
        return fragment;
    }

    /**
     * Overridden onCreate method.
     * Gets the donations from the database.
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this.getContext();
        this.donationsTableHelper = new DonationsTableHelper(this.context);
        this.donations = this.donationsTableHelper.getDonationsGroupedByDate();
        this.donationsTableHelper.closeDB();
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
        this.view = inflater.inflate(R.layout.fragment_donation_tracking, container, false);
        showFAB();

        mRecyclerView = (RecyclerView) this.view.findViewById(R.id.donations_recycler_view);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Display a message if no donations have been recorded
        TextView message = (TextView) this.view.findViewById(R.id.message);
        if (this.donations.size() == 0)
            message.setVisibility(View.VISIBLE);
        else
            message.setVisibility(View.GONE);

        // specify an adapter
        mAdapter = new DonationsAdapter(this.donations);
        mRecyclerView.setAdapter(mAdapter);

        CardView mlCardView = (CardView) this.view.findViewById(R.id.ml_card_view);
        mlCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonationGraphActivity.class);
                intent.putExtra("graph", "ml");
                startActivity(intent);
            }
        });

        CardView babiesCardView = (CardView) this.view.findViewById(R.id.babies_card_view);
        babiesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonationGraphActivity.class);
                intent.putExtra("graph", "babies");
                startActivity(intent);
            }
        });

        return this.view;
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
     * Overridden onResume method.
     * Refreshes the views, in case the donation data has changed.
     */
    public void onResume()
    {
        super.onResume();
        DonationsTableHelper donationsTableHelper = new DonationsTableHelper(this.getContext());
        int totalDonated = donationsTableHelper.getTotalDonations();
        donationsTableHelper.closeDB();
        TextView totalDonatedTextView = (TextView) this.view.findViewById(R.id.ml_expressed);
        totalDonatedTextView.setText(String.valueOf(totalDonated) + " ml");
        TextView babiesFedTextView = (TextView) this.view.findViewById(R.id.babies_fed);
        int babiesFed = Integer.valueOf(totalDonated / AMOUNT_CONSUMED_DAILY);
        babiesFedTextView.setText(String.valueOf(babiesFed) + " babies");

        // store the context in a variable so it can be accessed from onClick event handlers
        this.context = this.getContext();

        this.donationsTableHelper = new DonationsTableHelper(this.getContext());

        // Update the data
        this.donations.clear();
        this.donations.addAll(this.donationsTableHelper.getDonationsGroupedByDate());
        mAdapter.notifyDataSetChanged();

        // Close the connection to the database
        this.donationsTableHelper.closeDB();

        // Display a message if no donations have been recorded
        TextView message = (TextView) this.view.findViewById(R.id.message);
        if (this.donations.size() == 0)
            message.setVisibility(View.VISIBLE);
        else
            message.setVisibility(View.GONE);
    }

    /**
     * Overridden onPause method
     */
    public void onPause()
    {
        super.onPause();
    }
}
