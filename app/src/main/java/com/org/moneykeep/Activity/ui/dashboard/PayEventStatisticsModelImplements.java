package com.org.moneykeep.Activity.ui.dashboard;

import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.retrofitBean.PayOrIncomeEventListBean;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayEventStatisticsModelImplements implements PayEventStatisticsInterface.IModel{
    private PayEventStatisticsInterface.IPresenter iPresenter;
    private PayEventStatisticsAPI api;
    public PayEventStatisticsModelImplements(PayEventStatisticsInterface.IPresenter iPresenter) {
        this.iPresenter = iPresenter;
        api = RetrofitUntil.getRetrofit().create(PayEventStatisticsAPI.class);
    }

    @Override
    public void SelectDayPayStatistics(String account, String date) {
        Call<PayOrIncomeEventListBean> payEventListBeanCall = api.SelectPayEventStatistics(account, date, "", "");
        payEventListBeanCall.enqueue(new Callback<PayOrIncomeEventListBean>() {
            @Override
            public void onResponse(Call<PayOrIncomeEventListBean> call, Response<PayOrIncomeEventListBean> response) {

                if (response.code() == HttpURLConnection.HTTP_OK){
                    iPresenter.GetAllStatisticsPieSuccessfulCallBack(response.body());
                    iPresenter.GetMonthPayColumnsCallBack(response.body());
                }
            }

            @Override
            public void onFailure(Call<PayOrIncomeEventListBean> call, Throwable t) {
                iPresenter.GetAllStatisticsPieUnSuccessfulCallBack("查询失败:" + t.getMessage());
            }
        });
    }

    @Override
    public void SelectMonthPayStatistics(String account, String month, String year) {
        Call<PayOrIncomeEventListBean> payOrIncomeEventListBeanCall = api.SelectPayEventStatistics(account, "", month, year);
        payOrIncomeEventListBeanCall.enqueue(new Callback<PayOrIncomeEventListBean>() {
            @Override
            public void onResponse(Call<PayOrIncomeEventListBean> call, Response<PayOrIncomeEventListBean> response) {
                iPresenter.GetAllStatisticsPieSuccessfulCallBack(response.body());
                iPresenter.GetMonthPayColumnsCallBack(response.body());
            }

            @Override
            public void onFailure(Call<PayOrIncomeEventListBean> call, Throwable t) {
                iPresenter.GetAllStatisticsPieUnSuccessfulCallBack("查询失败:" + t.getMessage());
            }
        });
    }

    @Override
    public void SelectYearPayStatistics(String user_account, String selectYear) {
        Call<PayOrIncomeEventListBean> payOrIncomeEventListBeanCall = api.SelectPayEventStatistics(user_account, "", "", selectYear);
        payOrIncomeEventListBeanCall.enqueue(new Callback<PayOrIncomeEventListBean>() {
            @Override
            public void onResponse(Call<PayOrIncomeEventListBean> call, Response<PayOrIncomeEventListBean> response) {
                iPresenter.GetAllStatisticsPieSuccessfulCallBack(response.body());
                iPresenter.GetYearPayColumnsCallBack(response.body());
            }

            @Override
            public void onFailure(Call<PayOrIncomeEventListBean> call, Throwable t) {
                iPresenter.GetAllStatisticsPieUnSuccessfulCallBack("查询失败:" + t.getMessage());
            }
        });
    }
}
