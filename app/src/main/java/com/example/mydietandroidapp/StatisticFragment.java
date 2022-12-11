package com.example.mydietandroidapp;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StatisticFragment extends Fragment {
    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    public static ArrayList<MealKcal> mealsKcalInfo;
    private TextView topTextView;
    private TextView bottomTextView;
    private String dCurrentDate;
    public Integer dayTotalKcal = 0;
    Map<String, Integer> kcalMap = new HashMap<String, Integer>() {
        {
            put("banana", 100);
            put("apple", 200);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistic, container, false);
        // Inflate the layout for this fragment
        myRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLayoutManager);

        getCurrentMeals();
        topTextView = v.findViewById(R.id.textView5);
        topTextView.setText(dCurrentDate+"\n오늘 하루 식사별 총 칼로리");
        getDayTotalKcal();
        bottomTextView = v.findViewById(R.id.textView4);
        bottomTextView.setText("오늘 섭취한 총 칼로리 : "+ dayTotalKcal);

        return v;
    }

    public void getCurrentMeals(){

        mealsKcalInfo = new ArrayList<>();
        String[] columns = new String[]{"_id", "name",
                "meal_count", "review", "meal_date", "meal_time", "image_uri", "address"};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
        Date date = new Date();
        dCurrentDate = formatter.format(date);

//        MealDBManager dbHelper = new MealDBManager(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM recipe_ingredient where RECIPE_ID = 195453",null);
//
//        SQLiteDatabase db = ;
//        String MEAL_DB = "Meal.db";
//        String MEAL_TABLE = "Meals";
//        MealDBManager dbManager = null;
//        db =
//        Cursor c = db.rawQuery("select * from person where date=?", new String[]{dCurrentDate});

        Cursor c = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, columns, null,
                null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                String meal_date = c.getString(4);
                if(meal_date.equals(dCurrentDate)){
                    int id = c.getInt(0);
                    String name = c.getString(1);
                    int meal_count = c.getInt(2);
                    String review = c.getString(3);
                    String meal_time = c.getString(5);
                    String image_uri = c.getString(6);
                    String address = c.getString(7);
                    mealsKcalInfo.add( new MealKcal(name, meal_date, meal_time, kcalMap.get(name), meal_count ));
                }
            }
            c.close();
        }

        MyKcalAdapter myAdapter = new MyKcalAdapter(mealsKcalInfo);
        myRecyclerView.setAdapter(myAdapter);
    }

    public void getDayTotalKcal(){
        for(MealKcal item : mealsKcalInfo){
            System.out.println(item.getKcal());
            dayTotalKcal += item.getKcal() * item.getCount();
        }
    }
}

