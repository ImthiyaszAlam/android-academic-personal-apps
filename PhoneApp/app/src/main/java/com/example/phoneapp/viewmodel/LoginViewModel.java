package com.example.phoneapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Closeable;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean>loginResult;
    private FirebaseAuth firebaseAuth;

    public LoginViewModel() {
        this.loginResult = new MutableLiveData<>();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean>login(String email,String password){
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        loginResult.setValue(true);
                    }else {
                        loginResult.setValue(false);
                    }
                });
        return loginResult;
    }

}
