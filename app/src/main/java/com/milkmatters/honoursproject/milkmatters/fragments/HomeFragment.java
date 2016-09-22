package com.milkmatters.honoursproject.milkmatters.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.activities.DonationGraphActivity;
import com.milkmatters.honoursproject.milkmatters.database.DonationsTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.FeedTableHelper;
import com.milkmatters.honoursproject.milkmatters.model.FeedItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Context context;
    private View view;
    private ArrayList<FeedItem> feedItems;
    private final int AMOUNT_CONSUMED_DAILY = 50;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        showFAB();

        CardView mlCardView = (CardView) this.view.findViewById(R.id.ml_card_view);
        mlCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonationGraphActivity.class);
                startActivity(intent);
            }
        });

        return view;
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
     * Overridden onResume method.
     * Refreshes the views, in case the donations data has changed.
     */
    public void onResume()
    {
        super.onResume();
        this.context = getContext();
        DonationsTableHelper donationsTableHelper = new DonationsTableHelper(this.getContext());
        int totalDonated = donationsTableHelper.getTotalDonations();
        donationsTableHelper.closeDB();
        TextView totalDonatedTextView = (TextView) this.view.findViewById(R.id.ml_expressed);
        totalDonatedTextView.setText(String.valueOf(totalDonated) + " ml");
        TextView babiesFedTextView = (TextView) this.view.findViewById(R.id.babies_fed);
        int babiesFed = Integer.valueOf(totalDonated / AMOUNT_CONSUMED_DAILY);
        babiesFedTextView.setText(String.valueOf(babiesFed) + " babies");


        FeedTableHelper feedTableHelper = new FeedTableHelper(this.getContext());
        feedItems = feedTableHelper.getAllFeedItems();
        FeedItem feedItem = feedItems.get(feedItems.size()-1);
        feedTableHelper.closeDB();

        TextView titleTextView = (TextView) view.findViewById(R.id.news_item_title);
        TextView timestampTextView = (TextView) view.findViewById(R.id.timestamp);
        TextView contentTextView = (TextView) view.findViewById(R.id.content);
        TextView hyperlinkTextView = (TextView) view.findViewById(R.id.hyperlink);
        titleTextView.setText(feedItem.getTitle());
        contentTextView.setText(feedItem.getContent());
        hyperlinkTextView.setText(feedItem.getHyperlink());
        timestampTextView.setText(feedItem.toString());
    }

    /**
     * Overridden onPause method
     */
    public void onPause()
    {
        super.onPause();
    }

    /**
     * Method to set the size of each cell in the grid
     */
    public void setGridSize()
    {
        // get the screen size
        Point size = new Point();
        this.getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int halfScreenWidth = (int)(screenWidth *0.5);
        // set the size of each cell in the grid
        GridLayout gridLayout = (GridLayout) this.view.findViewById(R.id.grid_layout);
        gridLayout.setMinimumWidth(screenWidth);
        gridLayout.getChildAt(0).setMinimumWidth(halfScreenWidth/2);
        gridLayout.getChildAt(1).setMinimumWidth(halfScreenWidth/2);
    }
}
