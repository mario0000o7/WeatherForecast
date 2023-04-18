package com.example.weatherforecast;

import android.widget.ProgressBar;

import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment1ViewModel extends ViewModel {
    private List<MyViewModelObserver> observers= new ArrayList<>();
    private ArrayList<HashMap<String,String>> days = new ArrayList<>();
    String city;
    Double temp;
    Double pressure;
    Double lon, lat;
    String description;
    String actualTime;
    String windSpeed;
    String windDeg;
    String humidity;
    String icon;
    String jsonList;
    int currentTempMode = MyWeatherApi.CELSIUS;
    public void addDay(HashMap<String,String> day){
        days.add(day);
    }
    public void clearDays(){
        days.clear();
    }

    public ArrayList<HashMap<String, String>> getDays() throws JSONException {
        ArrayList<HashMap<String,String>> days = new ArrayList<>();
        JSONArray jsonObject = new JSONArray(jsonList);
        for(int i=0;i<jsonObject.length();i++){
            JSONObject day = jsonObject.getJSONObject(i);
            HashMap<String,String> dayMap = new HashMap<>();
            dayMap.put("time",day.getString("time"));
            dayMap.put("temp",day.getString("temp"));
            dayMap.put("icon",day.getString("icon"));
            days.add(dayMap);

        }
        return days;
    }
    public void setCities()
    {
        notifyDayChanged();
    }

    public void addObserver(MyViewModelObserver observer){
        observers.add(observer);
    }
    public void removeObserver(MyViewModelObserver observer){
        observers.remove(observer);
    }

    public List<MyViewModelObserver> getObservers() {
        return observers;
    }

    private void notifyCityChanged(){
        for(MyViewModelObserver observer: observers){
            observer.onCityChanged(city);
        }
    }
    private void notifyAllChanged(){
        for(MyViewModelObserver observer: observers){
            observer.onAllChanged(city, temp, pressure, lon, lat, description, actualTime, windSpeed, windDeg, humidity,icon,jsonList);
        }
    }
    private void notifyDayChanged(){
        for(MyViewModelObserver observer: observers){
            observer.onDayChanged();
        }
    }
    public void setCity(String city){
        this.city = city;
        notifyDayChanged();
    }
    public void setDays(ArrayList<HashMap<String,String>> days){
        notifyCityChanged();
    }
    public void setAll(String city, Double temp, Double pressure, Double lon, Double lat, String description, String actualTime, String windSpeed, String windDeg, String humidity,String icon, String jsonList){
        this.city = city;
        this.temp = temp;
        this.pressure = pressure;
        this.lon = lon;
        this.lat = lat;
        this.description = description;
        this.actualTime = actualTime;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.humidity = humidity;
        this.icon=icon;
        this.jsonList=jsonList;

        notifyAllChanged();
    }

}