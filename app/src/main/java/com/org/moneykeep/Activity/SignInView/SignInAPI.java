package com.org.moneykeep.Activity.SignInView;

import com.org.moneykeep.retrofitBean.UserBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SignInAPI {

    @GET("/user/userLogin")
    Call<UserBean> UserLogin(@Query("account") String account,@Query("password") String password);
}
