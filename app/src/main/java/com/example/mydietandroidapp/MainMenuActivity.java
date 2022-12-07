package com.example.mydietandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;
    private Uri imageUri;
    private TextView textView_Date;
    private TextView textView_Time;
    private DatePickerDialog.OnDateSetListener dateCallbackMethod;
    private TimePickerDialog.OnTimeSetListener timeCallbackMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // bottom nav 설정
        bottomNavigationView = findViewById(R.id.bottomNav);
        // 처음화면
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new BlankFragment1()).commit(); //FrameLayout에 fragment.xml 띄우기

        MainFrame mainFrame = new MainFrame();
        MainFrame2 mainFrame2 = new MainFrame2();

        //바텀 네비게이션뷰 안의 아이템 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.item_fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainFrame2).commit();
                        break;
                    case R.id.item_fragment2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainFrame).commit();
                        break;
                    case R.id.item_fragment3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new BlankFragment1()).commit();
                        break;
                }
                return true;
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        imageView = rootView.findViewById(R.id.image_view);

        InitializeDateView(rootView);
        InitializeTimeView(rootView);
        InitializeDateListener();
        InitializeTimeListener();

    }

    public void InitializeDateView(View rootView) {
        textView_Date = (TextView) rootView.findViewById(R.id.mealDate);
    }

    public void InitializeDateListener() {
        dateCallbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                textView_Date.setText(year + "년 " + (month + 1) + "월 " + day + "일");
            }
        };
    }

    public void InitializeTimeView(View rootView) {
        textView_Time = (TextView) rootView.findViewById(R.id.mealTime);
    }

    public void InitializeTimeListener() {
        timeCallbackMethod = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                textView_Time.setText(hour + "시" + min + "분");
            }
        };
    }

    public void OnClickTimeHandler() {
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), timeCallbackMethod, 8, 10, true);

        dialog.show();
    }

    public void OnClickDateHandler() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateCallbackMethod, 2022, 12, 1);

        dialog.show();
    }

    // 갤러리 여는 코드
    public void onClickGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}