package com.imthiyas.contactme.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface SubmissionDao {
    @Insert
    void insertSubmission(Submission submission);

    @Query("SELECT * FROM submissions ORDER BY timestamp DESC")
    List<Submission> getAllSubmissions();
}
