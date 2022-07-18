package com.org.moneykeep.Activity.PunchCardView;

import android.util.Log;

import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.retrofitBean.UDSaveMoneyEventBean;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PunchCardViewModelImplements implements PunchCardViewInterface.IModel {
    private PunchCardViewInterface.IPresenter iPresenter;
    private PunchCardAPI api;

    public PunchCardViewModelImplements(PunchCardViewInterface.IPresenter iPresenter) {
        this.iPresenter = iPresenter;
        api = RetrofitUntil.getRetrofit().create(PunchCardAPI.class);
    }

    @Override
    public void selectPunchCardEvent() {

    }

    @Override
    public void deletePunchCardEvent(UDSaveMoneyEventBean udSaveMoneyEventBean, int isDelete) {
        Call<Integer> integerCall = api.punchCard(udSaveMoneyEventBean, isDelete);
        integerCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    iPresenter.deletePunchCardEventSuccessfulCallBack("删除成功...");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                iPresenter.deletePunchCardEventUnSuccessfulCallBack("删除失败:" + t.getMessage());
            }
        });
    }

    @Override
    public void updatePunchCardEvent(UDSaveMoneyEventBean udSaveMoneyEventBean, int isDelete) {
        Call<Integer> integerCall = api.punchCard(udSaveMoneyEventBean, isDelete);
        Log.i("udSaveMoneyEventBean", "remainingTimes == "+udSaveMoneyEventBean.getRemainingTimes());
        integerCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    iPresenter.PunchCardSuccessfulCallBack("打卡成功...");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                iPresenter.PunchCardUnSuccessfulCallBack("打卡失败..." + t.getMessage());
            }
        });
    }
}
