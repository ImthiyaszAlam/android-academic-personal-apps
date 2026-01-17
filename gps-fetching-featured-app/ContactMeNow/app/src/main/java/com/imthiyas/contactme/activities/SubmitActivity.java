package com.imthiyas.contactme.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imthiyas.contactme.R;
import com.imthiyas.contactme.data.AppDatabase;
import com.imthiyas.contactme.data.DatabaseHelper;
import com.imthiyas.contactme.data.Submission;
import com.imthiyas.contactme.util.AnswerStorage;
import com.imthiyas.contactme.util.RecordingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubmitActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 200;
    private Button submitButton, previousButton;
    private LocationManager locationManager;
    private TextView genderTextView, ageTextView, selfieTextView;
    private ImageView selfieImageView;
    private MediaRecorder mediaRecorder;
    private DatabaseHelper databaseHelper;
    private AppDatabase appDatabase;


    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);  // Update layout to activity_submit

        submitButton = findViewById(R.id.submit_button);
        previousButton = findViewById(R.id.previous_button);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // Initialize TextViews for displaying answers
        genderTextView = findViewById(R.id.gender_text);
        ageTextView = findViewById(R.id.age_text);
        selfieTextView = findViewById(R.id.selfie_text);
        selfieImageView = findViewById(R.id.selfie_image);


        // Set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Submission");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);
        appDatabase = AppDatabase.getInstance(this);

        displayData();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm1();
                stopRecordingService();
                finish();
            }
        });
        previousButton.setOnClickListener(v -> goToPreviousActivity());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void displayData() {
        // Get values from AnswerStorage and set defaults if null
        Integer genderChoice = AnswerStorage.getGenderChoice();
        Integer age = AnswerStorage.getAge();
        String selfiePath = AnswerStorage.getSelfiePath();

        // Handle gender choice, default to "Other" if null
        String genderText = "Other";
        if (genderChoice != null) {
            if (genderChoice == 0) {
                genderText = "Male";
            } else if (genderChoice == 1) {
                genderText = "Female";
            }
        }
        genderTextView.setText(genderText);

        // Handle age
        if (age != null && age != -1) {
            ageTextView.setText(String.valueOf(age)); // Convert Integer to String
        } else {
            ageTextView.setText("Not specified");
        }

        // Handle selfie display
        if (selfiePath != null && !selfiePath.isEmpty()) {
            File selfieFile = new File(selfiePath);

            if (selfieFile.exists()) {
                // Display selfie image using Glide with caching disabled
                selfieTextView.setText("Selfie Captured");
                Glide.with(this)
                        .load(selfieFile)
                        .skipMemoryCache(true)  // Disable memory caching
                        .diskCacheStrategy(DiskCacheStrategy.NONE)  // Disable disk caching
                        .into(selfieImageView);
            } else {
                Log.e("displayData", "Selfie file not found at path: " + selfiePath);
                selfieTextView.setText("File not found");
                Glide.with(this)
                        .load(R.drawable.anna)  // Placeholder image
                        .into(selfieImageView);
            }
        } else {
            selfieTextView.setText("No selfie uploaded");
            Glide.with(this)
                    .load(R.drawable.anna)  // Placeholder image
                    .into(selfieImageView);
        }
    }

    private void submitForm() {
        Integer genderChoice = AnswerStorage.getGenderChoice();
        Integer age = AnswerStorage.getAge();
        String selfiePath = AnswerStorage.getSelfiePath();
        String audioPath = stopRecordingAndGetAudioPath();
        String gpsCoordinates = getGPSCoordinates();
        String timestamp = getCurrentTimestamp();

        // Log values for debugging
        Log.d("SubmitActivity", "Age: " + age);
        Log.d("SubmitActivity", "Audio Path: " + audioPath);
        Log.d("SubmitActivity", "GPS Coordinates: " + gpsCoordinates);
        Log.d("SubmitActivity", "Timestamp: " + timestamp);

        // Assign default values if any field is null
        age = (age == null || age == -1) ? 0 : age;  // Default age to 0 if null or invalid
        audioPath = (audioPath == null) ? "default_audio_path.mp3" : audioPath;  // Default audio path
        gpsCoordinates = (gpsCoordinates == null) ? "0.0,0.0" : gpsCoordinates;  // Default GPS coordinates
        timestamp = (timestamp == null) ? "default_timestamp" : timestamp;       // Default timestamp

        // Check if essential data is available (no need to check null here as we've provided defaults)
        if (genderChoice == null) {
            genderChoice = 0;  // Default gender choice if null
        }

        // Generate a unique selfie path (if necessary)
//        String uniqueSelfiePath = generateUniqueFileName("selfie.png");
//
//        // Generate a unique audio path (if necessary)
//        String uniqueAudioPath = generateUniqueFileName("audio.mp3");

//        // Save the new selfie and audio to these unique paths if needed (e.g., save in storage)
        saveFileToStorage(selfiePath, selfiePath);
        saveFileToStorage(audioPath, audioPath);

        // Create a JSON object to store the form data
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("Q1", genderChoice);  // Store gender choice
            jsonData.put("Q2", age);           // Store age (defaulted if necessary)
            jsonData.put("Q3", selfiePath);  // Store unique selfie path
            jsonData.put("recording", audioPath);  // Store unique audio path
            jsonData.put("gps", gpsCoordinates);   // Store GPS coordinates (defaulted if necessary)
            jsonData.put("submit_time", timestamp);  // Store timestamp (defaulted if necessary)
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Save data to the local database
        long rowId = databaseHelper.insertSubmission(jsonData);
        if (rowId != -1) {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Navigate to ResultActivity
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("json_data", jsonData.toString());  // Pass the JSON data to ResultActivity
        intent.putExtra("audio_path", audioPath);  // Pass the unique audio path
        startActivity(intent);
    }
    private void submitForm1() {
        String localSelfiePath = saveImageToLocal(AnswerStorage.getSelfiePath());
        Integer genderChoice = AnswerStorage.getGenderChoice();
        Integer age = AnswerStorage.getAge();
        String selfiePath = localSelfiePath;
        String audioPath = stopRecordingAndGetAudioPath();
        String gpsCoordinates = getGPSCoordinates();
        String timestamp = getCurrentTimestamp();

        age = (age == null || age == -1) ? 0 : age;
        genderChoice = (genderChoice == null) ? 0 : genderChoice;
        audioPath = (audioPath == null) ? "default_audio_path.mp3" : audioPath;
        gpsCoordinates = (gpsCoordinates == null) ? "0.0,0.0" : gpsCoordinates;
        timestamp = (timestamp == null) ? "default_timestamp" : timestamp;

        // Create a Submission object and insert it into the database
        Submission submission = new Submission(genderChoice, age, selfiePath, audioPath, gpsCoordinates, timestamp);

        // Use a background thread to insert data
        new Thread(() -> appDatabase.submissionDao().insertSubmission(submission)).start();

        // Navigate to ResultActivity
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        finish();
    }

    // Helper method to save a file to a new path (e.g., copy from old path)
    private void saveFileToStorage(String oldPath, String newPath) {
        // Check for null paths and log for debugging
        if (oldPath == null || newPath == null) {
            Log.e("SubmitActivity", "File paths cannot be null. oldPath: " + oldPath + ", newPath: " + newPath);
            return; // Exit if paths are invalid
        }

        File oldFile = new File(oldPath);
        File newFile = new File(newPath);

        // Check if the old file exists
        if (oldFile.exists()) {
            try {
                // Copy file content to the new location
                InputStream in = new FileInputStream(oldFile);
                OutputStream out = new FileOutputStream(newFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();
                Log.d("SubmitActivity", "File copied successfully to " + newPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("SubmitActivity", "Old file does not exist at path: " + oldPath);
        }
    }

    private String stopRecordingAndGetAudioPath() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                return AnswerStorage.getAudio(); // Ensure this value is correctly set
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error stopping audio recording", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    private String getGPSCoordinates() {
        if (locationManager != null) {
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    return location.getLatitude() + "," + location.getLongitude();
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }

    private void goToPreviousActivity() {
        Intent intent = new Intent(this, Question3Activity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the Up button press
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // This will handle the back press
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String saveImageToLocal(String imagePath) {
        try {
            File sourceFile = new File(imagePath);
            File destFile = new File(getFilesDir(), "selfie_" + System.currentTimeMillis() + ".jpg");
            InputStream in = new FileInputStream(sourceFile);
            OutputStream out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void stopRecordingService() {
        Intent intent = new Intent(this, RecordingService.class);
        intent.setAction(RecordingService.ACTION_STOP);
        startService(intent);
        Toast.makeText(this, "Recording Successful", Toast.LENGTH_SHORT).show();
    }
}
