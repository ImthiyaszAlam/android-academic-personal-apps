package com.example.landsurvey;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Update the database version to reflect schema changes
@Database(entities = {Property.class}, version = 3, exportSchema = false)
public abstract class PropertyDatabase extends RoomDatabase {

    public abstract PropertyDao propertyDao();

    private static volatile PropertyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PropertyDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (PropertyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PropertyDatabase.class, "property_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
