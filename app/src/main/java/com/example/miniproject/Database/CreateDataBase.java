package com.example.miniproject.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food_database";
    private static final int DATABASE_VERSION = 18;

    private static final String Tbl_user ="user_table";
    private static final String user_id = "user_id";
    private static final String user_name ="user_name";
    private static final String password="password";
    private static final String age="age";
    private static final String height="height";
    private static final String weight="weight";
    private static final String sex ="sex";
    private static final String activity_day="activity_day";
    private static final String activity_day_week="activity_day_week";
    private static final String aim = "aim";
    private static final String BMR_need = "BMR";
    private static final String BMI = "BMI";
    private static final String protein_need ="protein_need";
    private static final String carb_need ="carb_need";
    private static final String fat_need ="fat_need";
    private static final String day_signUp = "day_signUp";

    // Tao bang user
    private static final String CREATE_Tbl_user =
            "CREATE TABLE " + Tbl_user + "(" +
                    user_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    user_name + " TEXT UNIQUE, " +
                    password + " TEXT, "+
                    age + " INTEGER CHECK (" + age + " > 0), "+
                    height + " INTEGER CHECK (" + height + " > 0), "+
                    weight + " INTEGER CHECK (" + weight + " > 0 ),"+
                    sex + " TEXT, " +
                    activity_day + " INTEGER CHECK (" + activity_day + " > 0),"+
                    activity_day_week + " INTEGER CHECK (" + activity_day_week + " > 0)," +
                    day_signUp + " TEXT, "+
                    BMR_need + " INTEGER ,"+
                    protein_need + " INTEGER CHECK ("+ protein_need + " > 0),"+
                    carb_need + " INTEGER CHECK ("+ protein_need + " > 0),"+
                    fat_need + " INTEGER CHECK ("+ protein_need + " > 0),"+
                    BMI + " DOUBLE ," +
                    aim + " TEXT)";

    // Khai bao bang food
    private static final String Tbl_food ="food_table";
    private static final String food_id = "food_id";
    private static final String food_name ="food_name";
    private static final String food_kcal ="food_kcal";
    private static final String food_protein ="food_protein";
    private static final String one_serving ="one_serving";
    private static final String name_serving ="name_serving";
    private static final String food_fats ="food_fats";
    private static final String food_carbs ="food_carbs";
    private static final String food_fibers ="food_fibers";
    private static final String food_sugar ="food_sugar";
    private static final String unit_of_measure = "unit_of_measure";


    // Tao bang food

    private static final String CREATE_Tbl_food =
            "CREATE TABLE " + Tbl_food + "(" +
                    food_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    food_name + " TEXT, "+
                    food_kcal + " INTEGER CHECK (" + food_kcal + " >= 0),"+
                    food_fats + " INTEGER CHECK (" + food_fats + " >= 0),"+
                    food_protein + " INTEGER CHECK (" + food_protein + " >= 0),"+
                    food_fibers + " INTEGER CHECK (" + food_fibers + " >= 0),"+
                    name_serving +" TEXT, "+
                    one_serving + " INTEGER CHECK (" + one_serving + " >= 0), "+
                    food_carbs + " INTEGER CHECK (" + food_carbs + " >= 0),"+
                    food_sugar + " INTEGER CHECK (" + food_sugar + " >= 0),"+
                    unit_of_measure + " TEXT, " +
                    user_id + " INTEGER, "+
                    " FOREIGN KEY (" + user_id + ") REFERENCES "+ Tbl_user + "(" + user_id + ")" +
                    ")";


    // Khai bao bang exercise

    private static final String  Tbl_exercise = "exercise_table";
    private static final String exercise_id = "exercise_id";
    private static final String exercise_name ="exercise_name";
    private static final String exercise_kcal ="exercise_kcal";
    private static final String exercise_time ="exercise_time";

    // Tao bang exercise
    public static final String CREATE_Tbl_exercise =
            "CREATE TABLE " + Tbl_exercise + "(" +
                    exercise_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    exercise_name + " TEXT, " +
                    exercise_kcal + " INTEGER, " +
                    exercise_time + " INTEGER)";

    // Khai bao bang food_daily
    private static final String  Tbl_food_daily = "Tbl_food_daily";
    private static final String id_food_daily ="id_daily_food";
    private static final String daily_date ="daily_date";
    private static final String meal ="meal";
    private static final String amount ="amount";
    private static final String total_eaten= "total_eaten";


    // tao bang Tbl_food_daily
    public static final String CREATE_Tbl_food_daily =
            "CREATE TABLE " + Tbl_food_daily + "(" +
                    id_food_daily + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    food_id + " INTEGER," +
                    amount + " INTEGER CHECK (" + amount + " >0), " +
                    meal + " TEXT," +
                    daily_date + " TEXT, " +
                    user_id + " INTEGER, "+
                    total_eaten + "INTEGER, " +
                    " FOREIGN KEY (" + user_id + ") REFERENCES "+ Tbl_user + "(" + user_id + ")" +
                    ")" ;

    //khai bao bang exercise_daily
    private static final String Tbl_exercise_daily = "exercise_daily_table";
    private static final String id_exercise_daily = "id_exercise_daily";
    private static final String exercise_daily_time = "exercise_daily_time";
    private static final String total_burned = "total_burned";
    public static final String CREATE_Tbl_exercise_daily =
            "CREATE TABLE " + Tbl_exercise_daily + "(" +
                    id_exercise_daily + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    exercise_id + " INTEGER CHECK( " + exercise_id + " > 0), " +
                    user_id + " INTEGER, " +
                    daily_date + " TEXT, " +
                    total_burned + " INTEGER, " +
                    exercise_daily_time + " INTEGER CHECK (" + exercise_daily_time + " > 0 ), " +
                    " FOREIGN KEY (" + user_id + ") REFERENCES " + Tbl_user + "(" + user_id + "), " +
                    " FOREIGN KEY (" + exercise_id + ") REFERENCES " + Tbl_exercise + "(" + exercise_id + ")" +
                    ")";

    // khai bao bang cure
    private static final String Tbl_cure = "cure_table";
    private static final String cure_id ="cure_id";
    private static final String cure_name ="cure_name";
    private static final String cure_type ="cure_type";
    private static final String during_cure ="during_cure";
    private static final String cure_time_daily = "cure_time_daily";
    private static final String cure_frequency ="cure_frequency";

    // tao bang cure
    public static final String CREATE_Tbl_cure =
            "CREATE TABLE " + Tbl_cure + "(" +
                    cure_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    cure_name + " TEXT, " +
                    cure_type + " TEXT, " +
                    during_cure + " TEXT, " +
                    cure_time_daily + " TEXT, " +
                    cure_frequency + " TEXT )";

    // khai bao bang daily cure
    private static final String Tbl_daily_cure = "daily_cure_table";
    private static final String id_cure_daily = "id_cure_daily";
    private static final String cure_daily_id ="cure_id";
    private static final String cure_daily_date = "cure_date";
    private static final String cure_daily_condition ="cure_daily_condition";

    // tao bang daily cure
    public static final String CREATE_Tbl_daily_cure =
            "CREATE TABLE " + Tbl_daily_cure + "(" +
                    id_cure_daily + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    cure_daily_id + " INTEGER, "+
                    cure_daily_date + " REAL, "+
                    cure_daily_condition + " TEXT )";

    private static final String Tbl_water = "water_table";
    private static final String goal_water = "goal_water";
    private static final String water_id = "water_id";
    private static final String water_size = "water_size";
    private static final String amount_water = "amount_water";
    private static final String time_update = "time_update";
    private static final String time_start ="time_start";
    private static final String time_end ="time_end";
    public static final String CREATE_Tbl_water =
            "CREATE TABLE " + Tbl_water + "(" +
                    water_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    goal_water + " INTEGER CHECK (" + goal_water + " >=0 ),"+
                    water_size + " INTEGER CHECK (" + water_size + " >=0 ),"+
                    amount_water + " INTEGER CHECK (" + amount_water + " >= 0 )," +
                    time_start + " TEXT, "+
                    time_end + " TEXT, "+
                    time_update + " REAL," +
                    user_id + " INTEGER, " +
                    " FOREIGN KEY (" + user_id + ") REFERENCES "+ Tbl_user + "(" + user_id + ")" +
                    ")";
    public CreateDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Tbl_user);
        db.execSQL(CREATE_Tbl_food);
        db.execSQL(CREATE_Tbl_exercise);
        db.execSQL(CREATE_Tbl_food_daily);
        db.execSQL(CREATE_Tbl_exercise_daily);
        db.execSQL(CREATE_Tbl_cure);
        db.execSQL(CREATE_Tbl_daily_cure);
        db.execSQL(CREATE_Tbl_water);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cap nhat bang khi co su thay doi phien ban
        db.execSQL("DROP TABLE IF EXISTS " + Tbl_user);
        db.execSQL("DROP TABLE IF EXISTS " + Tbl_food);
        db.execSQL("DROP TABLE IF EXISTS " + Tbl_exercise);
        db.execSQL("DROP TABLE IF EXISTS " + Tbl_exercise_daily);
        db.execSQL("DROP TABLE IF EXISTS " + Tbl_food_daily);
        db.execSQL("DROP TABLE IF EXISTS " + Tbl_cure);
        db.execSQL("DROP TABLE IF EXISTS " + Tbl_daily_cure);
        db.execSQL("DROP TABLE IF EXISTS " + Tbl_water);
        onCreate(db);
    }


}
