package com.example.miniproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.miniproject.DAO.exerciseDAO;
import com.example.miniproject.R;
import com.example.miniproject.adapter.exerciseAdapter;
import com.example.miniproject.mode.exercise;
import com.example.miniproject.DAO.userDAO;

import java.util.ArrayList;

public class list_exercise extends AppCompatActivity {
    ImageButton btn_add, btn_back;
    ListView lvExercise;
    Context context;
    exerciseAdapter exerciseAdapter;
    ArrayList<exercise> exercisesList;
    exerciseDAO exerciseDAO;
    int user_id;
    String daily_date;
    SearchView searchView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id = getIntent().getIntExtra("user_id",user_id);
        daily_date = getIntent().getStringExtra("day");

        context = this ;
        Toast.makeText(context,daily_date,Toast.LENGTH_SHORT).show();
        setContentView(R.layout.exercise_display);
        btn_back = findViewById(R.id.btn_back_edp);
        btn_add = findViewById(R.id.btn_add_newexercise_edp);
        lvExercise = findViewById(R.id.lv_exercise);
        exerciseDAO = new exerciseDAO(context);
        exercisesList = exerciseDAO.displayExercise();
        exerciseAdapter = new exerciseAdapter(context,exercisesList,user_id,daily_date);
        searchView = findViewById(R.id.search_edp);
        lvExercise.setAdapter(exerciseAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                exerciseAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                exerciseAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(list_exercise.this, add_exercise.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("day",daily_date);
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(list_exercise.this, MainActivity.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("day",daily_date);
                startActivity(intent);
            }
        });

    }
}
