package com.example.miniproject.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.miniproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class waterAdapter extends BaseAdapter {
    Context context;
    List<String> time;
    String timeRemind, time_Start;
    TextView txt_timeRemind,txt_size;
    int size;


    public waterAdapter(Context context, ArrayList<String> time, String time_Start, int size) {
        this.context = context;
        this.time = time;
        this.time_Start = time_Start;
        this.size = size;
    }

    @Override
    public int getCount() {
        return time.size();
    }

    @Override
    public Object getItem(int position) {
        return time.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_water_tracker,parent,false);
        }
        txt_timeRemind = convertView.findViewById(R.id.txt_timeWater_listWaterTracker);
        txt_size = convertView.findViewById(R.id.txt_size_water_listWaterTracker);

        String time_show;
        if(position == 0){
            time_show = time_Start;
            txt_timeRemind.setText(time_show);
            txt_size.setText(String.valueOf(size));
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            try {
                Date date = sdf.parse(time_Start);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, position * 60);
                time_show = sdf.format(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                time_show = "Invalid time";
            }
            txt_timeRemind.setText(time_show);
            txt_size.setText(String.valueOf(size));
        }

        return convertView;
    }
}
