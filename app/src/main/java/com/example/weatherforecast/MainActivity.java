package com.example.weatherforecast;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("currentCity", MODE_PRIVATE);
        new MyDatabase(this);







        ViewPager2 viewPager = findViewById(R.id.widok1);
        EditText editText = findViewById(R.id.search);
        MyAdapter myAdapter = new MyAdapter(this);
        myAdapter.addFragment(new Fragment1());
        myAdapter.addFragment(new Fragment1());
        myAdapter.addFragment(new Fragment1());



        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                   InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    WeatherApi weatherApi = new WeatherApi(editText.getText().toString(),myAdapter);
                    weatherApi.execute();


                    System.out.println("done");


                    return true;
                }
                return false;
            }
        });



        viewPager.setAdapter(myAdapter);
        viewPager.setCurrentItem(1, false);


        RecyclerView recyclerView = findViewById(R.id.favourites);
        ListAdapter listAdapter = new ListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageView listfav = findViewById(R.id.listfav);
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

        ImageView refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("refresh");
                WeatherApi weatherApi = new WeatherApi(sharedPreferences.getString("city", "brak"),myAdapter);
                weatherApi.execute();
            }
        });

        ImageView addfav = findViewById(R.id.addFavourite);
        addfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = MyDatabase.getInstance().addCity();
                if (city!=null)
                    listAdapter.addCity(city);











            }
        });


    }





}