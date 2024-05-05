package com.example.miniproject.mode;

public class water {
    private int goal_water;
    private int water_id;
    private int amount_water;
    private String time_update;
    private String time_Start;
    private String time_End;

    public water() {
    }

    public water(int goal_water, int water_id, int amount_water, String time_update, String time_Start, String time_End) {
        this.goal_water = goal_water;
        this.water_id = water_id;
        this.amount_water = amount_water;
        this.time_update = time_update;
        this.time_Start = time_Start;
        this.time_End = time_End;
    }

    public int getGoal_water() {
        return goal_water;
    }

    public void setGoal_water(int goal_water) {
        this.goal_water = goal_water;
    }

    public int getWater_id() {
        return water_id;
    }

    public void setWater_id(int water_id) {
        this.water_id = water_id;
    }

    public int getAmount_water() {
        return amount_water;
    }

    public void setAmount_water(int amount_water) {
        this.amount_water = amount_water;
    }

    public String getTime_update() {
        return time_update;
    }

    public void setTime_update(String time_update) {
        this.time_update = time_update;
    }

    public String getTime_Start() {
        return time_Start;
    }

    public void setTime_Start(String time_Start) {
        this.time_Start = time_Start;
    }

    public String getTime_End() {
        return time_End;
    }

    public void setTime_End(String time_End) {
        this.time_End = time_End;
    }
}