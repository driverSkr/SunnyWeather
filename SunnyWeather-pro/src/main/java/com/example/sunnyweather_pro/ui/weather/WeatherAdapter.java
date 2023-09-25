package com.example.sunnyweather_pro.ui.weather;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.sunnyweather_pro.logic.model.PlaceInfo;

import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class WeatherAdapter extends FragmentStatePagerAdapter {
    private List<PlaceInfo> placeList;

    public WeatherAdapter(@NonNull FragmentManager fm, List<PlaceInfo> placeList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("boge", "当前的位置 = " + position);
        return WeatherFragment.newInstance(placeList.get(position), getCount());
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return placeList.size();
    }
}
