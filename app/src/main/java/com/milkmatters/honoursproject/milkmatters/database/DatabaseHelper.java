package com.milkmatters.honoursproject.milkmatters.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

/**
 * Class to interface with the database
 * Contains all common database methods and attributes
 * Created by mitchell on 2016/08/18.
 * @author Mitchell Green
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    protected static final int DATABASE_VERSION = 1;

    // Database Name
    protected static final String DATABASE_NAME = "donationDatabase";

    // Table Name
    protected static final String TABLE_DONATIONS = "donations";

    // Column names
    protected static final String KEY_ID = "id";
    protected static final String KEY_DATE = "date";
    protected static final String KEY_QUANTITY = "quantity";

    // Table Create Statement
    // Transaction table create statement
    protected static final String CREATE_TABLE_DONATIONS = "CREATE TABLE "
            + TABLE_DONATIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DATE + " DATE, " +
            KEY_QUANTITY + " INTEGER" + ");";

    // Data
    private int day;
    private int month;
    private int year;
    private String date;

    /**
     * Constructor for the DatabaseHelper class
     * @param context the context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        refreshDate();
    }

    /**
     * Overridden onCreate method
     * @param db the SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the table
        db.execSQL(CREATE_TABLE_DONATIONS);
    }

    /**
     * Overridden onUpgrade method
     * @param db the SQLite database
     * @param oldVersion the old version number
     * @param newVersion the new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drops older tables on update
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATIONS);

        // create the new tables
        onCreate(db);
    }

    /**
     * Method to close the connection to the database
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * Method to get the current day
     * @return the current day
     */
    public int getDay() {
        return day;
    }

    /**
     * Method to set the current day
     * @param day the current day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Method to get the current month
     * @return the current month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Method to set the current month
     * @param month the current month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Method to get the current year
     * @return the current year
     */
    public int getYear() {
        return year;
    }

    /**
     * Method to set the current year
     * @param year the current year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Method to get the current date
     * @return the current date
     */
    public String getDate() {
        return date;
    }

    /**
     * Method to refresh the current date information
     */
    public void refreshDate() {
        Calendar c = Calendar.getInstance();
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.month = c.get(Calendar.MONTH) + 1;
        this.year = c.get(Calendar.YEAR);
        this.date = String.valueOf(year);
        if (month < 10)
            this.date = this.date + "-0" + String.valueOf(month);
        else
            this.date = this.date + "-" + String.valueOf(month);
        if (day < 10)
            this.date = this.date + "-0" + String.valueOf(day);
        else
            this.date = this.date + "-" + String.valueOf(day);
    }
}
