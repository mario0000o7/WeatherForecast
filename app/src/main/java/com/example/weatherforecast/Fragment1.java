package com.example.weatherforecast;

import static android.content.Context.MODE_PRIVATE;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;
import java.util.Observable;

public class Fragment1 extends Fragment implements MyViewModelObserver {


    private Fragment1ViewModel mViewModel;


    public Fragment1ViewModel getmViewModel() {
        return mViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(Fragment1ViewModel.class);
        mViewModel.addObserver(this);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("currentCity", MODE_PRIVATE);
        mViewModel.city = sharedPreferences.getString("city", "brak");
        mViewModel.temp = (double) sharedPreferences.getFloat("temp", 0);
        mViewModel.pressure = (double) sharedPreferences.getFloat("pressure", 0);
        mViewModel.lon = (double) sharedPreferences.getFloat("lon", 0);
        mViewModel.lat = (double) sharedPreferences.getFloat("lat", 0);
        mViewModel.description = sharedPreferences.getString("description", "brak");
        mViewModel.actualTime = sharedPreferences.getString("actualTime", "brak");
        System.out.println("onCreate1");
    }
    void refresh(){
        if (getView() == null) return;

        ImageView fav = getView().getRootView().findViewById(R.id.addFavourite);
        if(fav!=null)
            if(MyDatabase.getInstance().isCityInDatabase(mViewModel.city))
                fav.setImageResource(R.drawable.iconmonstr_favorite_7);
            else
                fav.setImageResource(R.drawable.iconmonstr_favorite_8);


        TextView cityName = getView().findViewById(R.id.cityname);
        cityName.setText(mViewModel.city);
        TextView tempv = getView().findViewById(R.id.temperature);
        tempv.setText(mViewModel.temp.toString());
        TextView pressurev = getView().findViewById(R.id.preassure);
        pressurev.setText(mViewModel.pressure.toString());
        TextView lonv = getView().findViewById(R.id.longitudeicon);
        lonv.setText(mViewModel.lon.toString());
        TextView latv = getView().findViewById(R.id.latitudeicon);
        latv.setText(mViewModel.lat.toString());
        TextView descriptionv = getView().findViewById(R.id.description);
        descriptionv.setText(mViewModel.description);
        TextView actualTimev = getView().findViewById(R.id.time);
        actualTimev.setText(mViewModel.actualTime);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy1");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView1");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();




        System.out.println("onActivityCreated");


    }

    @Override
    public void onResume() {

        super.onResume();
        refresh();
        System.out.println("onResume1");




    }

    @Override
    public void onCityChanged(String city) {
        mViewModel.city = city;
        refresh();
    }

    @Override
    public void onTempChanged(Double temp) {
        mViewModel.temp = temp;
        refresh();

    }

    @Override
    public void onPressureChanged(Double pressure) {
        mViewModel.pressure = pressure;
        refresh();


    }

    @Override
    public void onLonChanged(Double lon) {
        mViewModel.lon = lon;
        refresh();


    }

    @Override
    public void onLatChanged(Double lat) {
        mViewModel.lat = lat;
        refresh();

    }

    @Override
    public void onDescriptionChanged(String description) {
        mViewModel.description = description;
        refresh();

    }

    @Override
    public void onActualTimeChanged(String actualTime) {
        mViewModel.actualTime = actualTime;
        refresh();

    }

    @Override
    public void onAllChanged(String city, Double temp, Double pressure, Double lon, Double lat, String description, String actualTime) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("currentCity", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("city", city);
        editor.putFloat("temp", temp.floatValue());
        editor.putFloat("pressure", pressure.floatValue());
        editor.putFloat("lon", lon.floatValue());
        editor.putFloat("lat", lat.floatValue());
        editor.putString("description", description);
        editor.putString("actualTime", actualTime);
        editor.apply();

        mViewModel.city = city;
        mViewModel.temp = temp;
        mViewModel.pressure = pressure;
        mViewModel.lon = lon;
        mViewModel.lat = lat;
        mViewModel.description = description;
        mViewModel.actualTime = actualTime;
        refresh();
    }


}