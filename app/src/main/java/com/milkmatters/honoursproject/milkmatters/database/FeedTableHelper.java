package com.milkmatters.honoursproject.milkmatters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.milkmatters.honoursproject.milkmatters.model.Donation;
import com.milkmatters.honoursproject.milkmatters.model.EventItem;
import com.milkmatters.honoursproject.milkmatters.model.FeedItem;
import com.milkmatters.honoursproject.milkmatters.model.NewsItem;

import java.util.ArrayList;

/**
 * Database helper class for the meters table.
 */
public class FeedTableHelper extends DatabaseHelper {
    /**
     * Constructor for the FeedTableHelper class
     *
     * @param context the context
     */
    public FeedTableHelper(Context context) {
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
     * Create a news item
     *
     * @param newsItem the news item
     * @return the ID of the created news item
     */
    public long createNewsItem(NewsItem newsItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP, newsItem.getTimeStamp());
        values.put(KEY_TITLE, newsItem.getTitle());
        values.put(KEY_CONTENT, newsItem.getContent());
        values.put(KEY_HYPERLINK, newsItem.getHyperlink());
        values.put(KEY_DATE, "null");
        values.put(KEY_TIME, "null");
        values.put(KEY_PLACE, "null");
        values.put(KEY_TYPE, "News");

        // insert row
        long newsItemID = db.insert(TABLE_FEED, null, values);

        return newsItemID;
    }

    /**
     * Create a event item
     *
     * @param eventItem the event item
     * @return the ID of the created event item
     */
    public long createEventItem(EventItem eventItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP, eventItem.getTimeStamp());
        values.put(KEY_TITLE, eventItem.getTitle());
        values.put(KEY_CONTENT, eventItem.getContent());
        values.put(KEY_HYPERLINK, eventItem.getHyperlink());
        values.put(KEY_DATE, eventItem.getEventDate());
        values.put(KEY_TIME, eventItem.getEventTime());
        values.put(KEY_PLACE, eventItem.getEventPlace());
        values.put(KEY_TYPE, "Event");

        // insert row
        long eventItemID = db.insert(TABLE_FEED, null, values);

        return eventItemID;
    }

    /**
     * Get all feed items
     *
     * @return a list of feed items
     */
    public ArrayList<FeedItem> getAllFeedItems() {
        ArrayList<FeedItem> feedItems = new ArrayList<FeedItem>();

        String selectQuery = "SELECT * FROM " + TABLE_FEED + " ORDER BY " + KEY_TIMESTAMP +
                " DESC, " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all the rows and adding to the list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String timestamp = c.getString(c.getColumnIndex(KEY_TIMESTAMP));
                String title = c.getString(c.getColumnIndex(KEY_TITLE));
                String content = c.getString(c.getColumnIndex(KEY_CONTENT));
                String hyperlink = c.getString(c.getColumnIndex(KEY_HYPERLINK));
                String type = c.getString(c.getColumnIndex(KEY_TYPE));

                if (type.equals("News"))
                {
                    NewsItem newsItem = new NewsItem(id, title, content, timestamp, hyperlink);
                    feedItems.add(newsItem);  // adding to the list of feed items
                }
                else if (type.equals("Event"))
                {
                    String date = c.getString(c.getColumnIndex(KEY_DATE));
                    String time = c.getString(c.getColumnIndex(KEY_TIME));
                    String place = c.getString(c.getColumnIndex(KEY_PLACE));
                    EventItem eventItem = new EventItem(id, title, content, timestamp, hyperlink,
                            date, time, place);
                    feedItems.add(eventItem);  // adding to the list of feed items
                }

            } while (c.moveToNext());
        }

        return feedItems;
    }
}
