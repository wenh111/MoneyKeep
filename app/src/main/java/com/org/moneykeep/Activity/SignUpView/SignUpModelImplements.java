package com.org.moneykeep.Activity.SignUpView;

import android.util.Log;

import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.retrofitBean.UserBean;
import com.org.moneykeep.retrofitBean.UserMessageBeen;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpModelImplements implements SignUpInterface.IModel {

    @Override
    public void SendUserMessage(UserBean userBean, SignUpInterface.IModelCallBack callBack) {
        Retrofit retrofit = RetrofitUntil.getRetrofit();
        SignUpAPI api = retrofit.create(SignUpAPI.class);
        Call<Integer> integerCall = api.InsertUserMessage(userBean);
        Log.i("insertMessage","integerCall =========>" + userBean);
        integerCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    callBack.SendUserSuccessfulCallBack("注册成功");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                callBack.SendUserUnSuccessfulCallBack("上传信息失败" + t.getMessage());
            }
        });
    }

    //private Boolean isOk = false;
    @Override
    public void SelectMessage(UserBean userBean, SignUpInterface.IModelCallBack callBack) {
        Retrofit retrofit = RetrofitUntil.getRetrofit();
        SignUpAPI api = retrofit.create(SignUpAPI.class);
        Call<UserMessageBeen> integerCall = api.SelectUserMessage(userBean.getAccount(), userBean.getTelephone_number());
        integerCall.enqueue(new Callback<UserMessageBeen>() {
            @Override
            public void onResponse(Call<UserMessageBeen> call, Response<UserMessageBeen> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    UserMessageBeen user = response.body();
                    //Log.i("SelectMessage","isOk =========>" + isOk);
                    if(user.getCount() == 0){
                        callBack.SelectUserSuccessfulCallBack(userBean);
                    }else{
                        if(user.getUserMessage().get(0).getAccount().equals(userBean.getAccount()) && user.getUserMessage().get(0).getTelephone_number().equals(userBean.getTelephone_number())
                                || user.getUserMessage().get(0).getAccount().equals(userBean.getAccount())){
                            callBack.SelectUserUnSuccessfulCallBack("邮箱已存在...");
                        } else if(user.getUserMessage().get(0).getTelephone_number().equals(userBean.getTelephone_number())){
                            callBack.SelectUserUnSuccessfulCallBack("电话已存在...");
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<UserMessageBeen> call, Throwable t) {
                callBack.SelectUserUnSuccessfulCallBack("查询错误:" + t.getMessage());
                Log.i("SelectMessage","Throwable =========>" + t.getMessage());
                //callBack.SelectUserUnSuccessfulCallBack("查询错误:" + t.getMessage());
            }
        });
    }


}
