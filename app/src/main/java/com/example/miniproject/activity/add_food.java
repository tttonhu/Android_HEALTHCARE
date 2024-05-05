package com.example.miniproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.miniproject.DAO.foodDAO;
import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.R;
import com.example.miniproject.fragment.food_fragment;

public class add_food extends AppCompatActivity {
    Context context;
    foodDAO foodDAO;
    EditText edName_food, edName_serving, edOne_serving;
    Button btn_continue;
    ImageButton btn_cancel;
    RadioButton btn_grams, btn_mililiter;
    String name_food, name_serving,one_serving,unit_of_measure = "gram",daily_date;
    CreateDataBase db;
    int user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food1);
        user_id = getIntent().getIntExtra("user_id", 0);
        daily_date = getIntent().getStringExtra("day");
        edName_food = findViewById(R.id.ed_namefood);
        edName_serving = findViewById(R.id.ed_nameserving);
        edOne_serving = findViewById(R.id.ed_oneserving);
        btn_grams = findViewById(R.id.btn_grams);
        btn_mililiter = findViewById(R.id.btn_mililiter);
        btn_continue = findViewById(R.id.btn_continue);
        btn_cancel = findViewById(R.id.btn_cancel_af1);
        context = this;
        foodDAO = new foodDAO(context);
        db = new CreateDataBase(this);

        btn_grams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unit_of_measure = "gram";
                btn_mililiter.setChecked(false);
            }
        });

        btn_mililiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unit_of_measure = "mililiter";
                btn_grams.setChecked(false);
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_food = edName_food.getText().toString();
                name_serving = edName_serving.getText().toString();
                one_serving = edOne_serving.getText().toString();
                int one_serving_int = Integer.parseInt(one_serving);
                if(name_food.equals("") || name_serving.equals("")||one_serving.equals("")){
                    Toast.makeText(add_food.this, "Please fill all the fields",Toast.LENGTH_LONG).show();
                }else{
                    if(foodDAO.checkFood(name_food)){
                        Toast.makeText(add_food.this,"Food Already Exists",Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                boolean addFoodSuccess = foodDAO.addFood(name_food,name_serving,one_serving_int,unit_of_measure);
                if(addFoodSuccess){
                    Toast.makeText(add_food.this,"Sucessful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(add_food.this, add_food2.class);
                    intent.putExtra("name_food",name_food);
                    intent.putExtra("user_id",user_id);
                    intent.putExtra("day",daily_date);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(add_food.this,"User sign up failed",Toast.LENGTH_LONG).show();
                }
                
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.add_food, new food_fragment());
                fragmentTransaction.addToBackStack(null);  // Add fragment to back stack
                fragmentTransaction.commit();
            }
        });
    }
}
