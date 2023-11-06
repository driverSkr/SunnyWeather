package com.example.SunnyWeather.ui.place;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SunnyWeather.R;
import com.example.SunnyWeather.WeatherActivity;
import com.example.SunnyWeather.logic.model.Place;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    //存储地点数据的列表，用于显示在 RecyclerView 中的每个项
    private List<Place> placeList;
    //用于与适配器交互和处理点击事件
    private PlaceFragment fragment;

    public PlaceAdapter(List<Place> placeList, PlaceFragment fragment) {
        this.placeList = placeList;
        this.fragment = fragment;
    }

    //内部静态类 ViewHolder，用于表示 RecyclerView 中每个子项的视图
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
    @Override//用于创建 ViewHolder 并关联对应的布局文件 place_item
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //最外层布局注册了一个点击事件监听器
        //在点击事件中获取当前点击项的经纬度坐标和地区名称，并把它们传入Intent中，最后调用Fragment的startActivity()方法启动WeatherActivity
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Place place = placeList.get(position);

            FragmentActivity activity = fragment.getActivity();
            if (activity instanceof WeatherActivity){
                //如果在 WeatherActivity 中，直接将获取到的地点的经纬度和地区名称设置到 WeatherActivity 的 viewModel 中，并调用 refreshWeather() 方法来刷新天气数据
                WeatherActivity weatherActivity = (WeatherActivity) activity;
                weatherActivity.drawerLayout.closeDrawers();
                weatherActivity.viewModel.locationLng = place.getLocation().getLng();
                weatherActivity.viewModel.locationLat = place.getLocation().getLat();
                weatherActivity.viewModel.placeName = place.getName();
                weatherActivity.refreshWeather();
            }else {
                //如果在其他 Activity 中，创建一个 Intent 并将获取到的地点的经纬度和地区名称作为额外的数据传入 Intent。然后调用 startActivity() 方法启动 WeatherActivity，并关闭当前 Activity
                Intent intent = new Intent(parent.getContext(), WeatherActivity.class);
                intent.putExtra("location_lng",place.getLocation().getLng());
                intent.putExtra("location_lat",place.getLocation().getLat());
                intent.putExtra("place_name",place.getName());

                fragment.startActivity(intent);
                if (fragment.getActivity() != null){
                    fragment.getActivity().finish();
                }
            }

            //当点击了任何子项布局时，在跳转到WeatherActivity之前，先调用PlaceViewModel的savePlace()方法来存储选中的城市
            fragment.viewModel.savePlace(place);
        });
        return holder;
    }

    @Override//用于将地点数据绑定到 ViewHolder 中的视图控件上，显示在 RecyclerView 中
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
    }

    @Override//返回地点数据列表的大小，即 RecyclerView 中子项的数量
    public int getItemCount() {
        return placeList.size();
    }
}
