package com.example.mydietandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainFrame extends Fragment {
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;
    private Uri imageUri;
    private TextView textView_Date;
    private TextView textView_Time;
    private DatePickerDialog.OnDateSetListener dateCallbackMethod;
    private TimePickerDialog.OnTimeSetListener timeCallbackMethod;

    public void onStart(Bundle savedInstanceState) {
        super.onStart(); //savedInstanceState 확인해야함

        getActivity().setContentView(R.layout.fragment_main);

        if (savedInstanceState == null) {

            MainFragment mainFragment = new MainFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragment, mainFragment, "main")
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        imageView = rootView.findViewById(R.id.image_view);

        InitializeDateView(rootView);
        InitializeTimeView(rootView);
        InitializeDateListener();
        InitializeTimeListener();

        return rootView;
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

    public void addMeal(View view) {

        // 음식 사진과 식사 장소 입력 부 추가해야함.
        ContentValues addValues = new ContentValues();
        addValues.put(MyContentProvider.NAME,
                ((EditText) getView().findViewById(R.id.foodName)).getText().toString());
        addValues.put(MyContentProvider.MEAL_COUNT,
                Integer.parseInt(((EditText) getView().findViewById(R.id.mealCount)).getText().toString()));
        addValues.put(MyContentProvider.REVIEW,
                ((EditText) getView().findViewById(R.id.mealReview)).getText().toString());
        addValues.put(MyContentProvider.MEAL_DATE,
                ((TextView) getView().findViewById(R.id.mealDate)).getText().toString());
        addValues.put(MyContentProvider.MEAL_TIME,
                ((TextView) getView().findViewById(R.id.mealTime)).getText().toString());

        System.out.println(((TextView) getView().findViewById(R.id.mealTime)).getText().toString());
        if (imageUri != null) {

            addValues.put(MyContentProvider.IMAGE_URI,
                    imageUri.toString()
            );
        } else {
            addValues.put(MyContentProvider.IMAGE_URI,
                    "no image");
        }
        addValues.put(MyContentProvider.ADDRESS, MainFragment.getNowAddress());

        getActivity().getContentResolver().insert(MyContentProvider.CONTENT_URI, addValues);

        imageUri = null;
        Toast.makeText(getActivity().getBaseContext(),
                "Record Added", Toast.LENGTH_LONG).show();

    }

}