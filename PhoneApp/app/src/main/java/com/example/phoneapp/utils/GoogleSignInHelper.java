package com.example.phoneapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.example.phoneapp.R;
import com.example.phoneapp.activity.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GoogleSignInHelper {
    Activity activity;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    public GoogleSignInHelper(Activity activity) {
        this.activity = activity;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.googleSignInClient = GoogleSignIn.getClient(activity,googleSignInOptions);
    }

    public void signInWithGoogle(ActivityResultLauncher<Intent>launcher){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        launcher.launch(signInIntent);
    }

    public void handleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                firebaseAuthWithGoogle(account.getIdToken(),account);
            }
        } catch (ApiException e) {
            Toast.makeText(activity, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken,GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            storeUserDataInFirestore(user,account);
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            Toast.makeText(activity, " Authentication successful", Toast.LENGTH_SHORT).show();
                            activity.finish();
                        }
                    } else {
                        Toast.makeText(activity, "Firebase Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void storeUserDataInFirestore(FirebaseUser user, GoogleSignInAccount account) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getDisplayName());
        userData.put("email", user.getEmail());
        if (account.getPhotoUrl() != null) {
            userData.put("profilePicture", account.getPhotoUrl().toString());
        } else {
            userData.put("profilePicture", ""); // Default or placeholder image URL
        }

        firestore.collection("googleUsers")
                .document(user.getUid())
                .set(userData)
                .addOnSuccessListener(aVoid -> Toast.makeText(activity, "User data stored successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Failed to store user data", Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Print stack trace to Logcat
                });
    }
}
