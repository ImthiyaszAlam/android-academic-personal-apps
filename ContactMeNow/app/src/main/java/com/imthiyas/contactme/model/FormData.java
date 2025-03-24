package com.imthiyas.contactme.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FormData {
    private int genderId;          // Q1: Choice ID for gender
    private Integer age;            // Q2: Age (compulsory)
    private String selfiePath;     // Q3: Path to captured selfie image
    private String audioPath;      // Path to audio recording file
    private String gpsCoordinates; // GPS Coordinates as "latitude,longitude"
    private String submitTime;

    public int getGenderId() {
        return genderId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }


    public String getSelfiePath() {
        return selfiePath;
    }

    public void setSelfiePath(String selfiePath) {
        this.selfiePath = selfiePath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(String gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("Q1", genderId);
            json.put("Q2", age);
            json.put("Q3", selfiePath != null ? selfiePath : "");
            json.put("recording", audioPath != null ? audioPath : "");
            json.put("gps", gpsCoordinates != null ? gpsCoordinates : "");
            json.put("submit_time", submitTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
