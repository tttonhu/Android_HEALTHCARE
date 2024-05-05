package com.example.miniproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.R;

public class getStart extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button  signup;
        TextView tv_login;
        setContentView(R.layout.getstart);
        signup = findViewById(R.id.btn_getstart);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getStart.this, signupActivity.class);
                startActivity(intent);
            }
        });
        tv_login =findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getStart.this, LogIn.class);
                startActivity(intent);
            }
        });
    }
}
