package com.org.moneykeep.Activity.AddPayEventView;

import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.retrofitBean.PayEventBean;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPayEventModelImplements implements AddPayEventInterface.IModel{
    private AddPayEventInterface.IPresenter iPresenter;

    public AddPayEventModelImplements(AddPayEventInterface.IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    @Override
    public void InsertPayEvent(PayEventBean payEvent) {
        Retrofit retrofit = RetrofitUntil.getRetrofit();
        AddPayEventAPI api = retrofit.create(AddPayEventAPI.class);
        Call<Integer> integerCall = api.InsertPayEvent(payEvent);
        integerCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    iPresenter.AddPayEventSuccessfulCallBack("创建成功...");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                iPresenter.AddPayEventUnSuccessfulCallBack("创建失败:" + t.getMessage());
            }
        });
    }
}
