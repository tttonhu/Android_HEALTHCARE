package com.example.miniproject.adapter;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.miniproject.R;
import com.example.miniproject.activity.MainActivity;
import com.example.miniproject.fragment.food_fragment;
import com.example.miniproject.fragment.home_fragment;
import com.example.miniproject.mode.food;
import com.example.miniproject.DAO.foodDAO;
import com.example.miniproject.DAO.food_diaryDAO;
import com.example.miniproject.DAO.userDAO;

import java.util.ArrayList;
import java.util.List;
public class foodAdapter extends BaseAdapter implements Filterable {

    int food_id, amount = 1,food_gam,user_id,one_serving,food_kcal_int,food_amount;
    String food_name, meal="meal",daily_date,name_Serving;
    Context context;
    foodDAO foodDAO;
    food_diaryDAO food_diaryDAO;
    List<food> foodList, foodListOld;
    TextView txt_name, txt_calo,txt_g;
    ImageButton btn_add, btn_delete;
    String email;
    food_fragment foodFragment;

    private food_fragment.OnUserIDSelectedListener mListener;

    public void setListener(food_fragment.OnUserIDSelectedListener listener) {
        this.mListener = listener;
    }
    public foodAdapter(Context context,ArrayList<food> foodList, int user_id, String daily_date,String email) {
        this.context = context;
        this.foodList = foodList;
        this.user_id = user_id;
        this.foodListOld = foodList;
        this.daily_date = daily_date;
        this.email = email;
    }

    public foodAdapter(){}

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_food,parent,false);
        }

        food food = foodList.get(position);
        foodDAO = new foodDAO(context);
        food_diaryDAO = new food_diaryDAO(context);
        food_id = food.getFood_id();
        food_kcal_int = food.getFood_kcal();
        food_name = food.getFood_name();
        name_Serving = food.getName_serving();
        one_serving = food.getOne_serving();

        txt_name = view.findViewById(R.id.txt_ed_name_lf);
        txt_name.setText(food_name);
        txt_calo = view.findViewById(R.id.txt_ed_kcal_lf);
        txt_calo.setText(String.valueOf(food_kcal_int) + " kcal");
        txt_g = view.findViewById(R.id.txt_ed_gam_lf);


        btn_delete = view.findViewById(R.id.btn_delete_lf);
        btn_add = view.findViewById(R.id.btn_add_lf);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheckedDelete = foodDAO.deleteFood(food_id);
                if (isCheckedDelete) {
                    // Tìm vị trí của phần tử cần xóa trong foodList
                    int index = -1;
                    for (int i = 0; i < foodList.size(); i++) {
                        if (foodList.get(i).getFood_id() == food_id) {
                            index = i;
                            break;
                        }
                    }
                    if (index != -1) {
                        foodList.remove(index);
                        notifyDataSetChanged();
                        Log.e("Success", "Delete food success");
                    } else {
                        Log.e("Fail", "FAIL");
                    }
                }
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food selectedFood = foodList.get(position);
                int foodSelected_id = selectedFood.getFood_id();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_diaryfood,null);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                TextView txt_foodName = dialogView.findViewById(R.id.txt_nameFood_dialogDiary);
                txt_foodName.setText(selectedFood.getFood_name());
                TextView txt_nameServing = dialogView.findViewById(R.id.txt_food_type_Dialog);
                txt_nameServing.setText("g");
                TextView txt_foodKcal = dialogView.findViewById(R.id.txt_food_kcal_Dialog);
                txt_foodKcal.setText(String.valueOf(selectedFood.getFood_kcal())+" kcal");
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
                        if(amount <= 1){
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
                       if(amount <= 0){
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
                       boolean addDiary = food_diaryDAO.add_DiaryFood(user_id,foodSelected_id,meal,amount,daily_date);
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
        return view;
    }
    public static void selectedMeal(RadioButton btn_2, RadioButton btn_3,RadioButton btn_4){
            btn_2.setChecked(false);
            btn_3.setChecked(false);
            btn_4.setChecked(false);
    }

    // Tim kiem
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if(search.isEmpty()){
                    foodList = foodListOld;
                }else {
                    foodList = foodDAO.searchFood(search);
                    ArrayList<food> list = new ArrayList<>();
                    for(food food_check : foodList){
                        if(food_check.getFood_name().toLowerCase().contains(search.toLowerCase())){
                            list.add(food_check);
                        }
                    }
                    foodList = list;
                }
                FilterResults results = new FilterResults();
                results.values = foodList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                foodList = (ArrayList<food>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
