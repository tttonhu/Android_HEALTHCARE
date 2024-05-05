package com.example.miniproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.miniproject.DAO.userDAO;
import com.example.miniproject.R;
import com.example.miniproject.adapter.foodAdapter;
import com.example.miniproject.databinding.ActivityMainBinding;
import com.example.miniproject.fragment.blog_fragment;
import com.example.miniproject.fragment.food_fragment;
import com.example.miniproject.fragment.home_fragment;
import com.example.miniproject.fragment.user_fragment;
import com.example.miniproject.mode.user;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    userDAO userDAO;
    Context context;
    String email,day;
    int user_id;
    Bundle bundle = new Bundle();
    foodAdapter foodAdapter;

//    @Override
//    public void onUserIDlSelected(int user_id) {
//       this.user_id = user_id;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        foodAdapter = new foodAdapter();
//        foodAdapter.setListener(this);
        user_id = getIntent().getIntExtra("user_id",0);
        day = getIntent().getStringExtra("day");
        bundle.putInt("user_id",user_id);
        bundle.putString("day",day);
        replaceFragment(new home_fragment(), bundle);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.home){
                replaceFragment(new home_fragment(),bundle);
            } else if (itemId == R.id.favourite) {
                replaceFragment(new food_fragment(),bundle);
            }else if(itemId == R.id.user){
                replaceFragment(new user_fragment(),bundle);
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment, Bundle data){
        fragment.setArguments(data);
        FragmentManager faFragmentManager = getSupportFragmentManager();
        FragmentTransaction faFragmentTransaction = faFragmentManager.beginTransaction();
        faFragmentTransaction.replace(R.id.frame_layout,fragment);
        faFragmentTransaction.commit();
    }
    public int getUser_id(){
        return user_id;
    }
}