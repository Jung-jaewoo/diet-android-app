package com.example.mydietandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainFrame extends Fragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;
    private Uri imageUri;
    private TextView textView_Date;
    private TextView textView_Time;
    private DatePickerDialog.OnDateSetListener dateCallbackMethod;
    private TimePickerDialog.OnTimeSetListener timeCallbackMethod;

    MapView mapView;
    Marker marker;
    public static String nowAddress = "서울특별시 중구 필동로1길 30";

    public void onStart(Bundle savedInstanceState) {
        super.onStart(); //savedInstanceState 확인해야함

        getActivity().setContentView(R.layout.fragment_main);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.image_view);
        Button dateBtn = (Button) rootView.findViewById(R.id.button3);
        Button timeBtn = (Button) rootView.findViewById(R.id.button4);
        Button addBtn = (Button) rootView.findViewById(R.id.button);

        imageView.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);


        InitializeDateView(rootView);
        InitializeTimeView(rootView);
        InitializeDateListener();
        InitializeTimeListener();

        //
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        System.out.println("구글맵");
        mapView.getMapAsync(this);
        //

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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                OnClickDateHandler();
                break;
            case R.id.button4:
                OnClickTimeHandler();
                break;
            case R.id.button:
                addMeal(view);
                break;
            case R.id.image_view:
                onClickGallery(view);
                break;

        }
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
        System.out.println("갤러리 :" + requestCode + " " + resultCode + " " + data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            System.out.println("이프문");
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
        addValues.put(MyContentProvider.ADDRESS, getNowAddress());

        getActivity().getContentResolver().insert(MyContentProvider.CONTENT_URI, addValues);

        imageUri = null;
        Toast.makeText(getActivity().getBaseContext(),
                "Record Added", Toast.LENGTH_LONG).show();

    }


    //*******

    public static String getNowAddress() {
        return nowAddress;
    }

    private void getAddress(LatLng position) {
        Geocoder geoCoder = new Geocoder(this.getContext(), Locale.KOREA);   // Geocoder 로 자기 나라에 맞게 설정
        List<Address> address = null;
        try {
                address = geoCoder.getFromLocation(position.latitude, position.longitude, 1);
                if (address != null && address.size() > 0) {
                    nowAddress = address.get(0).getAddressLine(0).toString();
                    System.out.println(nowAddress);
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(37.55802, 126.99857), 14);

        googleMap.animateCamera(cameraUpdate);

        marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.55802, 126.99857))
                .title("동국대학교"));


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                marker.remove();
                System.out.println("포인트"+point.latitude+"" + point.longitude);
                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("위치")
                );
                System.out.println(point.latitude + "" + point.longitude);
                getAddress(point);
            }
        });

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        String markerId = marker.getId();
        // 선택한 타겟의 위치
        LatLng location = marker.getPosition();
        System.out.println("여");
        System.out.println("마커 클릭 Marker ID : "
                + markerId + "(" + location.latitude + " " + location.longitude + ")");
        return false;
    }
    //*****

}
