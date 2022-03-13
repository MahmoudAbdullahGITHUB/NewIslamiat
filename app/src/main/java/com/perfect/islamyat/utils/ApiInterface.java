package com.perfect.islamyat.utils;


import com.perfect.islamyat.models.myPrayerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("timings/{timeStamp}")
    public Call<myPrayerModel> getTimings(@Path("timeStamp") String timeStamp,
                                          @Query("latitude") double latitude,
                                          @Query("longitude") double longitude,
                                          @Query("method") int method);


}
