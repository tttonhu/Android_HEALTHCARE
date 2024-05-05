package com.example.miniproject.mode;

public class food_daily {
    private int id_daily_food;
    private int food_id;
    private String daily_date;
    private String meal;
    private int amount;
    public food_daily(){

    }
    public food_daily(int id_daily_food, int food_id, String daily_date, String meal, int amount) {
        this.id_daily_food = id_daily_food;
        this.food_id = food_id;
        this.daily_date = daily_date;
        this.meal = meal;
        this.amount = amount;

    }

    public int getId_daily_food() {
        return id_daily_food;
    }

    public void setId_daily_food(int id_daily_food) {
        this.id_daily_food = id_daily_food;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getDaily_date() {
        return daily_date;
    }

    public void setDaily_date(String daily_date) {
        this.daily_date = daily_date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
