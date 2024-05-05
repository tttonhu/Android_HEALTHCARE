package com.example.miniproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.DAO.waterDAO;
import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.R;
import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.Service.diaryService;
import com.example.miniproject.Service.userService;
import com.example.miniproject.mode.user;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class user_result extends AppCompatActivity {
    userDAO userDAO ;
    waterDAO waterDAO;
    Context context;
    TextView txt_kcal, txt_BMI, txt_height, txt_weight, txt_condition, txt_water, txt_day;
    CreateDataBase db ;
    Button btn_continue;
    String bodyConditon;

    int user_id;
    user user = new user();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_result);
        user_id = getIntent().getIntExtra("user_id",0);
        if(user_id == 0 ){
            Toast.makeText(context, String.valueOf(user_id),Toast.LENGTH_SHORT).show();
        }
        int height, weight;
        double BMR;
        double BMI;
        double BMR_need = 0;
        txt_kcal = findViewById(R.id.txt_kcal_Result);
        txt_BMI = findViewById(R.id.txt_BMI_Result);
        txt_height =findViewById(R.id.txt_height_Result);
        txt_weight = findViewById(R.id.txt_weight_Result);
        txt_condition = findViewById(R.id.txt_condition_Result);
        txt_water = findViewById(R.id.txt_water_Result);
        txt_day = findViewById(R.id.txt_day_result);
        btn_continue = findViewById(R.id.btn_continue_Result);
        context = this;
        userDAO = new userDAO(context);

        user user = new user();
        user = userDAO.getUser(user_id);
        height = userDAO.getUser(user_id).getHeight();
        weight = userDAO.getUser(user_id).getWeight();
        BMI = userDAO.getUser(user_id).getBMI();
        BMR = diaryService.kcalNeed(context,user_id);
        bodyConditon = userService.bodyCondition(context,user_id);
        Toast.makeText(getApplicationContext(),String.valueOf(BMR),Toast.LENGTH_LONG).show();

        txt_kcal.setText(String.valueOf(BMR)+ " kcal");
        txt_BMI.setText(String.valueOf(BMI));
        txt_weight.setText(String.valueOf(weight)+" Kg");
        txt_height.setText(String.valueOf(height)+" cm");

        int water = weight * 30;
        waterDAO = new waterDAO(context);
        waterDAO.total_water(user_id,water);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        txt_day.setText(currentTime);

        txt_water.setText(String.valueOf(water)+ " ml");
        txt_condition.setText(bodyConditon);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_result.this, information_2.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });

    }
}
