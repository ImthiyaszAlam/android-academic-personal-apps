package com.example.animemangatoon.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.animemangatoon.R;
import com.example.animemangatoon.fragments.AboutUsFragment;
import com.example.animemangatoon.fragments.ContactUsFragment;
import com.example.animemangatoon.fragments.CorrectionsFragment;
import com.example.animemangatoon.fragments.EditorialFragment;
import com.example.animemangatoon.fragments.EthicsFragment;
import com.example.animemangatoon.fragments.FactFragment;
import com.example.animemangatoon.fragments.HomeFragment;
import com.example.animemangatoon.fragments.OwnershipFragment;
import com.example.animemangatoon.fragments.PrivacyFragment;
import com.example.animemangatoon.fragments.TermsFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FrameLayout fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragment_container = findViewById(R.id.fragment_container);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        // bottomNavigationView = findViewById(R.id.bottomNav);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_btn_);

        if (savedInstanceState == null) {
            // Set HomeFragment as the default fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            fragment_container.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Home"); // Set title for HomeFragment
        }


        // getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.anime_app_logo));
        navigationView.setNavigationItemSelectedListener(this);
        // bottomNavigationView.inflateMenu(R.menu.bottom_menu);
        // Initialize the ActionBarDrawerToggle and attach it to the drawer and toolbar
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // Prevent the drawer from automatically opening by checking its state
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.primaryColor));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // This ensures the drawer doesn't open by default
        drawerLayout.closeDrawer(GravityCompat.START);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        closeDrawer();

        Fragment selectedFragment = null;
        String title = "";
        if (id == R.id.home) {
            title = "Home";
            selectedFragment = new HomeFragment();
        } else if (id == R.id.contactUs) {
            title = "Contact";
            selectedFragment = new ContactUsFragment();
        } else if (id == R.id.privacyPolicy) {
            title = "Privacy";
            selectedFragment = new PrivacyFragment();
        } else if (id == R.id.about) {
            title = "About";
            selectedFragment = new AboutUsFragment();
        } else if (id == R.id.factPolicy) {
            title = "Fact";
            selectedFragment = new FactFragment();
        } else if (id == R.id.correctionPolicy) {
            title = "Corrections";
            selectedFragment = new CorrectionsFragment();
        } else if (id == R.id.ethicsPolicy) {
            selectedFragment = new EthicsFragment();
            title = "Ethics";
        } else if (id == R.id.editorialPolicy) {
            title = "Editorial";
            selectedFragment = new EditorialFragment();
        } else if (id == R.id.ownershipPolicy) {
            title = "Ownership";
            selectedFragment = new OwnershipFragment();
        } else if (id == R.id.terms) {
            title = "Terms";
            selectedFragment = new TermsFragment();
        } else {
            return false; // Handle unexpected item clicks
        }

        // Load the selected fragment
        if (selectedFragment != null) {
            loadFragment(selectedFragment);
            getSupportActionBar().setTitle(title);
            invalidateOptionsMenu(); // Update the options menu
        }
        closeDrawer();
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment_container.setVisibility(View.VISIBLE);
        transaction.replace(R.id.fragment_container, fragment);
        // transaction.addToBackStack(null); // Optional, if you want to allow back navigation
        transaction.commit();

    }

    private void closeDrawer() {
        // Close the drawer if using a DrawerLayout
        drawerLayout.closeDrawer(GravityCompat.START);

    }


}