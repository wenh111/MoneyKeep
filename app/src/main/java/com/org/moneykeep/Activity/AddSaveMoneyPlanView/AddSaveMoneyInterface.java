package com.org.moneykeep.Activity.AddSaveMoneyPlanView;

import com.org.moneykeep.retrofitBean.SaveMoneyEventBean;

public interface AddSaveMoneyInterface {
    interface IModel {
        void insertSaveMoneyEvent(SaveMoneyEventBean saveMoneyEventBean);
    }

    interface IPresenter {
        void addSaveMoney(String account, String number, String type, String title, String date, String targetMoney, String custom_text);

        void addSaveMoneySuccessfulCallBack(String SuccessfulMessage);

        void addSaveMoneyUnSuccessfulCallBack(String UnSuccessfulMessage);
    }

    interface IView {
        void addSaveMoneySuccessful(String successfulMessage);

        void addSaveMoneyUnSuccessful(int i, String message);
    }
}
