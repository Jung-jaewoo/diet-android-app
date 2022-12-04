package com.example.mydietandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainMenuActivity extends AppCompatActivity {

    Button navigate_btn;
    Button navigate_btn2;
    Button navigate_btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        navigate_btn = (Button) findViewById(R.id.inputMealButton);
        navigate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.mydietandroidapp.MainActivity");
                startActivity(intent);
            }
        });

        navigate_btn2 = (Button) findViewById(R.id.showMealButton);
        navigate_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.mydietandroidapp.MainActivity2");
                startActivity(intent);
            }
        });

//        navigate_btn3 = (Button) findViewById(R.id.analyzeMealButton);
//        navigate_btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent("com.example.mydietandroidapp.LAUNCH4");
//                startActivity(intent);
//            }
//        });
    }

    public void navigateInputMeal(View view) {
        Intent intent = new Intent(".MainActivity");
        startActivity(intent);
    }

    public void navigateShowMeal(View view) {
        Intent intent = new Intent(".MainActivity2");
        startActivity(intent);
    }

//    public void navigateMealAnalyze(View view) {
//        Intent intent = new Intent(".MainActivity4");
//        startActivity(intent);
//    }

}