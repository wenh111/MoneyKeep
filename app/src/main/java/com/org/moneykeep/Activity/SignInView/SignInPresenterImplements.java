package com.org.moneykeep.Activity.SignInView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.org.moneykeep.Activity.UserMainActivity;
import com.org.moneykeep.BmobTable.User;
import com.org.moneykeep.R;
import com.org.moneykeep.retrofitBean.UserBean;

public class SignInPresenterImplements implements SignInInterface.IPresenter, SignInInterface.IModelCallBack {

    private SignInInterface.IModel mModel;
    private SignInInterface.IView mView;
    private Context context;

    public SignInPresenterImplements(SignInInterface.IView mView,Context context) {
        mModel = new SignInModelImplements();
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void UserSignIn(String account, String password, String sql) {
        mModel.GetUserSignInMessage(account, password, sql, this);
    }

    @Override
    public void Successful(UserBean user) {

        String userName = "";
        //String objectId = "";
        String userAccount = "";
        String userPassword = "";
        String USER_EMAIL = "user_email";
        String USER_PASSWORD = "user_password";
        String USER_NAME = "user_name";
        String USER_OBJECTID = "user_objectId";
        String USER_ISUSED = "user_isused";


        SharedPreferences userdata = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        SharedPreferences.Editor user_editor = userdata.edit();
        Bundle bundle = new Bundle();

        userName = user.getName();
        //objectId = user.getId();
        userAccount = user.getAccount();
        userPassword = user.getPassword();

        user_editor.putString(USER_NAME, userName);
        //user_editor.putString(USER_OBJECTID, objectId);

        bundle.putString("user_name", userName);
        //bundle.putString("user_objectId", objectId);


        user_editor.putString(USER_EMAIL, userAccount);
        user_editor.putString(USER_PASSWORD, userPassword);

        user_editor.putBoolean(USER_ISUSED, true);
        user_editor.commit();

        bundle.putString("user_email", userAccount);

        mView.Successful("登陆成功",bundle);
    }

    @Override
    public void UnSuccessful(String error) {
        mView.UnSuccessful("登录失败:" + error);
    }
}
