package com.example.phoneapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.phoneapp.model.UserModel;
import com.example.phoneapp.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private LiveData<List<UserModel>>usersLiveData;


    public UserViewModel(){
        userRepository = new UserRepository();
        usersLiveData = userRepository.getUsersLiveData();
        userRepository.fetchUsers();
    }

    public LiveData<List<UserModel>>getUsersLiveData(){
        return usersLiveData;
    }
    public void addUser(UserModel user){
        userRepository.addUser(user);
    }

    public void updateUser(UserModel user){
        userRepository.updateUser(user);
    }

    public void deleteUser(String userId){
        userRepository.deleteUser(userId);
    }

}
