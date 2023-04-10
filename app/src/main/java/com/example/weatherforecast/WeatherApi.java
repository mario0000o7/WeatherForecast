package com.example.weatherforecast;

import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class WeatherApi extends AsyncTask<Void,Void,Weather> {
    String API_TOKEN= "059a1da9ccb871b070ce7269e3170e37";
    String city;
    MyAdapter myAdapter;
    OpenWeatherMapClient openWeatherMapClient;
    public WeatherApi(String city, MyAdapter myAdapter) {
        super();
        openWeatherMapClient= new OpenWeatherMapClient(API_TOKEN);
        openWeatherMapClient.setReadTimeout(10000);
        openWeatherMapClient.setConnectionTimeout(10000);
        this.myAdapter=myAdapter;
        System.out.println(myAdapter.fragments);
        this.city = city;
    }

    @Override
    public void onPostExecute(Weather weather) {
        if (weather==null){
            return;
        }

        Fragment tmp=myAdapter.getFragment(1);
        Double temp=Math.floor(weather.getTemperature().getValue());

        Double pressure=Math.floor(weather.getAtmosphericPressure().getValue());
        Double lon=Math.floor(weather.getLocation().getCoordinate().getLongitude());
        Double lat=Math.floor(weather.getLocation().getCoordinate().getLatitude());
        String description=weather.getWeatherState().getDescription();
        String time=weather.getCalculationTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String wind= String.valueOf(weather.getWind().getSpeed());
        String humidity= String.valueOf(weather.getHumidity().getValue());
        String windDeg= String.valueOf(weather.getWind().getDegrees());

        Fragment1ViewModel fragment1ViewModel=((Fragment1)tmp).getmViewModel();
        fragment1ViewModel.setAll(city, temp, pressure, lon, lat, description, time, wind, windDeg, humidity);

        }


    @Override
    protected Weather doInBackground(Void... voids) {
        try {
           Weather weather= openWeatherMapClient.currentWeather().single().byCityName(city).language(Language.POLISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
           return weather;
        }
        catch (Exception e){
            return null;
        }

    }
}

