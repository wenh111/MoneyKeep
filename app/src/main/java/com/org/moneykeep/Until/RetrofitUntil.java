package com.org.moneykeep.Until;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUntil {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://114.132.54.246:9090")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Retrofit getRetrofit(){
        return retrofit;
    }
}
