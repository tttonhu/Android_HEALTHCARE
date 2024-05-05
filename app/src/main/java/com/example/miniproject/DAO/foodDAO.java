package com.example.miniproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.example.miniproject.Database.CreateDataBase;
import com.example.miniproject.mode.food;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class foodDAO {
    private final CreateDataBase dbHelper;
    public foodDAO(Context context) {
        this.dbHelper = new CreateDataBase(context);
    }
    public boolean addFood (String food_name, String name_serving,int one_serving,String unit_of_measure){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("food_name", food_name);
        values.put("name_serving", name_serving);
        values.put("unit_of_measure",unit_of_measure);
        values.put("one_serving", one_serving);
        long result = db.insert("food_table", null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean checkFood(String name_food){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from food_table where food_name = ?", new String[]{name_food});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addFood2(String food_name,int food_kcal, int food_fats, int food_protein, int food_fibers, int food_carbs, int food_sugar){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("food_kcal",food_kcal);
        values.put("food_fats", food_fats);
        values.put("food_protein", food_protein);
        values.put("food_fibers",food_fibers);
        values.put("food_carbs",food_carbs);
        values.put("food_sugar",food_sugar);

        int rowsAffected = db.update("food_table",values,"food_name=?",new String[]{food_name});
        return  rowsAffected > 0;
    }
    public ArrayList<food> displayFood(){
        ArrayList<food> listFood = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM food_table", null);

        if(cursor.moveToFirst()){
            do{
                food food = new food();
                food.setFood_id(cursor.getInt(cursor.getColumnIndexOrThrow("food_id")));
                food.setFood_name(cursor.getString(cursor.getColumnIndexOrThrow("food_name")));
                food.setFood_kcal( cursor.getInt(cursor.getColumnIndexOrThrow("food_kcal")));
                food.setFood_fats(cursor.getInt(cursor.getColumnIndexOrThrow("food_fats")));
                food.setFood_protein(cursor.getInt(cursor.getColumnIndexOrThrow("food_protein")));
                food.setFood_fibers(cursor.getInt(cursor.getColumnIndexOrThrow("food_fibers")));
                food.setFood_carbs(cursor.getInt(cursor.getColumnIndexOrThrow("food_carbs")));
                food.setFood_sugar(cursor.getInt(cursor.getColumnIndexOrThrow("food_sugar")));
                food.setOne_serving(cursor.getInt(cursor.getColumnIndexOrThrow("one_serving")));
                food.setName_serving(cursor.getString(cursor.getColumnIndexOrThrow("name_serving")));
                food.setUnit_of_measure(cursor.getString(cursor.getColumnIndexOrThrow("unit_of_measure")));
                listFood.add(food);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listFood;
    }

    public food informationFood(int food_id){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String query = "SELECT * FROM food_table WHERE food_id =?";

        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(food_id)});
        food food = new food();
        if(cursor.moveToFirst()){
            food.setFood_name(cursor.getString(cursor.getColumnIndexOrThrow("food_name")));
            food.setFood_kcal(cursor.getInt(cursor.getColumnIndexOrThrow("food_kcal")));
            food.setFood_fats(cursor.getInt(cursor.getColumnIndexOrThrow("food_fats")));
            food.setFood_carbs(cursor.getInt(cursor.getColumnIndexOrThrow("food_carbs")));
            food.setFood_protein(cursor.getInt(cursor.getColumnIndexOrThrow("food_protein")));
            food.setFood_fibers(cursor.getInt(cursor.getColumnIndexOrThrow("food_fibers")));
            food.setFood_sugar(cursor.getInt(cursor.getColumnIndexOrThrow("food_sugar")));
            food.setName_serving(cursor.getString(cursor.getColumnIndexOrThrow("name_serving")));
            food.setOne_serving(cursor.getInt(cursor.getColumnIndexOrThrow("one_serving")));
            food.setUnit_of_measure(cursor.getString(cursor.getColumnIndexOrThrow("unit_of_measure")));
            cursor.close();
        }else{
            Log.e("error information food", "khong co thong tin food de hien thi");
        }

        return food;
    }

    public food FoodID(String food_name){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String query = "SELECT * FROM food_table WHERE food_name =?";

        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(food_name)});
        food food = new food();
        if(cursor.moveToFirst()){
            food.setFood_id(cursor.getInt(cursor.getColumnIndexOrThrow("food_id")));
            cursor.close();
        }else{
            Log.e("error information food", "khong co thong tin food de hien thi");
        }
        return food;
    }

    public boolean deleteFood(int food_id){

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String selection = "food_id = ?";
        String[] selectionArgs = { String.valueOf(food_id) };
        int deletedRows = db.delete("food_table", selection, selectionArgs);
        db.close();
        return deletedRows > 0;
    }

    public ArrayList<food> searchFood(String name_food){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ArrayList<food> listFood = new ArrayList<>();
        String query = "SELECT * FROM food_table WHERE food_name LIKE ?";
        String[] input = {"%" + name_food + "%"};
        Cursor cursor = db.rawQuery(query,input);
        do {
            if(cursor.moveToFirst()){
                food food = new food();
                food.setFood_id(cursor.getInt(cursor.getColumnIndexOrThrow("food_id")));
                food.setFood_name(cursor.getString(cursor.getColumnIndexOrThrow("food_name")));
                food.setFood_kcal( cursor.getInt(cursor.getColumnIndexOrThrow("food_kcal")));
                food.setFood_fats(cursor.getInt(cursor.getColumnIndexOrThrow("food_fats")));
                food.setFood_protein(cursor.getInt(cursor.getColumnIndexOrThrow("food_protein")));
                food.setFood_fibers(cursor.getInt(cursor.getColumnIndexOrThrow("food_fibers")));
                food.setFood_carbs(cursor.getInt(cursor.getColumnIndexOrThrow("food_carbs")));
                food.setFood_sugar(cursor.getInt(cursor.getColumnIndexOrThrow("food_sugar")));
                food.setOne_serving(cursor.getInt(cursor.getColumnIndexOrThrow("one_serving")));
                food.setName_serving(cursor.getString(cursor.getColumnIndexOrThrow("name_serving")));
                food.setUnit_of_measure(cursor.getString(cursor.getColumnIndexOrThrow("unit_of_measure")));
                listFood.add(food);
            }
        }while(cursor.moveToNext());
        db.close();
        return listFood;
    }
}
