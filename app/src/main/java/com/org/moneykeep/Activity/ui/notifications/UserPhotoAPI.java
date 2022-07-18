package com.org.moneykeep.Activity.ui.notifications;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserPhotoAPI {
    @Multipart
    @POST("/photo/uploadPhoto")
    Call<String> UploadPhoto(@Part MultipartBody.Part part, @Query("account") String account);
}
