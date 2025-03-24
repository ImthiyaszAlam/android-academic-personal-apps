package com.example.phoneapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.phoneapp.R;
import com.example.phoneapp.utils.GoogleSignInHelper;
import com.example.phoneapp.viewmodel.SignUpViewModel;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    SignUpViewModel signUpViewModel;
    private EditText name, email, password;
    private Button signupBtn, loginBtn;
    private SignInButton googleSignInBtn;
    private GoogleSignInHelper googleSignInHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();

        googleSignInHelper = new GoogleSignInHelper(this);
        name = findViewById(R.id.etUserName);
        email = findViewById(R.id.etUserEmail);
        password = findViewById(R.id.etPassword);

        signupBtn = findViewById(R.id.signupBtn);
        loginBtn = findViewById(R.id.loginBtn);
        googleSignInBtn = findViewById(R.id.googleSignInBtn);

        googleSignInBtn.setOnClickListener(v->googleSignInHelper.signInWithGoogle(googleSignInActivityResultLauncher));

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        loginBtn.setOnClickListener(v->{
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
        signupBtn.setOnClickListener(v -> {
            String userName = name.getText().toString().trim();
            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)){
                Toast.makeText(this,"All fields are required",Toast.LENGTH_SHORT).show();
                return;
            }
            signUpViewModel.signUp(userName, userEmail, userPassword).observe(this, success -> {
                if (success != null && success) {
                    Toast.makeText(SignUpActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private final ActivityResultLauncher<Intent> googleSignInActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    googleSignInHelper.handleSignInResult(result.getData());
                }
            });


}