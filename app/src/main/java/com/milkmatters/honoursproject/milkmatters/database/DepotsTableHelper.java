package com.milkmatters.honoursproject.milkmatters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.milkmatters.honoursproject.milkmatters.model.Depot;
import com.milkmatters.honoursproject.milkmatters.model.Donation;
import com.milkmatters.honoursproject.milkmatters.model.EventItem;
import com.milkmatters.honoursproject.milkmatters.model.FeedItem;
import com.milkmatters.honoursproject.milkmatters.model.NewsItem;

import java.util.ArrayList;

/**
 * Database helper class for the depots table.
 */
public class DepotsTableHelper extends DatabaseHelper {
    /**
     * Constructor for the DepotsTableHelper class
     *
     * @param context the context
     */
    public DepotsTableHelper(Context context) {
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
     * Create a depot
     *
     * @param depot the depot
     * @return the ID of the created depot
     */
    public long createDepot(Depot depot) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, depot.getName());
        values.put(KEY_HOURS, depot.getHours());
        values.put(KEY_CONTACT_NAME, depot.getContactName());
        values.put(KEY_CONTACT_DETAILS, depot.getContactDetails());
        values.put(KEY_EXTRA, depot.getExtra());
        values.put(KEY_LATITUDE, depot.getLocation().getLatitude());
        values.put(KEY_LONGITUDE, depot.getLocation().getLongitude());

        // insert row
        long depotID = db.insert(TABLE_DEPOTS, null, values);

        return depotID;
    }

    /**
     * Get all depots
     *
     * @return a list of depots
     */
    public ArrayList<Depot> getAllDepots() {
        ArrayList<Depot> depots = new ArrayList<Depot>();

        String selectQuery = "SELECT * FROM " + TABLE_DEPOTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all the rows and adding to the list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                String hours = c.getString(c.getColumnIndex(KEY_HOURS));
                String contactName = c.getString(c.getColumnIndex(KEY_CONTACT_NAME));
                String contactDetails = c.getString(c.getColumnIndex(KEY_CONTACT_DETAILS));
                String extra = c.getString(c.getColumnIndex(KEY_EXTRA));
                double latitude = c.getDouble(c.getColumnIndex(KEY_LATITUDE));
                double longitude = c.getDouble(c.getColumnIndex(KEY_LONGITUDE));
                Location location = new Location("");
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                Depot depot = new Depot(name, hours, contactName, contactDetails, extra,
                        location, id);
                depots.add(depot);

            } while (c.moveToNext());
        }

        return depots;
    }
}