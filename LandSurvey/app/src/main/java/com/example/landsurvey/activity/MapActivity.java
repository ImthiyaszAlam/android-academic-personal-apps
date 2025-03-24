package com.example.landsurvey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.landsurvey.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private Polygon polygon;
    private GoogleMap mMap;
    private List<LatLng> boundaryPoints = new ArrayList<>();
    private final int MAX_MARKS = 5; // User can only select 5 points

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Set up the map fragment and callback
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;

            // Set default camera position to Delhi, India
            LatLng defaultLocation = new LatLng(28.6139, 77.2090);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f));

            // When the user taps on the map, add a marker at the location and store the point
            mMap.setOnMapClickListener(latLng -> {
                if (boundaryPoints.size() < MAX_MARKS) {
                    boundaryPoints.add(latLng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Mark " + boundaryPoints.size()));

                    // Draw polygon if enough points are selected
                    if (boundaryPoints.size() > 1) {
                        drawPolygon(boundaryPoints);
                    }

                    if (boundaryPoints.size() == MAX_MARKS) {
                        // All points are selected, calculate the centroid
                        LatLng centroid = calculateCentroid(boundaryPoints);

                        // Show a toast for debugging
                        Toast.makeText(this, "Boundary completed. Centroid: " + centroid.latitude + ", " + centroid.longitude, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "You've already selected 5 points.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Button to return the centroid location to the previous activity
        Button selectLocationButton = findViewById(R.id.button_save_location);
        selectLocationButton.setOnClickListener(v -> {
            if (boundaryPoints.size() == MAX_MARKS) {
                // Calculate centroid of selected boundary points
                LatLng centroid = calculateCentroid(boundaryPoints);

                // Pass the centroid location back to AddPropertyActivity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("centroid_latitude", centroid.latitude);
                returnIntent.putExtra("centroid_longitude", centroid.longitude);
                setResult(RESULT_OK, returnIntent);
                finish(); // Close the map activity
            } else {
                Toast.makeText(this, "Please select 5 points to form a boundary", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to draw a polygon on the map
    private void drawPolygon(List<LatLng> points) {
        if (polygon != null) {
            polygon.remove(); // Remove the previous polygon
        }

        PolygonOptions polygonOptions = new PolygonOptions()
                .addAll(points)
                .strokeColor(0xFF0000FF) // Blue color for polygon border
                .fillColor(0x7F0000FF)   // Semi-transparent blue color for polygon fill
                .strokeWidth(5);         // Width of the polygon border

        polygon = mMap.addPolygon(polygonOptions);
    }

    // Function to calculate the centroid of a polygon
    private LatLng calculateCentroid(List<LatLng> points) {
        double latitudeSum = 0.0;
        double longitudeSum = 0.0;
        int pointCount = points.size();

        for (LatLng point : points) {
//            latitudeSum += point.latitude;
//            longitudeSum += point.longitude;
        }

        return new LatLng(latitudeSum / pointCount, longitudeSum / pointCount); // Average latitude and longitude
    }
}
