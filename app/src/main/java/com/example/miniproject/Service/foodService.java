package com.example.miniproject.Service;


import android.content.Context;
import android.util.Log;

import com.example.miniproject.common;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;
import com.example.miniproject.DAO.userDAO;

public class foodService {


   public static int valueCarbon(Context context, int user_id,double carbon){
       int carbonNeed = diaryService.carbonNeed_gam(context,user_id);
       double carbon_per = (carbon/carbonNeed) * 100;
       int carbon_per_round = (int) Math.round(carbon_per);
       return carbon_per_round;
   }
   public static int valueProtein(Context context,int user_id,double protein ){
       int proteinNeed = diaryService.proteinNeed_gam(context,user_id);
       double protein_per = (protein/proteinNeed) *100;
       int protein_per_round = (int) Math.round(protein_per);
       return protein_per_round;
   }
    public static int valueFat(Context context,int user_id,double fat ){
        int fatNeed = diaryService.fatNeed_gam(context,user_id);
        double fat_per = (fat/fatNeed) *100;
        int fat_per_round = (int) Math.round(fat_per);
        return fat_per_round;
    }

    public static boolean checkNutritionFood(Context context, int fat, int protein, int carb, int kcal){
       userDAO userDAO = new userDAO(context);
       int total_kcal = fat*9 + protein*4 + carb*4;
       if(total_kcal > kcal){
           return false;
       }else{
           return true;
       }
    }
} 
