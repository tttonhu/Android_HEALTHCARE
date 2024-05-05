package com.example.miniproject.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.miniproject.mode.exercise_daily;

import com.example.miniproject.Database.CreateDataBase;

import java.util.ArrayList;

public class exercise_diaryDAO {
    Context context;
    private final CreateDataBase dbHelper;
    public exercise_diaryDAO(Context context) {
        this.dbHelper = new CreateDataBase(context);
    }

    public boolean add_DiaryExercise(int user_id, int exercise_id, int time, String day) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("exercise_id", exercise_id);
        values.put("exercise_daily_time", time);
        values.put("daily_date", day);

        long result = db.insert("exercise_daily_table", null, values);

        return result != -1; // Trả về true nếu insert thành công, false nếu không thành công
    }

    public ArrayList<exercise_daily> displayDiaryExercise(int user_id, String day){
        ArrayList<exercise_daily> listExerciseDaily = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String[] input = {day,String.valueOf(user_id)};

        String query = "SELECT * FROM exercise_daily_table WHERE daily_date =? AND user_id =?";

        Cursor cursor = db.rawQuery(query,input);
        if(cursor.moveToFirst()){
            do{
                exercise_daily exerciseDaily = new exercise_daily();
                exerciseDaily.setId_exercise_daily(cursor.getInt(cursor.getColumnIndexOrThrow("id_exercise_daily")));
                exerciseDaily.setExercise_id(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_id")));
                exerciseDaily.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
                exerciseDaily.setExercise_daily_date(cursor.getString(cursor.getColumnIndexOrThrow("daily_date")));
                exerciseDaily.setExercise_daily_time(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_daily_time")));
                listExerciseDaily.add(exerciseDaily);
            }while (cursor.moveToNext());
        }
        return listExerciseDaily;
    }

    public exercise_daily informationExercise_daily(String day, int exercise_id, int user_id){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String query = "SELECT * FROM exercise_daily_table WHERE daily_date =? and exercise_id =? and user_id =? ";
        String[] input = {day, String.valueOf(exercise_id), String.valueOf(user_id)};
        Cursor cursor = db.rawQuery(query,input);
        exercise_daily exerciseDaily= new exercise_daily();
        if(cursor.moveToFirst()){
            exerciseDaily.setExercise_daily_time(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_daily_time")));
            exerciseDaily.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
            exerciseDaily.setExercise_daily_date(cursor.getString(cursor.getColumnIndexOrThrow("daily_date")));
            exerciseDaily.setExercise_id(cursor.getInt(cursor.getColumnIndexOrThrow("exercise_id")));
            cursor.close();
        }
        return exerciseDaily;
    }

    public boolean delete_diaryExercise(int id_diary){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String selection = "id_exercise_daily = ?";
        String [] input = {String.valueOf(id_diary)};
        int deletedRows = db.delete("exercise_daily_table",selection,input);
        db.close();
        return deletedRows >0;
    }

}
