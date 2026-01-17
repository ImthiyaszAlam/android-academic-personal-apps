package com.imthiyas.contactme.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AnswerStorage {
    private static HashMap<String, Object> answers = new HashMap<>();
    private static final String TAG = "AnswerStorage";

    // Methods to set individual answers
    public static void setGenderChoice(int choiceId) {
        answers.put("Q1", choiceId);
    }

    public static void setAge(int age) {
        answers.put("Q2", age);
    }

    public static void setSelfie(Bitmap selfie) {
        answers.put("Q3", selfie);
    }

    public static void setAudio(String audioFilePath) {
        answers.put("recording", audioFilePath);
    }

    public static void setGPSLocation(String gpsCoordinates) {
        answers.put("gps", gpsCoordinates);
    }

    public static void setSubmissionTime(String timestamp) {
        answers.put("submit_time", timestamp);
    }

    // Add method to set the selfie file path
    public static void setSelfiePath(String selfiePath) {
        answers.put("selfie_path", selfiePath);
    }

    // Methods to get individual answers
    public static Integer getGenderChoice() {
        return (Integer) answers.getOrDefault("Q1", -1);
    }

    public static Integer getAge() {
        return (Integer) answers.getOrDefault("Q2", -1);
    }

    public static Bitmap getSelfie() {
        return (Bitmap) answers.get("Q3");
    }

    public static String getAudio() {
        return (String) answers.getOrDefault("recording", "");
    }

    public static String getGPSLocation() {
        return (String) answers.getOrDefault("gps", "");
    }

    public static String getSubmissionTime() {
        return (String) answers.getOrDefault("submit_time", "");
    }

    public static String getSelfiePath() {
        return (String) answers.getOrDefault("selfie_path", "");
    }

    public static void saveSubmission(String timestamp, String gpsCoordinates, Context context) {
        setSubmissionTime(timestamp);
        setGPSLocation(gpsCoordinates);

        // Convert answers to JSON
        JSONObject jsonObject = new JSONObject(answers);
        String jsonString = jsonObject.toString();

        // Save JSON file to internal storage or appropriate directory
        File file = new File(context.getFilesDir(), "submission.json");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(jsonString.getBytes());
            Log.d(TAG, "Submission saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error saving submission", e);
        }
    }

    public static String saveSelfie(Context context, Bitmap selfie) {
        String uniqueFileName = "selfie_" + System.currentTimeMillis() + ".jpg";
        File selfieFile = new File(context.getFilesDir(), uniqueFileName);

        try (FileOutputStream fos = new FileOutputStream(selfieFile)) {
            selfie.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Log.d(TAG, "Selfie saved at: " + selfieFile.getAbsolutePath());
            setSelfiePath(selfieFile.getAbsolutePath());  // Update AnswerStorage with the new path
            return selfieFile.getAbsolutePath();
        } catch (IOException e) {
            Log.e(TAG, "Error saving selfie", e);
            return null;
        }
    }
}
