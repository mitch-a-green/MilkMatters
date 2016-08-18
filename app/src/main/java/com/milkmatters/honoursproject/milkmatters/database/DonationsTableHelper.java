package com.milkmatters.honoursproject.milkmatters.database;

import com.milkmatters.honoursproject.milkmatters.model.Donation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Database helper class for the meters table
 * Created by mitchell on 2016/02/22.
 */
public class DonationsTableHelper extends DatabaseHelper
{
    /**
     * Constructor for the DatabaseHelper class
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
                Donation donation = new Donation(dateAdded, quantity);

                // adding to the transactions list
                donations.add(donation);
            } while (c.moveToNext());
        }

        return donations;
    }
}
