package com.imthiyas.contactme.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "submissions")
public class Submission {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int gender;
    private int age;
    private String selfiePath;
    private String audioPath;
    private String gps;
    private String timestamp;

    // Constructor, Getters, and Setters
    public Submission(int gender, int age, String selfiePath, String audioPath, String gps, String timestamp) {
        this.gender = gender;
        this.age = age;
        this.selfiePath = selfiePath;
        this.audioPath = audioPath;
        this.gps = gps;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getGender() { return gender; }
    public int getAge() { return age; }
    public String getSelfiePath() { return selfiePath; }
    public String getAudioPath() { return audioPath; }
    public String getGps() { return gps; }
    public String getTimestamp() { return timestamp; }
}
