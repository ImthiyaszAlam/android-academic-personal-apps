package com.imthiyas.paginatex.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imthiyas.paginatex.model.User;
import com.imthiyas.paginatex.model.UserResponse;
import com.imthiyas.paginatex.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> userList = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private int currentPage = 1;
    private boolean isLoading = false;

    public LiveData<List<User>> getUsers() {
        if (userList.getValue() == null) {
            loadUsers();
        }
        return userList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadUsers() {
        if (isLoading) return;
        isLoading = true;

        RetrofitClient.getApiService().getUsers(currentPage).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                isLoading = false;
                if (response.isSuccessful() && response.body() != null) {
                    List<User> currentList = userList.getValue() != null ? new ArrayList<>(userList.getValue()) : new ArrayList<>();
                    currentList.addAll(response.body().getUsers());
                    userList.setValue(currentList);
                    currentPage = response.body().getNextPage();
                    errorMessage.setValue(null);  // Clear error when successful
                } else {
                    errorMessage.setValue("Failed to load data. Tap to retry.");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                isLoading = false;
                errorMessage.setValue("Network error. Tap to retry.");
            }
        });
    }

    public void retry() {
        loadUsers();
    }
}
