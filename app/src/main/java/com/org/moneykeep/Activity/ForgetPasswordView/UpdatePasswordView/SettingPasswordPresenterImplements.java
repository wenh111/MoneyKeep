package com.org.moneykeep.Activity.ForgetPasswordView.UpdatePasswordView;

import com.org.moneykeep.Activity.ForgetPasswordView.AuthenticationView.ForgetPasswordActivity;
import com.org.moneykeep.R;

public class SettingPasswordPresenterImplements implements SettingPasswordInterface.IPresenter{
    private SettingPasswordInterface.IView iView;
    private SettingPasswordInterface.IModel iModel;

    public SettingPasswordPresenterImplements(SettingPasswordInterface.IView iView) {
        this.iView = iView;
        iModel = new SettingPasswordModelImplements(this);
    }

    @Override
    public void UpdateUserPassword(String input_password, String input_password2, String input_email) {
        if(input_password.equals("") || input_password2.equals("") || input_email.equals("")
                || !input_password.equals(input_password2) || !input_email.equals(ForgetPasswordActivity.email)){
            String message = "";
            int id = -1;
            if(input_password.equals("")){
                message = "请输入新密码...";
                id = R.id.new_password;
            }else if(input_password2.equals("")){
                message = "请再次确认密码...";
                id = R.id.new_password2;
            }else if(input_email.equals("")){
                message = "请输入账号...";
                id = R.id.email;
            }else if(!input_password.equals(input_password2)){
                message = "两次密码不一样...";
            }else if(!input_email.equals(ForgetPasswordActivity.email)){
                message = "账号输入错误...";
            }
            iView.UpdateUserPasswordUnSuccessful(message,id);
        }else{
            iModel.UpdateUserPassword(input_email,input_password,input_password2);
        }
    }

    @Override
    public void UpdateUserPasswordSuccessfulCallBack(String successful) {
        iView.UpdateUserPasswordSuccessful(successful);
    }

    @Override
    public void UpdateUserPasswordUnSuccessfulCallBack(String unsuccessful) {
        iView.UpdateUserPasswordUnSuccessful(unsuccessful,-1);
    }
}
