package com.org.moneykeep.Activity.DetailsView;

import com.org.moneykeep.retrofitBean.PayEventBean;
import com.org.moneykeep.retrofitBean.PayEventListBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DetailsAPI {
    @GET("/AllPay/Details")
    Call<PayEventBean> SelectDetailsPayMessage(@Query("id") int id);

    @POST("/AllPay/UpdatePayEvent")
    Call<Integer> UpdatePayMessage(@Body PayEventBean payEventBean);

    @POST("/AllPay/deletePayEvent")
    Call<Integer> DeletePayMessage(@Query("id") int id);
}
