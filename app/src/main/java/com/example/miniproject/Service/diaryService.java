package com.example.miniproject.Service;

import static java.security.AccessController.getContext;

import android.content.Context;

import com.example.miniproject.DAO.exerciseDAO;
import com.example.miniproject.DAO.exercise_diaryDAO;
import com.example.miniproject.DAO.foodDAO;
import com.example.miniproject.DAO.food_diaryDAO;
import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.mode.exercise_daily;
import com.example.miniproject.mode.food;
import com.example.miniproject.mode.food_daily;
import com.example.miniproject.mode.user;

import java.util.ArrayList;


public class diaryService {
    public static int totalEaten(Context context,ArrayList<food_daily> listFoodDaily){
        int total_eaten,food_id, amount, kcal;
        total_eaten = 0;

        for (food_daily food_daily : listFoodDaily){
            foodDAO foodDAO = new foodDAO(context);
            food_id = food_daily.getFood_id();
            amount = food_daily.getAmount();
            kcal = foodDAO.informationFood(food_id).getFood_kcal();
            total_eaten = total_eaten + (amount* kcal);
        }
        return total_eaten;
    }

    public static int totalFat(Context context,ArrayList<food_daily> listFoodDaily){
        int totalFat, fat, amount, food_id;
        totalFat = 0;

        for(food_daily food_daily : listFoodDaily){
            foodDAO foodDAO = new foodDAO(context);
            food_id = food_daily.getFood_id();
            amount = food_daily.getAmount();
            fat = foodDAO.informationFood(food_id).getFood_fats();
            totalFat = totalFat + (amount * fat);
        }
        return totalFat;
    }


    public static int totalBurned(Context context, ArrayList<exercise_daily> listExerciseDaily){
        int totalBurned, exercise_id, time, kcal;
        totalBurned = 0;
        for (exercise_daily exerciseDaily : listExerciseDaily){
            exerciseDAO exerciseDAO = new exerciseDAO(context);
            exercise_diaryDAO exercise_diaryDAO = new exercise_diaryDAO(context);
            time = exerciseDaily.getExercise_daily_time();
            exercise_id = exerciseDaily.getExercise_id();
            kcal = totalExercise(context,exercise_id,time);
            totalBurned = totalBurned + kcal;
        }
        return totalBurned;
    }

    public static int totalExercise(Context context,int exercise_id,int time_Exercise){
        int kcal,time, totalExercise =0;
        exerciseDAO exerciseDAO = new exerciseDAO(context);
        exercise_diaryDAO exerciseDiaryDAO = new exercise_diaryDAO(context);
        time = exerciseDAO.informationExercise(exercise_id).getExercise_time();
        kcal = exerciseDAO.informationExercise(exercise_id).getExercise_calo();
        double tg = kcal / time;
        String result = String.format("%.2f", tg); // Định dạng số tg với hai chữ số sau dấu phẩy
        double roundedTg = Double.parseDouble(result);
        totalExercise = (int) Math.round(roundedTg*time_Exercise);
        return totalExercise;
    }

    public static int totalCarb (Context context,ArrayList<food_daily> listFoodDaily){
        int totalCarb, carb, amount, food_id;
        totalCarb =0;
        for(food_daily food_daily : listFoodDaily){
            foodDAO foodDAO = new foodDAO(context);
            food_id = food_daily.getFood_id();
            amount = food_daily.getAmount();
            carb = foodDAO.informationFood(food_id).getFood_carbs();

            totalCarb = totalCarb + (amount * carb);
        }
        return totalCarb;
    }

    public static int totalProtein (Context context, ArrayList<food_daily> listFoodDaily){
        int totalProtein, protein, amount, food_id;
        totalProtein = 0;
        for(food_daily food_daily : listFoodDaily){
            foodDAO foodDAO = new foodDAO(context);
            amount = food_daily.getAmount();
            food_id = food_daily.getFood_id();
            protein = foodDAO.informationFood(food_id).getFood_protein();

            totalProtein = totalProtein + (amount * protein);
        }
        return totalProtein;
    }

    public static int kcalNeed(Context context, int user_id){
        int BMR, BMR_need = 0;
        double BMI;
        userDAO userDAO = new userDAO(context);
        BMR = (int) userDAO.getUser(user_id).getBMR();
        BMI = userDAO.getUser(user_id).getBMI();
        if(BMI < 18.5){
            BMR_need = BMR + 500;
        }else if(BMI >= 18.5 && BMI <= 24.9){
            BMR_need = BMR;
        }else if(BMI >= 25.0 && BMI <= 29.9){
            BMR_need = BMR - 500;
        }else if(BMI >= 30.0 && BMI <= 34.9){
            BMR_need = BMR - 700;
        }else if(BMI >=35 && BMI <=39.9){
            BMR_need = BMR - 1000;
        }else if(BMI >= 40.0){
            BMR_need = BMR - 1500;
        }
        return BMR_need;
    }

    public static int proteinNeed_gam(Context context, int user_id){
        int proteinNeed,BMR, BMR_need, proteinNeed_gam;
        proteinNeed = 0;
        userDAO userDAO = new userDAO(context);
        String aim = userDAO.getUser(user_id).getAim();
        BMR = (int) userDAO.getUser(user_id).getBMR();
        if(aim.equals("Gain Weight")){
            BMR_need = BMR + 500;
            proteinNeed = BMR_need * 30/100;
        }else if(aim.equals("Main Weight")){
            BMR_need = BMR;
            proteinNeed = BMR_need * 35/100;
        }else if(aim.equals("Lose Weight")){
            BMR_need = BMR - 500;
            proteinNeed = BMR_need * 35/100;
        }
        proteinNeed_gam = proteinNeed/4;
        return proteinNeed_gam;
    }

    public static int carbonNeed_gam(Context context, int user_id){
        int carbonNeed, BMR, BMR_need, carbonNeed_gam;
        carbonNeed = 0;
        userDAO userDAO = new userDAO(context);
        String aim = userDAO.getUser(user_id).getAim();
        BMR = (int) userDAO.getUser(user_id).getBMR();
        if(aim.equals("Gain Weight")){
            BMR_need = BMR + 500;
            carbonNeed = BMR_need * 40/100;
        }else if(aim.equals("Main Weight")){
            BMR_need = BMR;
            carbonNeed = BMR_need * 35/100;
        }else if(aim.equals("Lose Weight")){
            BMR_need = BMR - 500;
            carbonNeed = BMR_need * 35/100;
        }
        carbonNeed_gam = carbonNeed/4;
        return carbonNeed_gam;
    }

    public static int fatNeed_gam(Context context, int user_id){
        int fatNeed, BMR, BMR_need, fatNeed_gam;
        fatNeed = 0;
        userDAO userDAO = new userDAO(context);
        String aim = userDAO.getUser(user_id).getAim();
        BMR = (int) userDAO.getUser(user_id).getBMR();
        if(aim.equals("Gain Weight")){
            BMR_need = BMR + 500;
            fatNeed = BMR_need * 30/100;
        }else if(aim.equals("Main Weight")){
            BMR_need = BMR;
            fatNeed = BMR_need * 30/100;
        }else if(aim.equals("Lose Weight")){
            BMR_need = BMR - 500;
            fatNeed = BMR_need * 30/100;
        }
        fatNeed_gam = fatNeed / 9;
        return fatNeed_gam;
    }

    public static int amountWater(int goal_water, int size,int user_id,Context context){
        userDAO userDAO = new userDAO(context);
        int amountWater = (int) Math.round(goal_water/size);
        return amountWater;
    }

    private static Context getContext() {
        return null;
    }
}
