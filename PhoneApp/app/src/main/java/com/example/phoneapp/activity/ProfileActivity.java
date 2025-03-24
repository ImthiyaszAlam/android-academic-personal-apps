package com.example.phoneapp.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phoneapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileEmail = findViewById(R.id.profileEmail);
        profileName = findViewById(R.id.profileName);
        profileImage = findViewById(R.id.profileImageIV);

        // Display user profile and update ActionBar title after setting profile details
        displayUserProfile();
    }

    private void displayUserProfile() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String name = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();
            String image = firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null;

            profileName.setText(name != null ? name : "No Name");
            profileEmail.setText(email != null ? email : "No Email");

            if (image != null) {
                Picasso.get().load(image).into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.profile);
            }

            // Update ActionBar title after profile details are set
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Hi! " + (name != null ? name : "User") + " \uD83D\uDE04");
                //actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            profileName.setText("No Name");
            profileEmail.setText("No Email");
            profileImage.setImageResource(R.drawable.profile);

            // Optionally set ActionBar title if no user is found
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Hi! User \uD83D\uDE04");
                //actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }
}
