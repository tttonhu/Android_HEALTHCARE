    package com.example.miniproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;


import com.example.miniproject.DAO.foodDAO;
import com.example.miniproject.R;
import com.example.miniproject.activity.MainActivity;
import com.example.miniproject.activity.add_food;
import com.example.miniproject.adapter.foodAdapter;
import com.example.miniproject.mode.food;

import java.util.ArrayList;
import java.util.List;

public class food_fragment extends Fragment {
    Context context;
    foodDAO foodDAO;
    foodAdapter foodAdapter;
    ListView lvFood;
    ArrayList<food> foodList;
    private View food_view;
    ListView listview;
    ImageButton btn_add,btn_delete;
    String email, daily_date;
    ArrayList<food> dataFood = new ArrayList<>();
    int user_id;
    food food;
    SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        lvFood = view.findViewById(R.id.lv_food);

        context = getActivity();

        if (getArguments() != null) {
            user_id = getArguments().getInt("user_id");
            daily_date = getArguments().getString("day");
        }

        foodDAO = new foodDAO(getActivity());
        foodList = foodDAO.displayFood(); // Fetch food items from the database
        foodAdapter = new foodAdapter(getActivity(), foodList,user_id,daily_date, email);
        lvFood.setAdapter(foodAdapter);

        btn_add = view.findViewById(R.id.btn_ic_add);
        searchView = view.findViewById( R.id.search_food);

        context = getActivity();

//        if (getActivity() instanceof MainActivity) {
//            OnUserIDSelectedListener listener = (OnUserIDSelectedListener) getActivity();
//            if (listener != null) {
//                listener.onUserIDSelected(user_id);
//            }
//        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               foodAdapter.getFilter().filter(query);
               return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                foodAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), add_food.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("day",daily_date);
                startActivity(intent);
            }
        });

        return view;
    }
    public interface OnUserIDSelectedListener {
        void onUserIDSelected(int user_id);
    }
}
