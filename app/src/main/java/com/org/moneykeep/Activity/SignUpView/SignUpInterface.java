package com.org.moneykeep.Activity.SignUpView;

import com.org.moneykeep.retrofitBean.UserBean;

public interface SignUpInterface {

    interface IModel {
        void SendUserMessage(UserBean userBean, SignUpInterface.IModelCallBack callBack);
        void SelectMessage(UserBean userBean, IModelCallBack callBack);
    }

    interface IPresenter{

        void UserSignUp(String user_name, String user_account, String user_password, String user_telephone_number);

    }

    interface IView{
        void UserSignUpSuccessful(String successfulMessage);

        void UserSignUpUnSuccessful(String unsuccessfulMessage, int id);
    }

    interface IModelCallBack{

        void SendUserSuccessfulCallBack(String successful);
        void SendUserUnSuccessfulCallBack(String s);

        void SelectUserSuccessfulCallBack(UserBean userBean);
        void SelectUserUnSuccessfulCallBack(String s);
    }
}
