package com.org.moneykeep.Activity.ui.SaveMoney;

import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.retrofitBean.SaveMoneyEventListBean;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveMoneyFragmentModelImplements implements SaveMoneyFragmentInterface.IModel{

    private SaveMoneyFragmentInterface.IPresenter iPresenter;
    private SaveMoneyFragmentAPI api;
    public SaveMoneyFragmentModelImplements(SaveMoneyFragmentInterface.IPresenter iPresenter) {
        this.iPresenter = iPresenter;
        api = RetrofitUntil.getRetrofit().create(SaveMoneyFragmentAPI.class);
    }


    @Override
    public void getSaveMoneyPlanEvent(String user_account, String type) {
        Call<SaveMoneyEventListBean> saveMoneyEventListBeanCall = api.selectAllSaveMoneyPlanEvent(user_account, type);
        saveMoneyEventListBeanCall.enqueue(new Callback<SaveMoneyEventListBean>() {
            @Override
            public void onResponse(Call<SaveMoneyEventListBean> call, Response<SaveMoneyEventListBean> response) {
                if(response.code() == HttpURLConnection.HTTP_OK ){

                    iPresenter.showAllSaveMoneyPlanEventSuccessfulCallBack("查询成功...",response.body());

                }
            }

            @Override
            public void onFailure(Call<SaveMoneyEventListBean> call, Throwable t) {
                iPresenter.showAllSaveMoneyPlanEventUnSuccessfulCallBack("查询失败:" + t.getMessage());
            }
        });
    }


}
