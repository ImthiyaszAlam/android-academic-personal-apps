package com.example.landsurvey;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PropertyDao {

    @Insert
    void insert(Property property);


    @Query("SELECT * FROM property_table ORDER BY id ASC")
    LiveData<List<Property>> getAllProperties();

}
