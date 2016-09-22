package com.milkmatters.honoursproject.milkmatters.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.milkmatters.honoursproject.milkmatters.fragments.BabiesGraphFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.MlGraphFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                MlGraphFragment mlGraphFragment = new MlGraphFragment();
                return mlGraphFragment;
            case 1:
                BabiesGraphFragment babiesGraphFragment = new BabiesGraphFragment();
                return babiesGraphFragment;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
