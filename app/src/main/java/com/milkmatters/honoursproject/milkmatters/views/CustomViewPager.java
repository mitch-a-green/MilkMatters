package com.milkmatters.honoursproject.milkmatters.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Custom View Pager: basically a regular view pager that does not respond to swipe gestures.
 * Used in the ml donated and babies fed graph fragments.
 * Prevents the user from switching between these fragments by swiping on the screen.
 * This, in turn, ensures that the user can scroll on the graph by swiping left and right.
 */
public class CustomViewPager extends ViewPager {

    private boolean enabled;

    /**
     * Public constructor
     * @param context the context
     * @param attrs the attributes
     */
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    /**
     * Overridden onTouchEvent.
     * @param event the on touch event
     * @return a boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    /**
     * Overridden onInterceptTouchEvent.
     * @param event the on touch event
     * @return a boolean
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    /**
     * Method to enabling/disable paging
     * @param enabled a boolean indicating whether paging should be enabled/disabled
     */
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}