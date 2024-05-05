package com.example.miniproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.miniproject.Database.CreateDataBase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.example.miniproject.mode.exercise;

public class exerciseDAO {
    private final CreateDataBase dbHelper;
    public exerciseDAO(Context context) {
        this.dbHelper = new CreateDataBase(context);
    }

    public boolean addExercise(String exercise_name, int exercise_kcal, int exercise_time){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("exercise_name", exercise_name);
        values.put("exercise_kcal", exercise_kcal);
        values.put("exercise_time",exercise_time);
        long result = db.insert("exercise_table", null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean checkExercise(String exercise_name){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from exercise_table where exercise_name = ?", new String[]{exercise_name});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<exercise> displayExercise (){
        ArrayList<exercise> listExercise = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String query = "select * from exercise_table";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do{
                exercise exercise = new exercise();
                exercise.setExercise_id(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_id")));
                exercise.setExercise_name(cursor.getString(cursor.getColumnIndexOrThrow("exercise_name")));
                exercise.setExercise_calo(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_kcal")));
                exercise.setExercise_time(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_time")));
                listExercise.add(exercise);
            }while (cursor.moveToNext());
        }
        return listExercise;
    }
    public ArrayList<exercise> searchExercise(String exercise_name){
        ArrayList<exercise> listExercise = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String [] input = {"%" + exercise_name + "%"};
        String query = "SELECT * FROM exercise_table WHERE exercise_name LIKE ?";
        Cursor cursor = db.rawQuery(query,input);
       if(cursor.moveToFirst()){
           do{
               exercise exercise = new exercise();
               exercise.setExercise_id(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_id")));
               exercise.setExercise_name(cursor.getString(cursor.getColumnIndexOrThrow("exercise_name")));
               exercise.setExercise_time(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_time")));
               exercise.setExercise_calo(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_kcal")));
               listExercise.add(exercise);
           }while(cursor.moveToNext());
       }
        cursor.close();
        return listExercise;
    }

    public exercise informationExercise(int exercise_id){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String query = "SELECT * FROm exercise_table WHERE exercise_id = ?";
        String[] input = {String.valueOf(exercise_id)};
        Cursor cursor = db.rawQuery(query,input);
        exercise exercise = new exercise();
        if(cursor.moveToFirst()){
            exercise.setExercise_calo(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_kcal")));
            exercise.setExercise_time(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_time")));
            exercise.setExercise_id(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_id")));
            exercise.setExercise_name(cursor.getString(cursor.getColumnIndexOrThrow("exercise_name")));
            cursor.close();
        }
        return exercise;
    }

    public boolean deleteExercise(int exercise_id){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String selection = "exercise_id = ?";
        String[] selectionArgs = { String.valueOf(exercise_id) };
        int deletedRows = db.delete("exercise_table", selection, selectionArgs);
        db.close();
        return deletedRows > 0;
    }


}