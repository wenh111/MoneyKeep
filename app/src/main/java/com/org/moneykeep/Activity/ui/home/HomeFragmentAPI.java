package com.org.moneykeep.Activity.ui.home;

import com.org.moneykeep.retrofitBean.PayEventListBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HomeFragmentAPI {

    @GET("/AllPay")
    Call<PayEventListBean> SelectDayPayMessage(@Query("account") String account,
                                               @Query("category") String category,
                                               @Query("date") String date,
                                               @Query("month") String month,
                                               @Query("year") String year);

    @POST("/AllPay/deletePayEvent")
    Call<Integer> DeleteDayPayMessage(@Query("id") int id);

    @GET("AllPay/monthPayEvent")
    Call<PayEventListBean> getPayEvent(@Query("account") String account,
                                       @Query("category") String category,
                                       @Query("month") String month,
                                       @Query("year") String year,
                                       @Query("since") int since,
                                       @Query("perPages") int perPages,
                                       @Query("selectType") int selectType);
}
