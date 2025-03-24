package com.example.mapsdemo;

import com.google.android.gms.maps.model.LatLng;

public class PropertyEntity {
    private String name;
    private String location;
    private String details;

    public PropertyEntity(String name, LatLng location, String details) {
        this.name = name;
        this.location = String.valueOf(location);
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
