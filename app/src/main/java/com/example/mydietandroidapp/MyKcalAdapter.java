package com.example.mydietandroidapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyKcalAdapter extends RecyclerView.Adapter<MyKcalAdapter.MyViewHolder>  {
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
        }
    }

    private ArrayList<MealKcal> myMealKcalList;

    MyKcalAdapter(ArrayList<MealKcal> meals) {
        this.myMealKcalList = meals;
    }

    @Override
    public MyKcalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kcal_item_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyKcalAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int
            position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

//        myViewHolder.name.setText(myMealList.get(position).getName());
//        myViewHolder.count.setText(myMealList.get(position).getMeal_count() + "");
//        myViewHolder.review.setText(myMealList.get(position).getReview());
//        myViewHolder.time.setText(myMealList.get(position).getMeal_time() + "");
//        myViewHolder.adderess.setText(myMealList.get(position).getAddress() + "")
//        ;
        myViewHolder.textView.setText(position + 1 + ". " + myMealKcalList.get(position).getDate()
                + "\n" + myMealKcalList.get(position).getTime() +myMealKcalList.get(position).getName()
                + "\nkcal : " +myMealKcalList.get(position).getKcal() + "\ncount : " + myMealKcalList.get(position).getCount()
                +  "\n총 칼로리: " + (myMealKcalList.get(position).getKcal() * myMealKcalList.get(position).getCount()));}

    @Override
    public int getItemCount() {
        return myMealKcalList.size();
    }
}
