package com.example.miniproject.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.miniproject.DAO.foodDAO;
import com.example.miniproject.DAO.food_diaryDAO;
import com.example.miniproject.R;
import com.example.miniproject.activity.information_food;
import com.example.miniproject.mode.food;
import com.example.miniproject.mode.food_daily;

import java.util.List;

public class diaryFoodAdapter extends BaseAdapter {
    Context context;
    food_diaryDAO food_diaryDAO;
    int user_id, food_id, food_kcal, id_dairy,amount;
    String food_name, name_serving, meal;
    foodDAO foodDAO;
    List<food_daily>  listDiaryFood;
    ImageButton btn_delete;
    String daily_date;

    int one_serving;
    TextView txt_food_name,txt_food_kcal, txt_food_g,txt_meal,txt_amount;

    public diaryFoodAdapter(Context context, List<food_daily> listDiaryFood, int user_id,String daily_date){
        this.context = context;
        this.listDiaryFood = listDiaryFood;
        this.user_id = user_id;
        this.daily_date = daily_date;
    }

    @Override
    public int getCount() {
        return listDiaryFood.size();
    }

    @Override
    public Object getItem(int position) {
        return listDiaryFood.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_diary_food,parent,false);
        }
        food_daily food_daily = listDiaryFood.get(position);
        food_diaryDAO = new food_diaryDAO(context);
        foodDAO = new foodDAO(context);
        food_id = food_daily.getFood_id();

        id_dairy = food_daily.getId_daily_food();
        meal = food_daily.getMeal();
        amount = food_daily.getAmount();
        food food = foodDAO.informationFood(food_id);
        food_name = food.getFood_name();
        food_kcal = food.getFood_kcal();
        name_serving = food.getName_serving();
        one_serving = food.getOne_serving();
        txt_food_name = view.findViewById(R.id.txt_ed_name_ldf);
        txt_food_name.setText(food_name);
        txt_food_kcal = view.findViewById(R.id.txt_ed_kcal_ldf);
        txt_food_kcal.setText(String.valueOf(food_kcal)+ " kcal");
        txt_food_g = view.findViewById(R.id.txt_ed_gam_ldf);
        txt_meal = view.findViewById(R.id.txt_meal_ldf);
        txt_meal.setText(meal);
        txt_amount = view.findViewById(R.id.txt_amount_ldf);
        txt_amount.setText(String.valueOf(amount));
        btn_delete = view.findViewById(R.id.btn_delete_ldf);
        txt_food_g.setText(String.valueOf(one_serving)+ " g");

        txt_food_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(context, information_food.class);
                        intent.putExtra("food_id",food_id);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("day",daily_date);
                        context.startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkDeleteDiaryFood = food_diaryDAO.deleteDiaryFood(id_dairy);
                if(checkDeleteDiaryFood){
                    listDiaryFood.remove(position);
                    notifyDataSetChanged();

                    if (listener != null) {
                        listener.onFoodDiaryDeleted();
                    }
                    Log.e("Delete Success DiaryFood","Delete Successful");
                }
            }
        });

        return view;
    }
    public interface OnFoodDiaryDeletedListener {
        void onFoodDiaryDeleted();
    }
    private OnFoodDiaryDeletedListener listener;

    public void setOnFoodDiaryDeletedListener(OnFoodDiaryDeletedListener listener) {
        this.listener = listener;
    }
}
