package com.milkmatters.honoursproject.milkmatters.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.model.FeedItem;

import java.util.ArrayList;

/**
 * Adapter for the news and events feed.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private ArrayList<FeedItem> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final int SHORT_TEXT_MAX_LINES = 2;
        private boolean expanded = false;
        // each data item is just a string in this case
        public View view;
        protected TextView titleTextView;
        protected TextView timestampTextView;
        protected TextView contentTextView;
        protected TextView hyperlinkTextView;
        protected TextView showMoreTextView;
        public ViewHolder(View v) {
            super(v);
            view = v;
            titleTextView = (TextView) view.findViewById(R.id.news_item_title);
            timestampTextView = (TextView) view.findViewById(R.id.timestamp);
            contentTextView = (TextView) view.findViewById(R.id.content);
            hyperlinkTextView = (TextView) view.findViewById(R.id.hyperlink);
            showMoreTextView = (TextView) view.findViewById(R.id.show_more);
            showMoreTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (expanded == false) {
                        contentTextView.setMaxLines(Integer.MAX_VALUE);
                        contentTextView.setEllipsize(null);
                        showMoreTextView.setText("--- Show Less ---");
                        expanded = true;
                    }
                    else
                    {
                        contentTextView.setMaxLines(SHORT_TEXT_MAX_LINES);
                        contentTextView.setEllipsize(TextUtils.TruncateAt.END);
                        showMoreTextView.setText("--- Show More ---");
                        expanded = false;
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FeedAdapter(ArrayList<FeedItem> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        holder.titleTextView.setText(mDataset.get(position).getTitle());
        holder.contentTextView.setText(mDataset.get(position).getContent());
        holder.hyperlinkTextView.setText(mDataset.get(position).getHyperlink());
        holder.timestampTextView.setText(mDataset.get(position).toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
