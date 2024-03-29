package com.example.weatherforecast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();
    MainActivity mainActivity;

    public MyAdapter(@NonNull FragmentActivity fragmentActivity) {


        super(fragmentActivity);
        this.mainActivity = (MainActivity) fragmentActivity;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return fragments.get(1);
            case 2:
                return fragments.get(2);
            default:
                return fragments.get(0);
        }
    }

    public void addFragment(Fragment fragment) {
        ((Fragment1)fragment).setmViewModel(new ViewModelProvider(mainActivity).get(Fragment1ViewModel.class));
        fragments.add(fragment);
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }



}

