package com.example.miniproject;

import java.text.DecimalFormat;

public class common {
    public static String formatDouble(double input){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String output = decimalFormat.format(input);
        return output;
    }

    public static String ERROR_NULL = "Please enter this field!";
    public static String ERROR_CORRECT = "$ is not correct!";
    public static String ERROR_EXIST ="c already exist!";
}
