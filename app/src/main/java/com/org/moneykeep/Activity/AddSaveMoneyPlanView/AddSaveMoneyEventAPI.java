package com.org.moneykeep.Activity.AddSaveMoneyPlanView;

import com.org.moneykeep.retrofitBean.SaveMoneyEventBean;
import com.org.moneykeep.retrofitBean.SaveMoneyEventListBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddSaveMoneyEventAPI {

    @POST("/SaveMoney/AddSaveMoneyEvent")
    Call<Integer> insertSaveMonetEvent(@Body SaveMoneyEventBean saveMoneyEventBean);

}
