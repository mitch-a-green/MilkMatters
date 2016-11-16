package com.milkmatters.honoursproject.milkmatters.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.milkmatters.honoursproject.milkmatters.fragments.BabiesGraphFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.MlGraphFragment;

/**
 * Pager adapter for the babies fed and ml donated graphs.
 * Switches between the respective fragments.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    // integer to count number of tabs
    int tabCount;

    /**
     * Public constructor
     * @param fm the fragment manager
     * @param tabCount the number of tabs
     */
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    /**
     * Overridden getItem method
     * @param position the position
     * @return the selected fragment
     */
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

    /**
     * Overridden method to get the number of tabs.
     * @return the number of tabs
     */
    @Override
    public int getCount() {
        return tabCount;
    }
}
