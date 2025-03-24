package com.example.landsurvey;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "property_table")
public class Property implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int blocks;
    private int floors;
    private String address;

    private double latitude;
    private double longitude;

    public Property(String name, int blocks, int floors, String address, double latitude, double longitude) {
        this.name = name;
        this.blocks = blocks;
        this.floors = floors;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getFloors() {
        return floors;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
