package com.example.phoneapp.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.phoneapp.fragments.IncomingCallsFragment;
import com.example.phoneapp.fragments.MissedCallsFragment;
import com.example.phoneapp.fragments.OutgoingCallsFragment;

public class CallFragmentAdapter extends FragmentStateAdapter {

    String phoneNumber;

    public CallFragmentAdapter(@NonNull FragmentActivity fragmentActivity,String phoneNumber) {
        super(fragmentActivity);
        this.phoneNumber=phoneNumber;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber",phoneNumber);
        switch (position){
            case 0:
                fragment = new IncomingCallsFragment(phoneNumber);
                break;
            case 1:
                fragment = new OutgoingCallsFragment(phoneNumber);
                break;
            case 2:
                fragment = new MissedCallsFragment(phoneNumber);
                break;
            default:
                fragment = new IncomingCallsFragment(phoneNumber);
                break;
        }
        if (fragment!=null){
            fragment.setArguments(bundle);
        }
        return fragment;




    }



    @Override
    public int getItemCount() {
        return 3;
    }
}
