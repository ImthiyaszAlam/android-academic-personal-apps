package com.imthiyas.contactme.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.imthiyas.contactme.model.FormData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_data.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_NAME = "submissions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_JSON_DATA = "json_data";  // Storing JSON data in a column

    // SQL query to create the table
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_JSON_DATA + " TEXT);";  // Storing JSON data as text

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertSubmission(JSONObject jsonData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(COLUMN_JSON_DATA, jsonData.toString());  // Insert JSON data as string
        } catch (Exception e) {
            e.printStackTrace();
            return -1;  // Failure to insert
        }

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result;  // Returns row ID, or -1 if insert fails
    }

    @SuppressLint("Range")
    public List<FormData> getAllSubmissions() {
        List<FormData> submissions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    String jsonDataString = cursor.getString(cursor.getColumnIndex(COLUMN_JSON_DATA));
                    try {
                        JSONObject jsonObject = new JSONObject(jsonDataString);
                        FormData submission = new FormData();
                        submission.setGenderId(jsonObject.getInt("Q1"));
                        submission.setAge(jsonObject.getInt("Q2"));
                        submission.setSelfiePath(jsonObject.getString("Q3"));
                        submission.setAudioPath(jsonObject.getString("recording"));
                        submission.setGpsCoordinates(jsonObject.getString("gps"));
                        submission.setSubmitTime(jsonObject.getString("submit_time"));
                        submissions.add(submission);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return submissions;
    }
}
