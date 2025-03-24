package com.example.mapsdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PropertyListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PropertyAdapter propertyAdapter;
    private List<PropertyEntity> propertyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Example data
        propertyList = new ArrayList<>();
        propertyList.add(new PropertyEntity("Government Property 1", new LatLng(28.6139, 77.2090), "Details about Property 1"));
        propertyList.add(new PropertyEntity("Government Property 2", new LatLng(19.0760, 72.8777), "Details about Property 2"));

        propertyAdapter = new PropertyAdapter(propertyList, this);
        recyclerView.setAdapter(propertyAdapter);
    }
}
