package com.milkmatters.honoursproject.milkmatters.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
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
import android.widget.Button;
import android.widget.Toast;

import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.adapters.FeedAdapter;
import com.milkmatters.honoursproject.milkmatters.database.BecomeDonorTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.DepotsTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.DonationsTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.FeedTableHelper;
import com.milkmatters.honoursproject.milkmatters.dialogs.LogDonationDialogFragment;
import com.milkmatters.honoursproject.milkmatters.dialogs.ShowMessageDialogFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.AboutFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.BecomeDonorFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.DepotLocatorFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.DepotLocatorLoginFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.DonationTrackingFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.EducationFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.HomeFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.NewsFeedFragment;
import com.milkmatters.honoursproject.milkmatters.fragments.ResultFragment;
import com.milkmatters.honoursproject.milkmatters.helper.PopulateDatabase;
import com.milkmatters.honoursproject.milkmatters.model.Depot;
import com.milkmatters.honoursproject.milkmatters.model.Donation;
import com.milkmatters.honoursproject.milkmatters.model.EventItem;
import com.milkmatters.honoursproject.milkmatters.model.NewsItem;
import com.milkmatters.honoursproject.milkmatters.model.Question;

/**
 * Main activity.
 * Switches between various fragments, and controls the navigation drawer and
 * overflow menu options.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LogDonationDialogFragment.NoticeDialogListener,
        ShowMessageDialogFragment.NoticeDialogListener,
        AboutFragment.OnFragmentInteractionListener,
        BecomeDonorFragment.OnFormCompleteListener,
        DepotLocatorLoginFragment.OnLoginListener{
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

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if the user is logging a donation for the first time
                if (prefs.getBoolean("first_log", true)) {
                    prefs.edit().putBoolean("first_log", false).commit();
                    showDisclaimer();
                }
                else {
                    showLogDonationDialog();
                }
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
            if (!fragment.getTag().equals(getString(R.string.app_name)))
            {
                fragment = new HomeFragment();
                String title = getString(R.string.app_name);
                switchFragment(title);
            }
            else {
                super.onBackPressed();
            }
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
            // if the user is logging a donation for the first time
            if (prefs.getBoolean("first_log", true)) {
                prefs.edit().putBoolean("first_log", false).commit();
                showDisclaimer();
            }
            else {
                showLogDonationDialog();
            }
            return true;
        }

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
            // if the user has not authenticated herself before
            if (prefs.getBoolean("authenticated", false) == false) {
                fragment = new DepotLocatorLoginFragment();
                title = getString(R.string.depot_locator_fragment);
            }
            else {
                fragment = new DepotLocatorFragment();
                title = getString(R.string.depot_locator_fragment);
            }
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

        // if the app is running for the first time
        if (prefs.getBoolean("first_run", true)) {
            // populate the database with news feed items, depots and questions for the quiz
            PopulateDatabase populateDatabase = new PopulateDatabase(this.getApplicationContext());
            prefs.edit().putBoolean("first_run", false).commit();
            DialogFragment dialog = new ShowMessageDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", "Welcome to the Milk Matters Mobile Application!");
            args.putString("message", "Did you know that 50ml of breast milk is sufficient to sustain a 1kg premature baby for 24 hours?\n\nWe use this statistic in the app's Donation Tracker to calculate approximately how many babies you have fed for a day.");
            args.putString("button", "OK, Got it!");
            args.putBoolean("logDonation", false);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "welcome");
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
     * Method to show the disclaimer in a dialog
     */
    public void showDisclaimer()
    {
        DialogFragment dialog = new ShowMessageDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Disclaimer");
        args.putString("message", "The information generated in, and stored by, this application is solely for your own use.\n\nWe will not automatically send any of your information to Milk Matters. As such, records of the breast milk you have donated according to this app may differ from Milk Matters' official records.");
        args.putString("button", "OK, Got it!");
        args.putBoolean("logDonation", true);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "disclaimer");
    }

    /**
     * Method to show the log donation dialog
     */
    public void showLogDonationDialog()
    {
        DialogFragment newFragment = new LogDonationDialogFragment(); // Create the dialog fragment
        Bundle b = new Bundle();
        b.putString("title", "Record Donation");
        b.putString("hint", "Quantity (ml)");
        b.putString("positiveButton", "Record Donation");
        b.putString("negativeButton", "Cancel");
        newFragment.setArguments(b);
        newFragment.show(getSupportFragmentManager(), "confirmRename");
    }

    /**
     * Overridden onDialogClose method for the show message dialog
     * If the dialog was used to display the disclaimer, then open the log donation dialog.
     * @param logDonation a boolean indicating whether the dialog was used to display the disclaimer
     */
    @Override
    public void onDialogClose(boolean logDonation) {
        if (logDonation)
            showLogDonationDialog();
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
        Snackbar.make(coordinatorLayout, "Your donation has been recorded.", Snackbar.LENGTH_LONG)
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

    /**
     * Overridden OnFormComplete method.
     * Callback from the BecomeDonorFragment
     * Launches the ResultFragment, which displays the user's quiz result.
     * @param score the user's quiz score
     */
    @Override
    public void onFormComplete(int score) {

        fragment = null;
        fragment = ResultFragment.newInstance(score);
        String title = getString(R.string.become_donor_fragment);
        this.switchFragment(title);
    }

    /**
     * Callback method for the depot locator login fragment.
     * Switches to the actual depot locator fragment.
     */
    @Override
    public void onLogin() {
        prefs.edit().putBoolean("authenticated", true).commit();
        fragment = null;
        fragment = DepotLocatorFragment.newInstance();
        String title = getString(R.string.depot_locator_fragment);
        this.switchFragment(title);
    }

    /**
     * Overridden onActivityResult method.
     * Calls the onActivityResult callback method in the depot locator fragment.
     * Should build the Google API client and add the location layer,
     * once the user has either turned on or not turned on the device's location.
     * Display a warning message if the user does not turn on the device's location.
     * @param requestCode the request code
     * @param resultCode the result code
     * @param data the data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        DepotLocatorFragment fragment = (DepotLocatorFragment) getSupportFragmentManager().findFragmentByTag("Depot Locator");
        if ((fragment != null) && (fragment.getClass().getName().toString().equals(DepotLocatorFragment.class.getName())))
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
