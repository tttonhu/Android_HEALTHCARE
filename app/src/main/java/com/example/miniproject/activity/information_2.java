package com.example.miniproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.R;
import com.example.miniproject.Service.diaryService;
import com.example.miniproject.fragment.home_fragment;
import com.example.miniproject.mode.user;

public class information_2 extends AppCompatActivity {

    userDAO userDAO ;
    Context context;
    RadioButton btn_lose, btn_main, btn_gain;
    int user_id,protein_need,fat_need,carb_need;
    double BMI,BMR;
    String day;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_2);
        btn_lose = findViewById(R.id.btn_lose);
        btn_main = findViewById(R.id.btn_main);
        btn_gain = findViewById(R.id.btn_gain);
        context = this;
        userDAO = new userDAO(context);

        user_id = getIntent().getIntExtra("user_id",0);
        BMI = userDAO.getUser(user_id).getBMI();
        day = userDAO.getUser(user_id).getDay_signUp();

        if(BMI < 18.5 ){
            btn_gain.setChecked(false);
            Toast.makeText(getApplicationContext(),"Thieu can",Toast.LENGTH_LONG).show();
        }else  if(BMI > 18.5 && BMI < 24.9){
            int colorRGB = Color.rgb(96,131,52);
            String hexColor = String.format("#%06X", (0xFFFFFF & colorRGB));
            btn_main.setBackgroundColor(Color.parseColor(hexColor));
            btn_main.setEnabled(true);
            Toast.makeText(getApplicationContext(),"Binh thuong",Toast.LENGTH_LONG).show();
        }else {
            int colorRGB = Color.rgb(96,131,52);
            String hexColor = String.format("#%06X", (0xFFFFFF & colorRGB));
            btn_lose.setBackgroundColor(Color.parseColor(hexColor));
            btn_lose.setEnabled(true);
            Toast.makeText(getApplicationContext(),"Thua can",Toast.LENGTH_LONG).show();
        }

        btn_gain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aim = "Gain Weight";
                boolean updateAim = userDAO.updateAim(user_id, aim);
                if(updateAim == true){
                    protein_need = diaryService.proteinNeed_gam(context,user_id);
                    fat_need = diaryService.fatNeed_gam(context,user_id);
                    carb_need = diaryService.carbonNeed_gam(context,user_id);
                    boolean update_Nutrition = userDAO.updateNutritionNeed(user_id,protein_need,fat_need,carb_need);
                    if(update_Nutrition == true){
                        Intent intent = new Intent(information_2.this, MainActivity.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("day",day);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Khong thanh cong",Toast.LENGTH_LONG).show();
                }

            }
        });
        btn_lose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aim = "Lose Weight";
                boolean updateAim = userDAO.updateAim(user_id,aim);
                if(updateAim == true){
                    protein_need = diaryService.proteinNeed_gam(context,user_id);
                    fat_need = diaryService.fatNeed_gam(context,user_id);
                    carb_need = diaryService.carbonNeed_gam(context,user_id);
                    boolean update_Nutrition = userDAO.updateNutritionNeed(user_id,protein_need,fat_need,carb_need);
                    if(update_Nutrition == true){
                        Intent intent = new Intent(information_2.this, MainActivity.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("day",day);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Khong thanh cong",Toast.LENGTH_LONG).show();
                }

            }
        });
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aim = "Main Weight";
                boolean updateAim = userDAO.updateAim(user_id,aim);
                if(updateAim == true){
                    protein_need = diaryService.proteinNeed_gam(context,user_id);
                    fat_need = diaryService.fatNeed_gam(context,user_id);
                    carb_need = diaryService.carbonNeed_gam(context,user_id);
                    boolean update_Nutrition = userDAO.updateNutritionNeed(user_id,protein_need,fat_need,carb_need);
                    if(update_Nutrition == true){
                        Intent intent = new Intent(information_2.this, MainActivity.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("day",day);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Khong thanh cong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
