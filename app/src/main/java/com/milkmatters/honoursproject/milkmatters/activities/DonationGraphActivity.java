package com.milkmatters.honoursproject.milkmatters.activities;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TabHost;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.database.DonationsTableHelper;
import com.milkmatters.honoursproject.milkmatters.model.Donation;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Activity to display graphs depicting the ml donated and babies fed
 */
public class DonationGraphActivity extends AppCompatActivity {
    private ArrayList<Donation> donations;

    /**
     * Overridden onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_graph);

        getSupportActionBar().setTitle(getString(R.string.activity_donation_graph));

        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);

        // get the screen size
        Point size = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        int halfScreenHeight = (int)(screenHeight *0.5);

        DonationsTableHelper donationsTableHelper =
                new DonationsTableHelper(this.getApplicationContext());
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
        dataset.setDrawCubic(true);
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
    }
}
