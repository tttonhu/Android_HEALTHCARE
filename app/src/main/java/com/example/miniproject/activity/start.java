package com.example.miniproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.R;

public class start extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(start.this, getStart.class);
                startActivity(intent);
                finish(); // Đóng Splash Screen
            }
        },SPLASH_DURATION);
    }
}
