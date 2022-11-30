package com.example.mydietandroidapp;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView myPicture;
        TextView name;
        TextView count;
        TextView review;
        TextView time;

        MyViewHolder(View view) {
            super(view);
            myPicture = view.findViewById(R.id.imageView);
            name = view.findViewById(R.id.name);
            count = view.findViewById(R.id.count);
            review = view.findViewById(R.id.review);
            time = view.findViewById(R.id.time);
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
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int
            position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.myPicture.setImageURI(Uri.parse(myMealList.get(position).getImage_uri()));
        myViewHolder.name.setText(myMealList.get(position).getName());
        myViewHolder.count.setText(myMealList.get(position).getMeal_count() + "");
        myViewHolder.review.setText(myMealList.get(position).getReview());
        myViewHolder.time.setText(myMealList.get(position).getMeal_time() + "");
    }
    @Override
    public int getItemCount() { return myMealList.size();
    }
}
