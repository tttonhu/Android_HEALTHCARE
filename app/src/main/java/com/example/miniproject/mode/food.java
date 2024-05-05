package com.example.miniproject.mode;

public class food {
    private int food_id;
    private String food_name;
    private int food_kcal;
    private int food_protein;
    private int food_fats;
    private int one_serving;
    private String name_serving;
    private int food_carbs;
    private int food_fibers;
    private int food_sugar;
    private String unit_of_measure;

    public food(){
    }
    public food(int food_id, String food_name, int food_kcal, int food_protein, int food_fats,int one_serving, String name_serving,int food_carbs, int food_fibers, int food_sugar, String unit_of_measure ){
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_kcal = food_kcal;
        this.food_protein = food_protein;
        this.food_fats = food_fats;
        this.one_serving =one_serving;
        this.name_serving = name_serving;
        this.food_carbs = food_carbs;
        this.food_fibers = food_fibers;
        this.food_sugar = food_sugar;
        this.unit_of_measure = unit_of_measure;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_kcal() {
        return food_kcal;
    }

    public void setFood_kcal(int food_kcal) {
        this.food_kcal = food_kcal;
    }

    public int getFood_protein() {
        return food_protein;
    }

    public void setFood_protein(int food_protein) {
        this.food_protein = food_protein;
    }

    public int getFood_fats() {
        return food_fats;
    }

    public void setFood_fats(int food_fats) {
        this.food_fats = food_fats;
    }

    public String getName_serving() {
        return name_serving;
    }

    public int getOne_serving() {
        return one_serving;
    }

    public void setOne_serving(int one_serving) {
        this.one_serving = one_serving;
    }

    public void setName_serving(String food_serving) {
        this.name_serving = name_serving;    }

    public int getFood_carbs() {
        return food_carbs;
    }

    public void setFood_carbs(int food_carbs) {
        this.food_carbs = food_carbs;
    }

    public int getFood_fibers() {
        return food_fibers;
    }

    public void setFood_fibers(int food_fibers) {
        this.food_fibers = food_fibers;
    }

    public int getFood_sugar() {
        return food_sugar;
    }

    public void setFood_sugar(int food_sugar) {
        this.food_sugar = food_sugar;
    }

    public String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }
}
