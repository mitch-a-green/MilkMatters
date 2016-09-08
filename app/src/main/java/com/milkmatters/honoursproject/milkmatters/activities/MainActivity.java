package com.milkmatters.honoursproject.milkmatters.activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.adapters.FeedAdapter;
import com.milkmatters.honoursproject.milkmatters.database.DonationsTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.FeedTableHelper;
import com.milkmatters.honoursproject.milkmatters.dialogs.LogDonationDialogFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.AboutFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.BecomeDonorFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.DepotLocatorFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.DonationTrackingFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.EducationFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.HomeFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.NewsFeedFragment;
import com.milkmatters.honoursproject.milkmatters.model.Donation;
import com.milkmatters.honoursproject.milkmatters.model.NewsItem;

/**
 * Main activity.
 * Switches between various fragments, and controls the navigation drawer and
 * overflow menu options.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LogDonationDialogFragment.NoticeDialogListener,
        AboutFragment.OnFragmentInteractionListener {
    private Fragment fragment;
    SharedPreferences prefs = null;

    /**
     * Overridden onCreate method
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("com.milkmatters.honoursproject.milkmatters", MODE_PRIVATE);

        fragment = null;
        fragment = new HomeFragment();
        switchFragment(getString(R.string.app_name));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogDonationDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Overridden onBackPressed method.
     * Closes the drawer if it is open.
     * Otherwise, returns to the home screen.
     * Closes the app if already on the home screen.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Overridden onCreateOptionsMenu method
     * @param menu the menu
     * @return a boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Overridden onOptionsItemSelected method.
     * Performs the appropriate action for each option in the overflow menu
     * @param item the option item selected
     * @return a boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_donation) {
            showLogDonationDialog();
            return true;
        }
        else if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    /**
     * Overridden onNavigationItemSelected method.
     * Handles clicks on items in the navigation drawer.
     * Switches between the fragments/activities selected.
     * @param item the navigation item selected
     * @return a boolean
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            title = getString(R.string.app_name);
        } else if (id == R.id.nav_donation_tracking) {
            fragment = new DonationTrackingFragment();
            title = getString(R.string.donation_tracking_fragment);
        } else if (id == R.id.nav_depot_locator) {
            fragment = new DepotLocatorFragment();
            title = getString(R.string.depot_locator_fragment);
        } else if (id == R.id.nav_education) {
            fragment = new EducationFragment();
            title = getString(R.string.education_fragment);
        } else if (id == R.id.nav_news_feed) {
            fragment = new NewsFeedFragment();
            title = getString(R.string.news_feed_fragment);
        } else if (id == R.id.nav_about) {
            fragment = new AboutFragment();
            title = getString(R.string.about_fragment);
        } else if (id == R.id.nav_become_a_donor) {
            fragment = new BecomeDonorFragment();
            title = getString(R.string.become_donor_fragment);
        }

        switchFragment(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A method to switch between fragments
     * @param title the title of the fragment to switch to
     */
    public void switchFragment(String title)
    {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * Overridden onResume method
     */
    @Override
    public void onResume()
    {
        super.onResume();

        if (prefs.getBoolean("first_run", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs

            FeedTableHelper feedTableHelper = new FeedTableHelper(this.getApplicationContext());
            NewsItem newsItem1 = new NewsItem("Milk Needed", "We need milk due to excessive demand.",
                    "08-06-2016", "www.milkmatters.org");
            NewsItem newsItem2 = new NewsItem("Baby Timmy Saved", "Baby Timmy was saved. He was born 5 weeks premature and could not tolerate formula.",
                    "07-06-2016", "www.milkmatters.org");
            feedTableHelper.createNewsItem(newsItem1);
            feedTableHelper.createNewsItem(newsItem2);
            feedTableHelper.closeDB();

            prefs.edit().putBoolean("first_run", false).commit();
        }

        fragment.onResume();
    }

    /**
     * Overridden onPause method
     */
    @Override
    public void onPause()
    {
        super.onPause();
    }

    /**
     * Method to show the log donation dialog
     */
    public void showLogDonationDialog()
    {
        DialogFragment newFragment = new LogDonationDialogFragment(); // Create the dialog fragment
        Bundle b = new Bundle();
        b.putString("title", "Log Donation");
        b.putString("hint", "Quantity (ml)");
        b.putString("positiveButton", "Log Donation");
        b.putString("negativeButton", "Cancel");
        newFragment.setArguments(b);
        newFragment.show(getSupportFragmentManager(), "confirmRename");
    }

    /**
     * Overridden onPositiveClick
     * @param dialog the log donation dialog
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    /**
     * Overridden onNegativeClick
     * @param dialog the log donation dialog
     */
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    /**
     * Overridden onReturnValue method for the log donation dialog.
     * Logs the donation in the donations table.
     * @param quantity the quantity donated in ml
     * @param date the date of the donation
     */
    @Override
    public void onReturnValue(int quantity, String date) {
        // add the donation to the database
        DonationsTableHelper donationsTableHelper =
                new DonationsTableHelper(this.getApplicationContext());
        Donation donation = new Donation(date, quantity);
        donationsTableHelper.createDonation(donation);
        donationsTableHelper.closeDB();

        // display a message
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)
                findViewById(R.id.coordinator_layout);
        Snackbar.make(coordinatorLayout, "Logged your donation.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        this.onResume();
    }

    /**
     * Overridden onFragmentInteractionMethod
     * Switches to the Become a Donor Fragment
     * @param uri the URI
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
        fragment = null;
        fragment = new BecomeDonorFragment();
        String title = getString(R.string.become_donor_fragment);
        this.switchFragment(title);
    }
}
