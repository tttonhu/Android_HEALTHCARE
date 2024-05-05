package com.example.miniproject.mode;

public class user {
    private int user_id;
    private String email;
    private String name;
    private String password;
    private int age;
    private int height;
    private int weight;
    private String sex;
    private int activity_day;
    private int activity_day_week;
    private double BMI;
    private int BMR;
    private int protein_need;
    private int fat_need;
    private int carb_need;
    private String aim;
    private String day_signUp;

    public user() {
    }

    public user(int user_id, String email, String name, String password, int age, int height, int weight, String sex, int activity_day, int activity_day_week, double BMI, int BMR, int protein_need, int fat_need, int carb_need, String aim, String day_signUp) {
        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.activity_day = activity_day;
        this.activity_day_week = activity_day_week;
        this.BMI = BMI;
        this.BMR = BMR;
        this.protein_need = protein_need;
        this.fat_need = fat_need;
        this.carb_need = carb_need;
        this.aim = aim;
        this.day_signUp = day_signUp;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getActivity_day() {
        return activity_day;
    }

    public void setActivity_day(int activity_day) {
        this.activity_day = activity_day;
    }

    public int getActivity_day_week() {
        return activity_day_week;
    }

    public void setActivity_day_week(int activity_day_week) {
        this.activity_day_week = activity_day_week;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public int getBMR() {
        return BMR;
    }

    public void setBMR(int BMR) {
        this.BMR = BMR;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getDay_signUp() {
        return day_signUp;
    }

    public void setDay_signUp(String day_signUp) {
        this.day_signUp = day_signUp;
    }

    public int getProtein_need() {
        return protein_need;
    }

    public void setProtein_need(int protein_need) {
        this.protein_need = protein_need;
    }

    public int getFat_need() {
        return fat_need;
    }

    public void setFat_need(int fat_need) {
        this.fat_need = fat_need;
    }

    public int getCarb_need() {
        return carb_need;
    }

    public void setCarb_need(int carb_need) {
        this.carb_need = carb_need;
    }
}
