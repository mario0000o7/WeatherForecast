package com.example.weatherforecast;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ListAdapter extends RecyclerView.Adapter<MyViewHolder> implements UlubioneObserver{
    ArrayList<String>places=new ArrayList<>();
    private UlubioneViewModel ulubioneViewModel;
    MainActivity mainActivity;
    public ListAdapter(MainActivity mainActivity) {
        Map<String,Map<String,String>>cities = MyDatabase.getInstance().getCities();
        this.mainActivity = mainActivity;
        ulubioneViewModel = new ViewModelProvider(mainActivity).get(UlubioneViewModel.class);
        ulubioneViewModel.addObserwer(this);

        for (Map.Entry<String,Map<String,String>> entry : cities.entrySet()) {
            places.add(entry.getKey());
        }



    }



    public void addCity(String city){
        ImageView fav = mainActivity.findViewById(R.id.addFavourite);
        if(!places.contains(city)) {
            MyDatabase.getInstance().addCity();
            places.add(city);
            fav.setImageResource(R.drawable.iconmonstr_favorite_7);
            notifyItemInserted(places.size());

        }
        else {
            MyDatabase.getInstance().deleteCity(city);
            int position = places.indexOf(city);
            places.remove(city);
            notifyItemRemoved(position);
            fav.setImageResource(R.drawable.iconmonstr_favorite_8);
        }
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(),R.layout.fragment_ulubione,null);
        return new MyViewHolder(view,mainActivity);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        for (int i=0;i<places.size();i++){
            holder.getmTextView().setText(places.get(position));
        }

    }


    @Override
    public int getItemCount() {
        return places.size();
    }


    @Override
    public void onFavouriteCityChanged(String city) {
        addCity(city);
    }
    public void removeObserver(){
        ulubioneViewModel.removeObserver(this);
    }

}
