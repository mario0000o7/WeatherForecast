package com.example.weatherforecast;

public interface MyViewModelObserver {
    void onCityChanged(String city);
    void onTempChanged(Double temp);
    void onPressureChanged(Double pressure);
    void onLonChanged(Double lon);
    void onLatChanged(Double lat);
    void onDescriptionChanged(String description);
    void onActualTimeChanged(String actualTime);
    void onAllChanged(String city, Double temp, Double pressure, Double lon, Double lat, String description, String actualTime, String windSpeed, String windDeg, String humidity);


}
