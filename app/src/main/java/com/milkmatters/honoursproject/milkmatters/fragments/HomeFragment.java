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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.activities.DonationGraphActivity;
import com.milkmatters.honoursproject.milkmatters.activities.MainArticlesActivity;
import com.milkmatters.honoursproject.milkmatters.adapters.FeedListAdapter;
import com.milkmatters.honoursproject.milkmatters.controller.AppController;
import com.milkmatters.honoursproject.milkmatters.database.DonationsTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.FeedTableHelper;
import com.milkmatters.honoursproject.milkmatters.model.EducationFeedItem;
import com.milkmatters.honoursproject.milkmatters.model.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
    private static final String TAG = MainArticlesActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<EducationFeedItem> educationFeedItems;
    // private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
    private String URL_FEED = "http://www.json-generator.com/api/json/get/cpnlgqhszm?indent=2";

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

        CardView babiesCardView = (CardView) this.view.findViewById(R.id.babies_card_view);
        babiesCardView.setOnClickListener(new View.OnClickListener() {
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

        listView = (ListView) this.view.findViewById(R.id.list);

        educationFeedItems = new ArrayList<EducationFeedItem>();

        listAdapter = new FeedListAdapter(this.getActivity(), educationFeedItems);
        listView.setAdapter(listAdapter);

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        //getActionBar().setIcon(
        //        new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
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

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i <= 0; i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                EducationFeedItem item = new EducationFeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                educationFeedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
