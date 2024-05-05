package com.example.miniproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.DAO.exerciseDAO;
import com.example.miniproject.DAO.foodDAO;
import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.R;
import com.example.miniproject.Service.userService;
import com.example.miniproject.common;

public class add_exercise extends AppCompatActivity {
    Context context;
    exerciseDAO exerciseDAO;
    EditText ed_exercise_name, ed_kcal_exercise,ed_exercise_time;
    Button btn_exercise_add;
    ImageButton btn_cancel;
    CreateDataBase db;
    int user_id;
    String name, kcal, time,daily_date;
    TextView txt_erName,txt_erTime,txt_erKcal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);
        user_id = getIntent().getIntExtra("user_id",0);
        daily_date = getIntent().getStringExtra("day");
        ed_exercise_name = findViewById(R.id.ed_exercise_name);
        ed_kcal_exercise = findViewById(R.id.ed_kcal_exercise);
        ed_exercise_time = findViewById(R.id.ed_exercise_time);
        txt_erName = findViewById(R.id.txt_error_nameExer_addNewExercise);
        txt_erKcal = findViewById(R.id.txt_error_kcalExer_addNewExercise);
        txt_erTime = findViewById(R.id.txt_error_timeExer_addNewExercise);
        btn_exercise_add = findViewById(R.id.btn_exercise_add);
        btn_cancel = findViewById(R.id.btn_cancel_anexercise);
        context = this;
        exerciseDAO= new exerciseDAO(context);
        db = new CreateDataBase(this);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_exercise.this,list_exercise.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("day",daily_date);
                startActivity(intent);
            }
        });

        btn_exercise_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ed_exercise_name.getText().toString();
                kcal = ed_kcal_exercise.getText().toString();
                time = ed_exercise_time.getText().toString();
                int kcal_int = Integer.parseInt(kcal);
                int time_int = Integer.parseInt(time);
                if(name.equals("")){
                    txt_erName.setText(common.ERROR_NULL);
                    txt_erName.setVisibility(View.VISIBLE);
                }
                if(kcal.equals("")){
                    txt_erKcal.setText(userService.checkValue(kcal,999999,0,common.ERROR_CORRECT.replace("$","Calo"),common.ERROR_NULL));
                    txt_erKcal.setVisibility(View.VISIBLE);
                }
                if (time.equals("")){
                    txt_erTime.setText(userService.checkValue(time,1440,0,common.ERROR_CORRECT.replace("$","Time"),common.ERROR_NULL));
                    txt_erTime.setVisibility(View.VISIBLE);
                }

                if(!name.equals("")&& !kcal.equals("") && !time.equals("")){
                    if(exerciseDAO.checkExercise(name)== true){
                        txt_erName.setText(common.ERROR_EXIST.replace("c","Name exercise"));
                        txt_erName.setVisibility(View.VISIBLE);
                    }else{
                        boolean addExercise = exerciseDAO.addExercise(name,kcal_int,time_int);
                        if (addExercise == true){
                            Intent intent = new Intent(add_exercise.this, list_exercise.class);
                            intent.putExtra("user_id",user_id);
                            intent.putExtra("day",daily_date);
                            startActivity(intent);
                        }else {
                            Toast.makeText(add_exercise.this,"Add Exercise Failed!",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            name = ed_exercise_name.getText().toString();
            kcal = ed_kcal_exercise.getText().toString();
            time = ed_exercise_time.getText().toString();
            if(name.equals("")){
                txt_erName.setText(common.ERROR_NULL);
                txt_erName.setVisibility(View.VISIBLE);
            }
            else{
                txt_erName.setVisibility(View.INVISIBLE);
            }
            if(!kcal.equals("")){
                txt_erKcal.setText(userService.checkValue(kcal,999999,0,common.ERROR_CORRECT.replace("$","Calo"),common.ERROR_NULL));
                txt_erKcal.setVisibility(View.VISIBLE);
            }else {
                txt_erKcal.setText(common.ERROR_NULL);
                txt_erKcal.setVisibility(View.VISIBLE);
            }
            if (!time.equals("")){
                txt_erTime.setText(userService.checkValue(time,1440,0,common.ERROR_CORRECT.replace("$","Time"),common.ERROR_NULL));
                txt_erTime.setVisibility(View.VISIBLE);
            }else {
                txt_erTime.setText(common.ERROR_NULL);
                txt_erTime.setVisibility(View.VISIBLE);
            }
            if(!name.equals("")&& !kcal.equals("") && time.equals("")){
                int colorRGB = Color.rgb(96,131,52);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorRGB));
                btn_exercise_add.setBackgroundColor(Color.parseColor(hexColor));
                btn_exercise_add.setEnabled(true);
            }else{
                int colorRGB = Color.rgb(168,202,126);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorRGB));
                btn_exercise_add.setBackgroundColor(Color.parseColor(hexColor));
                btn_exercise_add.setEnabled(true);
            }
        }
        return true;
    }
}
