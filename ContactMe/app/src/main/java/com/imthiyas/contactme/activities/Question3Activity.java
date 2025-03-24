package com.imthiyas.contactme.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.imthiyas.contactme.R;
import com.imthiyas.contactme.util.AnswerStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Question3Activity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private ImageView selfieImageView;
    private Button captureButton, nextButton, backButton;
    private Bitmap selfieBitmap;

    Toolbar toolbar;
    // ActivityResultLauncher for handling camera permission request
    private final ActivityResultLauncher<String> cameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if (isGranted) {
                        openCamera();
                    } else {
                        Toast.makeText(Question3Activity.this, "Camera permission is required to take a photo", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    // ActivityResultLauncher for opening camera and receiving result
    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras != null) {
                        selfieBitmap = (Bitmap) extras.get("data");
                        selfieImageView.setImageBitmap(selfieBitmap); // Display the captured selfie
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        selfieImageView = findViewById(R.id.selfie_image_view);
        captureButton = findViewById(R.id.capture_button);
        nextButton = findViewById(R.id.next_button);
        backButton = findViewById(R.id.back_button);


        // Set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upload Selfie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the capture button listener to open the camera
        captureButton.setOnClickListener(v -> openCamera());

        // Set up the next button listener
        nextButton.setOnClickListener(v -> {
            // Save the selfie image file if it exists
            if (selfieBitmap != null) {
                saveSelfieImage(selfieBitmap);
            }

            // Navigate to the SubmitActivity (final page)
            startActivity(new Intent(Question3Activity.this, SubmitActivity.class));
        });

        // Set up the back button listener to go back to Question2Activity
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(Question3Activity.this, Question2Activity.class));
        });
    }

    private void openCamera() {
        // Check for camera permission
        if (ContextCompat.checkSelfPermission(Question3Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            // Permission granted, open camera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraActivityResultLauncher.launch(cameraIntent);
        }
    }

    private void saveSelfieImage(Bitmap bitmap) {
        // Save the selfie image to app-specific storage (internal storage)
        File storageDir = getFilesDir();  // Use app's internal storage
        File selfieFile = new File(storageDir, "selfie.png");

        try (FileOutputStream fos = new FileOutputStream(selfieFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);  // Save as PNG file
            // Save the selfie file path in AnswerStorage
            AnswerStorage.setSelfiePath(selfieFile.getAbsolutePath());
            Toast.makeText(Question3Activity.this, "Selfie saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(Question3Activity.this, "Error saving selfie", Toast.LENGTH_SHORT).show();
        }
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
}
