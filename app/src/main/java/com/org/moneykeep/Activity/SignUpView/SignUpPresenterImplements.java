package com.org.moneykeep.Activity.SignUpView;

import android.content.Context;
import android.util.Log;

import com.org.moneykeep.Excention.Nullexcetion;
import com.org.moneykeep.R;
import com.org.moneykeep.retrofitBean.UserBean;

public class SignUpPresenterImplements implements SignUpInterface.IPresenter, SignUpInterface.IModelCallBack {

    private SignUpInterface.IModel iModel;
    private SignUpInterface.IView iView;
    private Context context;

    public SignUpPresenterImplements(SignUpInterface.IView iView, Context context) {
        iModel = new SignUpModelImplements();
        this.iView = iView;
        this.context = context;
    }

    @Override
    public void UserSignUp(String user_name, String user_account, String user_password, String user_telephone_number) {
        int id = 0;
        try {
            if (user_name.equals("") || user_account.equals("")
                    || user_password.equals("") || user_telephone_number.equals("")) {
                if (user_name.equals("")) {
                    //sign_up_name.requestFocus();
                    id = R.id.sign_up_name;
                    throw new Nullexcetion();
                }
                if (user_account.equals("")) {
                    //sign_up_account.requestFocus();
                    id = R.id.sign_up_account;
                    throw new Nullexcetion();
                }
                if (user_password.equals("")) {
                    //sign_up_password.requestFocus();
                    id = R.id.sign_up_password;
                    throw new Nullexcetion();
                }
                if (user_telephone_number.equals("")) {
                    //sign_up_telephone_number.requestFocus();
                    id = R.id.sign_up_password;
                    throw new Nullexcetion();
                }

            } else{
                UserBean userBean = new UserBean();
                userBean.setAccount(user_account);
                userBean.setName(user_name);
                userBean.setPassword(user_password);
                userBean.setTelephone_number(user_telephone_number);
                Log.i("insertMessage","integerCall =========>" + userBean);
                //iModel.SendUserMessage(userBean,this);
                iModel.SelectMessage(userBean,this);
            }
        } catch (Nullexcetion e) {
            iView.UserSignUpUnSuccessful("请完善资料", id);
        }
    }



    @Override
    public void SendUserSuccessfulCallBack(String successful) {
        iView.UserSignUpSuccessful(successful);
    }

    @Override
    public void SendUserUnSuccessfulCallBack(String s) {
        iView.UserSignUpUnSuccessful(s,0);
    }

    @Override
    public void SelectUserSuccessfulCallBack(UserBean userBean) {
        iModel.SendUserMessage(userBean,this);
    }

    @Override
    public void SelectUserUnSuccessfulCallBack(String s) {

        iView.UserSignUpUnSuccessful(s, 0);
    }
}
