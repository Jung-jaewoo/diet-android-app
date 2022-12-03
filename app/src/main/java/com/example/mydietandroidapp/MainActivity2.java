package com.example.mydietandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    public static ArrayList<Meal> mealsInfo;
    private CalendarView calendarView;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        calendarView = findViewById(R.id.calendarView);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
                getStudents();
            }
        });
    }


    public void getStudents() {
        mealsInfo = new ArrayList<>();
        String[] columns = new String[]{"_id", "name",
                "meal_count", "review", "meal_date", "meal_time", "image_uri"};
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI, columns, null,
                null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                int meal_count = c.getInt(2);
                String review = c.getString(3);
                String meal_date = c.getString(4);
                String meal_time = c.getString(5);
                String image_uri = c.getString(6);
                System.out.println("date" + date);
                System.out.println(meal_date);
                if (meal_date.equals(date)) {
                    mealsInfo.add(new Meal(name, meal_count, review, meal_time, image_uri));
                }
            }
            c.close();
        }
        MyAdapter myAdapter = new MyAdapter(mealsInfo);
        myRecyclerView.setAdapter(myAdapter);
    }
}