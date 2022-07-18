package com.org.moneykeep.Activity.ui.SaveMoney;

import com.org.moneykeep.retrofitBean.SaveMoneyEventListBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SaveMoneyFragmentAPI {
    @GET("/SaveMoney/SelectSaveMoneyPlanEvent")
    Call<SaveMoneyEventListBean> selectAllSaveMoneyPlanEvent(@Query("account") String account, @Query("type") String type);
}
