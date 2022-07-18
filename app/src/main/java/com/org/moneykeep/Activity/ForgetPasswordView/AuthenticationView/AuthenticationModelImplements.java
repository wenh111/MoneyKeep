package com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView;

import com.org.moneykeep.BmobTable.User;
import com.org.moneykeep.Until.RetrofitUntil;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthenticationModelImplements implements AuthenticationInterface.IModel{
    @Override
    public void SelectUserMessage(String email, String telephone_number,AuthenticationInterface.IModelCallBack callBack) {
        Retrofit retrofit = RetrofitUntil.getRetrofit();
        AuthenticationAPI api = retrofit.create(AuthenticationAPI.class);
        Call<User> userCall = api.SelectUserMessage(email, telephone_number);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    callBack.AuthenticationSuccessful("验证成功...");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callBack.AuthenticationUnSuccessful("账号或所绑定的电话号码错误...");
            }
        });
    }
}
