package com.org.moneykeep.Activity.PunchCardView;

import com.org.moneykeep.retrofitBean.UDSaveMoneyEventBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PunchCardAPI {
    @POST("/SaveMoney/PunchOrDelete")
    Call<Integer> punchCard(@Body UDSaveMoneyEventBean udSaveMoneyEventBean, @Query("isDelete") int isDelete);
}
