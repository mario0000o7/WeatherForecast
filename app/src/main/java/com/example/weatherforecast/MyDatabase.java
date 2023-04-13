package com.example.weatherforecast;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class MyDatabase {
    private static MyDatabase instance;
    private final FeedReaderDbHelper dbHelper;
    private static Activity mainActivity;
    private  SharedPreferences sharedPreferences;
    private  SQLiteDatabase db;


    MyDatabase(Activity mainActivity) {
        MyDatabase.mainActivity = mainActivity;
        sharedPreferences = mainActivity.getSharedPreferences("currentCity", MODE_PRIVATE);
        dbHelper = new FeedReaderDbHelper(mainActivity);
        db = dbHelper.getWritableDatabase();

    }

    public static MyDatabase getInstance() {
        if (instance == null) {
            instance = new MyDatabase(mainActivity);
        }
        return instance;
    }

    public FeedReaderDbHelper getDbHelper() {
        return dbHelper;
    }
     void addCity(){
        try {
            if(isCityInDatabase(sharedPreferences.getString("city", "brak"))) {
                System.out.println("Juz jest w bazie");
                return;
            }
            db = dbHelper.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CITY, sharedPreferences.getString("city", "brak"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP, sharedPreferences.getFloat("temp", 0));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE, sharedPreferences.getFloat("pressure", 0));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT, sharedPreferences.getFloat("lat", 0));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LON, sharedPreferences.getFloat("lon", 0));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME, sharedPreferences.getString("actualTime", "brak"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, sharedPreferences.getString("description", "brak"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDSPEED, sharedPreferences.getString("windSpeed", "brak"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDDEG, sharedPreferences.getString("windDeg", "0"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY, sharedPreferences.getString("humidity", "brak"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ICON, sharedPreferences.getString("icon", "brak"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_JSONLIST, sharedPreferences.getString("jsonList", "brak"));
            db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        }catch (Exception | Error e){
            System.out.println("Juz jest w bazie");
            db.close();
            return;
        }
         sharedPreferences.getString("city", "brak");
        db.close();
     }
     public void deleteCity(String city){
        db=dbHelper.getWritableDatabase();
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city});
        db.close();
    }
     public void updateCity(String city,Double temp,Double pressure,Double lat,Double lon,String actualTime,String description,String windSpeed,String windDeg,String humidity,String icon,String jsonList){
        db=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CITY, city);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP, temp);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE, pressure);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT, lat);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LON, lon);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME, actualTime);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDSPEED, windSpeed);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDDEG, windDeg);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY, humidity);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ICON, icon);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_JSONLIST, jsonList);
        db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city});
        db.close();
    }
     public boolean isCityInDatabase(String city){
        db=dbHelper.getReadableDatabase();
        Cursor tmp=db.query(FeedReaderContract.FeedEntry.TABLE_NAME, null, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city}, null, null, null);
        boolean isCityInDatabase = tmp.getCount() > 0;
        tmp.close();
        db.close();
        return isCityInDatabase;
    }
     public void closeDatabase(){
        try {
            db.close();
            MyDatabase.instance = null;

        }
        catch (Exception ignored){
        }
    }
    public Map<String,Map<String,String>> getCities()
     {
        db=dbHelper.getReadableDatabase();
            String[] projection = {
                    FeedReaderContract.FeedEntry.COLUMN_NAME_CITY,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_LON,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_LAT,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_WINDSPEED,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_WINDDEG,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_ICON,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_JSONLIST
            };
            Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null, null, null, null);
            cursor.moveToFirst();
            Map<String,Map<String,String>> citiesData =new HashMap<>();
            while (!cursor.isAfterLast()) {
                Map<String,String> cityData =new HashMap<>();
                cityData.put("city",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CITY)));
                cityData.put("temp",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP)));
                cityData.put("pressure",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE)));
                cityData.put("lon",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LON)));
                cityData.put("lat",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT)));
                cityData.put("description",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION)));
                cityData.put("actualTime",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME)));
                cityData.put("windSpeed",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDSPEED)));
                cityData.put("windDeg",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDDEG)));
                cityData.put("humidity",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY)));
                cityData.put("icon",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ICON)));
                cityData.put("jsonList",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_JSONLIST)));
                citiesData.put(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CITY)),cityData);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return citiesData;

     }
    public Map<String,String> getCity(String city){
        db=dbHelper.getReadableDatabase();
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_CITY,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP,
                FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LON,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LAT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_WINDSPEED,
                FeedReaderContract.FeedEntry.COLUMN_NAME_WINDDEG,
                FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ICON,
                FeedReaderContract.FeedEntry.COLUMN_NAME_JSONLIST
        };
        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return null;
        }
        Map<String,String> cityData =new HashMap<>();
        cityData.put("city",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CITY)));
        cityData.put("temp",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP)));
        cityData.put("pressure",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE)));
        cityData.put("lon",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LON)));
        cityData.put("lat",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT)));
        cityData.put("description",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION)));
        cityData.put("actualTime",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME)));
        cityData.put("windSpeed",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDSPEED)));
        cityData.put("windDeg",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_WINDDEG)));
        cityData.put("humidity",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY)));
        cityData.put("icon",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_ICON)));
        cityData.put("jsonList",cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_JSONLIST)));
        cursor.close();
        db.close();

        return cityData;






    }


}
