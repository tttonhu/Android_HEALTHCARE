package com.example.miniproject.Service;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.activity.information_1;
import com.example.miniproject.activity.information_2;

import java.text.DecimalFormat;

public class userService {
    public static String bodyCondition (Context context, int user_id){
        userDAO userDAO = new userDAO(context);
        double BMI = userDAO.getUser(user_id).getBMI();
        String bodyCondition;
        if(BMI < 18.5 && BMI > 0 ){
            bodyCondition = "Underweight";
        }else if(BMI >= 18.5 && BMI <= 24.9){
            bodyCondition = "Fit body";
        }else if(BMI >= 25.0 && BMI <= 29.9){
            bodyCondition = "Overweight";
        }else if(BMI >= 30.0 && BMI <= 34.9){
            bodyCondition = "Level 1 obesity";
        }else if(BMI >=35 && BMI <=39.9){
            bodyCondition = "Level 2 obesity";
        }else if(BMI >= 40.0){
            bodyCondition = "Level 3 obesity";
        }else {
           bodyCondition ="Invail information";
        }
        return bodyCondition;
    }

    public static int user_BMR_minimum(String height, String weight, String sex,String age) {
        int height_val = Integer.parseInt(height);
        int weight_val = Integer.parseInt(weight);
        int age_val = Integer.parseInt(age);
        double BMR = 0;
        if (sex.equals("Fe-male")) {
            BMR = (9.99 * weight_val) + (6.25 * height_val) - (4.92 * age_val) - 161;
        } else if (sex.equals("Male")) {
            BMR = (9.99 * weight_val) + (6.25 * height_val) - (4.92 * age_val) + 5;
        }
        int roundBMR = (int) Math.round(BMR);
        return roundBMR;
    }

    public static int user_BMR_Activity(int BMR, String day, String min) {
        int day_int = Integer.parseInt(day);
        int min_int = Integer.parseInt(min);
        int time_act = day_int * min_int;
        double BMR_act = 0;
        int r_BMR_act = 0;
        if (time_act >= 0 && time_act < 30) {
            BMR_act = BMR * 1.2;
        } else if (time_act >= 30 && time_act < 90) {
            BMR_act = BMR * 1.375;
        } else if (time_act >= 90 && time_act < 150) {
            BMR_act = BMR * 1.55;
        } else if (time_act >= 150 && time_act < 210) {
            BMR_act = BMR * 1.725;
        } else if (time_act > 210) {
            BMR_act = BMR * 1.9;
        }
        r_BMR_act = (int) Math.round(BMR_act);
        return r_BMR_act;
    }

    public static int user_BMI(String weight, String height){
        int height_val = Integer.parseInt(height);
        int weight_val = Integer.parseInt(weight);
        double height_m = height_val/100.00;
        double BMI = weight_val/(height_m*height_m);
        int BMI_int = (int) Math.round(BMI);
        return BMI_int;
    }

    public static double roundToTwoDecimals(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(number);
        return Double.parseDouble(formattedNumber);
    }

    public static String checkValue(String input, int condition_vl1, int condition_vl2, String error1, String error2){
        int input_vl = Integer.parseInt(input);
        String error_vl;
        if(input_vl <= condition_vl1 && input_vl > condition_vl2){
            error_vl = null;
        } else if (input.equals("")){
            error_vl = error2;
        }else{
            error_vl = error1;
        }
        return error_vl;
    }

}
