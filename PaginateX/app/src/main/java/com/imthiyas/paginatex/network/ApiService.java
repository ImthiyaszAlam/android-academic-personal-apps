package com.imthiyas.paginatex.network;

import com.imthiyas.paginatex.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users")
        // Assuming API Endpoint
    Call<UserResponse> getUsers(@Query("page") int page);
}
