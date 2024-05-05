package com.example.miniproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.miniproject.DAO.userDAO;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.R;

import java.util.ArrayList;


public class displayuser extends AppCompatActivity{
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listuser);

        context = this;
        ListView listView = findViewById(R.id.list_user);

        userDAO userDao = new userDAO(context);
        ArrayList<String> userList = userDao.displayUser();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(adapter);
    }
}
