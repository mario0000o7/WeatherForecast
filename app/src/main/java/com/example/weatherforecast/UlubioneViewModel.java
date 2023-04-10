package com.example.weatherforecast;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class UlubioneViewModel extends ViewModel {
    private List<UlubioneObserver> observers= new ArrayList<>();
    public void addObserwer(UlubioneObserver observer){
        observers.add(observer);
    }
    public void removeObserver(UlubioneObserver observer){
        observers.remove(observer);
    }

    public List<UlubioneObserver> getObservers() {
        return observers;
    }

    public void notifyFavouriteCityChanged(String city){
        for(UlubioneObserver observer: observers){
            observer.onFavouriteCityChanged(city);
        }
    }



    // TODO: Implement the ViewModel
}