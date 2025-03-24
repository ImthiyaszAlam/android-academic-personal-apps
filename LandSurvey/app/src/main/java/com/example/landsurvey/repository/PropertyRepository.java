package com.example.landsurvey.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.landsurvey.Property;
import com.example.landsurvey.PropertyDao;
import com.example.landsurvey.PropertyDatabase;

import java.util.List;

public class PropertyRepository {

    private PropertyDao propertyDao;
    private LiveData<List<Property>> allProperties;

    public PropertyRepository(Application application) {
        PropertyDatabase database = PropertyDatabase.getInstance(application);
        propertyDao = database.propertyDao();
        allProperties = propertyDao.getAllProperties();
    }

    public LiveData<List<Property>> getAllProperties() {
        return allProperties;
    }

    public void insert(Property property) {
        PropertyDatabase.databaseWriteExecutor.execute(() -> propertyDao.insert(property));
    }
}
