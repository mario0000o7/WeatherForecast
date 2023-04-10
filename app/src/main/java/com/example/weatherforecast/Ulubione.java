package com.example.weatherforecast;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Ulubione extends Fragment implements MyViewModelObserver {

    private UlubioneViewModel mViewModel;

    public static Ulubione newInstance() {
        return new Ulubione();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        System.out.println("onCreateView5");
        return inflater.inflate(R.layout.fragment_ulubione, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated5");
        mViewModel = new ViewModelProvider(this).get(UlubioneViewModel.class);


    }


    @Override
    public void onCityChanged(String city) {

    }

    @Override
    public void onTempChanged(Double temp) {

    }

    @Override
    public void onPressureChanged(Double pressure) {

    }

    @Override
    public void onLonChanged(Double lon) {

    }

    @Override
    public void onLatChanged(Double lat) {

    }

    @Override
    public void onDescriptionChanged(String description) {

    }

    @Override
    public void onActualTimeChanged(String actualTime) {

    }

    @Override
    public void onAllChanged(String city, Double temp, Double pressure, Double lon, Double lat, String description, String actualTime, String windSpeed, String windDeg, String humidity) {

    }




}