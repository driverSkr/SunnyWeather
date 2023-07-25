package com.example.SunnyWeather.ui.place;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SunnyWeather.R;
import com.example.SunnyWeather.WeatherActivity;
import com.example.SunnyWeather.logic.model.Place;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private List<Place> placeList;
    private PlaceFragment fragment;

    public PlaceAdapter(List<Place> placeList, PlaceFragment fragment) {
        this.placeList = placeList;
        this.fragment = fragment;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView placeName;
        TextView placeAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            placeAddress = itemView.findViewById(R.id.placeAddress);
        }
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //最外层布局注册了一个点击事件监听器
        //在点击事件中获取当前点击项的经纬度坐标和地区名称，并把它们传入Intent中，最后调用Fragment的startActivity()方法启动WeatherActivity
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Place place = placeList.get(position);
            Intent intent = new Intent(parent.getContext(), WeatherActivity.class);
            intent.putExtra("location_lng",place.getLocation().getLng());
            intent.putExtra("location_lat",place.getLocation().getLat());
            intent.putExtra("place_name",place.getName());

            //当点击了任何子项布局时，在跳转到WeatherActivity之前，先调用PlaceViewModel的savePlace()方法来存储选中的城市
            fragment.viewModel.savePlace(place);
            fragment.startActivity(intent);
            if (fragment.getActivity() != null){
                fragment.getActivity().finish();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
