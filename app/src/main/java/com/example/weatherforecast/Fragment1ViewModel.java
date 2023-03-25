package com.example.weatherforecast;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Fragment1ViewModel extends ViewModel {
    private List<MyViewModelObserver> observers= new ArrayList<>();
    String city;
    Double temp;
    Double pressure;
    Double lon, lat;
    String description;
    String actualTime;

    public void addObserver(MyViewModelObserver observer){
        observers.add(observer);
    }
    public void removeObserver(MyViewModelObserver observer){
        observers.remove(observer);
    }
    private void notifyCityChanged(){
        for(MyViewModelObserver observer: observers){
            observer.onCityChanged(city);
        }
    }
    private void notifyAllChanged(){
        for(MyViewModelObserver observer: observers){
            observer.onAllChanged(city, temp, pressure, lon, lat, description, actualTime);
        }
    }
    public void setCity(String city){
        this.city = city;
        notifyCityChanged();
    }
    public void setAll(String city, Double temp, Double pressure, Double lon, Double lat, String description, String actualTime){
        this.city = city;
        this.temp = temp;
        this.pressure = pressure;
        this.lon = lon;
        this.lat = lat;
        this.description = description;
        this.actualTime = actualTime;
        notifyAllChanged();
    }
}