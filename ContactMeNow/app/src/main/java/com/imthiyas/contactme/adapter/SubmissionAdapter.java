package com.imthiyas.contactme.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.imthiyas.contactme.R;
import com.imthiyas.contactme.data.Submission;

import java.io.File;
import java.util.List;

public class SubmissionAdapter extends RecyclerView.Adapter<SubmissionAdapter.SubmissionViewHolder> {

    private List<Submission> submissions;
    private Context context;

    public SubmissionAdapter(Context context, List<Submission> submissions) {
        this.context = context;
        this.submissions = submissions;
    }

    @NonNull
    @Override
    public SubmissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_submission_row, parent, false);
        return new SubmissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubmissionViewHolder holder, int position) {
        Submission submission = submissions.get(position);
        if (submission.getGender() == 0) {
            holder.q1Text.setText("Male");
        } else if (submission.getGender() == 1) {
            holder.q1Text.setText("Female");
        } else {
            holder.q1Text.setText("Other");

        }


        // Set Q2 (Age)
        Integer age = submission.getAge();
        holder.q2Text.setText(age != null ? String.valueOf(age) : "N/A");

        // Set Q3 (Selfie Image)

        String selfiePath = submission.getSelfiePath(); // Get the image path
        Log.d("TAG", "onBindViewHolder: " + selfiePath);

        if (selfiePath != null && !selfiePath.isEmpty()) {
            File imageFile = new File(selfiePath);

            if (imageFile.exists()) {
                Glide.with(context)
                        .load(imageFile) // Load from the file
                        .into(holder.selfieImage);
            } else {
                // File path provided but file doesn't exist
                Glide.with(context)
                        .load(R.drawable.anna) // Default fallback image
                        .into(holder.selfieImage);
            }
        } else {
            // Null or empty file path
            Glide.with(context)
                    .load(R.drawable.anna) // Default fallback image
                    .into(holder.selfieImage);
        }

        String audioPath = submission.getAudioPath();
        if (audioPath != null && new File(audioPath).exists()) {
            holder.audioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.audioButton.setEnabled(true);
        } else {
            holder.audioButton.setText("Play");
            holder.audioButton.setEnabled(false);
        }

        // Set GPS Coordinates
        holder.gpsText.setText(submission.getGps() != null ? submission.getGps() : "N/A");

        // Set Submission Time
        holder.timeText.setText(submission.getTimestamp() != null ? submission.getTimestamp() : "N/A");
    }

    @Override
    public int getItemCount() {
        return submissions.size();
    }

    public static class SubmissionViewHolder extends RecyclerView.ViewHolder {
        TextView q1Text, q2Text, gpsText, timeText;
        ImageView selfieImage;
        Button audioButton;

        public SubmissionViewHolder(@NonNull View itemView) {
            super(itemView);
            q1Text = itemView.findViewById(R.id.q1_text);
            q2Text = itemView.findViewById(R.id.q2_text);
            gpsText = itemView.findViewById(R.id.gps_text);
            timeText = itemView.findViewById(R.id.time_text);
            selfieImage = itemView.findViewById(R.id.selfie_image);
            audioButton = itemView.findViewById(R.id.audio_button);
        }
    }
}
