package com.example.mydietandroidapp;

import android.net.Uri;

public class Meal {
    String name;
    int meal_count;
    String review;
    String meal_time;
    String image_uri;
    String address;
    String date;

    public Meal(String name, int meal_count, String review, String meal_time, String image_uri, String address, String date) {
        this.name = name;
        this.meal_count = meal_count;
        this.review = review;
        this.meal_time = meal_time;
        this.image_uri = image_uri;
        this.address = address;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getReview() {
        return review;
    }

    public String getMeal_time() {
        return meal_time;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public int getMeal_count() {
        return meal_count;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }
}
