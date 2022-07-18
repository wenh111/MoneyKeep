package com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView;

import android.content.Context;

public class AuthenticationPresenterImplements implements AuthenticationInterface.IPresenter,AuthenticationInterface.IModelCallBack{
    private AuthenticationInterface.IModel mModel;
    private AuthenticationInterface.IView mView;
    private Context context;

    public AuthenticationPresenterImplements(AuthenticationInterface.IView mView, Context context) {
        this.mView = mView;
        this.context = context;
        mModel = new AuthenticationModelImplements();
    }

    @Override
    public void Authentication(String email, String telephone_number) {
        if(email.equals("")){
            mView.AuthenticationUnSuccessful("请输入账号...");
        }else if(telephone_number.equals("")){
            mView.AuthenticationUnSuccessful("请输入电话号码...");
        }else{
            mModel.SelectUserMessage(email,telephone_number,this);
        }

    }

    @Override
    public void AuthenticationSuccessful(String successful) {
        mView.AuthenticationSuccessful(successful);
    }

    @Override
    public void AuthenticationUnSuccessful(String unsuccessful) {
        mView.AuthenticationUnSuccessful(unsuccessful);
    }
}
