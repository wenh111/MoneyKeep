package com.org.moneykeep.Activity.ForgetPasswordView.UpdatePasswordView;

public interface SettingPasswordInterface {

    interface IModel {
        void UpdateUserPassword(String input_email, String input_password, String input_password2);
    }

    interface IPresenter{
        void UpdateUserPassword(String input_password, String input_password2, String input_email);
        void UpdateUserPasswordSuccessfulCallBack(String successful);
        void UpdateUserPasswordUnSuccessfulCallBack(String unsuccessful);
    }

    interface IView{
        void UpdateUserPasswordSuccessful(String successful);
        void UpdateUserPasswordUnSuccessful(String message, int id);
    }


}
