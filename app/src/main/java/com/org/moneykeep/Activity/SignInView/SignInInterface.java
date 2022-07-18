package com.org.moneykeep.Activity.SignInView;

import android.os.Bundle;

import com.org.moneykeep.retrofitBean.UserBean;

public interface SignInInterface {
    interface IModel {
        void GetUserSignInMessage(String account,String password,String sql,IModelCallBack callback);

    }

    interface IPresenter{

        void UserSignIn(String account,String password,String sql);

    }

    interface IView{
        void Successful(String successful, Bundle bundle);

        void UnSuccessful(String error);
    }
    interface IModelCallBack{
        void Successful(UserBean user);

        void UnSuccessful(String error);
    }
}
