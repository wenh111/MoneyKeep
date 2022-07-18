package com.org.moneykeep.Activity.AddPayEventView;

import com.org.moneykeep.retrofitBean.PayEventBean;

public interface AddPayEventInterface {

    interface IModel {
        void InsertPayEvent(PayEventBean payEvent);
    }

    interface IPresenter{
        void AddPayEvent(String time, String location, String type, String remark, double money);
        void AddPayEventSuccessfulCallBack(String s);
        void AddPayEventUnSuccessfulCallBack(String s);
    }

    interface IView{
        void AddPayEventSuccessful(String s);
        void AddPayEventUnSuccessful(String s);
    }
}
