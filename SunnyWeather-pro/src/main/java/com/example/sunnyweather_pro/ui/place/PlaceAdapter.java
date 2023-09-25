package com.example.sunnyweather_pro.ui.place;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather_pro.R;
import com.example.sunnyweather_pro.logic.dao.SharedPreferenceDao;
import com.example.sunnyweather_pro.logic.model.Place;
import com.example.sunnyweather_pro.ui.weather.WeatherActivity;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    //存储地点数据的列表，用于显示在 RecyclerView 中的每个项
    private final List<Place> placeList;
    //用于与适配器交互和处理点击事件
    private final PlaceFragment fragment;
    private Context context;

    public PlaceAdapter(List<Place> placeList,PlaceFragment fragment){
        this.placeList = placeList;
        this.fragment = fragment;
    }

    //用于创建 ViewHolder 并关联对应的布局文件 place_item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new ViewHolder(view);
    }

    //用于将地点数据绑定到 ViewHolder 中的视图控件上，显示在 RecyclerView 中
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.name);
        holder.placeAddress.setText(place.address);

        //最外层布局注册了一个点击事件监听器
        //在点击事件中获取当前点击项的经纬度坐标和地区名称，并把它们传入Intent中，最后调用Fragment的startActivity()方法启动WeatherActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WeatherActivity.class);
            intent.putExtra("location_lng",place.location.lng);
            intent.putExtra("location_lat",place.location.lat);
            intent.putExtra("place_name",place.name);
            intent.putExtra("place_id",place.id);
            intent.putExtra("hasPlace",false);
            SharedPreferenceDao.save(place.name);
            fragment.requireActivity().startActivity(intent);
        });
    }

    //返回地点数据列表的大小，即 RecyclerView 中子项的数量
    @Override
    public int getItemCount() {
        return placeList.size();
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

}
