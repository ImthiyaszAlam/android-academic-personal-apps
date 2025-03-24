package com.example.meetingapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingapp.adapter.PersonAdapter;
import com.example.meetingapp.models.PersonModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private List<PersonModel> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        personList = new ArrayList<>();
        personList.add(new PersonModel("John Doe", R.drawable.person_meeting));
        personList.add(new PersonModel("Jane Smith", R.drawable.person_4));
        personList.add(new PersonModel("Sam Wilson", R.drawable.person_3));
        personList.add(new PersonModel("Alice Brown", R.drawable.person_4));
        personList.add(new PersonModel("John Doe", R.drawable.person_meeting));
        personList.add(new PersonModel("Jane Smith", R.drawable.person_4));
        personList.add(new PersonModel("Sam Wilson", R.drawable.person_3));
        personList.add(new PersonModel("Alice Brown", R.drawable.person_4));
        personList.add(new PersonModel("John Doe", R.drawable.person_meeting));
        personList.add(new PersonModel("Jane Smith", R.drawable.person_4));
        personList.add(new PersonModel("Sam Wilson", R.drawable.person_3));
        personList.add(new PersonModel("Alice Brown", R.drawable.person_4));
        personList.add(new PersonModel("John Doe", R.drawable.person_meeting));
        personList.add(new PersonModel("Jane Smith", R.drawable.person_4));
        personList.add(new PersonModel("Sam Wilson", R.drawable.person_3));
        personList.add(new PersonModel("Alice Brown", R.drawable.person_4));

        personAdapter = new PersonAdapter(this, personList);
        recyclerView.setAdapter(personAdapter);
    }
}