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
import com.milkmatters.honoursproject.milkmatters.model.EventItem;
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
            NewsItem newsItem2 = new NewsItem("Baby Timmy Saved", "Baby Timmy was saved. He was born 5 weeks premature and could not tolerate formula. His mother was very happy and excited. Thank you to all the moms who helped contribute and gave him a second chance at life.",
                    "07-06-2016", "www.milkmatters.org");
            NewsItem newsItem3 = new NewsItem("Incredible Mom-And-Baby Photos Show The Beauty Of The ‘Breast Crawl’", "When Jessica, 32, sat down to make her birth plan before her daughter’s birth last May, she had one priority (in addition, of course, to everyone’s health and safety): The “breast crawl.”\n" +
                    "\n" +
                    "Newborns are hardwired to breastfeed, and the breast crawl is the practice of letting them use those instincts to slowly inch their way to their mother’s nipple to nurse for the very first time. Immediately after delivery, the baby is placed on the mother’s abdomen and given time to scoot up to her breasts, sniffing along the way. (BreastCrawl.org says says it generally takes between 30 and 60 minutes, after which babies who haven’t arrived at the breast yet may need a little help.) Proponents say that giving babies that first hour of life to make the journey helps promote early initiation of breastfeeding ― which has clear benefits ― and eases moms and babies through one of life’s biggest transitions. \n" +
                    "\n" +
                    "“It’s not something I’d heard about before,” says Jessica, who went to a practice with two midwives and two OB-GYNs ― all of whom were supportive of the idea. “When I read about it, I thought it sounded magical.” \n" +
                    "\n" +
                    "Fortunately, Jessica and her husband hired Austin-based breastfeeding and birth photographer Leilani Rogers to document the birth, and she captured the crawl inch-by-inch. “To witness the connection baby made with mom over the span of that 48 minutes was fascinating,” Rogers said. “She didn’t fuss, just slowly but surely made her way to the nipple and latched on once she was in just the right position.”\n" +
                    "\n" +
                    "Magical, indeed!",
                    "08-19-2016", "http://www.huffingtonpost.com/entry/incredible-mom-and-baby-photos-show-the-beauty-of-the-breast-crawl_us_57b73741e4b03d5136881944");
            NewsItem newsItem4 = new NewsItem("Breast-Fed Babies May Have Longer Telomeres, Tied to Longevity", "Breast-fed babies have healthier immune systems, score higher on I.Q. tests and may be less prone to obesity than other babies.\n" +
                    "\n" +
                    "Now new research reveals another possible difference in breast-fed babies: They may have longer telomeres.\n" +
                    "\n" +
                    "Telomeres are stretches of DNA that cap the ends of chromosomes and protect the genes from damage. They’re often compared to the plastic tips at the end of shoelaces that prevent laces from unraveling. Telomeres shorten as cells divide and as people age, and shorter telomeres in adulthood are associated with chronic diseases like diabetes. Some studies have linked longer telomeres to longevity.\n" +
                    "\n" +
                    "The new study, published in The American Journal of Clinical Nutrition, is a hopeful one, its authors say, because it suggests telomere length in early life may be malleable. The researchers, who have been following a group of children since birth, measured the telomeres of 4- and 5-year-olds, and discovered that children who consumed only breast milk for the first four to six weeks of life had significantly longer telomeres than those who were given formula, juices, teas or sugar water.\n" +
                    "\n" +
                    "Drinking fruit juice every day during the toddler years and a lot of soda at age 4 was also associated with short telomeres.\n" +
                    "\n" +
                    "Socioeconomic differences among mothers can muddy findings about breast-feeding because the practice is more common among more educated mothers. However, this group of children was fairly homogeneous. All of them were born in San Francisco to low-income Latina mothers, most of whom qualified for a government food program.\n" +
                    "\n" +
                    "“This adds to the burgeoning evidence that when we make it easier for mothers to breast-feed, we make mothers and babies healthier,” said Dr. Alison M. Stuebe, an expert on breast-feeding who is the medical director of lactation services at UNC Health Care in Chapel Hill, N.C., and was not involved in the study. “The more we learn about breast milk, the more it’s clear it is pretty awesome and does a lot of cool stuff.”\n" +
                    "\n" +
                    "The study did not establish whether or not breast-feeding enhanced telomere length. It may be that babies born with longer telomeres are more likely to succeed at breast-feeding. A major drawback of the research was that telomere length was only measured at one point in time, when the children were 4 or 5 years old. There was no data on telomere length at birth or during the first few months of life.\n" +
                    "\n" +
                    "“We don’t have a baseline to see if these kids were different when they came out,” Dr. Stuebe said. “It could be that really healthy babies can latch on and feed well, and they already had longer telomeres. It could be successful breast-feeding is a sign of a more robust kid.”\n" +
                    "\n" +
                    "The researchers were following children who were part of the Hispanic Eating and Nutrition study, a group of 201 babies born in San Francisco to Latina mothers recruited in 2006 and 2007 while they were still pregnant. The goal of the research was to see how early life experiences, eating habits and environment influence growth and the development of cardiac and metabolic diseases as children grow.\n" +
                    "\n" +
                    "Researchers measured the babies’ weight and height when the children were born. At four to six weeks of age, they gathered detailed information about feeding practices, including whether the baby had breast milk and for how long, and whether other milk substitutes were used, such as formula, sugar-sweetened beverages, juices, flavored milks and waters. Information was also gathered about the mothers.\n" +
                    "\n" +
                    "Children were considered to have been exclusively breast-fed at 4 to 6 weeks of age if they received nothing but breast milk, as well as medicine or vitamins.\n" +
                    "\n" +
                    "When the children were 4 and 5 years old, researchers took blood spot samples that could be used to measure the telomeres in leukocytes, which are white blood cells, from 121 children. They found that children who were being exclusively breast-fed at 4 to 6 weeks of age had telomeres that were about 5 percent longer, or approximately 350 base pairs longer, than children who were not.\n" +
                    "\n" +
                    "The new findings may help explain the trove of benefits that accrue from breast-feeding, said Janet M. Wojcicki, an associate professor of pediatrics and epidemiology at the University of California, San Francisco, and the paper’s lead author.\n" +
                    "\n" +
                    "“What’s remarkable about breast-feeding is its ability to improve health across organ systems,” Dr. Wojcicki said. “Telomere biology is so central to the processes of aging, human health and disease, and may be the link to how breast-feeding impacts human health on so many levels.”\n" +
                    "\n" +
                    "There are several possible explanations for the correlation between breast-feeding and longer telomeres. Breast milk contains anti-inflammatory compounds, which may confer a protective effect on telomeres. It’s also possible that parents who exclusively breast-feed their babies are more scrupulous about a healthy diet generally.\n" +
                    "\n" +
                    "Yet another possibility is that breast-feeding is a proxy for the quality of mother-child attachment and bonding, said Dr. Pathik D. Wadhwa, who was not involved in the research but studies early-life determinants of health at the University of California, Irvine School of Medicine. “We know from studies looking at telomere length changes in babies who came from orphanages that the quality of the attachment and interaction, and more generally the quality of care that babies receive, plays a role in the rate of change in telomere length,” he said.\n" +
                    "\n" +
                    "When children are exposed to adversity, neglect or violence at an early age, “psychological stress creates a biochemical environment of elevated free radicals, inflammation and stress hormones that can be harmful to telomeres,” said Elissa Epel, one of the authors of the study who is a professor at the University of California, San Francisco, and director of the Aging, Metabolism and Emotions Lab.\n" +
                    "\n" +
                    "“The idea that breast-feeding may be protective for telomeres is heartening because we don’t know much about what’s going to help protect them in children, besides avoiding toxic stress. And boy, do we want to know,” Dr. Epel said.\n" +
                    "\n" +
                    "Although genes can’t be changed, Dr. Epel said, “This is part of the genome that appears to be at least partly under personal control.”",
                    "04-08-2016", "http://well.blogs.nytimes.com/2016/08/04/breast-fed-babies-may-have-longer-telomeres-tied-to-longevity/?smid=fb-share&_r=1");
            NewsItem newsItem5 = new NewsItem("Wayde donates R500K for premature babies", "Cape Town - Born at 29 weeks and weighing just over 1kg, Wayde van Niekerk’s parents never thought he would survive, let alone become a successful athlete.\n" +
                    "\n" +
                    "After his mother, Odessa Swarts, gave birth to him at Tygerberg 23 years ago, the South African track and field champion and 400m sprinter would later be treated at Groote Schuur Hospital’s neonatal unit, where he spent several weeks in an incubator.\n" + "“From what my mother tells me it was apparently a very difficult and emotional journey to have a premature baby. There were days where she was not sure whether I was going to make it the next day. That’s how sick I was,” he said.\n" +
                    "\n" +
                    "On Thursday, Van Niekerk, currently the fourth fastest person in history and the first athlete to run the 100m in under 10 seconds, and the 200m in under 20 seconds, donated half-a-million rand to the Newborns Groote Schuur Trust to aid the upgrade of that hospital’s neonatal unit.\n" +
                    "\n" +
                    "Through the Trust, the hospital is fundraising money to expand the unit, which is currently battling to cope due the incidence of pre-term birth in the province.\n" +
                    "\n" +
                    "The unit, which was built in the 1970s, is overcrowded and unable to meet the demand of caring for more than 3 000 babies a year - mostly premature babies.\n" +
                    "\n" +
                    "During the handover of the cheque at the Century City Convention Centre on Thursday, where he was also named the face of an ICT company, T-systems, Van Niekerk said through the donation he hoped to make his mother proud.\n" +
                    "\n" +
                    "“My mother is very passionate about premature babies since she cared for one herself. Through this donation I’m showing my support to her causes, and to make her proud. I’m my mother’s seed and I want to help her in every manner I can. But more importantly, I’m hoping to help thousands of premature babies who go through Groote Schuur Hospital’s neonatal unit.\n" +
                    "\n" +
                    "“As a small premature baby, I was given a fighting chance to survive, and so it’s very important to me to help give other preemies the best chance at life.”\n" +
                    "\n" +
                    "Dr Lloyd Tooke, senior neonatologist at Groote Schuur Hospital, said Van Niekerk’s success as an athlete had shown that premature babies could also achieve against the odds. “He is truly an inspiration not only for families of premature infants but for all of us.”\n" +
                    "\n" +
                    "Having raised more than R5 million so far, the hospital’s chief executive Bhavna Patel said the latest amount would be used to buy modern equipment for the upgraded unit.",
                    "20-05-2016", "http://www.iol.co.za/sport/athletics/pics-wayde-donates-r500k-for-premature-babies-2023890");
            NewsItem newsItem6 = new NewsItem("Aden finds out more about a breast milk bank called Milk Matters #IHeartBreakfast", "Aden Thomas chats to Jenny Wright from an organisation called Milk Matters. They're a community-based breast milk bank that pasteurises and distributes donations of screened breast milk from healthy donors to premature, ill and vulnerable babies whose own mothers cannot supply the breast milk to meet their baby’s needs. Milk Matters also facilitates the setting up of milk banks in health institutions.",
                    "01-09-2016", "https://soundcloud.com/heartbreakfast-1/aden-finds-out-more-about-a-breast-milk-bank-called-milk-matters-heartbreakfast");
            EventItem eventItem1 = new EventItem("Milk Matters Workshop", "Two honours students will be demonstrating the Milk Matters App",
                    "07-06-2016", "www.milkmatters.org", "14-09-2016", "10:00", "Rustenburg Square");
            EventItem eventItem2 = new EventItem("March for Milk", "We still have spaces left for this years March for Milk!!\nCome and enjoy a 2.5km or 5km walk / run for the whole family in aid of Milk Matters, the Breastmilk Bank.\nHelp raise funds and awareness and ensure more babies have access to life-saving donor milk.",
                    "02-03-2016", "https://www.facebook.com/events/696954203740860/?active_tab=highlights", "12-03-2016", "9:00", "Sea Point Beach Front");
            EventItem eventItem3 = new EventItem("Milk Matters Open Day", "The Milk Matters team would like to invite you to an open day at the Milk Matters Head Quarters. We wish to demonstrate how crucial your breastmilk donations are to the lives of premature, vulnerable infants by giving you a tour of our Neonatal department and a get together over a cup of tea.\nBe sure to book your preferred day and time as we will be taking you round in small groups at half hour time slots between 10.30 and 12.30.\nWe hope you can make it & look forward to seeing you.\nRSVP: info@milkmatters.org\n021 659 5599 by the 1st July",
                    "10-07-2012", "https://www.facebook.com/events/207957505994410/", "23-07-2012", "10:30", "Mowbray Maternity Hospital Education Department");
            EventItem eventItem4 = new EventItem("Milk Matters Open Day", "The Milk Matters team would like to invite you to an open day at the Milk Matters Head Quarters. We wish to demonstrate how crucial your breastmilk donations are to the lives of premature, vulnerable infants by giving you a tour of our Neonatal department and a get together over a cup of tea.\nBe sure to book your preferred day and time as we will be taking you round in small groups at half hour time slots between 10.30 and 12.30.\nWe hope you can make it & look forward to seeing you.\nRSVP: info@milkmatters.org\n021 659 5599 by the 1st July",
                    "10-07-2012", "https://www.facebook.com/events/207957505994410/", "25-07-2012", "10:30", "Mowbray Maternity Hospital Education Department");
            feedTableHelper.createNewsItem(newsItem1);
            feedTableHelper.createEventItem(eventItem1);
            feedTableHelper.createNewsItem(newsItem2);
            feedTableHelper.createNewsItem(newsItem3);
            feedTableHelper.createNewsItem(newsItem4);
            feedTableHelper.createNewsItem(newsItem5);
            feedTableHelper.createNewsItem(newsItem6);
            feedTableHelper.createEventItem(eventItem2);
            feedTableHelper.createEventItem(eventItem3);
            feedTableHelper.createEventItem(eventItem4);
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
