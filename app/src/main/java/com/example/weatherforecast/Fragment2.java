package com.example.weatherforecast;

import static android.content.Context.MODE_PRIVATE;

import androidx.lifecycle.ViewModelProvider;

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

public class Fragment2 extends Fragment1{


    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Override
    public void setmViewModel(Fragment1ViewModel mViewModel) {
        super.setmViewModel(mViewModel);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.removeObserver(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(Fragment1ViewModel.class);
        mViewModel.addObserver(this);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("currentCity", MODE_PRIVATE);
        mViewModel.city = sharedPreferences.getString("city", "brak");
        mViewModel.temp = (double) sharedPreferences.getFloat("temp", 0);
        mViewModel.pressure = (double) sharedPreferences.getFloat("pressure", 0);
        mViewModel.lon = (double) sharedPreferences.getFloat("lon", 0);
        mViewModel.lat = (double) sharedPreferences.getFloat("lat", 0);
        mViewModel.description = sharedPreferences.getString("description", "brak");
        mViewModel.actualTime = sharedPreferences.getString("actualTime", "brak");
        mViewModel.windSpeed =  sharedPreferences.getString("windSpeed", "brak");
        mViewModel.windDeg =  sharedPreferences.getString("windDeg", "0");
        mViewModel.humidity = sharedPreferences.getString("humidity", "brak");
        System.out.println("onCreateFragment2");
    }

    @Override
    public Fragment1ViewModel getmViewModel() {
        return super.getmViewModel();
    }

    @Override
    public void refresh() {
        if (getView() == null) return;
        ImageView fav = getView().getRootView().findViewById(R.id.addFavourite);
        if(fav!=null)
            if(MyDatabase.getInstance().isCityInDatabase(mViewModel.city))
                fav.setImageResource(R.drawable.iconmonstr_favorite_7);
            else
                fav.setImageResource(R.drawable.iconmonstr_favorite_8);


        TextView windSpeed = getView().findViewById(R.id.speedWindValue);
        windSpeed.setText(mViewModel.windSpeed+"m/s");
        ImageView windDeg = getView().findViewById(R.id.windDirImg);
        windDeg.setRotation(Float.parseFloat(mViewModel.windDeg));
        TextView humidity = getView().findViewById(R.id.humidityValue);
        humidity.setText(mViewModel.humidity+"%");
    }

}