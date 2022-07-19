package com.org.moneykeep.Activity.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView.ForgetPasswordActivity;
import com.org.moneykeep.Activity.SignInView.SignInActivity;
import com.org.moneykeep.R;
import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.databinding.FragmentNotificationsBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import cn.bmob.v3.datatype.BmobFile;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private Button but_log_out, but_update_password;
    public String USER_EMAIL = "user_email";
    public String USER_PASSWORD = "user_password";
    public String USER_ISUSED = "user_isused";
    private String USER_OBJECTID = "user_objectId";
    private TextView user_name, user_account_input;
    private String bundle_user_name, bundle_user_email, Bundle_user_objectId, uri_path;
    private BmobFile Bundle_user_icon;
    private ImageView user_icon;
    private String mpath = null;
    private Uri uri;
    private UserPhotoAPI api;


    private ActivityResultLauncher<Intent> intentActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    Intent resultData = result.getData();
                    uri = resultData.getData();
                    SharedPreferences uridata = null;
                    if (uridata == null) {
                        uridata = getActivity().getSharedPreferences("uri", Context.MODE_PRIVATE);
                    }
                    try {
                        mpath = getImagePath(uri, null);
                        SharedPreferences.Editor uri_editor = uridata.edit();

                        Log.i("mpath ===>", mpath);
                        File file = new File(mpath);
                        RequestBody body = RequestBody.create(file, MediaType.parse("image/jpeg"));
                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);

                        Call<UploadSuccessfulMessage> stringCall = api.UploadPhoto(part, bundle_user_email);
                        stringCall.enqueue(new Callback<UploadSuccessfulMessage>() {
                            @Override
                            public void onResponse(Call<UploadSuccessfulMessage> call, Response<UploadSuccessfulMessage> response) {
                                if (response.code() == HttpURLConnection.HTTP_OK) {
                                    Log.i("uploadSuccessful", "============>" + response.body().getUrl());
                                    uri_editor.putString("uripath","");
                                    uri_editor.putBoolean("needDownload",true);
                                    uri_editor.commit();
                                }
                            }

                            @Override
                            public void onFailure(Call<UploadSuccessfulMessage> call, Throwable t) {
                                Log.i("uploadUnSuccessful", "============>" + t.getMessage());
                            }
                        });

                        ContentResolver cr = getActivity().getContentResolver();
                        /* 将Bitmap设定到ImageView */
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        user_icon.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        Toast.makeText(getActivity(), "错误" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "错误", Toast.LENGTH_LONG).show();
                }

            });
    private boolean needDownload = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        api = RetrofitUntil.getRetrofit().create(UserPhotoAPI.class);
        View root = binding.getRoot();
        Bundle receive = getActivity().getIntent().getExtras();
        bundle_user_name = receive.getString("user_name");
        bundle_user_email = receive.getString("user_email");
        //Bundle_user_objectId = receive.getString("user_objectId");

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FindId();
        SetListen();

        user_name.setText(bundle_user_name);
        user_account_input.setText(bundle_user_email);

        SharedPreferences uridata = null;
        if (uridata == null) {
            uridata = getActivity().getSharedPreferences("uri", Context.MODE_PRIVATE);
        }

        //uri_path = uridata.getString("uripath", "");
        needDownload = uridata.getBoolean("needDownload",true);
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (requireActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } /*else {
                    //这里就是权限打开之后自己要操作的逻辑
                    Uri filepath = Uri.fromFile(new File(uri_path));
                    *//* 将Bitmap设定到ImageView *//*
                    if (!uri_path.equals("")) {
                        user_icon.setImageURI(filepath);
                    }
                }*/
            }
        }
        if(needDownload){
            setUserPhoto();
        }else{
            uri_path = uridata.getString("uripath", "");
            Uri filepath = Uri.fromFile(new File(uri_path));
            if (!uri_path.equals("")) {
                user_icon.setImageURI(filepath);
            }
        }




    }
    private String TAG = "PhotoMessage";

    private void setUserPhoto() {
        Call<ResponseBody> responseBodyCall = api.downPhoto(bundle_user_email);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    String fileName = "未命名.png";
                    Headers headers = response.headers();
                    String fileNameHeader = headers.get("Content-Disposition");
                    if(fileNameHeader != null){
                        fileName = fileNameHeader.replace("attachment;filename=","");
                        Log.i(TAG, fileName);
                    }
                    /*for (int i = 0; i < headers.size(); i++) {
                        String key = headers.name(i);
                        String value = headers.value(i);
                        Log.i(TAG, key + "=================>" + value);
                    }*/
                    writString2Disk(response,fileName);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void writString2Disk(Response<ResponseBody> response, String fileName) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                InputStream inputStream = response.body().byteStream();
                //File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File externalFilesDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                Log.i(TAG, "externalFilesDir ================>" + externalFilesDir.toURI());
                String uri = externalFilesDir +"/"+ fileName;
                File outFile = new File(externalFilesDir,fileName);
                Log.i(TAG, "externalFilesDir ================>" +uri);

                SharedPreferences uridata = null;
                if (uridata == null) {
                    uridata = getActivity().getSharedPreferences("uri", Context.MODE_PRIVATE);
                }
                SharedPreferences.Editor uri_editor = uridata.edit();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(outFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len = inputStream.read(buffer)) != -1){
                        fileOutputStream.write(buffer,0,len);
                    }
                    //Uri filepath = Uri.fromFile(outFile);
                    Uri filepath = Uri.fromFile(outFile);
                    user_icon.post(() -> user_icon.setImageURI(filepath));

                    uri_editor.putString("uripath", uri);
                    uri_editor.putBoolean("needDownload",false);
                    uri_editor.commit();

                    Log.i(TAG, "externalFilesDir ================>" + filepath);
                    Log.i(TAG, "successful");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    private void SetListen() {
        Onclick onclick = new Onclick();
        but_log_out.setOnClickListener(onclick);
        user_icon.setOnClickListener(onclick);
        but_update_password.setOnClickListener(onclick);
    }

    private class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.but_log_out:
                    Logout();
                    break;
                case R.id.user_icon:
                    ChangeIcon();
                    break;
                case R.id.but_update_password:
                    Intent intent = new Intent(getContext(), ForgetPasswordActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void ChangeIcon() {
        Intent intent = new Intent();
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);

        intentActivityResultLauncher.launch(intent);
    }

    private void Logout() {
        SharedPreferences controllerdata = null;
        if (controllerdata == null) {
            controllerdata = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        }
        SharedPreferences.Editor controller_editor = controllerdata.edit();
        controller_editor.putString(USER_EMAIL, "");
        controller_editor.putString(USER_PASSWORD, "");
        controller_editor.putBoolean(USER_ISUSED, false);
        controller_editor.commit();
        SharedPreferences uridata = null;
        if (uridata == null) {
            uridata = getActivity().getSharedPreferences("uri", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor uri_editor = uridata.edit();
        uri_editor.putString("uripath", "");
        uri_editor.putBoolean("needDownload",true);
        uri_editor.commit();
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void FindId() {
        but_update_password = getView().findViewById(R.id.but_update_password);
        user_icon = getView().findViewById(R.id.user_icon);
        but_log_out = getView().findViewById(R.id.but_log_out);
        user_name = getView().findViewById(R.id.user_name);
        user_account_input = getView().findViewById(R.id.user_account_input);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}