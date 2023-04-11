package com.example.weatherforecast;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DayList extends Fragment1 {

    private DayListViewModel mViewModel;

    public static DayList newInstance() {
        return new DayList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DayListViewModel.class);
        ArrayList<Day> days = new ArrayList<>();
        days.add(new Day());
        days.add(new Day());
        days.add(new Day());

        DayAdapter dayAdapter = new DayAdapter(days, getContext());
        RecyclerView recyclerView = getView().findViewById(R.id.dayListRecycle);
        recyclerView.setAdapter(dayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // TODO: Use the ViewModel
    }

    @Override
    void refresh() {

    }
}