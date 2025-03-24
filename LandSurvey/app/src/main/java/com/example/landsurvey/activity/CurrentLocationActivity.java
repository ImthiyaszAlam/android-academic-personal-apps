package com.example.landsurvey.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.landsurvey.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class CurrentLocationActivity extends AppCompatActivity {

    private Polygon polygon;
    private GoogleMap googleMap;
    private List<LatLng>boundryPoints = new ArrayList<>();
    private final int MAX_POINTS = 5;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng currentLocationLatLng;
    Button saveCurrentLocationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        saveCurrentLocationBtn = findViewById(R.id.button_save_current_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.current_map);
        mapFragment.getMapAsync(map -> {
            googleMap = map;
            fetchCurrentLocation();
        });

        saveCurrentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLocationLatLng!=null){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("lattitude",currentLocationLatLng.longitude);
                    returnIntent.putExtra("longitude",currentLocationLatLng.longitude);
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }else {
                    Toast.makeText(CurrentLocationActivity.this,"Current location not available",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void fetchCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()  && task.getResult()!=null){
                    Location location = task.getResult();
                    currentLocationLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(currentLocationLatLng).title("Current Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationLatLng,15));
                }else {
                    Toast.makeText(CurrentLocationActivity.this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}