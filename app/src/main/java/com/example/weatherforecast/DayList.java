package com.example.weatherforecast;

import static android.content.Context.MODE_PRIVATE;

import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DayList extends Fragment1 {


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
        mViewModel = new ViewModelProvider(requireActivity()).get(Fragment1ViewModel.class);
        refresh();





        // TODO: Use the ViewModel
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    void refresh() {
        if (getView() == null) return;

        ImageView fav = getView().getRootView().findViewById(R.id.addFavourite);
        if(fav!=null)
            if(MyDatabase.getInstance().isCityInDatabase(mViewModel.city))
                fav.setImageResource(R.drawable.iconmonstr_favorite_7);
            else
                fav.setImageResource(R.drawable.iconmonstr_favorite_8);

        FragmentContainerView day1 = getView().findViewById(R.id.day1);
        FragmentContainerView day2 = getView().findViewById(R.id.day2);
        FragmentContainerView day3 = getView().findViewById(R.id.day3);
        FragmentContainerView day4 = getView().findViewById(R.id.day4);
        FragmentContainerView day5 = getView().findViewById(R.id.day5);
        List<FragmentContainerView> list = new ArrayList<>();
        list.add(day1);
        list.add(day2);
        list.add(day3);
        list.add(day4);
        list.add(day5);
        ArrayList<HashMap<String,String>> data=mViewModel.getDays();
        System.out.println("data.size()"+data.size());
        System.out.println("list.size()"+list.size());
        if (data.size()>0&&list.size()>0)
            for(int i=0;i<list.size();i++){
                TextView day = list.get(i).findViewById(R.id.day);
                TextView temp = list.get(i).findViewById(R.id.temp);
                ImageView icon = list.get(i).findViewById(R.id.dayicon);
                day.setText(data.get(i).get("day"));
                temp.setText(data.get(i).get("temp"));
                icon.setImageResource(IconChanger.change(data.get(i).get("icon")));

            }

    }

    @Override
    public Fragment1ViewModel getmViewModel() {
        return mViewModel;
    }

    @Override
    public void setmViewModel(Fragment1ViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.removeObserver(this);
    }

    @Override
    public void onDayChanged() {
        super.onDayChanged();
    }
}