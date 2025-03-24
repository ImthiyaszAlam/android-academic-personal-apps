package com.example.phoneapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.phoneapp.utils.SharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CallReceiver extends BroadcastReceiver {

    private static final String TAG = "CallReceiver";
    private DatabaseReference databaseReference;
    private String lastState;
    private String lastNumber;
    SharedPreference sharedPreference;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        sharedPreference=new SharedPreference(context);
        if (action != null && action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (number != null) {
                // Format the phone number to ensure it is 10 digits long
                number = formatPhoneNumber(number);

                // Initialize Firebase Database reference
                databaseReference = FirebaseDatabase.getInstance().getReference("call_counts").child(number);
                Log.d(TAG, "onReceive: state: " + state);

                if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                    // Incoming call ringing
                    Log.d(TAG, "Incoming call ringing from: " + number);

                    lastState = TelephonyManager.EXTRA_STATE_RINGING;
                    lastNumber = number;
                    // Do nothing here for outgoing call count

                } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                    // Call is answered or outgoing
                    Log.d(TAG, "Call in progress. State: " + state);
                    if (TelephonyManager.EXTRA_STATE_RINGING.equals(lastState)) {
                        // Incoming call answered
                        updateCallCount("incoming");
                    } else {
                        // Outgoing call
                        updateCallCount("outgoing");
                    }
                    // Reset the last state after the call starts
                    lastState = TelephonyManager.EXTRA_STATE_OFFHOOK;

                } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                    // Call ended
                    if (TelephonyManager.EXTRA_STATE_RINGING.equals(lastState)) {
                        // Missed call
                        Log.d(TAG, "Missed call from: " + lastNumber);
                        updateCallCount("missed");
                    }
                    // Reset state
                    lastState = TelephonyManager.EXTRA_STATE_IDLE;
                    lastNumber = null;
                }
            } else {
                Log.d(TAG, "Phone number is null");
            }
        }
    }

    private void updateCallCount(String callType) {
        databaseReference.child(callType).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Long count = task.getResult().getValue(Long.class);
                if (count == null) {
                    count = 0L;
                }
                databaseReference.child(callType).setValue(count + 1);
            } else {
                Log.e(TAG, "Error getting data", task.getException());
            }
        });
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() > 10) {
            return phoneNumber.substring(phoneNumber.length() - 10);
        }
        return phoneNumber;
    }
}
