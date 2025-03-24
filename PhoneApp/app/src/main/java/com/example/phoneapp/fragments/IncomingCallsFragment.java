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
import com.example.phoneapp.databinding.FragmentIncomingCallsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IncomingCallsFragment extends Fragment {

    private FragmentIncomingCallsBinding binding;
    private DatabaseReference databaseReference;
    private String phoneNumber;

    public IncomingCallsFragment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize View Binding
        binding = FragmentIncomingCallsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("call_counts").child(phoneNumber).child("incoming");
        setupRealTimeUpdatesForIncoming();
        return view;
    }

    private void setupRealTimeUpdatesForIncoming() {
        Log.d(TAG,"Setting up real-time updates for incoming calls...");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long count = snapshot.getValue(Long.class);
                if (count ==null){
                    count = 0L;
                }
                Log.d(TAG,"incoming call count updated"+count);
                if (binding!=null){
                    binding.incomingCallCount.setText(String.valueOf(count));
                }else {
                    Log.d(TAG,"binding is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,"Error updating INCOMING call count",error.toException());
            }
        });
    }
}
