package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 viewPager = findViewById(R.id.widok1);

        viewPager.setAdapter(new MyAdapter(this));
        RecyclerView recyclerView = findViewById(R.id.favourites);
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


    }
}