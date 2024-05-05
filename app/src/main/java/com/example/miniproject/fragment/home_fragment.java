    package com.example.miniproject.fragment;

    import static com.example.miniproject.Service.diaryService.totalBurned;
    import static com.example.miniproject.Service.diaryService.totalEaten;

    import android.app.DatePickerDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ListView;
    import android.widget.SeekBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.miniproject.DAO.exercise_diaryDAO;
    import com.example.miniproject.R;
    import com.example.miniproject.Service.diaryService;
    import com.example.miniproject.activity.MainActivity;
    import com.example.miniproject.activity.information_food;
    import com.example.miniproject.activity.list_exercise;
    import com.example.miniproject.adapter.WaterIconAdapter;
    import com.example.miniproject.adapter.diaryFoodAdapter;
    import com.example.miniproject.adapter.dailyExerciseAdapter;
    import com.example.miniproject.mode.exercise_daily;
    import com.example.miniproject.mode.food_daily;

    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.List;
    import java.util.Locale;
    import com.example.miniproject.DAO.waterDAO;
    import com.example.miniproject.DAO.userDAO;
    import com.example.miniproject.DAO.food_diaryDAO;
    import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

    public class home_fragment extends Fragment {
        private MainActivity mActivity;

        String daily_date,day_display;
        int user_id,protein_need,fat_need,carb_need,goal_water,water_used= 0,weight;
        int  total_fats, total_protein, total_carb, total_eaten, kcal_need,food_id,amountWater =0;
        double BMR, BMR_need;
        WaterIconAdapter waterIconAdapter;

        waterDAO waterDAO;
        userDAO userDAO;
        exercise_diaryDAO exercise_diaryDAO;
        food_diaryDAO food_diaryDAO; 
        Context context;

        ArrayList<exercise_daily> diaryExercise;
        ArrayList<food_daily> diaryFoodList;
        diaryFoodAdapter diaryFoodAdapter;
        dailyExerciseAdapter  dailyExerciseAdapter;

        ExtendedFloatingActionButton btn_add_diary,btn_add_meal,btn_exercise;
        ListView lv_DiaryFood, lv_DailyExercise;
        TextView txt_total_water,txt_total_kcal, txt_eaten, txt_burned, txt_protein, txt_carb, txt_fats,txt_today;
        TextView txt_day;
        ImageButton btn_left, btn_right, btn_calendar,btn_addWater;
        SeekBar sBar_totalKcal;
        private Calendar calendar;

        boolean isAllFABVisible;
        private List<Integer> waterIconsList;
        private ArrayAdapter<Integer> adapter;
        private ListView listView;


        public home_fragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home,container,false);
            context = getActivity();
            if (getArguments() != null) {
                user_id = getArguments().getInt("user_id");
                daily_date = getArguments().getString("day");
            }

            // Khoi tao giao dien
            txt_today = view.findViewById(R.id.tv_today);
            txt_total_water = view.findViewById(R.id.tv_total_water_Home);
            txt_day = view.findViewById(R.id.tv_day_Home);
            txt_eaten = view.findViewById(R.id.txt_eaten_home);
            txt_burned = view.findViewById(R.id.txt_burned_home);
            txt_total_kcal = view.findViewById(R.id.txt_total_Calories_home);
            txt_protein = view.findViewById(R.id.txt_protein_home);
            txt_carb = view.findViewById(R.id.txt_carbs_home);
            txt_fats = view.findViewById(R.id.txt_fats_home);
            btn_left = view.findViewById(R.id.btn_left);
            btn_right = view.findViewById(R.id.btn_right);
            btn_calendar = view.findViewById(R.id.btn_calender);
            lv_DiaryFood = view.findViewById(R.id.lv_eaten);
            lv_DailyExercise = view.findViewById(R.id.lv_burned);
            btn_add_diary = view.findViewById(R.id.btn_add_diary_home);
            btn_add_meal = view.findViewById(R.id.btn_add_breakfast_home);
            btn_exercise = view.findViewById(R.id.btn_add_exercise_home);
            sBar_totalKcal = view.findViewById(R.id.seekBar_kcalNeed_fragmentHome);
            btn_addWater = view.findViewById(R.id.btn_addWater_homeFragment);
            listView = view.findViewById(R.id.lv_Water_homeFragment);

            waterIconsList = new ArrayList<>();
            waterIconAdapter = new WaterIconAdapter(context, waterIconsList);
            if (listView != null) {
                // Gọi phương thức setAdapter() trên listView ở đây
                listView.setAdapter(waterIconAdapter);
            } else {
                // Xử lý trường hợp listView là null
            }
            listView.setAdapter(waterIconAdapter);

            btn_addWater = view.findViewById(R.id.btn_addWater_homeFragment);
            btn_addWater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addWaterIcon();
                    amountWater = amountWater +1;
                }
            });

            // CHON NGAY HIEN TAI
            calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
            day_display = sdf.format(new Date());
            txt_today.setText("Today");

            txt_day.setText(day_display);
            userDAO = new userDAO(context);
            BMR = userDAO.getUser(user_id).getBMR();
            BMR_need = diaryService.kcalNeed(context,user_id);
            protein_need = userDAO.getUser(user_id).getProtein_need();
            carb_need = userDAO.getUser(user_id).getCarb_need();
            fat_need = userDAO.getUser(user_id).getFat_need();
            weight = userDAO.getUser(user_id).getWeight();
            goal_water = weight*30;

            // Hien thi list diary food
            food_diaryDAO = new food_diaryDAO(context);
            diaryFoodList = food_diaryDAO.displayDiaryFood(user_id, daily_date);
            diaryFoodAdapter = new diaryFoodAdapter(context,diaryFoodList,user_id,daily_date);
            lv_DiaryFood.setAdapter(diaryFoodAdapter);
            diaryFoodAdapter.notifyDataSetChanged();
            diaryFoodAdapter.setOnFoodDiaryDeletedListener(new diaryFoodAdapter.OnFoodDiaryDeletedListener() {
                @Override
                public void onFoodDiaryDeleted() {
                    updateNutrition();

                }
            });

            // Hien thi list diary exercise
            exercise_diaryDAO = new exercise_diaryDAO(context);
            diaryExercise = exercise_diaryDAO.displayDiaryExercise(user_id,daily_date);
            dailyExerciseAdapter = new dailyExerciseAdapter(context,diaryExercise,user_id,daily_date);
            lv_DailyExercise.setAdapter(dailyExerciseAdapter);
            dailyExerciseAdapter.notifyDataSetChanged();
            dailyExerciseAdapter.setOnExerciseDeletedListener(new dailyExerciseAdapter.OnExerciseDeletedListener() {
                @Override
                public void onExerciseDeleted() {
                   updateNutrition();
                }
            });


            // Set Text cho cac text view EATEN, BURNED, PROTEIN, FAT,CARB
            total_eaten = diaryService.totalEaten(context,diaryFoodList);
            kcal_need = (int)BMR_need -total_eaten;
            txt_total_water.setText(String.valueOf(water_used)+"/"+String.valueOf(goal_water)+" ml");
            txt_eaten.setText(String.valueOf(diaryService.totalEaten(context, diaryFoodList)));
            txt_burned.setText(String.valueOf(diaryService.totalBurned(context, diaryExercise)));
            txt_fats.setText(String.valueOf(diaryService.totalFat(context,diaryFoodList)) +" / " + String.valueOf(fat_need));
            txt_protein.setText(String.valueOf(diaryService.totalProtein(context,diaryFoodList)) +" / " + String.valueOf(protein_need));
            txt_carb.setText(String.valueOf(diaryService.totalCarb(context,diaryFoodList)) +" / " + String.valueOf(carb_need));
            txt_total_kcal.setText(String.valueOf(kcal_need));



            //btn_add_diary.setVisibility(View.GONE);
            btn_add_meal.setVisibility(View.GONE);
            btn_exercise.setVisibility(View.GONE);

            // XU LY ADD_DIARY
            isAllFABVisible = false;
            btn_add_diary.shrink();

            // bat su kien cho floating Button
            btn_add_diary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isAllFABVisible){
                        btn_add_meal.show();
                        btn_exercise.show();

                        // phong to btn_add
                        btn_add_diary.extend();
                        isAllFABVisible = true;
                    }else{
                        btn_add_meal.hide();
                        btn_exercise.hide();

                        //Thu nho btn add
                        btn_add_diary.shrink();
                        isAllFABVisible = false;
                    }
                }
            });
            btn_add_meal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    food_fragment foodFragment = new food_fragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("user_id",user_id);
                    bundle.putString("day", daily_date);
                    foodFragment.setArguments(bundle);
                    switchToFragment(foodFragment);
                }
            });
            btn_exercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), list_exercise.class);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("day",daily_date);
                    startActivity(intent);
                }
            });

            btn_calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setupDatePickerDialog(calendar, txt_day, sdf,sdf1);
                    if(daily_date !=null){
                        diaryFoodAdapter.notifyDataSetChanged();
                        txt_fats.setText(String.valueOf(diaryService.totalFat(context,diaryFoodList)) +" / " + String.valueOf(fat_need));
                        txt_protein.setText(String.valueOf(diaryService.totalProtein(context,diaryFoodList)) +" / " + String.valueOf(protein_need));
                        txt_carb.setText(String.valueOf(diaryService.totalCarb(context,diaryFoodList)) +" / " + String.valueOf(carb_need));
                        refreshListViewData();
                        updateNutrition();

                    }
                }
            });
            txt_day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setupDatePickerDialog(calendar,txt_day,sdf,sdf1);
                    if(daily_date !=null){
                        diaryFoodAdapter.notifyDataSetChanged();
                        txt_fats.setText(String.valueOf(diaryService.totalFat(context,diaryFoodList)) +" / " + String.valueOf(fat_need));
                        txt_protein.setText(String.valueOf(diaryService.totalProtein(context,diaryFoodList)) +" / " + String.valueOf(protein_need));
                        txt_carb.setText(String.valueOf(diaryService.totalCarb(context,diaryFoodList)) +" / " + String.valueOf(carb_need));
                        displayDailyExercise(user_id,daily_date);
                        refreshListViewData();
                        updateNutrition();
                    }
                }
            });

            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar yesterday = Calendar.getInstance();
                    yesterday.add(Calendar.DAY_OF_MONTH,-1);
                    day_display = sdf.format(yesterday.getTime());
                    daily_date = sdf1.format(yesterday.getTime());
                    txt_day.setText(day_display);
                    txt_today.setText("Yesterday");

                    if(daily_date !=null) {
                        diaryFoodAdapter.notifyDataSetChanged();
                        displayDailyExercise(user_id, daily_date);
                        refreshListViewData();
                        updateNutrition();
                    }
                }
            });

            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar tomorrow = Calendar.getInstance();
                    tomorrow.add(Calendar.DAY_OF_MONTH,1);
                    day_display = sdf.format(tomorrow.getTime());
                    daily_date = sdf1.format(tomorrow.getTime());
                    txt_day.setText(day_display);
                    txt_today.setText("Tomorrow");
                    if(daily_date !=null){
                        refreshListViewData();
                        displayDailyExercise(user_id,daily_date);
                        refreshListViewData();
                        updateNutrition();
                    }
                }
            });
            return view;
        }
        private void setupDatePickerDialog(Calendar calendar, TextView textView, SimpleDateFormat sdf,SimpleDateFormat sdf1) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        day_display = sdf.format(calendar.getTime());
                        daily_date = sdf1.format(calendar.getTime());
                        textView.setText(day_display);

                        Calendar today = Calendar.getInstance();
                        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                                calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                                calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                                txt_today.setText("Today");
                        } else {
                            txt_today.setText(daily_date);
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }
        private void switchToFragment(Fragment fragment) {
            FragmentManager fragmentManager = getChildFragmentManager(); // hoặc getChildFragmentManager() nếu trong Fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_fragment, fragment);
            fragmentTransaction.addToBackStack(null); // Để quay lại Fragment trước đó khi nhấn nút back
            fragmentTransaction.commit();
        }

        private void displayDailyExercise(int user_id, String day){
            exercise_diaryDAO = new exercise_diaryDAO(context);
            diaryExercise = exercise_diaryDAO.displayDiaryExercise(user_id,day);
            dailyExerciseAdapter = new dailyExerciseAdapter(getContext(),diaryExercise,user_id,daily_date);
            lv_DailyExercise.setAdapter(dailyExerciseAdapter);
        }
        private void startFragment(Fragment fragment) {
            FragmentManager fragmentManager = getParentFragmentManager(); // Sử dụng getParentFragmentManager() cho Fragment cha
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_fragment, fragment);
            fragmentTransaction.addToBackStack(null); // Để quay lại Fragment trước đó khi nhấn nút back
            fragmentTransaction.commit();
        }

        @Override
        public void onResume() {
            super.onResume();
            refreshListViewData();
        }

        private void refreshListViewData() {
            // Cập nhật dữ liệu cho ListView
            food_diaryDAO = new food_diaryDAO(context);
            diaryFoodList = food_diaryDAO.displayDiaryFood(user_id, daily_date);
            diaryFoodAdapter = new diaryFoodAdapter(context, diaryFoodList, user_id,daily_date);
            lv_DiaryFood.setAdapter(diaryFoodAdapter);
            diaryFoodAdapter.notifyDataSetChanged();
        }
        private void updateNutrition(){
            txt_total_water.setText(String.valueOf(water_used)+"/"+String.valueOf(goal_water)+" ml");
            total_fats = diaryService.totalFat(context,diaryFoodList);
            total_protein = diaryService.totalProtein(context,diaryFoodList);
            total_carb = diaryService.totalCarb(context,diaryFoodList);
            total_eaten = diaryService.totalEaten(context,diaryFoodList);
            kcal_need = (int)BMR_need -total_eaten;
            txt_eaten.setText(String.valueOf(diaryService.totalEaten(context, diaryFoodList)));
            txt_burned.setText(String.valueOf(diaryService.totalBurned(context,diaryExercise)));
            txt_fats.setText(String.valueOf(total_fats) +" / " + String.valueOf(fat_need));
            txt_protein.setText(String.valueOf(total_protein) +" / " + String.valueOf(protein_need));
            txt_carb.setText(String.valueOf(total_carb) +" / " + String.valueOf(carb_need));
            txt_total_kcal.setText(String.valueOf(kcal_need));

            double result = (total_eaten / BMR_need);
            String formattedResult = String.format("%.2f", result);
            double percentage = Double.parseDouble(formattedResult) * 100;

            int progressValue = (int) Math.min(percentage, 100); // Giới hạn giá trị trong khoảng 0-100 để đặt cho ProgressBar
            sBar_totalKcal.setProgress(progressValue);
        }

        private void addWaterIcon() {
            if (waterIconsList != null && waterIconAdapter != null) {
                waterIconsList.add(R.drawable.water); // Thêm icon Water vào danh sách
                waterIconAdapter.notifyDataSetChanged(); // Cập nhật Adapter để cập nhật ListView
            } else {
                // Xử lý nếu waterIconsList hoặc waterIconAdapter là null
            }
        }
    }