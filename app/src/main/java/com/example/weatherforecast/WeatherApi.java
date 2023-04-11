package com.example.weatherforecast;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;

public class WeatherApi extends AsyncTask<Void,Void, ArrayList<Object>> {
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
    public void onPostExecute(ArrayList<Object> weatherList) {
        if (weatherList==null){
            return;
        }
        Weather weather=(Weather)weatherList.get(0);
        Forecast forecast=(Forecast)weatherList.get(1);

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
        String location=weather.getLocation().getName();
        String icon=weather.getWeatherState().getIconId();

        Fragment1ViewModel fragment1ViewModel=((Fragment1)tmp).getmViewModel();
        fragment1ViewModel.setAll(location, temp, pressure, lon, lat, description, time, wind, windDeg, humidity,icon);
        Fragment tmp2=myAdapter.getFragment(2);
        Fragment1ViewModel fragment1ViewModel2=((Fragment1)tmp2).getmViewModel();
        fragment1ViewModel2.clearDays();
        for (int i=0;i<forecast.getWeatherForecasts().size();i++){

            Double temp2=Math.floor(forecast.getWeatherForecasts().get(i).getTemperature().getValue());
            String time2=forecast.getWeatherForecasts().get(i).getDayTime().getValue();
            String icon2=forecast.getWeatherForecasts().get(i).getWeatherState().getIconId();


            HashMap<String,String> day=new HashMap<>();
            day.put("temp", String.valueOf(temp2));
            day.put("time", time2);
            day.put("icon", icon2);
            fragment1ViewModel2.addDay(day);

        }
        fragment1ViewModel2.setCities();

        }


    @Override
    protected ArrayList<Object> doInBackground(Void... voids) {
        try {
           Weather weather= openWeatherMapClient.currentWeather().single().byCityName(city).language(Language.POLISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
           Forecast weather5Days= openWeatherMapClient.forecast5Day3HourStep().byCityName(city).language(Language.POLISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
              ArrayList<Object> weatherList=new ArrayList<>();
                weatherList.add(weather);
                weatherList.add(weather5Days);
                return weatherList;
        }
        catch (Exception e){
            return null;
        }

    }
}

