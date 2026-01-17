package com.imthiyas.contactme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.imthiyas.contactme.R;
import com.imthiyas.contactme.util.AnswerStorage;

public class Question2Activity extends AppCompatActivity {

    Toolbar toolbar;
    private EditText ageEditText;
    private Button nextButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        ageEditText = findViewById(R.id.age_edit_text);
        nextButton = findViewById(R.id.next_button);
        backButton = findViewById(R.id.back_button);


        // Set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Enter Age");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set up the Next button listener
        nextButton.setOnClickListener(v -> {
            if (validateAge()) {
                // Save age in AnswerStorage
                AnswerStorage.setAge(Integer.parseInt(ageEditText.getText().toString()));

                // Navigate to the next activity (Question3Activity)
                startActivity(new Intent(Question2Activity.this, Question3Activity.class));
                finish();
            } else {
                Toast.makeText(Question2Activity.this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the Back button listener to go back to Question1Activity
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(Question2Activity.this, Question1Activity.class));
        });
    }

    private boolean validateAge() {
        // Validate that the age input is not empty and is a valid number
        String ageText = ageEditText.getText().toString();
        if (!TextUtils.isEmpty(ageText)) {
            try {
                int age = Integer.parseInt(ageText);
                return age > 0; // Ensure age is positive
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the Up button press
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // This will handle the back press
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
