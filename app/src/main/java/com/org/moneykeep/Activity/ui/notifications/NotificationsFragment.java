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
import com.org.moneykeep.BmobTable.User;
import com.org.moneykeep.R;
import com.org.moneykeep.Until.RetrofitUntil;
import com.org.moneykeep.databinding.FragmentNotificationsBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;


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
                        uri_editor.putString("uripath", mpath);
                        uri_editor.commit();
                        Log.i("mpath ===>", mpath);
                        File file = new File(mpath);
                        RequestBody body = RequestBody.create(file, MediaType.parse("image/jpeg"));
                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
                        UserPhotoAPI api = RetrofitUntil.getRetrofit().create(UserPhotoAPI.class);
                        Call<UploadSuccessfulMessage> stringCall = api.UploadPhoto(part, bundle_user_email);
                        stringCall.enqueue(new Callback<UploadSuccessfulMessage>() {
                            @Override
                            public void onResponse(Call<UploadSuccessfulMessage> call, Response<UploadSuccessfulMessage> response) {
                                if (response.code() == HttpURLConnection.HTTP_OK) {
                                    Log.i("uploadSuccessful", "============>" + response.body().getUrl());
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle receive = getActivity().getIntent().getExtras();
        bundle_user_name = receive.getString("user_name");
        bundle_user_email = receive.getString("user_email");
        //Bundle_user_objectId = receive.getString("user_objectId");
        SharedPreferences uridata = null;
        if (uridata == null) {
            uridata = getActivity().getSharedPreferences("uri", Context.MODE_PRIVATE);
        }
        uri_path = uridata.getString("uripath", "");

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FindId();
        SetListen();

        user_name.setText(bundle_user_name);
        user_account_input.setText(bundle_user_email);

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (requireActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    //这里就是权限打开之后自己要操作的逻辑
                    Uri filepath = Uri.fromFile(new File(uri_path));
                    /* 将Bitmap设定到ImageView */
                    if (!uri_path.equals("")) {
                        user_icon.setImageURI(filepath);
                    }
                }
            }
        }


       /* BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", Bundle_user_objectId);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    User user = list.get(0);
                    BmobFile icon = user.getIcon();
                    if (icon != null) {
                        icon.download(new DownloadFileListener() {
                            @Override
                            public void done(String s, BmobException e) {
                                user_icon.setImageBitmap(BitmapFactory.decodeFile(s));
                            }

                            @Override
                            public void onProgress(Integer integer, long l) {

                            }
                        });
                    }

                } else {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_LONG).show();
                }
            }
        });*/
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