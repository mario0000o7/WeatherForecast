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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class WeatherApi extends AsyncTask<Void, Void, ArrayList<Object>> {
    String API_TOKEN = "059a1da9ccb871b070ce7269e3170e37";
    String city;
    MyAdapter myAdapter;
    MyWeatherApi openWeatherMapClient;
    public static final int REFRESH=1;
    public static final int FIND=0;
    private int mode=0;


    public WeatherApi(String city, MyAdapter myAdapter,int mode) {
        super();
        openWeatherMapClient = new MyWeatherApi(API_TOKEN);

        this.myAdapter = myAdapter;
        System.out.println(myAdapter.fragments);
        this.city = city;
        this.mode=mode;
    }

    @Override
    public void onPostExecute(ArrayList<Object> weatherList) {
        if (weatherList == null) {
            return;
        }
        JSONObject weather = (JSONObject) weatherList.get(0);
        JSONObject weatherForecasts5Days = (JSONObject) weatherList.get(1);

        Fragment tmp = myAdapter.getFragment(1);
        JSONObject main = null;
        try {
            main = weather.getJSONObject("main");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Double temp  = Math.floor( main.optDouble("temp"));

        Double pressure = Math.floor(main.optDouble("pressure"));
        JSONObject coord = null;
        try {
            coord = weather.getJSONObject("coord");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Double lon = Math.floor(coord.optDouble("lon"));
        Double lat = Math.floor(coord.optDouble("lat"));
        JSONObject weatherJson = weather.optJSONArray("weather").optJSONObject(0);
        String description = weatherJson.optString("description");
        int dt = weather.optInt("dt");
        String time =  new SimpleDateFormat("HH:mm").format(Date.from(Instant.ofEpochSecond(dt)));
        JSONObject windJson = weather.optJSONObject("wind");
        String wind = String.valueOf(windJson.optDouble("speed"));
        String humidity = String.valueOf(main.optDouble("humidity"));
        String windDeg = String.valueOf(windJson.optDouble("deg"));
        String location = weather.optString("name");
        String icon = weatherJson.optString("icon");

        Fragment1ViewModel fragment1ViewModel = ((Fragment1) tmp).getmViewModel();
        Fragment tmp2 = myAdapter.getFragment(2);
        Fragment1ViewModel fragment1ViewModel2 = ((Fragment1) tmp2).getmViewModel();
        fragment1ViewModel2.clearDays();
        JSONArray list = null;
        try {
            list = weatherForecasts5Days.getJSONArray("list");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < list.length(); i++) {
            Double temp2 = Math.floor(list.optJSONObject(i).optJSONObject("temp").optDouble("eve"));
            String time2 =  StringUtils.capitalize(new SimpleDateFormat("EEE",new Locale("pl")).format(Date.from(Instant.ofEpochSecond(list.optJSONObject(i).optInt("dt")))));
            System.out.println(time2);
            String icon2 = list.optJSONObject(i).optJSONArray("weather").optJSONObject(0).optString("icon");


            HashMap<String, String> day = new HashMap<>();
            day.put("temp", String.valueOf(temp2));
            day.put("time", time2);
            day.put("icon", icon2);
            fragment1ViewModel2.addDay(day);

        }

        MyDatabase.getInstance().updateCity(location, temp, pressure, lat, lon, time,description, wind, windDeg, humidity, icon);

        if(mode==FIND)
        {
            fragment1ViewModel.setAll(location, temp, pressure, lon, lat, description, time, wind, windDeg, humidity, icon);
            fragment1ViewModel2.setCities();
        }



    }


    @Override
    protected ArrayList<Object> doInBackground(Void... voids) {
        try {
            JSONObject weatherData = openWeatherMapClient.getCurrentWeather(city);
            JSONObject weather5Days = openWeatherMapClient.get5DaysForecast(city);

            ArrayList<Object> weatherList = new ArrayList<>();
            weatherList.add(weatherData);
            weatherList.add(weather5Days);
            return weatherList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

