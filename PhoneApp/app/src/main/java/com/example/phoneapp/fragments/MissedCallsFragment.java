package com.example.phoneapp.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.phoneapp.databinding.FragmentMissedCallsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MissedCallsFragment extends Fragment {

    private FragmentMissedCallsBinding binding;
    private DatabaseReference databaseReference;
    private String phoneNumber;

    public MissedCallsFragment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize View Binding
        binding = FragmentMissedCallsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("call_counts").child(phoneNumber).child("missed");
        setupRealTimeUpdateForMissed();

        return view;
    }

    private void setupRealTimeUpdateForMissed() {
        Log.d(TAG,"Setting up real-time updates for missed calls...");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long count = snapshot.getValue(Long.class);
                if (count == null){
                    count = 0L;
                }

                Log.d(TAG, "missed call count updated: " + count);
                if (binding!=null){
                    binding.missedCallCount.setText(String.valueOf(count));
                } else {
                    Log.e(TAG, "Binding is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e(TAG, "Error updating missed call count", error.toException());
            }
        });
    }

}
