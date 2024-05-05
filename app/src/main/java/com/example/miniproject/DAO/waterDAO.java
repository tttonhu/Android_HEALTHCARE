package com.example.miniproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.mode.water;

public class waterDAO {
    private final CreateDataBase dbHelper;
    public waterDAO(Context context){
        this.dbHelper = new CreateDataBase(context);
    }

    public boolean addWater(int user_id, int total_water, int water_size,String time_update,String time_start, String time_end){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("total_water", total_water);
        values.put("user_id",user_id);
        values.put("water_size",water_size);
        values.put("time_update",time_update);
        values.put("time_start",time_start);
        values.put("time_end",time_end);
        long result = db.insert("water_table",null,values);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean total_water(int user_id, int total_water) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM user_table WHERE user_id = " + user_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("user_id", user_id);
            values.put("goal_water", total_water); // Here you are setting total_water
            db.insert("water_table", null, values);
            db.close();
            return true;
        } else {
            Log.e("Error", "Khong them duoc");
            db.close();
            return false;
        }

    }

    public water getWater(int user_id) {
        water water = new water();
        String query = "SELECT total_water FROM water_table WHERE user_id=?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(user_id)});

        if(cursor.moveToFirst()){
            int total_waterIndex = cursor.getColumnIndex("goal_water");
            int water_idIndex = cursor.getColumnIndex("water_id");
            int amount_waterIndex = cursor.getColumnIndex("amount_water");

            if(total_waterIndex !=-1 && water_idIndex !=-1  && amount_waterIndex !=-1){
                water.setGoal_water(cursor.getInt(total_waterIndex));
                water.setWater_id(cursor.getInt(water_idIndex));
                water.setAmount_water(cursor.getInt(amount_waterIndex));
            }
        }else{
            Log.e("Error", "khong ton tai");
        }
        cursor.close();
        return water;
    }
}
