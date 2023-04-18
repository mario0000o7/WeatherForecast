package com.example.weatherforecast;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

public class MyViewHolder extends RecyclerView.ViewHolder{
    private Fragment1ViewModel mViewModel;
    private UlubioneViewModel mViewModel2;
    private TextView mTextView;
    private MainActivity mainActivity;
    public MyViewHolder(@NonNull View itemView, MainActivity mainActivity) {
        super(itemView);
        mViewModel = new ViewModelProvider(mainActivity).get(Fragment1ViewModel.class);
        mViewModel2 = new ViewModelProvider(mainActivity).get(UlubioneViewModel.class);
        //change size of item
        mTextView=itemView.findViewById(R.id.hello);
        TextView textView = itemView.findViewById(R.id.hello);
        SharedPreferences sharedPreferences = itemView.getRootView().getContext().getSharedPreferences("currentCity", MODE_PRIVATE);
        ImageView removeButton = itemView.findViewById(R.id.removeCity);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> cityData = MyDatabase.getInstance().getCity(mTextView.getText().toString());
                if(cityData!=null) {
                    mViewModel.setAll(cityData.get("city"), Double.valueOf(cityData.get("temp")), Double.valueOf(cityData.get("pressure")), Double.valueOf(cityData.get("lon")), Double.valueOf(cityData.get("lat")), cityData.get("description"), cityData.get("actualTime"),cityData.get("windSpeed"),cityData.get("windDeg"),cityData.get("humidity"),cityData.get("icon"),cityData.get("jsonList"));
                }



            }

        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel2.notifyFavouriteCityChanged(mTextView.getText().toString());
            }
        });
    }
    public TextView getmTextView() {
        return mTextView;
    }
    public void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void setmTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }



}
