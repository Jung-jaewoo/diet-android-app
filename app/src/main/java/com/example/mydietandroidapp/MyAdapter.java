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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
        }
    }

    private ArrayList<Meal> myMealList;

    MyAdapter(ArrayList<Meal> meals) {
        this.myMealList = meals;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int
            position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

//        myViewHolder.name.setText(myMealList.get(position).getName());
//        myViewHolder.count.setText(myMealList.get(position).getMeal_count() + "");
//        myViewHolder.review.setText(myMealList.get(position).getReview());
//        myViewHolder.time.setText(myMealList.get(position).getMeal_time() + "");
//        myViewHolder.adderess.setText(myMealList.get(position).getAddress() + "")
//        ;
        myViewHolder.textView.setText("    "+ (position + 1) + ". " + myMealList.get(position).getName()
                + "    " + myMealList.get(position).getMeal_time());
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MealDetailActivity.class);
                intent.putExtra("image", myMealList.get(position).getImage_uri());
                intent.putExtra("name", myMealList.get(position).getName());
                intent.putExtra("count", myMealList.get(position).getMeal_count() + "");
                intent.putExtra("review", myMealList.get(position).getReview());
                intent.putExtra("time",myMealList.get(position).getMeal_time() + "" );
                intent.putExtra("address",myMealList.get(position).getAddress() + "" );
                intent.putExtra("date", myMealList.get(position).getDate() + "");

                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myMealList.size();
    }
}
