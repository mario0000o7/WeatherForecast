package com.example.weatherforecast;

import androidx.fragment.app.Fragment;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class WeatherApi extends Thread{
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
        this.city = city;
    }

    @Override
    public void run() {
        super.run();
        Weather weather=openWeatherMapClient.currentWeather().single().byCityName(city).language(Language.POLISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
        Fragment tmp=myAdapter.getFragment(1);
        Double temp=Math.floor(weather.getTemperature().getValue());
        Double pressure=Math.floor(weather.getAtmosphericPressure().getValue());
        Double lon=Math.floor(weather.getLocation().getCoordinate().getLongitude());
        Double lat=Math.floor(weather.getLocation().getCoordinate().getLatitude());
        String description=weather.getWeatherState().getDescription();
        String time=weather.getCalculationTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));


        if (tmp.isAdded())
            ((Fragment1)tmp).setAll(city, temp, pressure, lon, lat, description, time);
        tmp=myAdapter.getFragment(0);
        if (tmp.isAdded())
            ((Fragment1)tmp).setAll(city, temp, pressure, lon, lat, description, time);
        tmp=myAdapter.getFragment(2);
        if(tmp.isAdded())
            ((Fragment1)tmp).setAll(city, temp, pressure, lon, lat, description, time);




    }
}

