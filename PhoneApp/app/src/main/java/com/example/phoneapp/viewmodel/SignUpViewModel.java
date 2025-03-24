package com.example.phoneapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpViewModel extends ViewModel {
    private final MutableLiveData<Boolean> signupResult;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firebaseFirestore;

    public SignUpViewModel() {
        this.signupResult = new MutableLiveData<>();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public LiveData<Boolean> signUp(String name, String email, String password) {
        Log.d("SignUp", "Starting sign up process");
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            Log.d("SignUp", "User created successfully");

                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            firebaseUser.updateProfile(profileUpdate)
                                    .addOnCompleteListener(profileUpdateTask->{
                                        if (profileUpdateTask.isSuccessful()){
                                            Log.d("Signup","profile updated successfully");
                                        }
                                    });
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("Name", name);
                            userMap.put("Email", email);
                            userMap.put("Password",password);

                            firebaseFirestore.collection("emailUsers").document(firebaseUser.getUid())
                                    .set(userMap)
                                    .addOnSuccessListener(aVoid -> {
                                        signupResult.setValue(true);
                                        Log.d("SignUp", "User data saved successfully");
                                    })
                                    .addOnFailureListener(e -> {
                                        signupResult.setValue(false);
                                        Log.e("SignUp", "Failed to save user data to FireStore", e);
                                    });
                        } else {
                            signupResult.setValue(false);
                            Log.e("SignUp", "Error: FirebaseUser is null after sign-up");
                        }
                    } else {
                        signupResult.setValue(false);
                        if (task.getException() != null) {
                            Log.e("SignUp", "Sign-up failed: " + task.getException().getMessage());
                        } else {
                            Log.e("SignUp", "Sign-up failed: Unknown error");
                        }
                    }
                });
        return signupResult;
    }
}
