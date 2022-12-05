package com.example.mydietandroidapp;

import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mydietandroidapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    View rootView;
    MapView mapView;
    Marker marker;
    public static String nowAddress = "서울특별시 중구 필동로1길 30";

    private void getAddress(LatLng position) {
        Geocoder geoCoder = new Geocoder(this.getContext(), Locale.KOREA);   // Geocoder 로 자기 나라에 맞게 설정
        List<Address> address = null;
        try {
            if (geoCoder != null) {
                address = geoCoder.getFromLocation(position.latitude, position.longitude, 1);
                if (address != null && address.size() > 0) {
                    nowAddress = address.get(0).getAddressLine(0).toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainFragment() {
    }

    public static String getNowAddress(){
        return nowAddress;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        return rootView;
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
                marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .title("루프리코리아"));
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
}

