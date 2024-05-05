package com.example.miniproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.R;
import com.example.miniproject.common;
import com.example.miniproject.fragment.home_fragment;
import com.example.miniproject.mode.user;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogIn extends AppCompatActivity {
    CreateDataBase dbHelper;
    Context context;
    userDAO userDAO;
    int user_id;

    FragmentManager fragmentManager;

    Button btn_SignUp, btn_SignIn,btn_continue;
    TextInputLayout textIPLayout_userName, textIPLayout_password;
    TextInputEditText  txtIPEdit_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);

        textIPLayout_userName = findViewById(R.id.txtIPLayout_userName_logIn);
        textIPLayout_password = findViewById(R.id.txtIPLayout_password_logIn);
        btn_SignIn = findViewById(R.id.log_signin);
        btn_SignUp = findViewById(R.id.log_signup);
        btn_continue = findViewById(R.id.btn_continue_Login);
        context = this;
        userDAO = new userDAO(context);
        dbHelper = new CreateDataBase(this);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this,displayuser.class);
                startActivity(intent);
            }
        });
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name, password;
                user_name = textIPLayout_userName.getEditText().getText().toString().trim();
                password = textIPLayout_password.getEditText().getText().toString().trim();
                user_id = userDAO.getUser_id(user_name).getUser_id();
                boolean isLoggedId = userDAO.checkUser(user_name, password);
                if(isLoggedId == true){
                    if ( !user_name.equals("") && !password.equals("")) {
                        Intent intent = new Intent(LogIn.this, MainActivity.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("day",formattedDate);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LogIn.this, "User name and Password not correct!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"User name and Password not correct",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, signupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        String user_name, password;
        user_name = textIPLayout_userName.getEditText().getText().toString().trim();
        password = textIPLayout_password.getEditText().getText().toString().trim();
        String er_user, er_pwd;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(user_name.equals("")){
                er_user = common.ERROR_NULL;
                textIPLayout_userName.setError(er_user);
            }else{
                textIPLayout_userName.setError(null);
            }
            if(password.equals("")){
                er_pwd = common.ERROR_NULL;
                textIPLayout_password.setError(er_pwd);
            }
            else{
                textIPLayout_password.setError(null);
            }
            if(!user_name.equals("") && !password.equals("")){
                int colorRGB = Color.rgb(96,131,52);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorRGB));
                btn_SignIn.setBackgroundColor(Color.parseColor(hexColor));
                btn_SignIn.setEnabled(true);
            }else{
                int colorRGB = Color.rgb(168,202,126);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorRGB));
                btn_SignIn.setBackgroundColor(Color.parseColor(hexColor));
                btn_SignIn.setEnabled(true);
            }
        }
        return true;
    }
}