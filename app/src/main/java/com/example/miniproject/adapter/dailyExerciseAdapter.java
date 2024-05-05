package com.example.miniproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.miniproject.DAO.exerciseDAO;
import com.example.miniproject.DAO.exercise_diaryDAO;
import com.example.miniproject.DAO.food_diaryDAO;
import com.example.miniproject.R;
import com.example.miniproject.Service.diaryService;
import com.example.miniproject.mode.exercise_daily;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class dailyExerciseAdapter extends BaseAdapter {
    ArrayList<exercise_daily> exercise_daily;
    Context context;
    int user_id,exercise_id,id_dairy;
    String daily_date;
    TextView txt_nameExercise, txt_time, txt_kcal;
    ImageButton btn_delete;
    exercise_diaryDAO exercise_diaryDAO;
    exerciseDAO exerciseDAO;
    String name;
    int kcal, time,kcal_burned;


    public dailyExerciseAdapter(Context context,ArrayList<exercise_daily> exercise_daily, int user_id,String daily_date) {
        this.context = context;
        this.exercise_daily = exercise_daily;
        this.user_id = user_id;
        this.daily_date = daily_date;
    }

    @Override
    public int getCount() {
        return exercise_daily.size();
    }

    @Override
    public Object getItem(int position) {
        return exercise_daily.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_diary_exercise,parent,false);
        }
        exercise_daily exerciseDaily = exercise_daily.get(position);

        exercise_diaryDAO = new exercise_diaryDAO(context);
        exerciseDAO = new exerciseDAO(context);
        exercise_id = exerciseDaily.getExercise_id();
        id_dairy = exerciseDaily.getId_exercise_daily();
        name = exerciseDAO.informationExercise(exercise_id).getExercise_name();
        time = exerciseDaily.getExercise_daily_time();
        kcal = diaryService.totalExercise(context,exercise_id,time);
        //kcal_burned = diaryService.totalBurned(context,daily_date,exercise_daily,user_id);

        txt_nameExercise = convertView.findViewById(R.id.txt_ed_name_lde);
        txt_nameExercise.setText(name);
        txt_kcal = convertView.findViewById(R.id.txt_ed_kcal_lde);
        txt_kcal.setText(String.valueOf(kcal)+" kcal");
        txt_time = convertView.findViewById(R.id.txt_ed_gam_lde);
        txt_time.setText(String.valueOf(time)+" mins");

        btn_delete = convertView.findViewById(R.id.btn_delete_lde);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheckDelete = exercise_diaryDAO.delete_diaryExercise(id_dairy);
                if(isCheckDelete){
                    int index = -1;
                    for (int i = 0; i < exercise_daily.size(); i++) {
                        if (exercise_daily.get(i).getId_exercise_daily()== id_dairy) {
                            index = i;
                            break;
                        }
                    }
                    if (index != -1) {
                        exercise_daily.remove(index);
                        notifyDataSetChanged();
                        Log.e("Success", "Delete food success");
                        if (listener != null) {
                            listener.onExerciseDeleted();
                        }

                    } else {
                        Log.e("Fail", "FAIL");
                    }
                }else{
                    Log.e("Fail","xoa daily exercise ko thanh cong");
                }
            }
        });
        return convertView;
    }
    public interface OnExerciseDeletedListener {
        void onExerciseDeleted();
    }

    private OnExerciseDeletedListener listener;

    public void setOnExerciseDeletedListener(OnExerciseDeletedListener listener) {
        this.listener = listener;
    }
}
