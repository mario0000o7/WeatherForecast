package com.example.weatherforecast;

import androidx.lifecycle.ViewModel;

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
    public void addDay(HashMap<String,String> day){
        days.add(day);
    }
    public void clearDays(){
        days.clear();
    }

    public ArrayList<HashMap<String, String>> getDays() {
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
            observer.onAllChanged(city, temp, pressure, lon, lat, description, actualTime, windSpeed, windDeg, humidity,icon);
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
    public void setAll(String city, Double temp, Double pressure, Double lon, Double lat, String description, String actualTime, String windSpeed, String windDeg, String humidity,String icon){
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

        System.out.println("setAll: "+windDeg);
        notifyAllChanged();
    }

}