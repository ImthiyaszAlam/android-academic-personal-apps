package com.imthiyas.demoproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.imthiyas.demoproject.databinding.ActivityMainBinding;
import com.imthiyas.flexiblenavigationview.FlexibleNavigationView;

public class MainActivity extends AppCompatActivity implements FlexibleNavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding mainBinding;
    FlexibleNavigationView flexibleNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        flexibleNavView = findViewById(R.id.flexible_nav_view);

        // Customize the navigation header
        flexibleNavView.setHeaderTitle("Demo Project");
        flexibleNavView.setHeaderSubtitle("Demo Project Details");
        flexibleNavView.setHeaderImage(R.drawable.divide); // Replace with your drawable resource

        // Add navigation items
        flexibleNavView.addNavigationItem("Home", 1);
        flexibleNavView.addNavigationItem("Register", 2);
        flexibleNavView.addNavigationItem("Setting", 3);
        flexibleNavView.addNavigationItem("Logout", 4);
        flexibleNavView.addNavigationItem("More", 5);

        // Set the navigation menu (ensure you have this menu resource)
        flexibleNavView.setNavigationMenu(R.menu.nav_menu);

        // Set listener for navigation item selection
        flexibleNavView.setOnNavigationItemSelectedListener(this);

        // Load the default fragment (HomeFragment) when the activity starts
        loadFragment(new HomeFragment());
    }

    @Override
    public void onNavigationItemSelected(int itemId) {
        if (itemId == 1) { // Home
            // Redirect to HomeActivity
           loadFragment(new HomeFragment());
        } else if (itemId == 2) { // Register
            // Handle Register action
            Toast.makeText(this, "Register selected", Toast.LENGTH_SHORT).show();
        } else if (itemId == 3) { // Settings
            // Handle Settings action
            Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
        } else if (itemId == 4) { // Logout
            // Handle Logout action
            Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show();
        } else if (itemId == 5) { // More
            // Handle More action
            Toast.makeText(this, "More selected", Toast.LENGTH_SHORT).show();
        } else {
            // Handle unknown item
            Toast.makeText(this, "Unknown item selected", Toast.LENGTH_SHORT).show();
        }
    }



    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment) // Assuming you have a FrameLayout with this ID
                    .commit();
        }
    }
}


