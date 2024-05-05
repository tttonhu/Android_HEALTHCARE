package com.example.miniproject.activity;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.R;
import com.example.miniproject.Service.diaryService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.example.miniproject.DAO.userDAO;

import java.util.ArrayList;

public class nutrition_setting extends AppCompatActivity {
    String email, user_id_st,daily_date;
    Context context;
    int user_id, fat_need, pro_need,carb_need,kcal_pro,kcal_carb,kcal_fat ;
    double bmr_need,protein_percent, carb_percent, fat_percent;

    ImageButton btn_addCarb,btn_minusCarb,btn_addProtein,btn_minusProtein,btn_addFat,btn_minusFat,btn_back;
    SeekBar seekBar_carb, seekBar_protein,seekBar_fat;
    TextView txt_carb_g, txt_carb_de, txt_carb_kcal;
    TextView txt_pro_g, txt_pro_de,txt_pro_kcal;
    TextView txt_fat_g, txt_fat_de,txt_fat_kcal;
    ArrayList<PieEntry> entiers = new ArrayList<>();
    Button btn_save;
    userDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_setting);

        btn_addCarb = findViewById(R.id.btn_addCarbons_nutritionSetting);
        btn_minusCarb = findViewById(R.id.btn_minusCarbs_nutritionSetting);
        btn_addProtein = findViewById(R.id.btn_addProtein_nutritionSetting);
        btn_minusProtein = findViewById(R.id.btn_minusProtein_nutritionSetting);
        btn_addFat = findViewById(R.id.btn_addFat_nutritionSetting);
        btn_minusFat = findViewById(R.id.btn_minusFat_nutritionSetting);
        btn_save = findViewById(R.id.btn_save_nutritionSetting);
        btn_back = findViewById(R.id.btn_back_nutritionSetting);

        txt_carb_g = findViewById(R.id.txtCarbon_g_nutritionSetting);
        txt_carb_de = findViewById(R.id.txt_carbon_de_nutritionSetting);
        txt_carb_kcal = findViewById(R.id.txt_carbonKcal_nutritionSetting);

        txt_pro_g = findViewById(R.id.txt_protein_g_nutritionSetting);
        txt_pro_de = findViewById(R.id.txt_protein_de_nutritionSetting);
        txt_pro_kcal = findViewById(R.id.txt_proteinKcal_nutritionSetting);

        txt_fat_g = findViewById(R.id.txt_fat_g_nutritionSetting);
        txt_fat_de = findViewById(R.id.txt_fat_de_nutritionSetting);
        txt_fat_kcal = findViewById(R.id.txt_fatKcal_nutritionSetting);

        seekBar_carb = findViewById(R.id.seekBar_Carbon);
        seekBar_protein = findViewById(R.id.seekBar_Protein);
        seekBar_fat = findViewById(R.id.seekBar_Fat);

        context = this;
        userDAO = new userDAO(context);
        user_id = getIntent().getIntExtra("user_id",0);
        daily_date = getIntent().getStringExtra("day");

        bmr_need = diaryService.kcalNeed(context,user_id);
        Toast.makeText(context,String.valueOf(bmr_need),Toast.LENGTH_SHORT).show();
        carb_need = diaryService.carbonNeed_gam(context,user_id);
        pro_need = diaryService.proteinNeed_gam(context,user_id);
        fat_need = diaryService.fatNeed_gam(context,user_id);

        txt_carb_g.setText(String.valueOf(carb_need));
        txt_pro_g.setText(String.valueOf(pro_need));
        txt_fat_g.setText(String.valueOf(fat_need));

        kcal_carb = carb_need * 4;
        kcal_pro = pro_need * 4;
        kcal_fat = fat_need * 9;
        int BMR_need_update = kcal_carb + kcal_pro +kcal_fat;

        protein_percent = (kcal_pro/ bmr_need)*100 ;
        Toast.makeText(context,String.valueOf(protein_percent),Toast.LENGTH_SHORT).show();
        int roundeProtein_percent = (int) Math.round(protein_percent);

        carb_percent = (carb_need*4/ bmr_need) *100;
        int roundeCarbon_percent = (int) Math.round(carb_percent);

        fat_percent = (fat_need*9 / bmr_need) * 100;
        int roundeFat_percent = (int) Math.round(fat_percent);

        txt_carb_kcal.setText(String.valueOf(kcal_carb)+"kcal");
        txt_pro_kcal.setText(String.valueOf(kcal_pro)+"kcal");
        txt_fat_kcal.setText(String.valueOf(kcal_fat)+"kcal");

        txt_carb_de.setText(String.valueOf(roundeCarbon_percent)+ " %");
        txt_pro_de.setText(String.valueOf(roundeProtein_percent)+ " %");
        txt_fat_de.setText(String.valueOf(roundeFat_percent)+ " %");
        seekBar_protein.setProgress(roundeProtein_percent);
        seekBar_carb.setProgress(roundeCarbon_percent);
        seekBar_fat.setProgress(roundeFat_percent);

        PieChart pieChart = findViewById(R.id.nutrition_setting_chart);

        ArrayList<PieEntry> entiers = new ArrayList<>();
        entiers.add(new PieEntry(roundeCarbon_percent,"Carbon"));
        entiers.add(new PieEntry(roundeFat_percent,"Fat"));
        entiers.add(new PieEntry(roundeProtein_percent,"Protein"));

        PieDataSet pieDataSet = new PieDataSet(entiers,"Nutrition");
        pieChart.setDrawSliceText(false);
        pieDataSet.setColors(PASTEL_COLORS);
        pieChart.setEntryLabelTextSize(30f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();

        seekBar_protein.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_pro_de.setText(progress + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                protein_percent = seekBar_protein.getProgress();
                kcal_pro = (int) Math.round(protein_percent * bmr_need / 100);
                pro_need = (int) Math.round(kcal_pro / 4);
                txt_pro_g.setText(String.valueOf(pro_need)+ " g");
                txt_pro_kcal.setText(String.valueOf(kcal_pro)+ " kcal");
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Protein")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }

                PieEntry updatedProteinEntry = new PieEntry((float) protein_percent, "Protein");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);                updatePieChart(entiers);
            }
        });

        seekBar_fat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_fat_de.setText(progress +" %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                fat_percent = seekBar_fat.getProgress();
                kcal_fat = (int) Math.round(fat_percent*bmr_need/100);
                fat_need = (int) Math.round(kcal_fat/9);
                txt_fat_g.setText(String.valueOf(fat_need)+" g");
                txt_fat_kcal.setText(String.valueOf(kcal_fat)+" kcal");
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Fat")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }

                PieEntry updatedProteinEntry = new PieEntry((float) fat_percent, "Fat");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);
                updatePieChart(entiers);
            }
        });

        seekBar_carb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_carb_de.setText(progress+" %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                carb_percent = seekBar_carb.getProgress();
                kcal_carb = (int) Math.round(carb_percent*bmr_need/100);
                carb_need = (int) Math.round(kcal_carb/9);
                txt_carb_g.setText(String.valueOf(carb_need)+ " g");
                txt_carb_kcal.setText(String.valueOf(kcal_carb)+" kcal");
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Carbon")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }

                PieEntry updatedProteinEntry = new PieEntry((float) carb_percent, "Carbon");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);
                updatePieChart(entiers);
            }
        });

        btn_addFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = seekBar_fat.getProgress(); // Lấy giá trị hiện tại của seekBar
                int newValue = currentValue + 1; // Tăng giá trị lên 1 đơn vị
                kcal_fat = (int) Math.round(newValue*bmr_need/100);
                fat_need = (int) Math.round(kcal_fat/9);
                txt_fat_g.setText(String.valueOf(fat_need)+" g");
                txt_fat_kcal.setText(String.valueOf(kcal_fat)+" kcal");
                seekBar_fat.setProgress(newValue); // Cập nhật giá trị mới cho seekBar
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Fat")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }

                PieEntry updatedProteinEntry = new PieEntry((float) newValue, "Fat");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);
                updatePieChart(entiers);
            }
        });

        btn_addProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = seekBar_protein.getProgress(); // Lấy giá trị hiện tại của seekBar
                int newValue = currentValue + 1; // Tăng giá trị lên 1 đơn vị
                seekBar_protein.setProgress(newValue); // Cập nhật giá trị mới cho seekBar
                kcal_pro = (int) Math.round(newValue * bmr_need / 100);
                pro_need = (int) Math.round(kcal_pro / 4);
                txt_pro_g.setText(String.valueOf(pro_need)+ " g");
                txt_pro_kcal.setText(String.valueOf(kcal_pro)+ " kcal");
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Protein")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }

                PieEntry updatedProteinEntry = new PieEntry((float) newValue, "Protein");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);
                updatePieChart(entiers);
            }
        });

        btn_addCarb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = seekBar_carb.getProgress(); // Lấy giá trị hiện tại của seekBar
                int newValue = currentValue + 1; // Tăng giá trị lên 1 đơn vị
                seekBar_carb.setProgress(newValue); // Cập nhật giá trị mới cho seekBar
                kcal_carb = (int) Math.round(newValue*bmr_need/100);
                carb_need = (int) Math.round(kcal_carb/9);
                txt_carb_g.setText(String.valueOf(carb_need)+ " g");
                txt_carb_kcal.setText(String.valueOf(kcal_carb)+" kcal");
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Carbon")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }

                PieEntry updatedProteinEntry = new PieEntry((float) newValue, "Carbon");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);
                updatePieChart(entiers);
            }
        });

        btn_minusFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = seekBar_fat.getProgress(); // Lấy giá trị hiện tại của seekBar
                int newValue = currentValue - 1; // Tăng giá trị lên 1 đơn vị
                kcal_fat = (int) Math.round(newValue*bmr_need/100);
                fat_need = (int) Math.round(kcal_fat/9);
                txt_fat_g.setText(String.valueOf(fat_need)+" g");
                txt_fat_kcal.setText(String.valueOf(kcal_fat)+" kcal");
                seekBar_fat.setProgress(newValue); // Cập nhật giá trị mới cho seekBar
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Fat")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }

                PieEntry updatedProteinEntry = new PieEntry((float) newValue, "Fat");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);
                updatePieChart(entiers);
            }
        });
        btn_minusProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = seekBar_protein.getProgress(); // Lấy giá trị hiện tại của seekBar
                int newValue = currentValue - 1; // Tăng giá trị lên 1 đơn vị
                kcal_pro = (int) Math.round(newValue * bmr_need / 100);
                pro_need = (int) Math.round(kcal_pro / 4);
                txt_pro_g.setText(String.valueOf(pro_need)+ " g");
                txt_pro_kcal.setText(String.valueOf(kcal_pro)+ " kcal");
                seekBar_protein.setProgress(newValue); // Cập nhật giá trị mới cho seekBar
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Protein")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }

                PieEntry updatedProteinEntry = new PieEntry((float) newValue, "Protein");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);
                updatePieChart(entiers);
            }
        });
        btn_minusCarb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = seekBar_carb.getProgress(); // Lấy giá trị hiện tại của seekBar
                int newValue = currentValue - 1; // Tăng giá trị lên 1 đơn vị
                seekBar_carb.setProgress(newValue); // Cập nhật giá trị mới cho seekBar
                kcal_carb = (int) Math.round(newValue*bmr_need/100);
                carb_need = (int) Math.round(kcal_carb/9);
                txt_carb_g.setText(String.valueOf(carb_need)+ " g");
                txt_carb_kcal.setText(String.valueOf(kcal_carb)+" kcal");
                if (!entiers.isEmpty()) {
                    // Remove the existing PieEntry with label "Protein" if it exists
                    PieEntry existingProteinEntry = null;
                    for (PieEntry entry : entiers) {
                        if (entry.getLabel().equals("Carbon")) {
                            existingProteinEntry = entry;
                            break;
                        }
                    }
                    if (existingProteinEntry != null) {
                        entiers.remove(existingProteinEntry);
                    }
                }
                PieEntry updatedProteinEntry = new PieEntry((float) newValue, "Carbon");
                entiers.add(updatedProteinEntry);
                pieChart.setDrawSliceText(false);
                pieChart.setEntryLabelTextSize(30f);
                updatePieChart(entiers);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkUpdate = userDAO.updateNutritionNeed2(user_id,pro_need,fat_need,carb_need,BMR_need_update);
                if(checkUpdate){
                    Intent intent = new Intent(nutrition_setting.this, MainActivity.class);
                    intent.putExtra("day",daily_date);
                    intent.putExtra("user_id",user_id);
                    startActivity(intent);
                }
            }
        });

    }

    public static final int[] PASTEL_COLORS = {
            rgb("#D7A49A"), rgb("#AFB796"), rgb("#9DB6CF")
    };

    private void updatePieChart(ArrayList<PieEntry> entries) {
        PieChart pieChart = findViewById(R.id.nutrition_setting_chart);

        PieDataSet pieDataSet = new PieDataSet(entries, "Nutrition");
        pieDataSet.setColors(PASTEL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }
}
