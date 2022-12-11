package com.example.mydietandroidapp;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainFrame2 extends Fragment {
    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    public static ArrayList<Meal> mealsInfo;
    private CalendarView calendarView;
    private String date;

    public void onStart(Bundle savedInstanceState) {
        super.onStart();
        getActivity().setContentView(R.layout.fragment_main2);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main2, container, false);

        myRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLayoutManager);

        calendarView = (CalendarView) v.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
                System.out.println("selected");
                getMeals();
            }
        });

        return v;
    }


    public void getMeals() {
        mealsInfo = new ArrayList<>();
        String[] columns = new String[]{"_id", "name",
                "meal_count", "review", "meal_date", "meal_time", "image_uri", "address"};
        Cursor c = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, columns, null,
                null, null, null);
        System.out.println("date" + date);
        if (c != null) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                int meal_count = c.getInt(2);
                String review = c.getString(3);
                String meal_date = c.getString(4);
                String meal_time = c.getString(5);
                String image_uri = c.getString(6);
                String address = c.getString(7);
                System.out.println(meal_date);
                if (meal_date.equals(date)) {
                    mealsInfo.add(new Meal(name, meal_count, review, meal_time, image_uri, address, date));
                }
            }
            c.close();
        }
        MyAdapter myAdapter = new MyAdapter(mealsInfo);
        myRecyclerView.setAdapter(myAdapter);
    }
}