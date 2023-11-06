package com.driverskr.sunnyweather_kotlin.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.driverskr.sunnyweather_kotlin.logic.Repository
import com.driverskr.sunnyweather_kotlin.logic.model.Place

/**
 * @Author: driverSkr
 * @Time: 2023/11/6 15:39
 * @Description: 地址ViewModel$
 */
class PlaceViewModel: ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}