package com.org.moneykeep.Activity.ForgetPasswordView.UpdatePasswordView;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SettingPasswordAPI {
    @POST("/user/userForgetPassword/updatePassWord")
    Call<Integer> UpdatePassword(@Query("account") String account,@Query("password") String password);
}
