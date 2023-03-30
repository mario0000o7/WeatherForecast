package com.example.weatherforecast;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MyDatabase {
    private static MyDatabase instance;
    private FeedReaderDbHelper dbHelper;
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
     String addCity(){
        try {
            if(isCityInDatabase(sharedPreferences.getString("city", "brak"))) {
                System.out.println("Juz jest w bazie");
                return null;
            }

            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CITY, sharedPreferences.getString("city", "brak"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP, sharedPreferences.getFloat("temp", 0));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE, sharedPreferences.getFloat("pressure", 0));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT, sharedPreferences.getFloat("lat", 0));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LON, sharedPreferences.getFloat("lon", 0));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME, sharedPreferences.getString("actualTime", "brak"));
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, sharedPreferences.getString("description", "brak"));
            db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        }catch (Exception | Error e){
            System.out.println("Juz jest w bazie");
            return null;
        }
        return sharedPreferences.getString("city", "brak");
     }
     public void deleteCity(String city){
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city});
    }
     public void updateCity(String city){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CITY, sharedPreferences.getString("city", "brak"));
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP, sharedPreferences.getFloat("temp", 0));
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE, sharedPreferences.getFloat("pressure", 0));
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT, sharedPreferences.getFloat("lat", 0));
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LON, sharedPreferences.getFloat("lon", 0));
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME, sharedPreferences.getString("actualTime", "brak"));
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, sharedPreferences.getString("description", "brak"));
        db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city});
    }
     public boolean isCityInDatabase(String city){
        return db.query(FeedReaderContract.FeedEntry.TABLE_NAME, null, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city}, null, null, null).moveToFirst();
    }
     public void closeDatabase(){
        db.close();
    }
     public Cursor getCursor(){
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_CITY,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP,
                FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LON,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LAT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME
        };
        return db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null, null, null, null);
    }
    public Map<String,String> getCity(String city){
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_CITY,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TEMP,
                FeedReaderContract.FeedEntry.COLUMN_NAME_PRESSURE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LON,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LAT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ACTUALTIME
        };
        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, FeedReaderContract.FeedEntry.COLUMN_NAME_CITY + " = ?", new String[]{city}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
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
        return cityData;






    }


}
