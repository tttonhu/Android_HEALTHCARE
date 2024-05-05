package com.example.miniproject.activity;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.DAO.food_diaryDAO;
import com.example.miniproject.R;
import com.example.miniproject.Service.foodService;
import com.example.miniproject.fragment.home_fragment;
import com.example.miniproject.mode.food;
import com.github.mikephil.charting.charts.PieChart;
import com.example.miniproject.DAO.foodDAO;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class information_food extends AppCompatActivity {
    Context context;
    TextView txt_fat,txt_carb,txt_protein,txt_fatDly,txt_proDly,txt_carDly,txt_kcalDly,txt_totalKcal;
    EditText ed_amount,ed_nameServing;
    PieChart pieChart;
    ProgressBar pro_carb, pro_fat, pro_protein, pro_kcal;
    Button btn_addDiary;
    int BMR;
    foodDAO foodDAO;
    userDAO userDAO;
    int amount1,one_serving;
    food_diaryDAO food_diaryDAO;
    String nameOfServing,meal,daily_date,name_Serving;
    ImageButton btn_back;


    int food_id,carb, kcal,protein,fat,kcalDly, pro_amount,car_amount,fat_amount,user_id,carb_need,pro_need,fat_need;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_food);
        context = this;

        food_id = getIntent().getIntExtra("food_id",0);
        user_id = getIntent().getIntExtra("user_id",0);
        daily_date = getIntent().getStringExtra("day");
        foodDAO = new foodDAO(context);
        userDAO = new userDAO(context);
        food_diaryDAO = new food_diaryDAO(context);

        carb = foodDAO.informationFood(food_id).getFood_carbs();
        one_serving = foodDAO.informationFood(food_id).getOne_serving();
        name_Serving = foodDAO.informationFood(food_id).getName_serving();
        kcal = foodDAO.informationFood(food_id).getFood_kcal();
        protein = foodDAO.informationFood(food_id).getFood_protein();
        nameOfServing = foodDAO.informationFood(food_id).getName_serving();
        fat = foodDAO.informationFood(food_id).getFood_fats();
        BMR = userDAO.getUser(user_id).getBMR();
        carb_need = userDAO.getUser(user_id).getCarb_need();
        pro_need = userDAO.getUser(user_id).getProtein_need();
        fat_need = userDAO.getUser(user_id).getFat_need();

        txt_carb = findViewById(R.id.txt_carbDly_inforFood);
        txt_protein = findViewById(R.id.txt_proDly_inforFood);
        txt_fat = findViewById(R.id.txt_fatDly_inforFood);
        txt_fatDly = findViewById(R.id.txt_fat_inforFood);
        txt_carDly = findViewById(R.id.txt_carb_inforFood);
        txt_proDly = findViewById(R.id.txt_protein_inforFood);
        txt_kcalDly = findViewById(R.id.txt_kcal_inforFood);
        ed_amount = findViewById(R.id.ed_number_inforFood);
        pieChart = findViewById(R.id.inforFood_chart);
        pro_carb = findViewById(R.id.pro_carb);
        pro_kcal = findViewById(R.id.pro_kcal);
        pro_fat = findViewById(R.id.pro_fat);
        pro_protein = findViewById(R.id.pro_pro);
        txt_totalKcal = findViewById(R.id.ed_nameserving_inforFood);
        btn_addDiary = findViewById(R.id.btn_add_diary_inforFood);
        btn_back = findViewById(R.id.btn_back_inforFood);

        int totalKcal = carb*4 + fat*9 + protein*4;
        double totalKcal_per = (totalKcal/BMR) * 100;
        int totalKcal_per_round = (int) Math.round(totalKcal_per);

        pro_kcal.setProgress(totalKcal_per_round);
        txt_totalKcal.setText(String.valueOf(totalKcal)+ " Kcal");
        ed_amount.addTextChangedListener(createTextWatcher1(txt_carb,carb,"Carb"));
        ed_amount.addTextChangedListener(createTextWatcher1(txt_fat,fat,"Fat"));
        ed_amount.addTextChangedListener(createTextWatcher1(txt_protein,protein,"Protein"));
        ed_amount.addTextChangedListener(createTextWatcher(txt_kcalDly,totalKcal,BMR,pro_kcal,"Kcal"));
        ed_amount.addTextChangedListener(createTextWatcher(txt_carDly,carb,carb_need,pro_carb,"Carbs"));
        ed_amount.addTextChangedListener(createTextWatcher(txt_proDly,protein,pro_need,pro_protein,"Protein"));
        ed_amount.addTextChangedListener(createTextWatcher(txt_fatDly,fat,fat_need,pro_fat,"Fats"));

        updatePieChart(carb, fat, protein);

        btn_addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_diaryfood,null);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                TextView txt_foodName = dialogView.findViewById(R.id.txt_nameFood_dialogDiary);
                txt_foodName.setText(foodDAO.informationFood(food_id).getFood_name());
                TextView txt_nameServing = dialogView.findViewById(R.id.txt_food_type_Dialog);
                txt_nameServing.setText("g");
                TextView txt_foodKcal = dialogView.findViewById(R.id.txt_food_kcal_Dialog);
                txt_foodKcal.setText(String.valueOf(foodDAO.informationFood(food_id).getFood_kcal())+" kcal");
                EditText ed_amount = dialogView.findViewById(R.id.ed_amount_dialog);

                RadioButton btn_gam, btn_food_type, btn_breakfast, btn_lunch, btn_dinner, btn_others;
                ImageButton btn_minus_amount, btn_add_amount,btn_cancel;
                Button btn_add_diary;

                btn_gam = dialogView.findViewById(R.id.radioBtn_gam);
                btn_food_type = dialogView.findViewById(R.id.radioBtn_type);
                btn_breakfast = dialogView.findViewById(R.id.radioBtn_Breakfast);
                btn_lunch = dialogView.findViewById(R.id.radioBtn_lunch);
                btn_dinner = dialogView.findViewById(R.id.radioBtn_Dinner);
                btn_others = dialogView.findViewById(R.id.radioBtn_Others);
                btn_minus_amount = dialogView.findViewById(R.id.btn_minus_amount_dialog);
                btn_add_amount = dialogView.findViewById(R.id.btn_add_amount_dialog);
                btn_add_diary = dialogView.findViewById(R.id.btn_add_to_Diary);
                btn_cancel = dialogView.findViewById(R.id.btn_cancel_dialogDiaryFood);

                btn_gam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_nameServing.setText("gam");
                        btn_food_type.setChecked(false);
                    }
                });
                btn_food_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_nameServing.setText(one_serving+ " " +name_Serving);
                        btn_gam.setChecked(false);
                    }
                });
                // BAT SU KIEN BUTTON CHON BUA AN
                btn_breakfast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meal = "Breakfast";
                        selectedMeal(btn_lunch,btn_dinner,btn_others);
                    }
                });
                btn_lunch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meal ="Lunch";
                        selectedMeal(btn_breakfast,btn_dinner,btn_others);
                    }
                });
                btn_dinner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meal = "Dinner";
                        selectedMeal(btn_breakfast,btn_lunch,btn_others);
                    }
                });
                btn_others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        meal = "Others";
                        selectedMeal(btn_breakfast,btn_lunch,btn_dinner);
                    }
                });

                btn_minus_amount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(amount1 <= 1){
                            amount1 = 1;
                            ed_amount.setText(String.valueOf(amount1));
                        } else if (amount1 > 1) {
                            amount1 = amount1 - 1;
                            ed_amount.setText(String.valueOf(amount1));
                        }
                    }
                });
                btn_add_amount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(amount1 <= 0){
                            amount1 = 1;
                            ed_amount.setText(String.valueOf(amount1));
                        } else if (amount1 > 0) {
                            amount1 = amount1 + 1;
                            ed_amount.setText(String.valueOf(amount1));
                        }
                    }
                });

                btn_add_diary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean addDiary = food_diaryDAO.add_DiaryFood(user_id,food_id,meal,amount1,daily_date);
                        if(addDiary){
                            dialog.dismiss();
                            Toast.makeText(context,"Add diary eaten Successful!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"THAT BAI", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void updatePieChart(int carb, int fat, int protein) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(carb, "Carb"));
        entries.add(new PieEntry(fat, "Fat"));
        entries.add(new PieEntry(protein, "Protein"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Nutrition");
        pieChart.setDrawSliceText(false);
        pieDataSet.setColors(PASTEL_COLORS);
        pieChart.setEntryLabelTextSize(30f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private TextWatcher createTextWatcher(final TextView textView, int input, int input2, ProgressBar pro, String type) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                afterChange(textView, input, input2, pro, type);
            }
        };
    }

    private TextWatcher createTextWatcher1(final TextView textView, int input, String type) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                afterChange2(textView, input, type);
            }
        };
    }

    public void afterChange(TextView textView, int value, int value2, ProgressBar pro, String type) {
        String amountStr = ed_amount.getText().toString().trim();
        if (amountStr.isEmpty()) {
            amountStr = "1";
        }
        double amount = Double.parseDouble(amountStr);
        double per_input = Math.round(((amount * value) / value2) * 100);
        textView.setText(String.valueOf(per_input) + " %" + type);
        pro.setProgress((int) per_input);
    }

    public void afterChange2(TextView textView, int input, String type) {
        int value;
        String amountStr = ed_amount.getText().toString().trim();
        if (amountStr.isEmpty()) {
            amountStr = "1";
        }
        int amount = Integer.parseInt(amountStr);
        value = input * amount;
        textView.setText(type + " " + String.valueOf(value) + " g");
    }

    public static final int[] PASTEL_COLORS = {
            rgb("#D7A49A"), rgb("#AFB796"), rgb("#9DB6CF")
    };

    public static void selectedMeal(RadioButton btn_2, RadioButton btn_3,RadioButton btn_4){
        btn_2.setChecked(false);
        btn_3.setChecked(false);
        btn_4.setChecked(false);
    }
}