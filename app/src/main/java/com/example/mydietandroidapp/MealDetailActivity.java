package com.example.mydietandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MealDetailActivity extends AppCompatActivity {

    private Intent intent;
    private String image;
    private String name;
    private String count;
    private String review;
    private String time;
    private String address;
    private String date;

    private ImageView imageView;
    private TextView dateView;
    private TextView nameView;
    private TextView timeView;
    private TextView countView;
    private TextView reviewView;
    private TextView addressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        intent = getIntent();

        image = intent.getStringExtra("image");
        name = intent.getStringExtra("name");
        count = intent.getStringExtra("count");
        review = intent.getStringExtra("review");
        time = intent.getStringExtra("time");
        address = intent.getStringExtra("address");
        date = intent.getStringExtra("date");

        imageView = findViewById(R.id.imageView);
        dateView = findViewById(R.id.dateView);
        nameView = findViewById(R.id.nameView);
        timeView = findViewById(R.id.timeView);
        countView = findViewById(R.id.countView);
        reviewView = findViewById(R.id.reviewView);
        addressView = findViewById(R.id.addressView);

        if (image.equals("no image")) {
            imageView.setImageResource(R.drawable.defaultimg);
        } else {
            imageView.setImageURI(Uri.parse(image));
        }
        dateView.setText(date);
        nameView.setText(name);
        timeView.setText(time);
        countView.setText(count);
        reviewView.setText(review);
        addressView.setText(address);

    }


}