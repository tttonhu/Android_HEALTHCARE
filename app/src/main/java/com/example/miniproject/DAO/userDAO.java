package com.example.miniproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.Context;

import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.mode.user;

import java.util.ArrayList;
import java.util.Date;

public class userDAO {
    private final CreateDataBase dbHelper;

    public userDAO(Context context) {
        this.dbHelper = new CreateDataBase(context);
    }

    public boolean insertData(String name, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", name);
        values.put("password", password);
        long result = db.insert("user_table", null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkUserName(String user_name) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table where user_name = ?", new String[]{user_name});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUser(String user_name, String password) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user_table WHERE user_name = ? AND password = ?", new String[]{user_name, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true; // Tồn tại bản ghi thỏa mãn điều kiện
        } else {
            return false; // Không tồn tại bản ghi thỏa mãn điều kiện
        }
    }

    public boolean updateInformation(int user_id, String min, String day, String weight,String height, String age, String sex,int BMR,double BMI,String day_signUp) {
        int min_val = Integer.parseInt(min);
        int day_val = Integer.parseInt(day);
        int height_val = Integer.parseInt(height);
        int weight_val = Integer.parseInt(weight);
        int age_val = Integer.parseInt(age);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("activity_day_week", day_val);
        values.put("activity_day", min_val);
        values.put("height", height_val);
        values.put("weight", weight_val);
        values.put("age", age_val);
        values.put("sex", sex);
        values.put("BMR",BMR);
        values.put("BMI",BMI);
        values.put("day_signUp",day_signUp);

        int rowsAffected = db.update("user_table", values, "user_id=?", new String[]{String.valueOf(user_id)});

        return rowsAffected > 0; // Trả về true nếu có dòng dữ liệu được cập nhật, ngược lại trả về false
    }

    public boolean updateAim (int user_id,String aim){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("aim",aim);
        int rowsAffected = db.update("user_table", values, "user_id=?",new String[]{String.valueOf(user_id)});
        return rowsAffected > 0;
    }

    public boolean updateNutritionNeed(int user_id, int protein_need, int fat_need, int carbon_need) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("protein_need", protein_need);
        values.put("fat_need", fat_need);
        values.put("carb_need", carbon_need);
        String[] whereArgs = { String.valueOf(user_id) };
        int rowAffected = db.update("user_table", values, "user_id=?", whereArgs);
        return rowAffected > 0;
    }

    public boolean updateNutritionNeed2(int user_id, int protein_need, int fat_need, int carbon_need,int BMR_need) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("protein_need", protein_need);
        values.put("fat_need", fat_need);
        values.put("carb_need", carbon_need);
        values.put("BMR",BMR_need);
        String[] whereArgs = { String.valueOf(user_id) };
        int rowAffected = db.update("user_table", values, "user_id=?", whereArgs);
        return rowAffected > 0;
    }

    public user getUser(int user_id){
        user user = new user();
        String query = "SELECT height, weight, BMR, BMI,user_id,aim,day_signUp,protein_need,fat_need,carb_need FROM user_table WHERE user_id=?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(user_id)});

        if(cursor.moveToFirst()){
           int heightIndex = cursor.getColumnIndex("height");
           int weightIndex = cursor.getColumnIndex("weight");
           int BMIIndex = cursor.getColumnIndex("BMI");
           int BMRIndex = cursor.getColumnIndex("BMR");
           int user_idIndex = cursor.getColumnIndex("user_id");
           int aim_idIndex = cursor.getColumnIndex("aim");
           int day_signUpIndex = cursor.getColumnIndex("day_signUp");
           int proteinIndex = cursor.getColumnIndex("protein_need");
           int carbIndex = cursor.getColumnIndex("carb_need");
           int fatIndex = cursor.getColumnIndex("fat_need");

           if(heightIndex !=-1 && weightIndex !=-1 && BMIIndex !=-1 && BMRIndex !=-1 && user_idIndex !=-1 && aim_idIndex !=-1 && day_signUpIndex !=-1 && proteinIndex !=-1 && carbIndex !=-1 &&fatIndex !=-1){
               user.setWeight(cursor.getInt(weightIndex));
               user.setHeight(cursor.getInt(heightIndex));
               user.setAge(cursor.getInt(BMIIndex));
               user.setBMI(cursor.getDouble(BMIIndex));
               user.setBMR(cursor.getInt(BMRIndex));
               user.setAim(cursor.getString(aim_idIndex));
               user.setDay_signUp(cursor.getString(day_signUpIndex));
               user.setProtein_need(cursor.getInt(proteinIndex));
               user.setCarb_need(cursor.getInt(carbIndex));
               user.setFat_need(cursor.getInt(fatIndex));
           }else {

           }
        }
        cursor.close();
        return user;
    }

    public user getUser_id(String user_name) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        user user = new user();
        String query = "SELECT user_id FROM user_table WHERE user_name =?";
        String [] input = {user_name};
        Cursor cursor = db.rawQuery(query,input);

        if(cursor.moveToFirst()){
            user.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
        }
        return user;
    }
    public ArrayList<String>  displayUser() {
        ArrayList<String> userList= new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM user_table", null);
            if (cursor.moveToFirst()) {
                do {
                    String fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    String min = cursor.getString(cursor.getColumnIndexOrThrow("activity_day"));
                    String day = cursor.getString(cursor.getColumnIndexOrThrow("activity_day_week"));
                    String age = cursor.getString(cursor.getColumnIndexOrThrow("age"));
                    String height = cursor.getString(cursor.getColumnIndexOrThrow("height"));
                    String weight = cursor.getString(cursor.getColumnIndexOrThrow("weight"));
                    String user_id = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));

                    String userString = "Full Name: " + fullName + "\n" +
                            "Email: " + email + "\n" +
                            "Password: " + password + "\n"+
                            "min: " + min +"\n" +
                            "day: " + day + "\n"+
                            "age: " + age + "\n" +
                            "height: " + height + "\n" +
                            "weight: " + weight + "\n" +
                            "user_id: " + user_id
                            ;

                    userList.add(userString);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return userList;
    }

    public boolean updateUser(int user_id, double BMR, double BMI, int height, int weight, int age, String gender,String aim){
        String BMR_st, BMI_st, height_st, weight_st, age_st;
        BMI_st = String.valueOf(BMI);
        BMR_st = String.valueOf(BMR);
        height_st = String.valueOf(height);
        weight_st = String.valueOf(weight);
        age_st = String.valueOf(age);

        ContentValues values = new ContentValues();
        values.put("BMR", BMR_st);
        values.put("BMI", BMI_st);
        values.put("height", height_st);
        values.put("weight", weight_st);
        values.put("age", age_st);
        values.put("sex", gender);
        values.put("aim", aim);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.update("user_table", values, "email=?", new String[]{String.valueOf(user_id)});

        return rowsAffected > 0;


    }
}