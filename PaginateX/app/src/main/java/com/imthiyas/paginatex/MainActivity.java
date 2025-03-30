package com.imthiyas.paginatex;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.imthiyas.paginatex.adapter.UserAdapter;
import com.imthiyas.paginatex.viewmodels.UserViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private UserViewModel userViewModel;
    private UserAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        adapter = new UserAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    adapter.setLoading(true);
                    new Handler().postDelayed(() -> {
                        userViewModel.loadUsers();
                        adapter.setLoading(false);
                        isLoading = false;
                    }, 1000);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            userViewModel.loadUsers();
            swipeRefreshLayout.setRefreshing(false);
        });

    }
}