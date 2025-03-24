
package com.imthiyas.contactme.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.imthiyas.contactme.R;
import com.imthiyas.contactme.data.DatabaseHelper;
import com.imthiyas.contactme.model.FormData;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TableLayout resultTable;
    private DatabaseHelper databaseHelper;
    private MediaPlayer mediaPlayer;  // MediaPlayer instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTable = findViewById(R.id.result_table);
        databaseHelper = new DatabaseHelper(this);

        // Load and display all submissions in the table
        loadAndDisplayResults();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release media player resources when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void loadAndDisplayResults() {
        List<FormData> submissions = databaseHelper.getAllSubmissions();
        if (submissions == null || submissions.isEmpty()) {
            Log.e("ResultActivity", "No submissions found");
            Toast.makeText(this, "No submissions to display", Toast.LENGTH_SHORT).show();
            return;
        }

        for (FormData submission : submissions) {
            displaySubmission(submission);
        }
    }


    private void displaySubmission(FormData submission) {
        TableRow row = new TableRow(this);
//        row.setPadding(16, 16, 16, 16);

        // Display Q1 (Gender choice)
        TextView q1Text = new TextView(this);
        q1Text.setText(getGenderText(submission.getGenderId()));
        q1Text.setPadding(16, 8, 16, 8);
        row.addView(q1Text);

        // Display Q2 (Age)
        TextView q2Text = new TextView(this);
        Integer age = submission.getAge();
        if (age != null) {
            q2Text.setText(String.valueOf(age));
        } else {
            q2Text.setText("N/A");
        }
        q2Text.setPadding(16, 8, 16, 8);
        row.addView(q2Text);

        // Display Q3 (Selfie image)
        ImageView selfieImage = new ImageView(this);
        selfieImage.setPadding(16, 8, 16, 8);
        String selfiePath = submission.getSelfiePath();

        // Debugging: Log selfiePath to verify it's different for each submission
        Log.d("Submission", "Selfie Path: " + selfiePath);

        // Ensure the selfie image is properly set based on its path
        File selfieFile = new File(selfiePath);
        if (selfieFile.exists()) {
            Uri selfieUri = Uri.fromFile(selfieFile);  // Try with file URI first
            selfieImage.setImageURI(selfieUri);
            Log.d("Submission", "Image set for path: " + selfiePath);
        } else {
            // Placeholder if no selfie is found
            selfieImage.setImageResource(R.drawable.anna);  // Placeholder image
            Log.d("Submission", "Using placeholder image");
        }

        selfieImage.setLayoutParams(new TableRow.LayoutParams(100, 100));
        row.addView(selfieImage);

        // Display Recorded Audio with play option
        Button audioButton = new Button(this);
        audioButton.setText("Play Audio");
        String audioPath = submission.getAudioPath();
        if (audioPath != null && new File(audioPath).exists()) {
            audioButton.setOnClickListener(view -> playAudio(audioPath));
        } else {
            audioButton.setText("Audio Not Available");
            audioButton.setEnabled(false);
        }
        row.addView(audioButton);

        // Display GPS Coordinates
        TextView gpsText = new TextView(this);
        gpsText.setText(submission.getGpsCoordinates() != null ? submission.getGpsCoordinates() : "N/A");
        gpsText.setPadding(16, 8, 16, 8);
        row.addView(gpsText);

        // Display Submission Time
        TextView timeText = new TextView(this);
        timeText.setText(submission.getSubmitTime() != null ? submission.getSubmitTime() : "N/A");
        timeText.setPadding(16, 8, 16, 8);
        row.addView(timeText);

        // Add the row to the table
        resultTable.addView(row);
    }

    private void playAudio(String audioPath) {
        if (audioPath.equals("No recording found")) {
            Toast.makeText(this, "No audio recorded yet", Toast.LENGTH_SHORT).show();
            return;
        }

        File audioFile = new File(audioPath);
        if (audioFile.exists()) {
            try {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(audioPath);
                mediaPlayer.prepare();
                mediaPlayer.start();
                Toast.makeText(this, "Playing Audio", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error playing audio", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Audio Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private String getGenderText(Integer genderId) {
        if (genderId == null) return "N/A";
        switch (genderId) {
            case 0: return "Male";
            case 1: return "Female";
            default: return "Other";
        }
    }


}
