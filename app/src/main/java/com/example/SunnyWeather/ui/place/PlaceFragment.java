package com.example.SunnyWeather.ui.place;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SunnyWeather.R;
import com.example.SunnyWeather.WeatherActivity;
import com.example.SunnyWeather.logic.model.Place;

import java.util.List;

import kotlin.Result;

public class PlaceFragment extends Fragment {

    public PlaceViewModel viewModel;
    private PlaceAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PlaceViewModel.class);

        //如果当前已有存储的城市数据，那么就获取已存储的数据并解析成Place对象
        if (viewModel.isPlaceSaved()){
            Place place = viewModel.getSavedPlace();
            Intent intent = new Intent(getContext(), WeatherActivity.class);
            //使用它的经纬度坐标和城市名直接跳转并传递给WeatherActivity,这样用户就不需要每次都重新搜索并选择城市了
            intent.putExtra("location_lng", place.getLocation().getLng());
            intent.putExtra("location_lat", place.getLocation().getLat());
            intent.putExtra("place_name", place.getName());

            startActivity(intent);
            if (getActivity() != null){
                getActivity().finish();
            }
        }

        recyclerView = requireView().findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlaceAdapter(viewModel.getPlaceList(),this);
        recyclerView.setAdapter(adapter);

        EditText searchPlaceEdit = requireView().findViewById(R.id.searchPlaceEdit);
        ImageView bgImageView = getView().findViewById(R.id.bgImageView);

        searchPlaceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String content = charSequence.toString();
                if (!content.isEmpty()) {
                    viewModel.searchPlaces(content);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    bgImageView.setVisibility(View.VISIBLE);
                    viewModel.getPlaceList().clear();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        viewModel.getPlaceLiveData().observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> result) {
                if (result != null){
                    recyclerView.setVisibility(View.VISIBLE);
                    bgImageView.setVisibility(View.GONE);
                    viewModel.getPlaceList().clear();
                    viewModel.getPlaceList().addAll(result);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "未能查询到任何地点", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
