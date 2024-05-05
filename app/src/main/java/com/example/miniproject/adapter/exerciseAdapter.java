package com.example.miniproject.adapter;

import android.app.AlertDialog;
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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.miniproject.DAO.exerciseDAO;
import com.example.miniproject.R;
import com.example.miniproject.Service.diaryService;
import com.example.miniproject.mode.exercise;
import com.example.miniproject.DAO.exercise_diaryDAO;

import org.jetbrains.annotations.NotNull;

public class exerciseAdapter extends BaseAdapter implements Filterable {
    Context context;
    String name,daily_date;
    int kcal,user_id,exercise_id,kcal_burned;
    int time_pratice = 60;
    int time;
    exercise_diaryDAO exercise_diaryDAO;
    TextView txt_name, txt_kcal,txt_time;
    SearchView searchView;
    ImageButton btn_add, btn_delete;
    exerciseDAO exerciseDAO = new exerciseDAO(context);
    ArrayList<exercise> listExercise = new ArrayList<>();
    ArrayList<exercise> listExerciseOld = new ArrayList<>();

    public exerciseAdapter(Context context, ArrayList<exercise> listExercise, int user_id,String daily_date) {
        this.context = context;
        this.listExercise = listExercise;
        this.user_id = user_id;
        this.listExerciseOld = listExercise;
        this.daily_date = daily_date;
    }


    @Override
    public int getCount() {
        if (listExercise != null) {
            return listExercise.size();
        } else {
            return 0; // hoặc bất kỳ giá trị nào phù hợp với logic của bạn
        }
    }

    @Override
    public Object getItem(int position) {
        return listExercise.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView,@NotNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_food,parent,false);
        }
        exercise exercise = listExercise.get(position);
        exercise_diaryDAO = new exercise_diaryDAO(context);
        exerciseDAO = new exerciseDAO(context);
        exercise_id = exercise.getExercise_id();
        name = exerciseDAO.informationExercise(exercise_id).getExercise_name();
        kcal = exerciseDAO.informationExercise(exercise_id).getExercise_calo();
        time = exerciseDAO.informationExercise(exercise_id).getExercise_time();


        txt_name = convertView.findViewById(R.id.txt_ed_name_lf);
        txt_name.setText(name);
        txt_kcal = convertView.findViewById(R.id.txt_ed_kcal_lf);
        txt_kcal.setText(String.valueOf(kcal)+ " kcal");
        txt_time = convertView.findViewById(R.id.txt_ed_gam_lf);
        txt_time.setText(String.valueOf(time)+ " mins");
        searchView = convertView.findViewById(R.id.search_edp);

        btn_add = convertView.findViewById(R.id.btn_add_lf);
        btn_delete = convertView.findViewById(R.id.btn_delete_lf);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise exerciseSelected = listExercise.get(position);
                int exerSelect_id = exerciseSelected.getExercise_id();
                int exerSelect_kcal = exerciseSelected.getExercise_calo();
                int exerSelect_time = exerciseSelected.getExercise_time();
                String exerSelect_name = exerciseSelected.getExercise_name();

                AlertDialog.Builder dialogExercise = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_exercise,null);
                dialogExercise.setView(dialogView);
                AlertDialog dialog = dialogExercise.create();
                dialog.show();

                TextView txt_name, txt_kcal, txt_time;
                EditText ed_amount_time;
                ImageButton btn_cancel, btn_add_time, btn_minus_time;
                RadioButton btn_min;
                Button btn_addDiary;

                txt_name = dialogView.findViewById(R.id.txt_nameExercise_dialog);
                txt_kcal = dialogView.findViewById(R.id.txt_kcal_exercise_dialog);
                txt_time = dialogView.findViewById(R.id.txt_time_exercise_dialog);
                ed_amount_time = dialogView.findViewById(R.id.txt_time_dialog);
                txt_name.setText(exerSelect_name);
                txt_kcal.setText(String.valueOf(exerSelect_kcal)+" kcal");
                txt_time.setText(String.valueOf(exerSelect_time)+" min");
                ed_amount_time.setText(String.valueOf(time_pratice));

                btn_min = dialogView.findViewById(R.id.radioBtn_min);
                btn_add_time = dialogView.findViewById(R.id.btn_add_time_dialog);
                btn_minus_time = dialogView.findViewById(R.id.btn_minus_time_dialog);
                btn_cancel = dialogView.findViewById(R.id.btn_cancel_dialogExercise);
                btn_addDiary = dialogView.findViewById(R.id.btn_add_exercise_dialog);

                btn_add_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(time_pratice >= 1 ){
                            time_pratice = time_pratice + 1;
                            ed_amount_time.setText(String.valueOf(time_pratice));
                        } else if (time <=0 ) {
                            time_pratice = 1;
                            ed_amount_time.setText(String.valueOf(time_pratice));
                        }
                        else {
                            Toast.makeText(context,"Khong hop le", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btn_minus_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(time_pratice >=1 ){
                            time_pratice = time_pratice - 1;
                            ed_amount_time.setText(String.valueOf(time_pratice));
                        } else if (time_pratice <= 0) {
                            time_pratice = 1;
                            ed_amount_time.setText(time_pratice);
                        }else{
                            Toast.makeText(context,"Khong hop le", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_addDiary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,String.valueOf(time_pratice), Toast.LENGTH_SHORT).show();
                        boolean addDiary = exercise_diaryDAO.add_DiaryExercise(user_id,exerSelect_id,time_pratice,daily_date);
                        if(addDiary){
                            dialog.dismiss();
                           Toast.makeText(context,"Them daily exercise thang cong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheckedDelete = exerciseDAO.deleteExercise(exercise_id);
                if(isCheckedDelete){
                    listExercise.remove(position);
                    notifyDataSetChanged();
                    exerciseDAO.displayExercise();
                    notifyDataSetChanged();
                    Log.e("Success","Delete exercise success");
                    if (listener != null) {
                        listener.onExerciseDeleted();
                    }
                }else {
                    Log.e("Fail","FAIL");
                }
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if(search.isEmpty()){
                    listExercise = listExerciseOld;
                }else{
                    Toast.makeText(context,name,Toast.LENGTH_SHORT).show();
                    listExercise = exerciseDAO.searchExercise(search);
                    ArrayList<exercise> list = new ArrayList<>();
                    for (exercise exercise_check : listExercise){
                        if(exercise_check.getExercise_name().toLowerCase().contains(search.toLowerCase())){
                            list.add(exercise_check);
                        }
                    }
                    listExercise = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listExercise;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listExercise = (ArrayList<exercise>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface OnExerciseDeletedListener {
        void onExerciseDeleted();
    }

    private OnExerciseDeletedListener listener;

    public void setOnExerciseDeletedListener(OnExerciseDeletedListener listener) {
        this.listener = listener;
    }
}
