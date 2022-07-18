package com.org.moneykeep.Activity.ForgetPasswordView.UpdatePasswordView;

import com.org.moneykeep.Until.RetrofitUntil;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingPasswordModelImplements implements SettingPasswordInterface.IModel{
    private SettingPasswordInterface.IPresenter iPresenter;

    public SettingPasswordModelImplements(SettingPasswordInterface.IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    @Override
    public void UpdateUserPassword(String input_email, String input_password, String input_password2) {
        Retrofit retrofit = RetrofitUntil.getRetrofit();
        SettingPasswordAPI API = retrofit.create(SettingPasswordAPI.class);
        Call<Integer> integerCall = API.UpdatePassword(input_email, input_password);
        integerCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    iPresenter.UpdateUserPasswordSuccessfulCallBack("密码重置成功...");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                iPresenter.UpdateUserPasswordUnSuccessfulCallBack("密码重置失败:" + t.getMessage());
            }
        });

    }
}
