package com.example.miniproject.mode;

public class exercise_daily {
    private int id_exercise_daily;
    private int exercise_id;
    private String exercise_daily_date;
    private int exercise_daily_time;
    private int user_id;

    public exercise_daily(){}

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public exercise_daily(int id_exercise_daily, int exercise_id, String exercise_daily_date, int exercise_daily_time, int user_id) {
        this.id_exercise_daily = id_exercise_daily;
        this.exercise_id = exercise_id;
        this.exercise_daily_date = exercise_daily_date;
        this.exercise_daily_time = exercise_daily_time;
        this.user_id = user_id;
    }

    public int getId_exercise_daily() {
        return id_exercise_daily;
    }

    public void setId_exercise_daily(int id_exercise_daily) {
        this.id_exercise_daily = id_exercise_daily;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getExercise_daily_date() {
        return exercise_daily_date;
    }

    public void setExercise_daily_date(String exercise_daily_date) {
        this.exercise_daily_date = exercise_daily_date;
    }

    public int getExercise_daily_time() {
        return exercise_daily_time;
    }

    public void setExercise_daily_time(int exercise_daily_time) {
        this.exercise_daily_time = exercise_daily_time;
    }
}
