package com.example.phoneapp.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phoneapp.databinding.FragmentOutgoingCallsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OutgoingCallsFragment extends Fragment {
    private FragmentOutgoingCallsBinding binding;
    private DatabaseReference databaseReference;
    private final String phoneNumber;

    public OutgoingCallsFragment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize View Binding
        binding = FragmentOutgoingCallsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("call_counts").child(phoneNumber).child("outgoing");
        setupRealTimeUpdateForOutgoing();

        return view;
    }

    private void setupRealTimeUpdateForOutgoing() {
        Log.d(TAG, "Setting up real-time updates for outgoing calls...");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long count = dataSnapshot.getValue(Long.class);
                if (count == null) {
                    count = 0L;
                }
                Log.d(TAG, "Outgoing call count updated: " + count);
                if (binding != null) {
                    binding.outgoingCallCount.setText(String.valueOf(count));
                } else {
                    Log.e(TAG, "Binding is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error updating outgoing call count", databaseError.toException());
            }
        });
    }
}
