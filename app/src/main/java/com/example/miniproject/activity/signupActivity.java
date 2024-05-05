package com.example.miniproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.miniproject.DAO.userDAO;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.R;
import com.example.miniproject.mode.user;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +// toi thieu 1 so
                    "(?=.*[a-z])" + // toi thieu 1 chu cai thuong
                    "(?=.*[A-Z])" + // toi thieu 1 chu cai hoa
                    "(?=.*[!@#$%^&*+=])" + // toi thieu 1 ki tu dac biet
                    "(?=\\S+$)" + // khong co khoang trang
                    ".{8,}$"); // toi thieu 8 ki tu


    Context context;
    userDAO userDAO;
    int user_id;
    TextInputLayout textIPName,textIPPassword,textIPRePassword;
    TextInputEditText  txtIPEdit_Name, txtIPEdit_Password, txtIPEdit_RePassword;
    Button btnSignUp, btnGotoLogIn,btnDisplayUser;
    CreateDataBase db ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        textIPName = findViewById(R.id.txtIPLayout_fullname_signIn);
        txtIPEdit_Name = findViewById(R.id.txtIPEdit_fullname_signIn);
        textIPPassword = findViewById(R.id.txtIPLayout_password_signIn);
        txtIPEdit_Password = findViewById(R.id.txtIPEdit_password_signIn);
        textIPRePassword = findViewById(R.id.txtIPLayout_repassword_signIn);
        txtIPEdit_RePassword = findViewById(R.id.txtIPEdit_repassword_signIn);
        btnGotoLogIn = findViewById(R.id.btn_gotosignin);
        context = this;
        userDAO = new userDAO(context);
        db = new CreateDataBase(this);
        btnSignUp = findViewById(R.id.btn_signup);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,password, repwd;
                name = textIPName.getEditText().getText().toString().trim();
                password = textIPPassword.getEditText().getText().toString().trim();
                repwd = textIPRePassword.getEditText().getText().toString().trim();
                if (name.equals("")||password.equals("")||repwd.equals("")){
                }else {
                    if(userDAO.checkUser(name,password)){
                        textIPName.setError("User Already Exist");
                    }else{
                        boolean SignUpSccessfull = userDAO.insertData(name,password);
                        if(SignUpSccessfull == true){
                            int user_id = userDAO.getUser_id(name).getUser_id();
                            Toast.makeText(getApplicationContext(), String.valueOf(user_id), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signupActivity.this, information_1.class );
                            intent.putExtra("user_id", user_id);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        btnGotoLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupActivity.this, LogIn.class);
                startActivity(intent);
            }
        });

        textIPName.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText inputName = (TextInputEditText) v;
                    String inputData = inputName.getText().toString();
                    if (TextUtils.isEmpty(inputData)) {
                        textIPName.setError("          Please fill in this field");
                    } else {
                        textIPName.setError(null);
                    }
                }
            }
        });
        textIPPassword.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    TextInputEditText inputPassword = (TextInputEditText) v;
                    String inputData = inputPassword.getText().toString();
                    Matcher matcher = PASSWORD_PATTERN.matcher(inputData);

                    if (TextUtils.isEmpty(inputData)) {
                        textIPPassword.setError("          Please fill in this field");
                    } else if(!matcher.matches()) {
                        textIPPassword.setError("          Password can toi thieu 8 ki tu(1 ki tu so, 1 ki tu dac biet,1 ki tu hoa, 1 ki tu thuong");
                    }else {
                        textIPPassword.setError(null);
                    }
                }
            }
        });
        textIPRePassword.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText inputRePassword = (TextInputEditText) v;
                    String inputData = inputRePassword.getText().toString();

                    if (TextUtils.isEmpty(inputData)) {
                        textIPRePassword.setError("          Please fill in this field");
                    } else {
                        textIPRePassword.setError(null);
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
            // kiểm tra Full Name trống
            if (textIPName.getEditText().getText().toString().trim().isEmpty()) {
                textIPName.setError("          Please fill in this field");
            } else {
                textIPName.setError(null);
            }
            // Kiểm tra Password trống
            if(textIPPassword.getEditText().getText().toString().trim().isEmpty()){
                textIPPassword.setError("          Please fill in this field");
            }else{
                String passwordInput = textIPPassword.getEditText().getText().toString();
                Matcher matcher = PASSWORD_PATTERN.matcher(passwordInput);
                if(!matcher.matches()){
                    textIPPassword.setError("          Password can toi thieu 8 ki tu(1 ki tu so, 1 ki tu dac biet,1 ki tu hoa, 1 ki tu thuong");
                }else{
                    textIPPassword.setError(null);
                }
            }
            // kiểm tra RePassword trống
            if(textIPRePassword.getEditText().getText().toString().trim().isEmpty()){
                textIPRePassword.setError("          Please fill in this field");
            }else{
                textIPRePassword.setError(null);
            }
        }
        String name, password, repwd;
        name = textIPName.getEditText().getText().toString().trim();
        password = textIPPassword.getEditText().getText().toString().trim();
        repwd = textIPRePassword.getEditText().getText().toString().trim();
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (!name.equals("") && !password.equals("") && !repwd.equals("")) {
            int colorRGB = Color.rgb(96,131,52);
            String hexColor = String.format("#%06X", (0xFFFFFF & colorRGB));
            btnSignUp.setBackgroundColor(Color.parseColor(hexColor));
            btnSignUp.setEnabled(true);
        }
        else{
            int colorRGB = Color.rgb(203,221,181);
            String hexColor = String.format("#%06X", (0xFFFFFF & colorRGB));
            btnSignUp.setBackgroundColor(Color.parseColor(hexColor));
            btnSignUp.setEnabled(true);
        }
        return super.onTouchEvent(event);
    }
}