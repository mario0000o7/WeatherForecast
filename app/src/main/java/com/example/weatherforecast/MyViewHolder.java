package com.example.weatherforecast;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

public class MyViewHolder extends RecyclerView.ViewHolder{
    private Fragment1ViewModel mViewModel;
    private TextView mTextView;
    private MainActivity mainActivity;
    public MyViewHolder(@NonNull View itemView, MainActivity mainActivity) {
        super(itemView);
        mViewModel = new ViewModelProvider(mainActivity).get(Fragment1ViewModel.class);
        //change size of item
        mTextView=itemView.findViewById(R.id.hello);
        TextView textView = itemView.findViewById(R.id.hello);
        SharedPreferences sharedPreferences = itemView.getRootView().getContext().getSharedPreferences("currentCity", MODE_PRIVATE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(sharedPreferences.getString("city", "brak"));
                Map<String,String> cityData = MyDatabase.getInstance().getCity(mTextView.getText().toString());
                if(cityData!=null) {
                    mViewModel.setAll(cityData.get("city"), Double.valueOf(cityData.get("temp")), Double.valueOf(cityData.get("pressure")), Double.valueOf(cityData.get("lon")), Double.valueOf(cityData.get("lat")), cityData.get("description"), cityData.get("actualTime"));
                }



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
