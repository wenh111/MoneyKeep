package com.org.moneykeep.Activity.AddPayEventView;

import com.org.moneykeep.retrofitBean.PayEventBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddPayEventAPI {
    @POST("/AllPay/AddEvent")
    Call<Integer> InsertPayEvent(@Body PayEventBean payEventBean);

}
