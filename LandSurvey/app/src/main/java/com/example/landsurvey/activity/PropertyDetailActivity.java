package com.example.landsurvey.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.landsurvey.Property;
import com.example.landsurvey.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class PropertyDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView textViewName, textViewBlocks, textViewFloors, textViewAddress;
    private GoogleMap mMap;
    private Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.landsurvey.R.layout.activity_property_detail);

        textViewName = findViewById(com.example.landsurvey.R.id.detail_text_view_name);
        textViewBlocks = findViewById(com.example.landsurvey.R.id.detail_text_view_blocks);
        textViewFloors = findViewById(com.example.landsurvey.R.id.detail_text_view_floors);
        textViewAddress = findViewById(com.example.landsurvey.R.id.detail_text_view_address);

        if (getIntent() != null && getIntent().hasExtra("property")) {
            property = (Property) getIntent().getSerializableExtra("property");
            populateDetails();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detail_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void populateDetails() {
        textViewName.setText(property.getName());
        textViewBlocks.setText("Blocks: " + property.getBlocks());
        textViewFloors.setText("Floors: " + property.getFloors());
        textViewAddress.setText("Address: " + property.getAddress());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng propertyLocation = new LatLng(property.getLatitude(), property.getLongitude());
        mMap.addMarker(new MarkerOptions().position(propertyLocation).title(property.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(propertyLocation, 15));

        CircleOptions circleOptions = new CircleOptions()
                .center(propertyLocation)
                .radius(100)
                .strokeColor(0x55FF0000)
                .fillColor(0x220000FF)
                .strokeWidth(2);
        mMap.addCircle(circleOptions);
    }
}
