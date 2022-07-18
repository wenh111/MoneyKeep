package com.org.moneykeep.Activity.SignUpView;

import com.org.moneykeep.retrofitBean.UserBean;
import com.org.moneykeep.retrofitBean.UserMessageBeen;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SignUpAPI {

    @GET("/user/userSignUp")
    Call<UserMessageBeen> SelectUserMessage(@Query("account") String account, @Query("telephone_number") String user_telephone_number);

    @POST("/user/userMessageInsert")
    Call<Integer> InsertUserMessage(@Body UserBean userBean);
}
