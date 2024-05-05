package com.example.miniproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.R;
import com.example.miniproject.Service.userService;
import com.example.miniproject.common;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DecimalFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formattable;
import java.util.Locale;

public class information_1 extends AppCompatActivity {

    TextInputLayout txtIPLayout_min,txtIPLayout_day;
    TextInputLayout txtIPLayout_weight, txtIPLayout_height, txtIPLayout_age;
    TextInputEditText txtIPEdit_min, txtIPEdit_day;
    TextInputEditText txtIPEdit_weight, txtIPEdit_height, txtIPEdit_age;
    Button btn_continue;
    ImageButton btn_male, btn_female;
    String min, day, weight, height, age,sex,er_min, er_day, er_height, er_weight, er_age ;
    userDAO userDAO;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_1);
        context = this;
        int user_id = getIntent().getIntExtra("user_id",0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);

        btn_continue = findViewById(R.id.bnt_continue_inf1);
        btn_male = findViewById(R.id.img_btn_male);
        btn_female = findViewById(R.id.img_btn_female);
        txtIPLayout_min = findViewById(R.id.txtIPLayout_min_inf1); 
        txtIPLayout_day = findViewById(R.id.txtIPLayout_days_inf1);
        txtIPLayout_weight = findViewById(R.id.txtIPLayout_weight_if1);
        txtIPLayout_height = findViewById(R.id.txtIPLayout_height_inf1);
        txtIPLayout_age = findViewById(R.id.txtIPLayout_age_if1);
        txtIPEdit_min = findViewById(R.id.txtIPEdit_min_inf1);
        txtIPEdit_day = findViewById(R.id.txtEdit_days_if1);
        txtIPEdit_weight = findViewById(R.id.txt_IPEdit_weight_if1);
        txtIPEdit_height = findViewById(R.id.txt_IPEdit_height_if1);
        txtIPEdit_age = findViewById(R.id.txtIPEdit_age_inf1);
        context = this;
        userDAO = new userDAO(this);

        ScrollView scrollView3 = findViewById(R.id.scrollView3);

        scrollView3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    min = txtIPLayout_min.getEditText().getText().toString().trim();
                    day = txtIPLayout_day.getEditText().getText().toString().trim();
                    weight = txtIPLayout_weight.getEditText().getText().toString().trim();
                    height = txtIPLayout_height.getEditText().getText().toString().trim();
                    age = txtIPLayout_age.getEditText().getText().toString().trim();

                    String err_min, err_day, err_height, err_weight,err_age;
                    if(!min.equals("")){
                        er_min = userService.checkValue(min, 1440,0,common.ERROR_CORRECT.replace("$", "Min"), common.ERROR_NULL);
                        txtIPLayout_min.setError(er_min);
                    }
                    if(!day.equals("")){
                        er_day = userService.checkValue(day,7,0,common.ERROR_CORRECT.replace("$","Day"),common.ERROR_NULL);
                        txtIPLayout_day.setError(er_day);
                    }
                    if(!height.equals("")){
                        er_height = userService.checkValue(height,300,0,common.ERROR_CORRECT.replace("$","Height"),common.ERROR_NULL);
                        txtIPLayout_height.setError(er_height);
                    }
                    if(!weight.equals("")){
                        er_weight = userService.checkValue(weight,9999,0,common.ERROR_CORRECT.replace("$","Weight"), common.ERROR_NULL);
                        txtIPLayout_weight.setError(er_weight);
                    }
                    if(!age.equals("")){
                        er_age = userService.checkValue(age,300,0,common.ERROR_CORRECT.replace("$","Age"), common.ERROR_NULL);
                        txtIPLayout_age.setError(er_age);
                    }

                    return true;
                }
                return false;
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                min = txtIPLayout_min.getEditText().getText().toString().trim();
                day = txtIPLayout_day.getEditText().getText().toString().trim();
                weight = txtIPLayout_weight.getEditText().getText().toString().trim();
                height = txtIPLayout_height.getEditText().getText().toString().trim();
                age = txtIPLayout_age.getEditText().getText().toString().trim();

                if(!min.equals("") && !day.equals("") && !weight.equals("") && !height.equals("") && !age.equals("")){
                    er_min = userService.checkValue(min, 1440,0,common.ERROR_CORRECT.replace("$", "Min"), common.ERROR_NULL);
                    er_day = userService.checkValue(day,7,0,common.ERROR_CORRECT.replace("$","Day"),common.ERROR_NULL);
                    er_height = userService.checkValue(height,300,0,common.ERROR_CORRECT.replace("$","Height"),common.ERROR_NULL);
                    er_weight = userService.checkValue(weight,9999,0,common.ERROR_CORRECT.replace("$","Weight"), common.ERROR_NULL);
                    er_age = userService.checkValue(age,300,0,common.ERROR_CORRECT.replace("$","Age"), common.ERROR_NULL);
                    if(er_min == null && er_day ==null && er_height==null && er_weight==null && er_age == null){

                        int BMR_minimum = 0;
                        int BMR_act = 0;
                        double BMI = userService.user_BMI(weight,height);
                        double fmBMI_db = userService.roundToTwoDecimals(BMI);

                        if(sex != null){
                            BMR_minimum = userService.user_BMR_minimum(height,weight,sex,age);
                            BMR_act = userService.user_BMR_Activity(BMR_minimum,day,min);
                            boolean insert_information = userDAO.updateInformation(user_id,min,day,weight,height,age,sex,BMR_act,fmBMI_db,formattedDate);
                            if(insert_information == true){
                                Intent intent = new Intent(information_1.this, user_result.class);
                                intent.putExtra("user_id",user_id);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Khong thanh cong", Toast.LENGTH_LONG).show();
                            }
                        }

                    }else{
                        txtIPLayout_min.setError(er_min);
                        txtIPLayout_day.setError(er_day);
                        txtIPLayout_height.setError(er_height);
                        txtIPLayout_weight.setError(er_weight);
                        txtIPLayout_age.setError(er_age);
                    }
                }
            }
        });

        btn_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "Male";
                // Lấy icon từ drawable
                Drawable icon = ContextCompat.getDrawable(v.getContext(), R.drawable.male);

                // Bọc icon vào DrawableCompat để thực hiện thao tác thay đổi màu
                icon = DrawableCompat.wrap(icon);

                // Thay đổi màu của icon thành màu đỏ
                DrawableCompat.setTint(icon, Color.RED);

                btn_male.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(251, 201, 196)));

                // Cập nhật biểu tượng của ImageButton với màu đã được thay đổi
                btn_male.setImageDrawable(icon);
            }
        });

        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "Fe-male";
                // Lấy icon từ drawable
                Drawable icon = ContextCompat.getDrawable(v.getContext(), R.drawable.female);

                // Bọc icon vào DrawableCompat để thực hiện thao tác thay đổi màu
                icon = DrawableCompat.wrap(icon);

                // Thay đổi màu của icon thành màu đỏ
                DrawableCompat.setTint(icon, Color.RED);

                btn_female.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(251, 201, 196)));

                // Cập nhật biểu tượng của ImageButton với màu đã được thay đổi
                btn_female.setImageDrawable(icon);

            }
        });
    }
}
