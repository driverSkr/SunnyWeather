package com.example.SunnyWeather.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.SunnyWeather.logic.dao.PlaceDao;
import com.example.SunnyWeather.logic.model.DailyResponse;
import com.example.SunnyWeather.logic.model.Place;
import com.example.SunnyWeather.logic.model.PlaceResponse;
import com.example.SunnyWeather.logic.model.RealtimeResponse;
import com.example.SunnyWeather.logic.model.Weather;
import com.example.SunnyWeather.logic.network.SunnyWeatherNetwork;
import com.example.SunnyWeather.logic.utils.Result;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//单例类,作为仓库层的统一封装入口(把需要开启线程的操作放在了这里)
/*主要工作就是判断调用方请求的数据应该是从本地数据源中获取还是从网络数据源中获取，并将获得的数据返回给调用方
。因此，仓库层有点像是一个数据获取与缓存的中间层，在本地没有缓存数据的情况下就去网络层获取，如果本地已经有缓存了，就直接将缓存数据返回*/
/*搜索城市数据的请求并没有太多缓存的必要，每次都发起网络请求去获取最新的数据即可，因此这里就不进行本地缓存的实现了*/
public class Repository {

    //用于保存城市数据到本地数据源
    public static void savePlace(Place place){
        PlaceDao.savePlace(place);
    }
    //用于从本地数据源获取已保存的城市数据
    public static Place getSavedPlace() {
        return PlaceDao.getSavedPlace();
    }
    //用于检查本地是否已保存城市数据
    public static boolean isPlaceSaved() {
        return PlaceDao.isPlaceSaved();
    }

    //创建了一个名为 executor 的线程池对象
    //使用 newSingleThreadExecutor() 方法创建的是一个单线程的线程池，用于在后台单独的线程中执行任务
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    //用于搜索相关的城市数据
    /*段代码通过使用线程池将城市数据的搜索任务在后台单独的线程中执行
    * 这样做的好处是，将耗时的网络请求等操作放在后台线程中，避免阻塞主线程，同时使用 LiveData 实现数据的观察和通知，方便在UI层进行响应*/
    public LiveData<List<Place>> searchPlaces(String query){
        //创建了一个可变的 LiveData 对象，用于存储搜索结果并在后续进行数据更新通知
        MutableLiveData<List<Place>> liveData = new MutableLiveData<>();

        //使用 executor 线程池对象执行一个后台任务。
        // 在这里，创建了一个匿名的 Runnable 对象，并在其中实现了后台任务的逻辑
        executor.execute(() -> {
            try {
                //在后台任务中，调用 SunnyWeatherNetwork.searchPlaces(query) 方法进行城市数据的网络搜索请求，查询相关的城市数据
                PlaceResponse placeResponse = (PlaceResponse) SunnyWeatherNetwork.searchPlaces(query);
                if (placeResponse.getStatus().equals("ok")){
                    //从 placeResponse 中获取城市数据列表
                    List<Place> places = placeResponse.getPlaces();
                    //将获取到的城市数据列表 places 通过 liveData 的 postValue 方法进行更新，通知观察者（通常是UI层）数据已更新
                    liveData.postValue(places);
                }else {
                    //将空值（null）通过 liveData 的 postValue 方法进行更新，同样通知观察者数据已更新，但此时数据为空
                    liveData.postValue(null);
                }
            }catch (Throwable e){
                //在发生异常的情况下，也将空值（null）通过 liveData 的 postValue 方法进行更新，通知观察者数据已更新，但此时数据为空
                liveData.postValue(null);
            }finally {
                // 关闭 executor，释放线程资源
                executor.shutdown();
            }
        });
        //返回存储搜索结果的 LiveData 对象 liveData，允许调用方观察搜索结果并进行相应处理
        return liveData;
    }

    //接受经度 lng 和纬度 lat 作为参数，用于获取对应地点的实时天气数据和每日天气数据
    /*这段代码利用了多线程技术，通过同时执行两个异步任务来获取实时天气数据和每日天气数据
    * 然后，根据两个请求的结果，构造一个 Weather 对象，并用 Result 对象对结果进行包装，最后以 LiveData<Result<Weather>> 的形式返回给调用方，供其观察和处理刷新天气数据的结果。*/
    public static LiveData<Result<Weather>> refreshWeather(String lng,String lat){
        //使用 fire 方法来执行一个异步任务。fire 方法接受一个 Callable<Result<T>> 类型的函数对象作为参数，在这里我们使用 lambda 表达式传入一个函数体
        return fire(() -> {
            try {
                //创建了一个拥有两个线程的线程池，用于同时执行两个任务，分别获取实时天气数据和每日天气数据
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                //使用线程池 executorService 提交了一个任务，用于获取实时天气数据。在这里我们使用了 lambda 表达式来定义任务的具体操作
                Future<RealtimeResponse> realtimeFuture = executorService.submit(() -> {
                    try {
                        return SunnyWeatherNetwork.getRealtimeWeather(lng,lat);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                //同样，使用线程池 executorService 提交了另一个任务，用于获取每日天气数据
                Future<DailyResponse> dailyFuture  = executorService.submit(() -> {
                    try {
                        return SunnyWeatherNetwork.getDailyWeather(lng,lat);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                //通过 realtimeFuture.get() 方法阻塞等待获取实时天气数据任务的结果
                RealtimeResponse realtimeResponse = realtimeFuture.get();
                //通过 dailyFuture.get() 方法阻塞等待获取每日天气数据任务的结果
                DailyResponse dailyResponse = dailyFuture.get();

                //关闭线程池，释放资源
                executorService.shutdown();

                //检查实时天气数据和每日天气数据的请求是否都成功
                if ("ok".equals(realtimeResponse.getStatus()) && "ok".equals(dailyResponse.getStatus())){
                    //如果数据请求成功，通过 realtimeResponse 和 dailyResponse 分别获取实时天气数据和每日天气数据，并使用这些数据创建一个 Weather 对象
                    Weather weather = new Weather(realtimeResponse.getResult().getRealtime(),dailyResponse.getResult().getDaily());
                    //返回一个包装了成功结果的 Result 对象，其中的数据是上一步创建的 Weather 对象
                    return Result.success(weather);
                }else {
                    return Result.failure(new RuntimeException(
                            "realtime response status is " + realtimeResponse.getStatus() +
                            ", daily response status is " + dailyResponse.getStatus()));
                }
            }catch (Exception e){
                return Result.failure(e);
            }
        });
    }

    //用于在后台线程执行网络请求的逻辑，并返回一个 LiveData 对象用于观察请求结果,其中的数据类型是 Result<T>
    //Callable 是 Java 中表示可以在单独的线程中运行并返回结果的接口
    /*这段代码通过使用线程池来执行传入的网络请求任务，并通过 LiveData 将任务的结果封装起来，使得请求结果可以被观察和处理
    * 注意这里的 fire 方法并没有直接执行网络请求的操作，而是通过传入的 Callable 对象，间接地在后台线程中执行请求并返回结果*/
    private static <T> LiveData<Result<T>> fire(Callable<Result<T>> block){
        //创建了一个可变的 LiveData 对象，用于存储请求结果并在后续进行数据更新通知
        MutableLiveData<Result<T>> liveData = new MutableLiveData<>();
        //创建了一个 ExecutorService 对象，它是一个线程池，用于在后台单独的线程中执行任务
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //submit() 方法将一个任务（Runnable）提交给线程池执行
        executorService.submit(() ->{
            try {
                //调用 block.call() 执行传入的 Callable 对象，即执行实际的网络请求和数据处理，得到结果 Result<T>
                Result<T> result = block.call();
                //将得到的结果 result 通过 postValue 方法设置给 LiveData 对象 liveData，这样观察者（通常是UI层）会收到数据更新的通知
                liveData.postValue(result);
            }catch (Exception e){
                //如果发生异常，将异常信息封装成一个失败的 Result 对象，并赋值给 result
                Result<T> result = Result.failure(e);
                liveData.postValue(result);
            }
        });

        //关闭线程池，确保资源被释放
        executorService.shutdown();
        return liveData;
    }
}
