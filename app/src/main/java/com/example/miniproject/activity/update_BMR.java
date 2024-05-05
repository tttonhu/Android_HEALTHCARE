package com.example.miniproject.activity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.R;
import com.example.miniproject.Service.userService;
import com.example.miniproject.mode.user;

public class update_BMR extends AppCompatActivity {
    EditText ed_BMR,ed_height, ed_weight, ed_age,ed_gender,ed_activity_Level, ed_goal;
    ImageButton btn_done;
    String goal, gender, BMR_st, height_st, weight_st, age_st, bodyCondtion;
    Context context;
    userDAO userDAO;
    Spinner sp_gender, sp_goal;
    double BMR,BMI;
    int age, height, weight,user_id;
    ImageButton btn_cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_bmr);
        context = this;
        userDAO = new userDAO(context);
        ed_BMR = findViewById(R.id.ed_kcal_day_update_BMR);
        ed_height = findViewById(R.id.ed_height_update_BMR);
        ed_weight = findViewById(R.id.ed_weight_update_BMR);
        ed_age = findViewById(R.id.ed_age_update_BMR);
        sp_gender = findViewById(R.id.sp_gender_update_BMR);
        ed_activity_Level = findViewById(R.id.ed_activity_level_update_BMR);
        sp_goal = findViewById(R.id.sp_goal_update_BMR);
        btn_done = findViewById(R.id.btn_done_update_BMR);
        btn_cancel = findViewById(R.id.btn_cancel_updateBMR);


        user_id = getIntent().getIntExtra("user_id",0);
        if(user_id == 0 ){
            Toast.makeText(context, String.valueOf(user_id),Toast.LENGTH_SHORT).show();
        }

        BMR = userDAO.getUser(user_id).getBMR();
        BMI = userDAO.getUser(user_id).getBMI();
        height = userDAO.getUser(user_id).getHeight();
        weight = userDAO.getUser(user_id).getWeight();
        age = userDAO.getUser(user_id).getAge();
        gender = userDAO.getUser(user_id).getSex();
        goal = userDAO.getUser(user_id).getAim();

        ed_BMR.setText(String.valueOf(BMR));
        ed_height.setText(String.valueOf(height));
        ed_weight.setText(String.valueOf(weight));
        ed_age.setText(String.valueOf(age));

        String [] list_gender = {"Male","Female"};
        String [] list_goal = {"Gain Weight", "Main Weight", "Lose Weight"};

        ArrayAdapter<String> adapter_gender = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,list_gender);
        adapter_gender.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sp_gender.setAdapter(adapter_gender);
        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender= parent.getItemAtPosition(position).toString();
                Toast.makeText(context,gender,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter_goal = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,list_goal);
        adapter_goal.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sp_goal.setAdapter(adapter_goal);
        sp_goal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                goal = parent.getItemAtPosition(position).toString();
                Toast.makeText(context,goal,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm,null);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                TextView txt_Title, txt_content;
                Button btn_yes, btn_no;
                txt_Title = dialogView.findViewById(R.id.txt_title_dialog_confirm);
                txt_content = dialogView.findViewById(R.id.txt_context_dialog_confirm);
                btn_yes = dialogView.findViewById(R.id.btn_yes_cfDialog);
                btn_no = dialogView.findViewById(R.id.btn_no_cfDialog);

                txt_Title.setText("Confirm change your information");
                txt_content.setText("Are you sure you want to change the information ?");

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BMR_st = ed_BMR.getText().toString().trim();
                        height_st = ed_height.getText().toString().trim();
                        weight_st = ed_weight.getText().toString().trim();
                        age_st = ed_age.getText().toString().trim();

                        BMR = Double.parseDouble(BMR_st);
                        height = Integer.parseInt(height_st);
                        weight = Integer.parseInt(weight_st);
                        BMI = userService.user_BMI(weight_st,height_st);
                        bodyCondtion = userService.bodyCondition(context,user_id);

                        boolean checkUpdateUser = userDAO.updateUser(user_id,BMR,BMI,height,weight,age,gender,goal);
                        if(checkUpdateUser){
                            Toast.makeText(context,"Update Successful", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Update Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



               BMR_st = ed_BMR.getText().toString().trim();
               height_st = ed_height.getText().toString().trim();
               weight_st = ed_weight.getText().toString().trim();
               age_st = ed_age.getText().toString().trim();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
