package com.imthiyas.contactme.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.imthiyas.contactme.R;
import com.imthiyas.contactme.util.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnQuestion1;
    private Button btnResult;

    // Permissions for API level 33+
    String[] requestPermission = {Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Me");

        // Initialize buttons
        btnQuestion1 = findViewById(R.id.btn_question1);
        btnResult = findViewById(R.id.btn_result);

        // Check if the device is running API level 33 or higher and request permissions accordingly
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Request permissions for devices running Android 13 (API 33) and above
            PermissionUtils.requestPermission(this, requestPermission);
        }

        // Set button click listeners
        btnQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Question1Activity
                Intent intent = new Intent(MainActivity.this, Question1Activity.class);
                startActivity(intent);
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to ResultActivity
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });
    }
}
