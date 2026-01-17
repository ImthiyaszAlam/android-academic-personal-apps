
package com.imthiyas.contactme.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imthiyas.contactme.R;
import com.imthiyas.contactme.adapter.SubmissionAdapter;
import com.imthiyas.contactme.data.AppDatabase;
import com.imthiyas.contactme.data.DatabaseHelper;
import com.imthiyas.contactme.data.Submission;
import com.imthiyas.contactme.model.FormData;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    TableRow row;
    private RecyclerView recyclerView;
    private SubmissionAdapter adapter;
    private AppDatabase appDatabase;
    CardView dataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        recyclerView = findViewById(R.id.submission_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appDatabase = AppDatabase.getInstance(this);
        dataLayout = findViewById(R.id.dataLayout);


        loadSubmissions();
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

    //    private void loadAndDisplayResults() {
//        List<FormData> submissions = databaseHelper.getAllSubmissions();
//        if (submissions == null || submissions.isEmpty()) {
//            Log.e("ResultActivity", "No submissions found");
//            Toast.makeText(this, "No submissions to display", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        RecyclerView recyclerView = findViewById(R.id.submission_recycler_view);
//        SubmissionAdapter adapter = new SubmissionAdapter(this, submissions); // Pass in your data list
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//    }
    private void loadSubmissions() {
        new Thread(() -> {
            List<Submission> submissionList = appDatabase.submissionDao().getAllSubmissions();

            runOnUiThread(() -> {
                TextView noDataTextView = findViewById(R.id.no_data_text_view);

                if (submissionList == null || submissionList.isEmpty()) {
                    // No data in the database, show the "No data stored yet" message
                    noDataTextView.setVisibility(View.VISIBLE);
                    dataLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE); // Hide the RecyclerView
                } else {
                    // Data is available, update the RecyclerView
                    noDataTextView.setVisibility(View.GONE);
                    dataLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE); // Show the RecyclerView
                    adapter = new SubmissionAdapter(ResultActivity.this, submissionList);
                    recyclerView.setAdapter(adapter);
                }
            });
        }).start();
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
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }
    }

    private String getGenderText(Integer genderId) {
        if (genderId == null) return "N/A";
        switch (genderId) {
            case 0:
                return "Male";
            case 1:
                return "Female";
            default:
                return "Other";
        }
    }


}
