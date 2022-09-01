package com.org.moneykeep.Activity.ui.notifications;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface UserPhotoAPI {
    @Multipart
    @POST("/photo/uploadPhoto")
    Call<UploadSuccessfulMessage> UploadPhoto(@Part MultipartBody.Part part, @Query("account") String account);

    @Streaming
    @GET("/photo/getPhoto")
    Call<ResponseBody> downPhoto(@Query("account") String account);

    @GET("/photo/getUserPhotoUrl")
    Call<UploadSuccessfulMessage> getPhotoUrl(@Query("account") String account);
}
