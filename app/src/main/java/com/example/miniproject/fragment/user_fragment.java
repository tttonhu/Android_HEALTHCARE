package com.example.miniproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproject.DAO.userDAO;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.miniproject.R;
import com.example.miniproject.Service.userService;
import com.example.miniproject.activity.updateInformation;
import com.example.miniproject.mode.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class user_fragment extends Fragment {
    userDAO userDAO;
    TextView txt_BMI, txt_Weight, txt_Height, txt_Day, txt_Condition, txt_Water, txt_Time;
    ImageButton btn_Setting, btn_User;
    int user_id;
    String email,daily_date;
    double BMI = 0;

    public user_fragment() {
    }

    public static user_fragment newInstance() {
        user_fragment fragment = new user_fragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

            txt_BMI = view.findViewById(R.id.txt_BMI_User);
            txt_Weight = view.findViewById(R.id.txt_Weight_User);
            txt_Height = view.findViewById(R.id.txt_Height_User);
            txt_Day = view.findViewById(R.id.txt_Day_User);
            txt_Condition = view.findViewById(R.id.txt_Condition_User);
            btn_Setting = view.findViewById(R.id.btn_User_Setting);
            btn_User = view.findViewById(R.id.btn_User_User);
            txt_Water = view.findViewById(R.id.txt_water_User);
            txt_Time = view.findViewById(R.id.txt_time_User);

        if (getArguments() != null) {
            user_id = getArguments().getInt("user_id");
            daily_date = getArguments().getString("day");
            Context context = getContext();
            userDAO = new userDAO(context);
            BMI = userDAO.getUser(user_id).getBMI();
            txt_BMI.setText(String.valueOf(BMI));
            int height = userDAO.getUser(user_id).getHeight();
            int weight = userDAO.getUser(user_id).getWeight();
            txt_Height.setText(String.valueOf(height));
            txt_Weight.setText(String.valueOf(weight));
            int total_water = weight * 30;
            txt_Water.setText(String.valueOf(total_water) + " ml");
        }

            String bodyCondition;
            bodyCondition = userService.bodyCondition(getContext(),user_id);
            txt_Condition.setText(bodyCondition);

            btn_User.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), updateInformation.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("user_id", user_id);
                    bundle.putString("day",daily_date);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
            String currentTime = sdf.format(new Date());
            txt_Day.setText(currentTime);

            SimpleDateFormat time = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault());
            String drinkLastTime = time.format(new Date());
            txt_Time.setText(drinkLastTime);
            return view;
        }
}