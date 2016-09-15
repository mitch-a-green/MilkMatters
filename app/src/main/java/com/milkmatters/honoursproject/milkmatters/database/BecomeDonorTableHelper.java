package com.milkmatters.honoursproject.milkmatters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.milkmatters.honoursproject.milkmatters.model.Question;

import java.util.ArrayList;

/**
 * Database helper for the become a donor quiz questions
 */
public class BecomeDonorTableHelper extends DatabaseHelper{
    /**
     * Constructor for the Become a donor table helper class
     *
     * @param context the context
     */
    public BecomeDonorTableHelper(Context context) {
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
     * Create a Question item
     *
     * @param quest the news question
     * @return the ID of the created question
     */
    public long addQuestion(Question quest) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());

        // insert row
        long BecomeDonorItemID = db.insert(TABLE_BECOME_DONOR, null, values);

        return BecomeDonorItemID;
    }

    /**
     * Get all feed items
     *
     * @return a list of feed items
     */
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> quesList = new ArrayList<Question>();

        String selectQuery = "SELECT * FROM " + TABLE_BECOME_DONOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all the rows and adding to the list
        if (c.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(c.getInt(c.getColumnIndex(KEY_ID)));
                quest.setQUESTION(c.getString(c.getColumnIndex(KEY_QUES)));
                quest.setANSWER(c.getString(c.getColumnIndex(KEY_ANSWER)));
                quest.setOPTA(c.getString(c.getColumnIndex(KEY_OPTA)));
                quest.setOPTB(c.getString(c.getColumnIndex(KEY_OPTB)));
                quest.setOPTC(c.getString(c.getColumnIndex(KEY_OPTC)));
                quesList.add(quest);
            } while (c.moveToNext());
        }
        // return quest list
        return quesList;
    }
}
