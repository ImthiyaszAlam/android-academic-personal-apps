package com.imthiyas.contactme.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.imthiyas.contactme.R;
import com.imthiyas.contactme.util.AnswerStorage;
import com.imthiyas.contactme.util.PermissionUtils;
import com.imthiyas.contactme.util.RecordingService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Question1Activity extends AppCompatActivity {

    Toolbar toolbar;
    private Spinner genderSpinner;
    private Button nextButton;
    private int selectedGenderId = -1; // -1 indicates no selection
    private AudioRecord audioRecord;
    private String audioFilePath;
    private boolean isRecording = false;
    private static final int SAMPLE_RATE = 44100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qustion1);

        genderSpinner = findViewById(R.id.gender_spinner);
        nextButton = findViewById(R.id.next_button);

        if (PermissionUtils.checkPermission(this, Manifest.permission.RECORD_AUDIO)) {
            startRecordingService();
        } else {
            PermissionUtils.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        }


        // Set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Gender");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set up the Spinner for gender selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedGenderId = position; // Capture the selected gender ID
                AnswerStorage.setGenderChoice(selectedGenderId); // Store the selected gender in AnswerStorage
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedGenderId = -1;
                AnswerStorage.setGenderChoice(-1); // Default value if nothing is selected
            }
        });


        // Handle Next button click
        nextButton.setOnClickListener(v -> {
            if (validateGender()) {
//                 Proceed to the next activity (replace with actual activity)
                Intent intent = new Intent(Question1Activity.this, Question2Activity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Question1Activity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateGender() {
        // Validate that a gender has been selected
        return selectedGenderId != -1;
    }

    private void startRecording() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 200);
            return;
        }

        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);

        audioFilePath = getExternalFilesDir(null).getAbsolutePath() + "/recording.wav";

        audioRecord.startRecording();
        isRecording = true;

        // Start a new thread to save the recording data
        new Thread(() -> {
            writeAudioDataToFile();
            convertPcmToWav();
        }).start();
    }

    private void writeAudioDataToFile() {
        byte[] data = new byte[1024];
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(audioFilePath);
            while (isRecording) {
                int read = audioRecord.read(data, 0, data.length);
                if (read > 0) {
                    outputStream.write(data, 0, read);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRecording() {
        if (audioRecord != null) {
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    private void convertPcmToWav() {
        try (FileOutputStream wavOutputStream = new FileOutputStream(audioFilePath)) {
            long audioLength = new File(audioFilePath).length();
            long dataLength = audioLength + 36;
            int channels = 1;
            int byteRate = SAMPLE_RATE * 2 * channels;

            // Write WAV file header
            writeWavHeader(wavOutputStream, audioLength, dataLength, SAMPLE_RATE, channels, byteRate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeWavHeader(FileOutputStream out, long audioLength, long dataLength,
                                int sampleRate, int channels, int byteRate) throws IOException {
        out.write(new byte[]{'R', 'I', 'F', 'F',
                (byte) (dataLength & 0xff), (byte) ((dataLength >> 8) & 0xff), (byte) ((dataLength >> 16) & 0xff), (byte) ((dataLength >> 24) & 0xff),
                'W', 'A', 'V', 'E', 'f', 'm', 't', ' ',
                16, 0, 0, 0,
                1, 0, (byte) channels, 0,
                (byte) (sampleRate & 0xff), (byte) ((sampleRate >> 8) & 0xff), (byte) ((sampleRate >> 16) & 0xff), (byte) ((sampleRate >> 24) & 0xff),
                (byte) (byteRate & 0xff), (byte) ((byteRate >> 8) & 0xff), (byte) ((byteRate >> 16) & 0xff), (byte) ((byteRate >> 24) & 0xff),
                (byte) (2 * channels), 0,
                16, 0,
                'd', 'a', 't', 'a',
                (byte) (audioLength & 0xff), (byte) ((audioLength >> 8) & 0xff), (byte) ((audioLength >> 16) & 0xff), (byte) ((audioLength >> 24) & 0xff)});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopRecording();
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

    private void startRecordingService() {


        Intent intent = new Intent(this, RecordingService.class);
        intent.setAction(RecordingService.ACTION_START);
        startService(intent);
    }
}
