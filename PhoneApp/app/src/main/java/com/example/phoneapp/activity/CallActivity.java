package com.example.phoneapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.phoneapp.CallReceiver;
import com.example.phoneapp.adapter.CallFragmentAdapter;
import com.example.phoneapp.databinding.ActivityCallBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

public class CallActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    CallFragmentAdapter callFragmentAdapter;
    private ActivityCallBinding callBinding;
    private String userPhoneNumber;
    CallReceiver callReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callBinding = ActivityCallBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(callBinding.getRoot());


        checkAndRequestPermissions();
        FirebaseApp.initializeApp(this);


        //Broadcast Registration
        IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        callReceiver= new CallReceiver();
        registerReceiver(callReceiver, filter);


        // Retrieve phone number and name from the intent from MainActivity
        Intent intent = getIntent();
        String userName = intent.getStringExtra("name");
        userPhoneNumber = intent.getStringExtra("phoneNumber");
        String userEmail = intent.getStringExtra("email");

        callFragmentAdapter = new CallFragmentAdapter(this, userPhoneNumber);
        callBinding.viewPager2.setAdapter(callFragmentAdapter);

        // Update UI elements
        //getSupportActionBar().setTitle("Name: " + userName);
        callBinding.nameTV.setText(userName);
        callBinding.mobileTV.setText(userPhoneNumber);
        callBinding.emailTV.setText(userEmail);

        // Making phone call
        callBinding.callBtn.setOnClickListener(v -> {
            makePhoneCall();
        });


        // Attach the TabLayout to the ViewPager
        callBinding.tabLayout.addTab(callBinding.tabLayout.newTab().setText("Incoming"));
        callBinding.tabLayout.addTab(callBinding.tabLayout.newTab().setText("Outgoing"));
        callBinding.tabLayout.addTab(callBinding.tabLayout.newTab().setText("Missed"));

        callBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                callBinding.viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        callBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                callBinding.tabLayout.selectTab(callBinding.tabLayout.getTabAt(position));

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(callReceiver);
    }

    private void makePhoneCall() {
        if (userPhoneNumber != null && !userPhoneNumber.isEmpty()) {
            if (ContextCompat.checkSelfPermission(CallActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CallActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + userPhoneNumber));
                startActivity(callIntent);
            }
        } else {
            Toast.makeText(this, "Phone Number is not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // makePhoneCall(); // Retry making phone call
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void checkAndRequestPermissions() {
        // Check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        }
    }
}
