package com.example.miniproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.R;
import com.example.miniproject.fragment.user_fragment;

public class updateInformation extends AppCompatActivity {
    Button btn_BMR, btn_nutrition,btn_generate, btn_rate, btn_signOut;
    ImageButton btn_back;
    int user_id;
    Context context;
    String daily_date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.update_information);
        btn_BMR = findViewById(R.id.btn_update_BMR_user);
        btn_nutrition = findViewById(R.id.btn_nutrition_user);
        btn_generate = findViewById(R.id.btn_water_user);
        btn_rate = findViewById(R.id.btn_rate_user);
        btn_signOut = findViewById(R.id.btn_signout_user);
        btn_back = findViewById(R.id.btn_back_update_Information);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_id = bundle.getInt("user_id");
            daily_date = bundle.getString("day");
            Toast.makeText(context,String.valueOf(user_id),Toast.LENGTH_SHORT).show();
        }

        btn_BMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateInformation.this, update_BMR.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("day",daily_date);
                startActivity(intent);
            }
        });

        btn_nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateInformation.this,nutrition_setting.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("day",daily_date);
                startActivity(intent);
            }
        });
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateInformation.this, generate_new_schedule.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("day",daily_date);
                startActivity(intent);
            }
        });
        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateInformation.this, signupActivity.class);
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
