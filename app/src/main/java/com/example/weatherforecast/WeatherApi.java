package com.example.weatherforecast;

import android.os.AsyncTask;
import android.text.method.TextKeyListener;

import androidx.fragment.app.Fragment;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;


import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class WeatherApi extends AsyncTask<Void, Void, ArrayList<Object>> {
    String API_TOKEN = "059a1da9ccb871b070ce7269e3170e37";
    String city;
    MyAdapter myAdapter;
    OpenWeatherMapClient openWeatherMapClient;


    public WeatherApi(String city, MyAdapter myAdapter) {
        super();
        openWeatherMapClient = new OpenWeatherMapClient(API_TOKEN);
        openWeatherMapClient.setReadTimeout(10000);
        openWeatherMapClient.setConnectionTimeout(10000);
        this.myAdapter = myAdapter;
        System.out.println(myAdapter.fragments);
        this.city = city;
    }

    @Override
    public void onPostExecute(ArrayList<Object> weatherList) {
        if (weatherList == null) {
            return;
        }
        Weather weather = (Weather) weatherList.get(0);
        List<WeatherForecast> weatherForecasts = (List<WeatherForecast>) weatherList.get(1);

        Fragment tmp = myAdapter.getFragment(1);
        Double temp = Math.floor(weather.getTemperature().getValue());

        Double pressure = Math.floor(weather.getAtmosphericPressure().getValue());
        Double lon = Math.floor(weather.getLocation().getCoordinate().getLongitude());
        Double lat = Math.floor(weather.getLocation().getCoordinate().getLatitude());
        String description = weather.getWeatherState().getDescription();
        String time = weather.getCalculationTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String wind = String.valueOf(weather.getWind().getSpeed());
        String humidity = String.valueOf(weather.getHumidity().getValue());
        String windDeg = String.valueOf(weather.getWind().getDegrees());
        String location = weather.getLocation().getName();
        String icon = weather.getWeatherState().getIconId();

        Fragment1ViewModel fragment1ViewModel = ((Fragment1) tmp).getmViewModel();
        fragment1ViewModel.setAll(location, temp, pressure, lon, lat, description, time, wind, windDeg, humidity, icon);
        Fragment tmp2 = myAdapter.getFragment(2);
        Fragment1ViewModel fragment1ViewModel2 = ((Fragment1) tmp2).getmViewModel();
        fragment1ViewModel2.clearDays();
        Locale locale = new Locale("pl", "PL");
        for (int i = 0; i < weatherForecasts.size(); i++) {

            Double temp2 = Math.floor(weatherForecasts.get(i).getTemperature().getValue());
            String time2 = StringUtils.capitalize(weatherForecasts.get(i).getForecastTime().format(DateTimeFormatter.ofPattern("EEE", locale)).toString());
            System.out.println(time2);
            String icon2 = weatherForecasts.get(i).getWeatherState().getIconId();


            HashMap<String, String> day = new HashMap<>();
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
            Weather weather = openWeatherMapClient.currentWeather().single().byCityName(city).language(Language.POLISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
            Forecast weather5Days = openWeatherMapClient.forecast5Day3HourStep().byCityName(city).language(Language.POLISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
            Locale locale = new Locale("pl", "PL");
            List<WeatherForecast> weatherListWithoutDuplicates =
                    weather5Days.getWeatherForecasts()
                            .stream() // Create a stream of the weather forecasts
                            .collect(Collectors.collectingAndThen(
                                    Collectors.toCollection(
                                            () -> new TreeSet<>(Comparator.comparing(wf -> wf.getForecastTime().getDayOfWeek()
                                            ))
                                    ), ArrayList::new)
                            );
            weatherListWithoutDuplicates=weatherListWithoutDuplicates.stream().sorted( Comparator.comparing(WeatherForecast::getForecastTime)).collect(Collectors.toList());
            ArrayList<Object> weatherList = new ArrayList<>();
            weatherList.add(weather);
            weatherList.add(weatherListWithoutDuplicates);
            return weatherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

