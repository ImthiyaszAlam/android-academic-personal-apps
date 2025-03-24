package com.example.phoneapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.phoneapp.R;
import com.example.phoneapp.utils.GoogleSignInHelper;
import com.example.phoneapp.viewmodel.LoginViewModel;
import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    private EditText email,password;
    private Button signupButton,loginButton;
    private GoogleSignInHelper googleSignInHelper;
    private SignInButton googleLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        googleSignInHelper = new GoogleSignInHelper(this);
        googleLoginBtn = findViewById(R.id.googleLoginBtn);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signupButton = findViewById(R.id.signupBtn);
        loginButton = findViewById(R.id.loginBtn);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        signupButton.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        loginButton.setOnClickListener(v->{
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)){
                Toast.makeText(this,"All fields are required",Toast.LENGTH_SHORT).show();
                return;
            }
            loginViewModel.login(userEmail,userPassword).observe(this,success->{
                if (success){
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        googleLoginBtn.setOnClickListener(v->googleSignInHelper.signInWithGoogle(googleSignInActivityResultLauncher));


        }



    private final ActivityResultLauncher<Intent> googleSignInActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    googleSignInHelper.handleSignInResult(result.getData());
                }
            });


}