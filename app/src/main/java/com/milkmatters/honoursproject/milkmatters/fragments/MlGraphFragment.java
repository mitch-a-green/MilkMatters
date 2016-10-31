package com.milkmatters.honoursproject.milkmatters.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.database.DonationsTableHelper;
import com.milkmatters.honoursproject.milkmatters.model.Donation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MlGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MlGraphFragment extends Fragment {
    private Context context;
    private ArrayList<Donation> donations;
    private View view;

    /**
     * Public constructor
     */
    public MlGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MlGraphFragment.
     */
    public static MlGraphFragment newInstance() {
        MlGraphFragment fragment = new MlGraphFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Overridden onCreate method
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
    }

    /**
     * Overridden onCreateView method
     * @param inflater the layout inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.donation_graph, container, false);

        LineChart lineChart = (LineChart) view.findViewById(R.id.line_chart);

        DonationsTableHelper donationsTableHelper =
                new DonationsTableHelper(this.context);
        donations = donationsTableHelper.getAllDatesAndTotals();
        donationsTableHelper.closeDB();

        float total = 0;
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        for  (int i = 0; i < donations.size(); i++)
        {
            total += donations.get(i).getQuantity();
            entries.add(new Entry(total, i));
            labels.add(donations.get(i).getAnalogueDate());
        }

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
        dataset.setColor(Color.parseColor("#FFDB0963"));
        dataset.setCircleColor(Color.parseColor("#FFDB0963"));
        dataset.setFillColor(Color.parseColor("#eeb8d4"));
        dataset.setLineWidth(2f);
        dataset.setCircleRadius(4f);

        LineData data = new LineData(labels, dataset);
        dataset.setDrawCubic(false);
        dataset.setDrawFilled(true);

        //lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setLabelsToSkip(0);
        lineChart.getXAxis().setLabelRotationAngle(-90f);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.setData(data);
        lineChart.animateY(5000);
        lineChart.setVisibleXRangeMaximum(7);
        lineChart.moveViewToX(5);

        lineChart.setDescription("");    // Hide the description
        lineChart.getLegend().setEnabled(false);

        return this.view;
    }

    /**
     * Overridden onResume method
     */
    @Override
    public void onResume()
    {
        super.onResume();
    }

    /**
     * Overridden onPause method
     */
    @Override
    public void onPause()
    {
        super.onPause();
    }
}
