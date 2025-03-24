package com.example.phoneapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.phoneapp.databinding.ActivityMainBinding;
import com.example.phoneapp.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_EMAIL = "email";
    Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_MOBILE + " TEXT UNIQUE , " +
                COLUMN_EMAIL + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addUser(String name, String mobile, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_MOBILE, mobile);
        contentValues.put(COLUMN_EMAIL, email);
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }


    public List<UserModel> getAllUsers() {
        List<UserModel> userLists = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID+" DESC ");
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String mobile = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOBILE));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));

                UserModel user = new UserModel(id, name, mobile, email);
                userLists.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return userLists;
    }

    public void updateUser(int id, String name, String mobile, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_MOBILE, mobile);
        contentValues.put(COLUMN_EMAIL, email);
        database.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        database.close();
        Toast.makeText(context, "updated successfully", Toast.LENGTH_SHORT).show();


    }

    public void deleteUser(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        database.close();
        Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
    }
}
