package com.example.phoneapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CallDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "call_data.db";
    public static final String TABLE_NAME = "Calls";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    public static final String COLUMN_TYPE = "callType";
    public static final String COLUMN_COUNT = "count";
    private static final int DATABASE_VERSION = 1;

    public CallDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating the Calls table with columns for phone number, call type, and count
        String CREATE_CALLS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PHONE_NUMBER + " TEXT, "
                + COLUMN_TYPE + " TEXT, "
                + COLUMN_COUNT + " INTEGER"
                + ");"; // Corrected the SQL syntax here

        db.execSQL(CREATE_CALLS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to get call data for a specific phone number
    public Cursor getCallDataForNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query the database for all entries matching the phone number
        return db.query(TABLE_NAME, null, COLUMN_PHONE_NUMBER + "=?", new String[]{phoneNumber}, null, null, null);
    }

    // Method to insert or update call data
    public void insertOrUpdateCallData(String phoneNumber, String callType, int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_TYPE, callType);
        values.put(COLUMN_COUNT, count);

        // Update if exists, otherwise insert
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_PHONE_NUMBER + "=? AND " + COLUMN_TYPE + "=?", new String[]{phoneNumber, callType});
        if (rowsAffected == 0) {
            long newRowId = db.insert(TABLE_NAME, null, values);
            if (newRowId == -1) {
                Log.e("CallDBHelper", "Failed to insert new row"); // Log error if insertion fails
            } else {
                Log.d("CallDBHelper", "Inserted new row with ID: " + newRowId); // Log success
            }
        } else {
            // Row was updated successfully
            Log.d("CallDBHelper", "Updated existing row. Rows affected: " + rowsAffected);
        }
        db.close();

    }
}

