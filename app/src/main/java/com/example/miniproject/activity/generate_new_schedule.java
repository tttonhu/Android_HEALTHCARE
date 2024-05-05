package com.example.miniproject.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.miniproject.R;
import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.DAO.waterDAO;
import com.example.miniproject.adapter.waterAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class generate_new_schedule extends AppCompatActivity {
    int user_id;
    Context context;
    EditText ed_goal_water,ed_size, ed_intervalReminder;
    ImageButton btn_back;
    int goalWater,weight,size = 250, time = 60;
    String time_start, time_end;
    userDAO userDAO;
    waterDAO waterDAO;
    Button btn_save,btn_timeStart, btn_timeEnd, btn_newShedule;
    GridView gv_waterTracker;
    double amount_Item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_new_schedule);
        context = this;
        user_id = getIntent().getIntExtra("user_id",0);
        if (user_id == 0){
            Toast.makeText(context,"Khong nhan duoc user_id", Toast.LENGTH_SHORT).show();
        }
        userDAO = new userDAO(context);
        waterDAO = new waterDAO(context);
        weight = userDAO.getUser(user_id).getWeight();
        goalWater = weight * 30;


        ed_goal_water = findViewById(R.id.ed_goalDrink_generate);
        ed_size = findViewById(R.id.ed_size_generate);
        ed_intervalReminder = findViewById(R.id.ed_intervalReminder_generate);
        btn_back = findViewById(R.id.btn_back_generate);
        btn_timeEnd = findViewById(R.id.btn_timeSleep_end_generate);
        btn_timeStart = findViewById(R.id.btn_timeSleep_start_generate);
        btn_newShedule= findViewById(R.id.btn_newShedule_generate);
        btn_save = findViewById(R.id.btn_save_genrate);
        gv_waterTracker = findViewById(R.id.gv_waterTracker_generate);


        ed_goal_water.setText(String.valueOf(goalWater));
        ed_size.setText(String.valueOf(size));
        ed_intervalReminder.setText(String.valueOf(time));
        btn_timeStart.setText("06:00");
        btn_timeEnd.setText("22:00");

        time_start = btn_timeStart.getText().toString().trim();
        setupGridViewWithAdapter(time_start,size,goalWater,context);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_newShedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar currentTime = Calendar.getInstance();
                int year = currentTime.get(Calendar.YEAR);
                int month = currentTime.get(Calendar.MONTH) + 1; // Tháng tính từ 0 nên cần cộng thêm 1
                int day = currentTime.get(Calendar.DAY_OF_MONTH);
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                String timeUpdate = String.format(Locale.getDefault(), "%02d/%02d/%d %02d:%02d", day, month, year, hour, minute);
                goalWater =Integer.parseInt( ed_goal_water.getText().toString().trim());
                size = Integer.parseInt(ed_size.getText().toString().trim());
                time = Integer.parseInt(ed_size.getText().toString().trim());
                time_start = btn_timeStart.getText().toString().trim();
                time_end = btn_timeEnd.getText().toString().trim();

                setupGridViewWithAdapter(time_start,size,goalWater,context);

                btn_newShedule.setVisibility(View.GONE);
                btn_save.setVisibility(View.VISIBLE);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean check_addWater = waterDAO.addWater(user_id,goalWater,size,timeUpdate,time_start,time_end);
                        if(check_addWater){
                            Toast.makeText(context,"Thanh cong",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

            }
        });
        btn_timeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        btn_timeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });


    }

    private void showTimePickerDialog() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        btn_timeStart.setText(time);
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void setupGridViewWithAdapter(String timeStart, int size, int goalWater,Context context) {
        double amountItem = goalWater / size;
        int roundedAmountItem = (int) Math.round(amountItem);

        GridView gridView = findViewById(R.id.gv_waterTracker_generate);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < roundedAmountItem; i++) {
            data.add(""); // Thêm các phần tử vào danh sách, có thể là null hoặc chuỗi trống tùy vào yêu cầu
        }
        waterAdapter adapter = new waterAdapter(context, data, timeStart, size);
        gridView.setAdapter(adapter);
    }
//    private void sendNotification(){
//        NotificationManager notificationManager = (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
//                .setContentTitle("Drink Water")
//                .setContentTitle("Time for drink water")
//                .setSmallIcon(R.drawable.water)
//                .setProgress(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManager.notify(1,builder.build());
//
//    }
}
