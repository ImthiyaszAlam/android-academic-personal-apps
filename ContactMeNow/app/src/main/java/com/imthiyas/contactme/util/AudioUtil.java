package com.imthiyas.contactme.util;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import java.io.IOException;

public class AudioUtil {

    private MediaRecorder mediaRecorder;
    private String audioFilePath;
    private Context context;

    public AudioUtil(Context context) {
        this.context = context;
        // Setup the path for saving audio file
        this.audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/contactme_audio.wav";
    }

    // Start recording audio
    public void startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error starting audio recording", Toast.LENGTH_SHORT).show();
        }
    }

    // Stop recording and save the file
    public void stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                Toast.makeText(context, "Audio saved at " + audioFilePath, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error stopping audio recording", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Get the audio file path
    public String getAudioFilePath() {
        return audioFilePath;
    }
}
