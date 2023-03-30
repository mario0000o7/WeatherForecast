package com.example.weatherforecast;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class UlubioneViewModel extends ViewModel {
    private List<MyViewModelObserver> observers= new ArrayList<>();
    public void addObserwer(MyViewModelObserver observer){
        observers.add(observer);
    }
    public void removeObserver(MyViewModelObserver observer){
        observers.remove(observer);
    }



    // TODO: Implement the ViewModel
}