package com.example.phoneapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.phoneapp.R;
import com.example.phoneapp.adapter.UserListAdapter;
import com.example.phoneapp.databinding.ActivityMainBinding;
import com.example.phoneapp.model.UserModel;
import com.example.phoneapp.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding mainBinding;
    private UserViewModel userViewModel;
    private UserListAdapter userAdapter;
    LottieAnimationView progressBar;
    private EditText name;
    private EditText mobile;
    private EditText email;
    private Button submitBtn, resetBtn;
    private RecyclerView userRecyclerView;

    private List<UserModel> userLists;
    private final int PERMISSION_REQUEST_CODE = 111;

    private boolean isUpdating = false;
    private UserModel currentUser;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout signupForm;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Context context = this;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        bottomNavigationView = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                Log.w("MainActivity", "Fetching FCM registration token failed", task.getException());
//                return;
//            }
//
//            // Get new FCM registration token
//            String token = task.getResult();
//            Log.d("MainActivity", "FCM Token: " + token);
//
//            // Send the token to your server or store it locally
//        });

        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e("FCM", "Subscription failed");
                        }
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
        }

        updateDrawerHeader();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.dashboard) {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }else {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                    return true;
                } else if (item.getItemId() == R.id.notification) {
                    Toast.makeText(MainActivity.this, "Notifications selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.search) {
                    Toast.makeText(MainActivity.this, "Search selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else{
                    Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                    startActivity(intent);
                    return true;
                }
            }
        });

        name = findViewById(R.id.etName);
        mobile = findViewById(R.id.etMobile);
        email = findViewById(R.id.etEmail);
        submitBtn = findViewById(R.id.submitBtn);
        resetBtn = findViewById(R.id.resetButton);
        userRecyclerView = findViewById(R.id.userRecyclerView);
        signupForm = findViewById(R.id.signupForm);
        userLists = new ArrayList<>();

        // Setup ViewModel and Adapter
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userAdapter = new UserListAdapter(userLists);

        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(userAdapter);

        if (userLists!=null){
            loadUserData();
        }
        // Request necessary permissions
        checkAndRequestPermissions();

        // Validate inputs
        mobileValidation();
        emailValidation();

        // Observe LiveData from ViewModel
        userViewModel.getUsersLiveData().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> users) {
                userAdapter = new UserListAdapter(users);
                userRecyclerView.setAdapter(userAdapter);

                // Set item click listeners for update and delete actions
                userAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
                    @Override
                    public void onUpdateClick(UserModel user) {
                        updateUserData(user);
                    }

                    @Override
                    public void onDeleteClick(UserModel user) {
                        userViewModel.deleteUser(user.getId());
                        Toast.makeText(MainActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });

        // Submit button click listener
        submitBtn.setOnClickListener(v -> {
            if (saveValidData()) {
                String userName = name.getText().toString().trim();
                String userMobile = mobile.getText().toString().trim();
                String userEmail = email.getText().toString().trim();

                if (isUpdating) {
                    // Update existing user
                    currentUser.setName(userName);
                    currentUser.setMobile(userMobile);
                    currentUser.setEmail(userEmail);
                    userViewModel.updateUser(currentUser);
                    Toast.makeText(MainActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
                    isUpdating = false;
                    submitBtn.setText("ADD USER");
                } else {
                    // Add new user
                    if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userMobile) || TextUtils.isEmpty(userEmail)) {
                        Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        UserModel newUser = new UserModel(null, userName, userMobile, userEmail);
                        userViewModel.addUser(newUser);
                        userAdapter.addUserAtTop(newUser);
                        Toast.makeText(MainActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                    }
                }
                refreshRecyclerView();
                clearFields();
            }
        });

        // Reset button click listener
        resetBtn.setOnClickListener(v -> clearFields());
        recyclerScrolling();
    }
    private void loadUserData() {
        progressDialog.show();
        firebaseFirestore.collection("users").whereEqualTo("uid", firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            List<UserModel> newUsers = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                                UserModel user = documentSnapshot.toObject(UserModel.class);
                                newUsers.add(user);
                            }
                        }
                    } else {
                        // Handle the error case
                        Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateDrawerHeader() {
        View headerView = navigationView.getHeaderView(0);
        ImageView profileImage = headerView.findViewById(R.id.profileImage);
        TextView profileName = headerView.findViewById(R.id.profileName);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            String name = firebaseUser.getDisplayName();
            String image = firebaseUser.getPhotoUrl()!=null?firebaseUser.getPhotoUrl().toString():null;
            profileName.setText(name != null ? name : "No Name");

            if (image!=null){
                Picasso.get().load(image).into(profileImage);
            }else {
                profileImage.setImageResource(R.drawable.profile);
            }
        }else {
            profileName.setText("Guest");
            profileImage.setImageResource(R.drawable.profile);
        }
    }

    // Method to update user data in the form
    private void updateUserData(UserModel user) {
        currentUser = user;
        name.setText(user.getName());
        mobile.setText(user.getMobile());
        email.setText(user.getEmail());

        submitBtn.setText("UPDATE");
        isUpdating = true;
    }

    // Method to validate mobile input
    void mobileValidation() {
        mobile.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String input = mobile.getText().toString();
                if (!input.isEmpty() && input.charAt(0) < '6') {
                    mobile.setError("Number must start with 6 or greater");
                } else {
                    mobile.setError(null);
                }
            }
        });
    }

    // Method to validate email input
    void emailValidation() {
        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String userEmail = email.getText().toString();
                if (!isValidEmail(userEmail)) {
                    email.setError("Invalid email format. Ensure there's only one dot and includes @.");
                } else {
                    email.setError(null);
                }
            }
        });
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (!email.matches(emailRegex)) {
            return false;
        }
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        String localPart = parts[0];
        String domainPart = parts[1];
        return localPart.chars().filter(ch -> ch == '.').count() <= 1
                && domainPart.chars().filter(ch -> ch == '.').count() > 0;
    }

    // Refresh RecyclerView data
    private void refreshRecyclerView() {
        userViewModel.getUsersLiveData().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> users) {
                userAdapter.setUserLists(users);
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    // Clear input fields
    private void clearFields() {
        name.setText("");
        email.setText("");
        mobile.setText("");
        submitBtn.setText("ADD USER");
        isUpdating = false;
    }

    // Validate input data before saving
    private boolean saveValidData() {
        String userMobile = mobile.getText().toString();
        String userEmail = email.getText().toString();
        boolean isValid = true;

        if (!isMobileValid(userMobile)) {
            mobile.setError("Number must start with 6 or greater");
            isValid = false;
        } else {
            mobile.setError(null);
        }

        if (!isValidEmail(userEmail)) {
            email.setError("Invalid email format");
            isValid = false;
        } else {
            email.setError(null);
        }
        return isValid;
    }

    // Check if the mobile number is valid
    private boolean isMobileValid(String mobile) {
        return !mobile.isEmpty() && mobile.charAt(0) >= '6';
    }

    // Check and request necessary permissions
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
            Toast.makeText(this, "READ_CALL_LOG permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "READ_CALL_LOG permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "READ_CALL_LOG permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile){
            openProfileActivity();
        }else if (id == R.id.nav_incoming){
            //openCallActivity();
            Toast.makeText(this, "Incoming Call selected", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_outgoing){
            //openCallActivity();
            Toast.makeText(this, "Outgoing Call selected", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_missed){
            //openCallActivity();
            Toast.makeText(this, "Missed Call selected", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_notification){
            Intent intent = new Intent(MainActivity.this,NotificationActivity.class);
            startActivity(intent);
        }else {
            logout();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    private void openCallActivity(){
        Intent intent = new Intent(MainActivity.this,CallActivity.class);
        startActivity(intent);
    }
    private void openProfileActivity() {
        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    private void logout() {
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings){
            Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.help) {
            Toast.makeText(this, "Help selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.feedback) {
            Toast.makeText(this, "Feedback selected", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    public void recyclerScrolling(){
        userRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    bottomNavigationView.setVisibility(View.GONE);
                    //signupForm.setVisibility(View.GONE);
                } else if (dy<0) {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    //signupForm.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
