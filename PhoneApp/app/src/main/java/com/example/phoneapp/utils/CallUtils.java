package com.example.phoneapp.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.phoneapp.database.CallDBHelper;
import com.example.phoneapp.database.DBHelper;

// Update count based on call types
public class CallUtils {
    // Updates the call count for a specific phone number and call type
    public static void updateCallCount(CallDBHelper callDBHelper, String phoneNumber, String callType) {
        int currentCount = getCurrentCountForType(callDBHelper, phoneNumber, callType);
        Log.d("CallUtils", "Current count before update for phone number: " + phoneNumber + ", Type: " + callType + ", Current Count: " + currentCount);

        callDBHelper.insertOrUpdateCallData(phoneNumber, callType, currentCount + 1);
        Log.d("CallUtils", "Updated count for phone number: " + phoneNumber + ", Type: " + callType + ", New Count: " + (currentCount + 1));

    }


    private static int getCurrentCountForType(CallDBHelper callDBHelper, String phoneNumber, String callType) {
        int count = 0;
        try (Cursor cursor = callDBHelper.getCallDataForNumber(phoneNumber)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(CallDBHelper.COLUMN_TYPE));
                    if (type.equals(callType)) {
                        count = cursor.getInt(cursor.getColumnIndexOrThrow(CallDBHelper.COLUMN_COUNT));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("CallUtils", "Current count for " + phoneNumber + " with type " + callType + " is " + count);
        return count;
    }


    public static boolean isNumberInUserDatabase(DBHelper dbHelper, String phoneNumber) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;
        try {
            cursor = database.query(
                    DBHelper.TABLE_NAME,
                    new String[]{DBHelper.COLUMN_MOBILE},
                    DBHelper.COLUMN_MOBILE + "=?",
                    new String[]{phoneNumber},
                    null,
                    null,
                    null
            );
            exists = cursor.getCount() > 0;
            Log.d("CallLogReceiver","Phone number"+phoneNumber+ "exists in database"+exists);
        } catch (Exception e) {
            // Handle potential exceptions
            Log.e("CallLogReceiver", "Error checking number in database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return exists;
    }
}
