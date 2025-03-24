package com.example.landsurvey.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.landsurvey.Property;
import com.example.landsurvey.R;
import com.example.landsurvey.viewmodel.PropertyViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddPropertyActivity extends AppCompatActivity {

    private EditText propertyName, numberOfBlocks, numberOfFloors, addressET;
    private Button mapBtn, saveButton, currentLocationButton;
    private LatLng centroidLocation; // To store centroid from boundary
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PropertyViewModel propertyViewModel;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Bind views
        propertyName = findViewById(R.id.propertyName);
        numberOfBlocks = findViewById(R.id.numberOfBlocks);
        numberOfFloors = findViewById(R.id.numberOfFloors);
        addressET = findViewById(R.id.address);
        mapBtn = findViewById(R.id.mapButton);
        saveButton = findViewById(R.id.saveButton);
        currentLocationButton = findViewById(R.id.currentLocationButton);

        // Initialize ViewModel
        propertyViewModel = new ViewModelProvider(this).get(PropertyViewModel.class);

        // Handle map button click to select boundary from the map
        mapBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AddPropertyActivity.this, MapActivity.class);
            startActivityForResult(intent, 2);
        });

        // Handle save button click
        saveButton.setOnClickListener(v -> saveProperty());

        // Handle current location button click
        currentLocationButton.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(AddPropertyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(AddPropertyActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request location permission if not already granted
                ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                getCurrentLocation();
            }
        });
//        currentLocationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AddPropertyActivity.this,CurrentLocationActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }

    private void saveProperty() {
        // Get text from fields
        String propertyNameStr = propertyName.getText().toString().trim();
        String numberOfBlocksStr = numberOfBlocks.getText().toString().trim();
        String numberOfFloorsStr = numberOfFloors.getText().toString().trim();
        String addressStr = addressET.getText().toString().trim();

        // Debug logs
        Log.d("AddPropertyActivity", "Property Name: " + propertyNameStr);
        Log.d("AddPropertyActivity", "Number of Blocks: " + numberOfBlocksStr);
        Log.d("AddPropertyActivity", "Number of Floors: " + numberOfFloorsStr);
        Log.d("AddPropertyActivity", "Address: " + addressStr);
        Log.d("AddPropertyActivity", "Centroid Location: " + centroidLocation);

        // Validate fields
        if (propertyNameStr.isEmpty() || numberOfBlocksStr.isEmpty() || numberOfFloorsStr.isEmpty() || addressStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // If centroidLocation is not set, check if address is valid from current location
        if (centroidLocation == null && addressStr.isEmpty()) {
            Toast.makeText(this, "Please select a location on the map or use your current location", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert to integers and handle possible NumberFormatException
        int blocks;
        int floors;
        try {
            blocks = Integer.parseInt(numberOfBlocksStr);
            floors = Integer.parseInt(numberOfFloorsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create property object with centroid location or default to current location
        Property property;
        if (centroidLocation != null) {
            property = new Property(propertyNameStr, blocks, floors, addressStr, centroidLocation.latitude, centroidLocation.longitude);
        } else {
            // Default to current location if centroidLocation is not set
            property = new Property(propertyNameStr, blocks, floors, addressStr, 0, 0);
        }
        propertyViewModel.addProperty(property);

        Toast.makeText(this, "Property Saved", Toast.LENGTH_SHORT).show();
        finish();  // Close the activity and return to the previous one
    }

    // Handle result from MapActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data != null) {
                double latitude = data.getDoubleExtra("centroid_latitude", 0.0);
                double longitude = data.getDoubleExtra("centroid_longitude", 0.0);
                centroidLocation = new LatLng(latitude, longitude); // Get the centroid location

                try {
                    String address = getAddressFromLatLng(latitude, longitude);
                    addressET.setText(address != null ? address : "Address not found");
                } catch (IOException e) {
                    e.printStackTrace();
                    addressET.setText("Error fetching address");
                }
            }
        }
    }

    private String getAddressFromLatLng(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Request up to 5 addresses
        if (addresses != null && !addresses.isEmpty()) {
            Address address = addresses.get(0); // Get the most relevant address
            StringBuilder stringAddress = new StringBuilder();
            if (address.getThoroughfare() != null) {
                stringAddress.append(address.getThoroughfare()).append(", ");
            }
            if (address.getSubLocality() != null) {
                stringAddress.append(address.getSubLocality()).append(", ");
            }
            if (address.getLocality() != null) {
                stringAddress.append(address.getLocality()).append(", ");
            }
            if (address.getAdminArea() != null) {
                stringAddress.append(address.getAdminArea()).append(", ");
            }
            if (address.getCountryName() != null) {
                stringAddress.append(address.getCountryName());
            }
            return stringAddress.toString();
        }
        return "Address not found";
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        // Check if permissions are granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Create location request with high accuracy
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1); // Only get one update
        locationRequest.setInterval(5000); // 5 seconds
        locationRequest.setFastestInterval(2000); // 2 seconds

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                    Location location = locationResult.getLastLocation();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    try {
                        // Fetch the human-readable address from latitude and longitude
                        String address = getAddressFromLatLng(latitude, longitude);
                        if (address != null) {
                            // Set the address in the EditText field
                            addressET.setText(address);
                            centroidLocation = new LatLng(latitude, longitude); // Set current location as centroid
                        } else {
                            addressET.setText("Address not found");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(AddPropertyActivity.this, "Error fetching address", Toast.LENGTH_SHORT).show();
                    }

                    // Remove location updates to prevent further changes
                    fusedLocationProviderClient.removeLocationUpdates(this);
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
