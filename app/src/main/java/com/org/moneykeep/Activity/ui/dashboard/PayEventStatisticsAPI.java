package com.org.moneykeep.Activity.ui.dashboard;

import com.org.moneykeep.retrofitBean.PayEventListBean;
import com.org.moneykeep.retrofitBean.PayOrIncomeEventListBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PayEventStatisticsAPI {
    @GET("/AllPay/PayEventStatistics")
    Call<PayOrIncomeEventListBean> SelectPayEventStatistics(@Query("account") String account, @Query("date") String date,
                                                            @Query("month") String month, @Query("year") String year);
}
