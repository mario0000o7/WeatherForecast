package com.example.weatherforecast;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;


public class WeatherApi extends AsyncTask<Void, Void, ArrayList<Object>> {
    String API_TOKEN = "059a1da9ccb871b070ce7269e3170e37";
    String city;
    MyAdapter myAdapter;
    MyWeatherApi openWeatherMapClient;
    public static final int REFRESH=1;
    public static final int FIND=0;
    private final int mode;



    public WeatherApi(String city, MyAdapter myAdapter,int mode) {
        super();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(myAdapter.mainActivity.getApplicationContext(), ConnectivityManager.class);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                openWeatherMapClient = new MyWeatherApi(API_TOKEN);


                this.myAdapter = myAdapter;
                this.city = city;
                this.mode=mode;
            }
            else {
                throw new RuntimeException("Brak połączenia z internetem");
            }
        }
        else {
            throw new RuntimeException("Brak połączenia z internetem");
        }

    }

    @Override
    public void onPostExecute(ArrayList<Object> weatherList) {
        ProgressBar progressBar = myAdapter.mainActivity.findViewById(R.id.progressBar);
        if (weatherList == null) {
            Toast.makeText(myAdapter.mainActivity.getApplicationContext(), "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
            return;

        }
        if (weatherList.size()==0){
            Toast.makeText(myAdapter.mainActivity.getApplicationContext(), "Nie ma takiego miasta", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject weather = (JSONObject) weatherList.get(0);
        JSONObject weatherForecasts5Days = (JSONObject) weatherList.get(1);

        Fragment tmp = myAdapter.getFragment(1);
        JSONObject main;
        try {
            main = weather.getJSONObject("main");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Double temp  = Math.floor( main.optDouble("temp"));

        Double pressure = Math.floor(main.optDouble("pressure"));
        JSONObject coord;
        try {
            coord = weather.getJSONObject("coord");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Double lon = (coord.optDouble("lon"));
        Double lat = (coord.optDouble("lat"));
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
        JSONArray list;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            list = weatherForecasts5Days.getJSONArray("list");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        stringBuilder.append("[");

        for (int i = 0; i < list.length(); i++) {
            Double temp2 = Math.floor(Objects.requireNonNull(list.optJSONObject(i).optJSONObject("temp")).optDouble("eve"));
            String time2 =  StringUtils.capitalize(new SimpleDateFormat("EEE",new Locale("pl")).format(Date.from(Instant.ofEpochSecond(list.optJSONObject(i).optInt("dt")))));
            String icon2 = Objects.requireNonNull(list.optJSONObject(i).optJSONArray("weather")).optJSONObject(0).optString("icon");


            HashMap<String, String> day = new HashMap<>();
            day.put("temp", String.valueOf(temp2));
            day.put("time", time2);
            day.put("icon", icon2);
            if(i==list.length()-1)
                stringBuilder.append("{" + "\"temp\": \"").append(temp2).append("\", \"time\": \"").append(time2).append("\", \"icon\": \"").append(icon2).append("\"}");
            else
                stringBuilder.append("{" + "\"temp\": \"").append(temp2).append("\", \"time\": \"").append(time2).append("\", \"icon\": \"").append(icon2).append("\"},");
            fragment1ViewModel2.addDay(day);

        }
        stringBuilder.append("]");


        MyDatabase.getInstance().updateCity(location, temp, pressure, lat, lon, time,description, wind, windDeg, humidity, icon, stringBuilder.toString());

        if(mode==FIND)
        {
            fragment1ViewModel.setAll(location, temp, pressure, lon, lat, description, time, wind, windDeg, humidity, icon,stringBuilder.toString());
            fragment1ViewModel2.setCities();
        }
        progressBar.setVisibility(View.INVISIBLE);



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
        } catch (IOException e) {
            return null;
        }catch (JSONException|RuntimeException e) {
            return new ArrayList<Object>();
        }

    }
}


