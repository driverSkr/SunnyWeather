package com.driverskr.sunnyweather_kotlin.logic

import androidx.lifecycle.liveData
import com.driverskr.sunnyweather_kotlin.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

/**
 * @Author: driverSkr
 * @Time: 2023/11/6 15:29
 * @Description: 仓库层：数据获取与缓存的中间层，在本地没有缓存数据的情况下就去网络层获取，如果本地已经有缓存了，就直接将缓存数据返回$
 */
object Repository {

    /**
     * liveData()函数是lifecycle-livedata-ktx 库提供的一个非常强大且好用的功能，它可以自动构建并返回一个LiveData 对象，
     * 然后在它的代码块中提供一个挂起函数的上下文，这样我们就可以在liveData()函数的代码块中调用任意的挂起函数了
     */
    /**Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了。众所周知，Android 是
    不允许在主线程中进行网络请求的，诸如读写数据库之类的本地数据操作也是不建议在主线程
    中进行的，因此非常有必要在仓库层进行一次线程转换。**/
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}