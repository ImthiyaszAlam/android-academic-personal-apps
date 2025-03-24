// FlexibleNavigationView.java
package com.imthiyas.flexiblenavigationview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class FlexibleNavigationView extends LinearLayout {

    private NavigationView navigationView;
    private TextView headerTitle;
    private TextView headerSubtitle;
    private ImageView headerImage;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    public FlexibleNavigationView(Context context) {
        super(context);
        init(context);
    }

    public FlexibleNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.library_navigation_view, this, true);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        headerTitle = navigationView.getHeaderView(0).findViewById(R.id.header_title);
        headerSubtitle = navigationView.getHeaderView(0).findViewById(R.id.header_subtitle);
        headerImage = navigationView.getHeaderView(0).findViewById(R.id.header_image);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle((AppCompatActivity) context, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // Sync the toggle state

        // Handle navigation item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Handle navigation item clicks
                int id = menuItem.getItemId();
                // Example: You can implement actions for different menu items here
                drawerLayout.closeDrawers(); // Close the drawer after item selection
                return true;
            }
        });
    }

    public void setHeaderTitle(String title) {
        headerTitle.setText(title);
    }

    public void setHeaderSubtitle(String subtitle) {
        headerSubtitle.setText(subtitle);
    }

    public void setHeaderImage(int resId) {
        headerImage.setImageResource(resId);
    }

    public void setNavigationMenu(int menuResId) {
        navigationView.inflateMenu(menuResId);
    }

    public void addNavigationItem(String title, int id) {
        navigationView.getMenu().add(0, id, 0, title);
    }

    public void clearMenu() {
        navigationView.getMenu().clear();
    }
}
