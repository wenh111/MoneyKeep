package com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView;

import com.org.moneykeep.BmobTable.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthenticationAPI {
    @GET("/user/userForgetPassword")
    Call<User>  SelectUserMessage(@Query("account") String account,@Query("telephone_number") String telephone_number);

}
