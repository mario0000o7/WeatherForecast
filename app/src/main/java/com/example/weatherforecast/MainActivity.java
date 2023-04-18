package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;


public class MainActivity extends AppCompatActivity{
    MyAdapter myAdapter;
    ListAdapter listAdapter;
    ViewPager2 viewPager;
    boolean isTablet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        new MyDatabase(this);

        isTablet=(getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("currentCity", MODE_PRIVATE);
        EditText editText = findViewById(R.id.search);
        myAdapter = new MyAdapter(this);

        myAdapter.addFragment(new Fragment2());
        myAdapter.addFragment(new Fragment1());
        myAdapter.addFragment(new DayList());
        ConstraintLayout constraintLayout = findViewById(R.id.layout);



        RecyclerView recyclerView = findViewById(R.id.favourites);
        listAdapter = new ListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ImageView listfav = findViewById(R.id.listfav);
        recyclerView.setLayoutManager(linearLayoutManager);


        if (isTablet)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Add the first fragment to the first container
            fragmentTransaction.add(R.id.fragmentContainerView, myAdapter.getFragment(1));

            // Add the second fragment to the second container
            fragmentTransaction.add(R.id.fragmentContainerView2, myAdapter.getFragment(0));

            // Add the third fragment to the third container
            fragmentTransaction.add(R.id.fragmentContainerView3, myAdapter.getFragment(2));

            // Commit the transaction
            fragmentTransaction.commit();

        }
        else {
            viewPager = findViewById(R.id.widok1);
            viewPager.setAdapter(myAdapter);
            viewPager.setCurrentItem(1, false);
        }











        ProgressBar progressBar = findViewById(R.id.progressBar);



        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                   InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        WeatherApi weatherApi = new WeatherApi(editText.getText().toString(),myAdapter,WeatherApi.FIND);
                        weatherApi.execute();
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }



                    Log.println(Log.INFO, "editText", editText.getText().toString());


                    return true;
                }
                return false;
            }
        });








        listfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
            }
        });

        ImageView change = findViewById(R.id.changeModeTemp);
        if(sharedPreferences.getInt("currentModeTemp", 0)==0){
            change.setImageResource(R.drawable.cel);
        }
        else{
            change.setImageResource(R.drawable.faren);
        }
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("currentCity", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.apply();
                Log.println(Log.INFO, "refresh", "refresh");
                Map<String, Map<String,String>>cities = MyDatabase.getInstance().getCities();
                progressBar.setVisibility(View.VISIBLE);
                try {
                    for (String city : cities.keySet()) {
                        WeatherApi weatherApi = new WeatherApi(city, myAdapter, WeatherApi.REFRESH);
                        weatherApi.execute();
                    }
                    WeatherApi weatherApi = new WeatherApi(sharedPreferences.getString("city", "brak"), myAdapter, WeatherApi.FIND);
                    weatherApi.execute();
                    if (sharedPreferences.getInt("currentModeTemp", 0)==0) {
                        editor.putInt("currentModeTemp", MyWeatherApi.FARENHEIT);
                        MyWeatherApi.currentTempMode = MyWeatherApi.FARENHEIT;
                        change.setImageResource(R.drawable.faren);
                    } else {
                        editor.putInt("currentModeTemp", MyWeatherApi.CELSIUS);
                        MyWeatherApi.currentTempMode = MyWeatherApi.CELSIUS;
                        change.setImageResource(R.drawable.cel);
                    }
                }
                catch (RuntimeException e){
                    Toast.makeText(MainActivity.this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        ImageView refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.INFO, "refresh", "refresh");
                Map<String, Map<String,String>>cities = MyDatabase.getInstance().getCities();
                progressBar.setVisibility(View.VISIBLE);
                try {
                    for (String city : cities.keySet()) {
                        WeatherApi weatherApi = new WeatherApi(city, myAdapter, WeatherApi.REFRESH);
                        weatherApi.execute();
                    }
                    WeatherApi weatherApi = new WeatherApi(sharedPreferences.getString("city", "brak"), myAdapter, WeatherApi.FIND);
                    weatherApi.execute();
                }
                catch (RuntimeException e){
                    Toast.makeText(MainActivity.this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        ImageView addfav = findViewById(R.id.addFavourite);
        if (MyDatabase.getInstance().isCityInDatabase(sharedPreferences.getString("city", "brak"))) {
            addfav.setImageResource(R.drawable.iconmonstr_favorite_7);
        } else {
            addfav.setImageResource(R.drawable.iconmonstr_favorite_8);
        }
        addfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter.addCity(sharedPreferences.getString("city", "brak"));











            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyDatabase.getInstance().closeDatabase();
        listAdapter.removeObserver();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}