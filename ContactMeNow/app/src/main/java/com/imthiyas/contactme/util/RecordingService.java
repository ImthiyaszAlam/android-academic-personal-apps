package com.imthiyas.contactme.util;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordingService extends Service {

    private static final String TAG = "RecordingService";
    private MediaRecorder mediaRecorder;
    private String audioFilePath;
    public static final String ACTION_START = "com.example.START_RECORDING";
    public static final String ACTION_STOP = "com.example.STOP_RECORDING";
    private static final int REQUEST_CODE_RECORD_AUDIO = 1001;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                // Check permission before starting the recording
                startRecording();

            } else if (ACTION_STOP.equals(action)) {
                stopRecording();
                stopSelf();
            }
        }
        return START_STICKY;
    }


    private void startRecording() {
        if (mediaRecorder != null) {
            return;
        }

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            audioFilePath = getOutputFilePath();
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            // Use newer features for API 33 and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // For API level 33 and above, we can use additional recording settings (optional)
                // Add custom logic here if needed for API 33+
            }

            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "startRecording: Failed to start recording", e);
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }
            stopSelf();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                Toast.makeText(this, "Recording saved to: " + audioFilePath, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e(TAG, "stopRecording: Failed to stop recording", e);
            }
        }
    }

    private String getOutputFilePath() {
        File dir = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "Recordings");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(dir, "Recording_" + timestamp + ".mp4").getAbsolutePath();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // No binding provided, this is a standalone service
    }
}
