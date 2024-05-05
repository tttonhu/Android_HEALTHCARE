package com.example.miniproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.mode.food;
import com.example.miniproject.mode.food_daily;

import java.util.ArrayList;

public class food_diaryDAO{
    Context context;
    private final CreateDataBase dbHelper;

    public food_diaryDAO( Context context){
        this.dbHelper = new CreateDataBase(context);
    }

    public boolean add_DiaryFood(int user_id, int food_id, String meal, int amount,String daily_date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("food_id",food_id);
        values.put("meal",meal);
        values.put("amount",amount);
        values.put("daily_date",daily_date);

        long result = db.insert("Tbl_food_daily",null,values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<food_daily> displayDiaryFood(int user_id, String daily_date){
        ArrayList<food_daily> listDiaryFood = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        String query = "SELECT * FROM Tbl_food_daily WHERE user_id=? and daily_date =?";
        String[] input = {String.valueOf(user_id),daily_date};

        Cursor cursor = db.rawQuery(query,input);

        if(cursor.moveToFirst()){
            do{
                food_daily food_daily = new food_daily();
                food_daily.setId_daily_food(cursor.getInt(cursor.getColumnIndexOrThrow("id_daily_food")));
                food_daily.setFood_id(cursor.getInt(cursor.getColumnIndexOrThrow("food_id")));
                food_daily.setMeal(cursor.getString(cursor.getColumnIndexOrThrow("meal")));
                food_daily.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow("amount")));
                food_daily.setDaily_date(cursor.getString(cursor.getColumnIndexOrThrow("daily_date")));
                listDiaryFood.add(food_daily);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listDiaryFood;
    }

    public boolean deleteDiaryFood(int id_diary){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String key = "id_daily_food = ?";
        String [] values = {String.valueOf(id_diary)};
        int deleteRows = db.delete("Tbl_food_daily",key,values);
        db.close();
        return deleteRows > 0;
    }

}
