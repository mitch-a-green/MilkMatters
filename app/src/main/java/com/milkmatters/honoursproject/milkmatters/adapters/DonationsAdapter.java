package com.milkmatters.honoursproject.milkmatters.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.activities.MainActivity;
import com.milkmatters.honoursproject.milkmatters.model.Donation;
import com.milkmatters.honoursproject.milkmatters.model.FeedItem;

import java.util.ArrayList;

/**
 * The donations adapter.
 */
public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.ViewHolder> {
    private ArrayList<ArrayList<Donation>> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        protected TextView quantityTextView;
        protected TextView dateTextView;
        protected LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            view = v;
            quantityTextView = (TextView) this.view.findViewById(R.id.quantity);
            dateTextView = (TextView) this.view.findViewById(R.id.date);
            linearLayout = (LinearLayout) this.view.findViewById(R.id.linear_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DonationsAdapter(ArrayList<ArrayList<Donation>> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DonationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donation_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ArrayList<Donation> donations = mDataset.get(position);
        Log.e("date", String.valueOf(donations.get(0).getDate()));
        Log.e("quantity", String.valueOf(donations.get(0).getQuantity()));
        holder.linearLayout.removeAllViews();
        if (donations.size() < 2)
        {
            holder.quantityTextView.setText(String.valueOf(donations.get(0).getQuantity()) + " ml");
            holder.dateTextView.setText(convertDate(String.valueOf(donations.get(0).getDate())));
        }
        else if (donations.size() >= 2)
        {
            holder.quantityTextView.setText(String.valueOf(donations.get(0).getQuantity()) + " ml");
            holder.dateTextView.setText(String.valueOf(convertDate(donations.get(0).getDate())));
            for (int i = 1; i < donations.size(); i++)
            {
                TextView textView = new TextView(context);
                textView.setText("\t\t\t- " + String.valueOf(donations.get(i).getQuantity()) + " ml");
                holder.linearLayout.addView(textView);
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Method to convert a digital date to an analogue date
     * @param inDate the input digital date
     * @return the analogue date
     */
    public String convertDate(String inDate)
    {
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

        String[] splitDate = inDate.split("-");
        String outDate = String.valueOf(splitDate[0]);
        outDate = outDate + " " + months[Integer.valueOf(splitDate[1]) - 1];
        outDate = outDate + " " + splitDate[2];

        return outDate;
    }
}
