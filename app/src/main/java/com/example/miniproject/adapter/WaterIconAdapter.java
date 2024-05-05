package com.example.miniproject.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.miniproject.R;

import java.util.List;

public class WaterIconAdapter extends ArrayAdapter<Integer> {

    public WaterIconAdapter(Context context, List<Integer> waterIconsList) {
        super(context, R.layout.list_water, waterIconsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Đảm bảo tạo ViewHolder và inflate layout cho mỗi item trong danh sách
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_water, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.waterIcon = convertView.findViewById(R.id.waterIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set icon cho ImageView từ resource ID
        viewHolder.waterIcon.setImageResource(getItem(position));

        return convertView;
    }

    private static class ViewHolder {
        ImageView waterIcon;
    }
}