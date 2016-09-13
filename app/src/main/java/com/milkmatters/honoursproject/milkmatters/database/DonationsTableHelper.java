package com.milkmatters.honoursproject.milkmatters.database;

import com.milkmatters.honoursproject.milkmatters.model.Donation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        values.put(KEY_DATE, donation.getDate());
        values.put(KEY_QUANTITY, donation.getQuantity());

        // insert row
        long donation_id = db.insert(TABLE_DONATIONS, null, values);

        return donation_id;
    }

    /**
     * Get all donations that have been logged today
     *
     * @return a list of donations
     */
    public ArrayList<Donation> getAllDonationsLoggedToday() {
        ArrayList<Donation> donations = new ArrayList<Donation>();

        String selectQuery = "SELECT * FROM " + TABLE_DONATIONS + " WHERE " +
                KEY_DATE + " = ? ORDER BY " + KEY_DATE + " DESC, " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[]{getDate()});

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
     * Get all donations that have been logged this month
     *
     * @return a list of donations
     */
    public ArrayList<Donation> getAllDonationsLoggedThisMonth() {
        ArrayList<Donation> donations = new ArrayList<Donation>();
        String startDate = getDate().substring(0, 8) + "01";
        String endDate = getDate().substring(0, 8) + "31";

        String selectQuery = "SELECT * FROM " + TABLE_DONATIONS + " WHERE " +
                KEY_DATE + " BETWEEN ? AND ? ORDER BY " + KEY_DATE + " DESC, " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[]{startDate, endDate});

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
}
