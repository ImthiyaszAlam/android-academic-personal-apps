package com.imthiyas.contactme.repository;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageRepository {

    private static final String TAG = "StorageRepository";
    private Context context;

    public StorageRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    // Method to save JSON data to a file
    public boolean saveJsonFile(String fileName, String jsonData) {
        File file = new File(context.getFilesDir(), fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(jsonData.getBytes());
            Log.d(TAG, "JSON data saved to " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error saving JSON file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Method to save an image file (for the captured selfie)
    public boolean saveImageFile(String fileName, byte[] imageData) {
        File file = new File(context.getFilesDir(), fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageData);
            Log.d(TAG, "Image saved to " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error saving image file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Method to save an audio file (for the recording)
    public boolean saveAudioFile(String fileName, byte[] audioData) {
        File file = new File(context.getFilesDir(), fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(audioData);
            Log.d(TAG, "Audio file saved to " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error saving audio file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve the full path of a saved file by name
    public String getFilePath(String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return file.exists() ? file.getAbsolutePath() : null;
    }
}
