package com.milkmatters.honoursproject.milkmatters.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.activities.MainArticlesActivity;
import com.milkmatters.honoursproject.milkmatters.adapters.GridImageAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EducationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EducationFragment extends Fragment {
    private Context context;

    private View view;

    public EducationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment EducationFragment.
     */
    public static EducationFragment newInstance() {
        EducationFragment fragment = new EducationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.view = inflater.inflate(R.layout.fragment_education, container, false);

        hideFAB();

        GridView gridView = (GridView) this.view.findViewById(R.id.gridview);
        gridView.setAdapter(new GridImageAdapter(this.getContext()));

        /**
         * On Click event for Single Gridview Item
         * */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent intent = new Intent(context, MainArticlesActivity.class);
                switch (position){

                    case 0:
                        intent.putExtra("URL", "http://www.json-generator.com/api/json/get/bUPxrJWhpe?indent=2");
                        intent.putExtra("title", "Breastfeeding");
                        break;
                    case 1:
                        intent.putExtra("URL", "http://www.json-generator.com/api/json/get/cqgpmolcpu?indent=2");
                        intent.putExtra("title", "Donation");
                        break;
                    case 2:
                        intent.putExtra("URL", "http://www.json-generator.com/api/json/get/clCfAcBGEO?indent=2");
                        intent.putExtra("title", "Increase Milk Supply");
                        break;
                    case 3:
                        intent.putExtra("URL", "http://www.json-generator.com/api/json/get/bUEDNpSoXS?indent=2");
                        intent.putExtra("title", "Latching");
                        break;
                    case 4:
                        intent.putExtra("URL", "http://www.json-generator.com/api/json/get/cerpkVDwPm?indent=2");
                        intent.putExtra("title", "Nutrition");
                        break;
                    case 5:
                        intent.putExtra("URL", "http://www.json-generator.com/api/json/get/cegBHHXLoy?indent=2");
                        intent.putExtra("title", "Pumping");
                        break;
                    case 6:
                        intent.putExtra("URL", "http://www.json-generator.com/api/json/get/cedwXOYrWW?indent=2");
                        intent.putExtra("title", "Mastitis");
                        break;
                    case 7:
                        intent.putExtra("URL", "http://www.json-generator.com/api/json/get/cqmgBWepcO?indent=2");
                        intent.putExtra("title", "Parenting");
                        break;
                    case 8:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, "chelseajoyful@gmail.com");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Article Suggestion");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Here is an article I found very interesting...");
                        if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null)
                            startActivity(emailIntent);
                        break;
                }
                if (position!=8)
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
