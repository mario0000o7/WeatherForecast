package com.example.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private ArrayList<Day> days;
    private Context context;

    public DayAdapter(ArrayList<Day> days, Context context) {
        this.days = days;
        this.context = context;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day day = days.get(position);
        holder.day.setText("test");
        holder.temp.setText("test");
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView temp;
        ImageView icon;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            temp = itemView.findViewById(R.id.temp);
            icon = itemView.findViewById(R.id.dayicon);
        }
    }
}

