package com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView;

public interface AuthenticationInterface {

    interface IModel {
        void SelectUserMessage(String email, String telephone_number,AuthenticationInterface.IModelCallBack callBack);
    }

    interface IPresenter{
        void Authentication(String email, String telephone_number);
    }

    interface IView{
        void AuthenticationSuccessful(String successful);
        void AuthenticationUnSuccessful(String unsuccessful);
    }

    interface IModelCallBack{
        void AuthenticationSuccessful(String successful);
        void AuthenticationUnSuccessful(String successful);
    }
}
