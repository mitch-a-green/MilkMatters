package com.milkmatters.honoursproject.milkmatters.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.adapters.FeedAdapter;
import com.milkmatters.honoursproject.milkmatters.database.FeedTableHelper;
import com.milkmatters.honoursproject.milkmatters.model.FeedItem;

import java.util.ArrayList;

/**
 * The news and events feed fragment.
 */
public class NewsFeedFragment extends Fragment {
    // Context
    private Context context;
    // Data
    private ArrayList<FeedItem> feedItems = new ArrayList<FeedItem>();
    // Helper
    private FeedTableHelper feedTableHelper;
    // View
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View view;

    /**
     * Required empty public constructor
     */
    public NewsFeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment NewsFeedFragment.
     */
    public static NewsFeedFragment newInstance(String param1, String param2) {
        NewsFeedFragment fragment = new NewsFeedFragment();
        return fragment;
    }

    /**
     * Overridden onCreate method.
     * Gets the feed items from the database.
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this.getContext();
        this.feedTableHelper = new FeedTableHelper(this.context);
        this.feedItems = this.feedTableHelper.getAllFeedItems();
        this.feedTableHelper.closeDB();
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

        this.view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        hideFAB();

        mRecyclerView = (RecyclerView) this.view.findViewById(R.id.feed_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new FeedAdapter(this.feedItems);
        mRecyclerView.setAdapter(mAdapter);

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
     * Overridden onPause method
     */
    public void onPause()
    {
        super.onPause();
    }

    /**
     * Overridden onResume method
     * Updates the feed data, in case it has changed.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        // store the context in a variable so it can be accessed from onClick event handlers
        this.context = this.getContext();

        this.feedTableHelper = new FeedTableHelper(this.getContext());

        // Update the data
        this.feedItems.clear();
        this.feedItems.addAll(this.feedTableHelper.getAllFeedItems());
        mAdapter.notifyDataSetChanged();

        // Close the connection to the database
        this.feedTableHelper.closeDB();
    }
}
