package com.org.moneykeep.Activity.AddSaveMoneyPlanView;

import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.retrofitBean.SaveMoneyEventBean;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddSaveMoneyModelImplements implements AddSaveMoneyInterface.IModel{
    private AddSaveMoneyInterface.IPresenter iPresenter;
    private AddSaveMoneyEventAPI  api;

    public AddSaveMoneyModelImplements(AddSaveMoneyInterface.IPresenter iPresenter) {
        this.iPresenter = iPresenter;
        api = RetrofitUntil.getRetrofit().create(AddSaveMoneyEventAPI.class);
    }

    @Override
    public void insertSaveMoneyEvent(SaveMoneyEventBean saveMoneyEventBean) {
        Call<Integer> integerCall = api.insertSaveMonetEvent(saveMoneyEventBean);
        integerCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    iPresenter.addSaveMoneySuccessfulCallBack("添加成功...");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                iPresenter.addSaveMoneyUnSuccessfulCallBack("添加失败:" + t.getMessage());
            }
        });
    }

}
