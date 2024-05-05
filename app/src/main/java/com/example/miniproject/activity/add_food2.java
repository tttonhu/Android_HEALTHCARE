package com.example.miniproject.activity;

import static com.example.miniproject.adapter.foodAdapter.selectedMeal;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.DAO.foodDAO;
import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.DAO.food_diaryDAO;

import com.example.miniproject.R;
import com.example.miniproject.Service.foodService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class add_food2 extends AppCompatActivity {

    TextInputLayout txtIPLayout_nutrition, txtIPLayout_kcal, txtIPLayout_fats, txtIPLayout_protein, txtIPLayout_carbs, txtIPLayout_fiber, txtIPLayout_sugar;
    TextInputEditText txtIPEdit_kcal, txtIPEdit_protein, txtIPEdit_carbs, txtIPEdit_fat, txtIPEdit_fiber, txtIPEdit_sugar,txtIPEdit_nutrition;
    TextView txt_kcal,txt_fat,txt_fiber,txt_sugar,txt_carbon, txt_protein,txt_value_fat,txt_value_carbon,txt_value_protein,txt_servingSize;
    Button btn_continue;
    int kcal, fats, protein, sugar, fiber,carb,user_id,food_id,one_serving,amount;
    String name_food,unit,name_Serving,meal,daily_date;
    food_diaryDAO food_diaryDAO;
    foodDAO foodDAO;
    userDAO userDAO;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food2);
        context = this;
        name_food = getIntent().getStringExtra("name_food");
        user_id = getIntent().getIntExtra("user_id",0);
        daily_date = getIntent().getStringExtra("day");
        if(user_id == 0 ){
            Toast.makeText(context, String.valueOf(user_id),Toast.LENGTH_SHORT).show();
        }

        foodDAO = new foodDAO(context);
        userDAO = new userDAO(context);
        food_diaryDAO = new food_diaryDAO(context);
        food_id = foodDAO.FoodID(name_food).getFood_id();
        unit = foodDAO.informationFood(food_id).getUnit_of_measure();
        one_serving = foodDAO.informationFood(food_id).getOne_serving();
        name_Serving = foodDAO.informationFood(food_id).getName_serving();

        txt_kcal = findViewById(R.id.txt_Kcal_af2);
        txt_fat = findViewById(R.id.txt_fat_af2);
        txt_fiber = findViewById(R.id.txt_fiber_af2);
        txt_sugar = findViewById(R.id.txt_sugar_af2);
        txt_carbon = findViewById(R.id.txt_carbs_af2);
        txt_protein = findViewById(R.id.txt_protein_af2);
        txt_value_fat = findViewById(R.id.txt_Dlv_fat_af2);
        txt_value_carbon = findViewById(R.id.txt_Dlv_carbon_af2);
        txt_value_protein = findViewById(R.id.txt_Dlv_protein_af2);
        txt_servingSize = findViewById(R.id.txt_servingSize_af2);

        txtIPLayout_nutrition = findViewById(R.id.ed_nutrition_af2);
        txtIPLayout_kcal = findViewById(R.id.ed_kcal_af2);
        txtIPLayout_fats = findViewById(R.id.ed_fat_af2);
        txtIPLayout_protein = findViewById(R.id.ed_protein_af2);
        txtIPLayout_fiber = findViewById(R.id.ed_fiber_af2);
        txtIPLayout_sugar = findViewById(R.id.ed_sugar_af2);
        txtIPLayout_carbs = findViewById(R.id.ed_carbs_af2);

        txtIPEdit_nutrition = findViewById(R.id.txtIPEdit_nutrition_af2);
        txtIPEdit_kcal = findViewById(R.id.txt_IPEdit_kcal_af2);
        txtIPEdit_carbs = findViewById(R.id.txt_IPEdit_carbs_af2);
        txtIPEdit_fat = findViewById(R.id.txt_IPEdit_fat_af2);
        txtIPEdit_protein = findViewById(R.id.txt_IPEdit_protein_af2);
        txtIPEdit_fiber = findViewById(R.id.txt_IPEdit_fiber_af2);
        txtIPEdit_sugar = findViewById(R.id.txt_IPEdit_sugar_af2);

        btn_continue = findViewById(R.id.btn_continue_af2);

        txt_servingSize.setText("100 "+ unit );

        txtIPEdit_nutrition.addTextChangedListener(textWatcher(txt_servingSize,unit));
        txtIPEdit_fat.addTextChangedListener(createTextWatcher_Fat(txt_fat,txtIPLayout_fats,txt_value_fat,context,user_id));
        txtIPEdit_carbs.addTextChangedListener(createTextWatcher_Carbon(txt_carbon,txtIPLayout_carbs,txt_value_carbon,context,user_id));
        txtIPEdit_protein.addTextChangedListener(createTextWatcher_Protein(txt_protein,txtIPLayout_protein,txt_value_protein,context,user_id));
        txtIPEdit_kcal.addTextChangedListener(textWatcher(txt_kcal,"kcal"));
        txtIPEdit_fiber.addTextChangedListener(textWatcher(txt_fiber,"g"));
        txtIPEdit_sugar.addTextChangedListener(textWatcher(txt_sugar,"g"));

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kcal = Integer.parseInt(txtIPLayout_kcal.getEditText().getText().toString().trim());
                fats = Integer.parseInt(txtIPLayout_fats.getEditText().getText().toString().trim());
                protein = Integer.parseInt(txtIPLayout_protein.getEditText().getText().toString().trim());
                fiber = Integer.parseInt(txtIPLayout_fiber.getEditText().getText().toString().trim());
                carb = Integer.parseInt(txtIPLayout_carbs.getEditText().getText().toString().trim());
                sugar = Integer.parseInt(txtIPLayout_sugar.getEditText().getText().toString().trim());
                boolean checkNutrition = foodService.checkNutritionFood(context,fats,protein,carb,kcal);
                if(checkNutrition == false){
                    AlertDialog.Builder confirm = new AlertDialog.Builder(context);
                    View confirmView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm,null);
                    confirm.setView(confirmView);
                    AlertDialog confirmDialog = confirm.create();
                    confirmDialog.show();

                    TextView txt_Title, txt_content;
                    Button btn_yes, btn_no;
                    txt_Title = confirmView.findViewById(R.id.txt_title_dialog_confirm);
                    txt_content = confirmView.findViewById(R.id.txt_context_dialog_confirm);
                    btn_yes = confirmView.findViewById(R.id.btn_yes_cfDialog);
                    btn_no = confirmView.findViewById(R.id.btn_no_cfDialog);
                    txt_Title.setText("Confirm information food!");
                    txt_content.setText("Can you take another look at the calories? The number seems to be wrong.");

                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkAddFood();
                        }
                    });
                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmDialog.dismiss();
                        }
                    });
                }else{
                   checkAddFood();
                }
            }
        });
    }

    public void afterChange_Fat(TextInputLayout txtIPLayout, TextView textView, Context context, int user_id){
        String fat = txtIPLayout.getEditText().getText().toString().trim();
        double fat_double = Double.parseDouble(fat);
        int value = foodService.valueFat(context,user_id,fat_double);
        textView.setText(String.valueOf(value)+ " %");
    }
    public void afterChange_Carbon(TextInputLayout txtIPLayout, TextView textView, Context context, int user_id){
        String carbon = txtIPLayout.getEditText().getText().toString().trim();
        double carbon_double = Double.parseDouble(carbon);
        int carbon_precent = foodService.valueCarbon(context, user_id,carbon_double);
        textView.setText(String.valueOf(carbon_precent)+ " %");
    }

    public void afterChange_Protein(TextInputLayout txtIPLayout, TextView textView, Context context, int user_id){
        String protein = txtIPLayout.getEditText().getText().toString().trim();
        double protein_double = Double.parseDouble(protein);
        int protein_precent = foodService.valueCarbon(context, user_id,protein_double);
        textView.setText(String.valueOf(protein_precent)+ " %");
    }
    private TextWatcher createTextWatcher_Protein(final TextView textView1, final TextInputLayout txt_IPLayout, final TextView textView2, Context context, int user_id){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView1.setText(s+ " g");
            }
            @Override
            public void afterTextChanged(Editable s){
                afterChange_Protein(txt_IPLayout,textView2,context,user_id);
            }
        };
    }
    private TextWatcher createTextWatcher_Carbon(final TextView textView1, final TextInputLayout txt_IPLayout, final TextView textView2, Context context, int user_id){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView1.setText(s+ " g");
            }
            @Override
            public void afterTextChanged(Editable s){
                afterChange_Protein(txt_IPLayout,textView2,context,user_id);
            }
        };
    }
    private TextWatcher createTextWatcher_Fat(final TextView textView1, final TextInputLayout txt_IPLayout, final TextView textView2,Context context, int user_id){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView1.setText(s+" g");
            }
            @Override
            public void afterTextChanged(Editable s){
                afterChange_Fat(txt_IPLayout,textView2,context,user_id);
            }
        };
    }
    private TextWatcher textWatcher(final TextView textView, String unit){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText(s + " " + unit);
            }
            @Override
            public void afterTextChanged(Editable s){
            }
        };
    }

    public void checkAddFood(){
        boolean checkUpdateFood = foodDAO.addFood2(name_food, kcal, fats, protein, fiber, carb, sugar);
        if (checkUpdateFood) {
            Toast.makeText(getApplicationContext(), "Add food Sucessfood", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_diaryfood, null);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.show();

            TextView txt_foodName = dialogView.findViewById(R.id.txt_nameFood_dialogDiary);
            txt_foodName.setText(name_food);
            TextView txt_nameServing = dialogView.findViewById(R.id.txt_food_type_Dialog);
            txt_nameServing.setText("g");
            TextView txt_foodKcal = dialogView.findViewById(R.id.txt_food_kcal_Dialog);
            txt_foodKcal.setText(String.valueOf(kcal) + " kcal");
            EditText ed_amount = dialogView.findViewById(R.id.ed_amount_dialog);

            RadioButton btn_gam, btn_food_type, btn_breakfast, btn_lunch, btn_dinner, btn_others;
            ImageButton btn_minus_amount, btn_add_amount, btn_cancel;
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
                    txt_nameServing.setText(one_serving + " " + name_Serving);
                    btn_gam.setChecked(false);
                }
            });
            // BAT SU KIEN BUTTON CHON BUA AN
            btn_breakfast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    meal = "Breakfast";
                    selectedMeal(btn_lunch, btn_dinner, btn_others);
                }
            });
            btn_lunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    meal = "Lunch";
                    selectedMeal(btn_breakfast, btn_dinner, btn_others);
                }
            });
            btn_dinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    meal = "Dinner";
                    selectedMeal(btn_breakfast, btn_lunch, btn_others);
                }
            });
            btn_others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    meal = "Others";
                    selectedMeal(btn_breakfast, btn_lunch, btn_dinner);
                }
            });

            btn_minus_amount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amount <= 1) {
                        amount = 1;
                        ed_amount.setText(String.valueOf(amount));
                    } else if (amount > 1) {
                        amount = amount - 1;
                        ed_amount.setText(String.valueOf(amount));
                    }
                }
            });
            btn_add_amount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amount <= 0) {
                        amount = 1;
                        ed_amount.setText(String.valueOf(amount));
                    } else if (amount > 0) {
                        amount = amount + 1;
                        ed_amount.setText(String.valueOf(amount));
                    }
                }
            });

            btn_add_diary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean addDiary = food_diaryDAO.add_DiaryFood(user_id, food_id, meal, amount, daily_date);
                    if (addDiary) {
                        dialog.dismiss();
                        Toast.makeText(context, "Add diary eaten Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(add_food2.this, MainActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("day",daily_date);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "THAT BAI", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Check Again", Toast.LENGTH_LONG).show();
        }
    }



}
