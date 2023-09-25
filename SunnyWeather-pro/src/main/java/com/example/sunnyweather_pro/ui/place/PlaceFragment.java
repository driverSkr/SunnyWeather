package com.example.sunnyweather_pro.ui.place;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather_pro.MyApplication;
import com.example.sunnyweather_pro.R;
import com.example.sunnyweather_pro.logic.model.Place;

import java.util.List;

/*搜索地区：为想查询的地方的天气的经纬度做准备*/
@SuppressLint("NotifyDataSetChanged")
public class PlaceFragment extends Fragment {

    private PlaceViewModel viewModel;
    private final Activity activity = getActivity();
    private RecyclerView recyclerView;
    private PlaceAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);

        viewModel = new ViewModelProvider(this).get(PlaceViewModel.class);

        recyclerView = view.findViewById(R.id.rv_place_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlaceAdapter(viewModel.getPlaceList(),this);
        recyclerView.setAdapter(adapter);

        EditText searchPlaceEdit = view.findViewById(R.id.et_keyword);
        ImageView bgImageView = view.findViewById(R.id.bgImageView);
        //监听搜索栏的变化，一点变化，就将变化的值传给searchLiveData
        searchPlaceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = s.toString();
                if (!content.isEmpty()){
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

        //查询按钮
        view.findViewById(R.id.btn_query).setOnClickListener(v -> {
            String text = searchPlaceEdit.getText().toString();
            viewModel.searchPlaces(text);
        });

        //观察着searchLiveData地址数据的变化，每次变化都会查询一次地址数据
        viewModel.getPlacesLiveData().observe(getViewLifecycleOwner(), placeList -> {
            if (placeList != null){
                recyclerView.setVisibility(View.VISIBLE);
                bgImageView.setVisibility(View.GONE);
                viewModel.getPlaceList().clear();
                viewModel.getPlaceList().addAll(placeList);
                adapter.notifyDataSetChanged();
            }else {
                Toast.makeText(MyApplication.context, "未能查询到任何地点", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}