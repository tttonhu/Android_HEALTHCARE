package com.example.miniproject.mode;

public class water_daily {
    private int id_daily_water;
    private String day;
    private int amount;

    public water_daily() {
    }

    public int getId_daily_water() {
        return id_daily_water;
    }

    public void setId_daily_water(int id_daily_water) {
        this.id_daily_water = id_daily_water;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public water_daily(int id_daily_water, String day, int amount) {
        this.id_daily_water = id_daily_water;
        this.day = day;
        this.amount = amount;
    }
}
