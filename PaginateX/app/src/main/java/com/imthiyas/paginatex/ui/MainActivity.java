package com.imthiyas.paginatex.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.imthiyas.paginatex.R;
import com.imthiyas.paginatex.viewmodels.UserViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private UserAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView errorTextView;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        errorTextView = findViewById(R.id.errorTextView);

        adapter = new UserAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe User List Updates
        userViewModel.getUsers().observe(this, users -> {
            adapter.setUserList(users);
            errorTextView.setVisibility(View.GONE);
        });

        // Observe Errors
        userViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(error);
            } else {
                errorTextView.setVisibility(View.GONE);
            }
        });

        // Retry on Error Click
        errorTextView.setOnClickListener(v -> userViewModel.retry());

        // Infinite Scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    adapter.setLoading(true);
                    userViewModel.loadUsers();
                    adapter.setLoading(false);
                    isLoading = false;
                }
            }
        });

        // Pull to Refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
            userViewModel.loadUsers();
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
