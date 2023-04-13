package com.example.weatherforecast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MyWeatherApi {
    private final String apiToken;
    private final String lang = "pl";
    private final String units = "metric";
    MyWeatherApi(String apiToken) {
        this.apiToken = apiToken;

    }
    public JSONObject getCordinates(String city) throws IOException, JSONException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiToken + "&limit=1"+ "&lang=" + lang+ "&units=" + units);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            JSONObject data = new JSONObject(inline.toString());
            return data.getJSONObject("coord");
        }
    }

    public JSONObject getCurrentWeather(String city) throws JSONException, IOException {
        JSONObject coord = getCordinates(city);
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + coord.get("lat") + "&lon=" + coord.get("lon") + "&appid=" + apiToken+ "&lang=" + lang+ "&units=" + units);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            return new JSONObject(inline.toString());
        }
    }

    public JSONObject get5DaysForecast(String city) throws IOException, JSONException {
        JSONObject coord = getCordinates(city);
        URL url = new URL("https://api.openweathermap.org/data/2.5/forecast/daily?lat=" + coord.get("lat") + "&lon=" + coord.get("lon") + "&cnt=5&appid=" + apiToken+ "&lang=" + lang+ "&units=" + units);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            return new JSONObject(inline.toString());
        }
    }



}
