package com.example.phoneapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.phoneapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {


    private DatabaseReference databaseReference;
    private MutableLiveData<List<UserModel>>usersLiveData = new MutableLiveData<>();
    FirebaseAuth firebaseAuth;


    public UserRepository() {
        databaseReference = FirebaseDatabase.getInstance().getReference("addedUsers");
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!=null){
            String uid = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("added_users");
        }
    }

    public MutableLiveData<List<UserModel>>getUsersLiveData(){
        return usersLiveData;
    }

    public void fetchUsers(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<UserModel>userList = new ArrayList<>();
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    UserModel user = postSnapshot.getValue(UserModel.class);
                    userList.add(user);
                }
                usersLiveData.setValue(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addUser(UserModel user) {
        String id = databaseReference.push().getKey();
        user.setId(id);
        databaseReference.child(id).setValue(user);
    }

    public void updateUser(UserModel user){
        databaseReference.child(user.getId()).setValue(user);
    }

    public void deleteUser(String userId){
        databaseReference.child(userId).removeValue();
    }
}
