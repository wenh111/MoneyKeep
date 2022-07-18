package com.org.moneykeep.Activity.SignInView;

import androidx.annotation.NonNull;

import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.retrofitBean.UserBean;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInModelImplements implements SignInInterface.IModel{

    @Override
    public void GetUserSignInMessage(String account, String password, String sql, SignInInterface.IModelCallBack callback) {
        /*new BmobQuery<User>().doSQLQuery(sql, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                if(e == null){
                    user = bmobQueryResult.getResults();
                    callback.Successful(user.get(0));
                }else callback.UnSuccessful(e.getMessage());
            }
        },account,password);*/

        Retrofit retrofit = RetrofitUntil.getRetrofit();
        SignInAPI signInApi = retrofit.create(SignInAPI.class);
        Call<UserBean> userBeanCall = signInApi.UserLogin(account, password);
        userBeanCall.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(@NonNull Call<UserBean> call, @NonNull Response<UserBean> response) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    UserBean user = response.body();
                    callback.Successful(user);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserBean> call, @NonNull Throwable t) {
                callback.UnSuccessful("账号或密码错误...");
            }
        });

    }
}
