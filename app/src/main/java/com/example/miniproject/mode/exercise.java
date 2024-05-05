package com.example.miniproject.mode;

public class exercise {
    private int exercise_id;
    private String exercise_name;
    private int exercise_calo;
    private int exercise_time;

    public exercise() {
    }

    public exercise(int exercise_id, String exercise_name, int exercise_calo, int exercise_time) {
        this.exercise_id = exercise_id;
        this.exercise_name = exercise_name;
        this.exercise_calo = exercise_calo;
        this.exercise_time = exercise_time;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int excercise_id) {
        this.exercise_id = excercise_id;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public int getExercise_calo() {
        return exercise_calo;
    }

    public void setExercise_calo(int exercise_calo) {
        this.exercise_calo = exercise_calo;
    }

    public int getExercise_time() {
        return exercise_time;
    }

    public void setExercise_time(int exercise_time) {
        this.exercise_time = exercise_time;
    }
}
