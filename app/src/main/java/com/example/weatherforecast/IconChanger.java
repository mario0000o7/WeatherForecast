package com.example.weatherforecast;

public class IconChanger {
    static public int change(String icon){
        switch (icon){
            case "01d":
                return R.drawable.wi_day_sunny;
            case "01n":
                return R.drawable.wi_night_clear;
            case "02d":
                return R.drawable.wi_day_cloudy;
            case "02n":
                return R.drawable.wi_night_alt_cloudy;
            case "03d":
                return R.drawable.wi_cloud;
            case "03n":
                return R.drawable.wi_cloud;
            case "04d":
                return R.drawable.wi_cloudy;
            case "04n":
                return R.drawable.wi_cloudy;
            case "09d":
                return R.drawable.wi_showers;
            case "09n":
                return R.drawable.wi_showers;
            case "10d":
                return R.drawable.wi_day_rain;
            case "10n":
                return R.drawable.wi_night_alt_rain;
            case "11d":
                return R.drawable.wi_thunderstorm;
            case "11n":
                return R.drawable.wi_thunderstorm;
            case "13d":
                return R.drawable.wi_snow;
            case "13n":
                return R.drawable.wi_snow;
            case "50d":
                return R.drawable.wi_fog;
            case "50n":
                return R.drawable.wi_fog;
            default:
                return R.drawable.wi_na;
        }

    }
}
