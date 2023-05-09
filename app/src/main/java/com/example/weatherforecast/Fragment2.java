package com.example.weatherforecast;

import static android.content.Context.MODE_PRIVATE;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
//
    }

    @Override
    public Fragment1ViewModel getmViewModel() {
        return super.getmViewModel();
    }

    @SuppressLint("SetTextI18n")
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
        if (MyWeatherApi.currentTempMode == MyWeatherApi.CELSIUS)
            windSpeed.setText(mViewModel.windSpeed+" m/s");
        else
            windSpeed.setText(mViewModel.windSpeed+" mph");
        ImageView windDeg = getView().findViewById(R.id.windDirImg);
        windDeg.setRotation(Float.parseFloat(mViewModel.windDeg));
        TextView humidity = getView().findViewById(R.id.humidityValue);
        humidity.setText(mViewModel.humidity+"%");
    }

}