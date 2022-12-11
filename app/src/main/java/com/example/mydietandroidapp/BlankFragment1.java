package com.example.mydietandroidapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlankFragment1 extends Fragment {
    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    public static ArrayList<MealKcal> mealsKcalInfo;

    Map<String, Integer> kcalMap = new HashMap<String, Integer>() {
        {
            put("banana", 100);
            put("apple", 200);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        // Inflate the layout for this fragment
        myRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLayoutManager);

        getCurrentMeals();
        return v;
    }

    public void getCurrentMeals(){

        mealsKcalInfo = new ArrayList<>();
        String[] columns = new String[]{"_id", "name",
                "meal_count", "review", "meal_date", "meal_time", "image_uri", "address"};
        Cursor c = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, columns, null,
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
                String address = c.getString(7);
                System.out.println(meal_date);

                // todo : 여기서 select로 받아오고 adapter에 넣기
                mealsKcalInfo.add( new MealKcal(name, meal_date, meal_time, kcalMap.get(name), meal_count ));
            }
            c.close();
        }

        //
        MyKcalAdapter myAdapter = new MyKcalAdapter(mealsKcalInfo);
        myRecyclerView.setAdapter(myAdapter);
    }
}