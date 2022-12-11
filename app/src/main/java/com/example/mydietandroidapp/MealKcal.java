package com.example.mydietandroidapp;

public class MealKcal {
    String name;
    String date;
    String time;
    int kcal;
    int count;

    public MealKcal(String name, String date, String time, int kcal, int count){
        this.name = name;
        this.date = date;
        this.time = time;
        this.kcal = kcal;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getKcal() {
        return kcal;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}
