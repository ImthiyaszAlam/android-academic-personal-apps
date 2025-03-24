package com.example.landsurvey.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.landsurvey.Property;
import com.example.landsurvey.repository.PropertyRepository;

import java.util.List;

public class PropertyViewModel extends AndroidViewModel {

    private PropertyRepository repository;
    private LiveData<List<Property>> allProperties;

    public PropertyViewModel(Application application) {
        super(application);
        repository = new PropertyRepository(application);
        allProperties = repository.getAllProperties();
    }

    public LiveData<List<Property>> getAllProperties() {
        return allProperties;
    }

    public void addProperty(Property property) {
        repository.insert(property);
    }
}
