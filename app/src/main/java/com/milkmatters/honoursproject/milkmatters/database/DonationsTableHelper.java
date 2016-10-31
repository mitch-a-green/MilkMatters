package com.milkmatters.honoursproject.milkmatters.database;

import com.milkmatters.honoursproject.milkmatters.model.Donation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Database helper class for the donations table
 */
public class DonationsTableHelper extends DatabaseHelper
{
    /**
     * Get all donations that have been logged
     *
     * @return a list of donations
     */
    public ArrayList<Donation> getAllDonations() {
        ArrayList<Donation> donations = new ArrayList<Donation>();

        String selectQuery = "SELECT * FROM " + TABLE_DONATIONS + " ORDER BY " + KEY_DATE +
                " DESC, " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all the rows and adding to the list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String dateAdded = c.getString(c.getColumnIndex(KEY_DATE));
                int quantity = c.getInt(c.getColumnIndex(KEY_QUANTITY));
                Donation donation = new Donation(dateAdded, id, quantity);

                // adding to the donations list
                donations.add(donation);
            } while (c.moveToNext());
        }

        return donations;
    }

    /**
     * Constructor for the DonationsTableHelper class
     *
     * @param context the context
     */
    public DonationsTableHelper(Context context) {
        super(context);
    }

    /**
     * Overridden onCreate method
     *
     * @param db a SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    /**
     * Overridden onUpgrade method
     *
     * @param db         a SQLite database
     * @param oldVersion the old version number
     * @param newVersion the new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Create/log a donation
     *
     * @param donation the donation
     * @return the ID of the created donation
     */
    public long createDonation(Donation donation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, convertToCorrectDateFormat(donation.getSQLDate()));
        values.put(KEY_QUANTITY, donation.getQuantity());

        // insert row
        long donation_id = db.insert(TABLE_DONATIONS, null, values);

        return donation_id;
    }

    /**
     * Get all donations that have been logged on a given date
     * @param date the date in question
     * @return a list of donations
     */
    public ArrayList<Donation> getAllDonationsLoggedOnDate(String date) {
        ArrayList<Donation> donations = new ArrayList<Donation>();

        String selectQuery = "SELECT * FROM " + TABLE_DONATIONS + " WHERE " +
                KEY_DATE + " = ? ORDER BY date(" + KEY_DATE + ") DESC, " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[]{convertToCorrectDateFormat(date)});

        // looping through all the rows and adding to the list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String dateAdded = c.getString(c.getColumnIndex(KEY_DATE));
                int quantity = c.getInt(c.getColumnIndex(KEY_QUANTITY));
                Donation donation = new Donation(convertToCorrectDateFormat(dateAdded), id, quantity);

                // adding to the donations list
                donations.add(donation);
            } while (c.moveToNext());
        }

        return donations;
    }

    /**
     * Get all donations that have been logged this month
     *
     * @return a list of donations
     */
    public ArrayList<Donation> getAllDonationsLoggedThisMonth() {
        ArrayList<Donation> donations = new ArrayList<Donation>();
        String startDate = getDate().substring(0, 8) + "01";
        String endDate = getDate().substring(0, 8) + "31";

        String selectQuery = "SELECT * FROM " + TABLE_DONATIONS + " WHERE " +
                KEY_DATE + " BETWEEN ? AND ? ORDER BY date(" + KEY_DATE + ") DESC, " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[]{startDate, endDate});

        // looping through all the rows and adding to the list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String dateAdded = c.getString(c.getColumnIndex(KEY_DATE));
                int quantity = c.getInt(c.getColumnIndex(KEY_QUANTITY));
                Donation donation = new Donation(convertToCorrectDateFormat(dateAdded), id, quantity);

                // adding to the donations list
                donations.add(donation);
            } while (c.moveToNext());
        }

        return donations;
    }

    /**
     * Get all dates on which donations were made
     * and the total quantities donated on those days/
     *
     * @return a list of donations
     */
    public ArrayList<Donation> getAllDatesAndTotals() {
        ArrayList<Donation> donations = new ArrayList<Donation>();

        String selectQuery = "SELECT " + KEY_DATE + " AS date, SUM(" +
                KEY_QUANTITY + ") AS total FROM " + TABLE_DONATIONS + " GROUP BY " +
                KEY_DATE + " ORDER BY date(" + KEY_DATE + ") ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all the rows and adding to the list
        if (c.moveToFirst()) {
            do {
                String dateAdded = c.getString(c.getColumnIndex(KEY_DATE));
                int quantity = c.getInt(c.getColumnIndex("total"));
                Donation donation = new Donation(convertToCorrectDateFormat(dateAdded), -1, quantity);

                // adding to the donations list
                donations.add(donation);
            } while (c.moveToNext());
        }

        return donations;
    }

    /**
     * Get the total of all donations that have been logged
     *
     * @return the total of all donations that have been logged
     */
    public int getTotalDonations() {
        int total = 0;

        String selectQuery = "SELECT SUM(" + KEY_QUANTITY + ") AS total FROM " + TABLE_DONATIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // getting the total
        if (c.moveToFirst()) {
            do {
                total = c.getInt(c.getColumnIndex("total"));

            } while (c.moveToNext());
        }

        return total;
    }

    /**
     * Get all the donations grouped by date.
     * Each date corresponds to a list of donations.
     * The first item is the total donation for that date.
     * All subsequent items are individual donations for that date.
     * @return a list of lists of donations.
     */
    public ArrayList<ArrayList<Donation>> getDonationsGroupedByDate() {
        ArrayList<ArrayList<Donation>> allDonations =
                new ArrayList<ArrayList<Donation>>();
        String previousDate = "";
        int counter = 0;

        String selectQuery = "SELECT * FROM " + TABLE_DONATIONS + " ORDER BY date(" + KEY_DATE + ") DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // loop through the results and generate the output list
        if (c.moveToFirst()) {
            do {
                String date = convertToCorrectDateFormat(c.getString(c.getColumnIndex(KEY_DATE)));
                int quantity = c.getInt(c.getColumnIndex(KEY_QUANTITY));
                Donation donation = new Donation(date, quantity);
                Donation donationCopy = new Donation(date, quantity);
                if ((previousDate.equals("")) || previousDate.equals(null)) {
                    allDonations.add(new ArrayList<Donation>());
                    allDonations.get(counter).add(donationCopy);
                }
                else if (date.equals(previousDate))
                    allDonations.get(counter).get(0).increaseDonation(quantity);
                else
                {
                    counter++;
                    allDonations.add(new ArrayList<Donation>());
                    allDonations.get(counter).add(donationCopy);
                }
                allDonations.get(counter).add(donation);
                previousDate = date;

            } while (c.moveToNext());
        }

        return allDonations;
    }

    /**
     * Update a donation
     *
     * @param donation the donation to update
     * @return the donation ID
     */
    public int updateDonation(Donation donation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, donation.getDate());
        values.put(KEY_QUANTITY, donation.getQuantity());

        // updating row
        return db.update(TABLE_DONATIONS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(donation.getID())});
    }

    /**
     * Delete a donation
     *
     * @param donation the donation to delete
     */
    public void deleteDonation(Donation donation) {
        SQLiteDatabase db = this.getWritableDatabase();

        // delete the donation
        db.delete(TABLE_DONATIONS, KEY_ID + " = ?",
                new String[]{String.valueOf(donation.getID())});
    }


    public String convertToCorrectDateFormat(String date)
    {
        String output = "";
        String[] dateArray = date.split("-");

        output = output + dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
        Log.e("output", output);

        return output;
    }
}
