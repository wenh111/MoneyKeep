package com.org.moneykeep.Activity.DetailsView;

import com.org.moneykeep.Until.UpdateList;
import com.org.moneykeep.retrofitBean.PayEventBean;

public interface DetailsInterface {

    interface IModel {
        void getDetailsMessage(int id);

        void UpdateMessage(PayEventBean allPay);

        void deleteMessage(int id);
    }

    interface IPresenter {
        void getDetailsMessage(int id);

        void UpdateMessage(UpdateList newUpdateList);

        void deleteMessage(int id);

        //Successful、UnSuccessful
        void getDetailsMessageSuccessfulCallBack(PayEventBean body);

        void getDetailsMessageUnSuccessfulCallBack(String s);

        void UpdateMessageSuccessfulCallBack(String s);

        void UpdateMessageUnSuccessfulCallBack(String s);

        void deleteMessageSuccessfulCallBack(String s);

        void deleteMessageUnSuccessfulCallBack(String s);

    }

    interface IView {
        void getDetailsMessageSuccessful(PayEventBean body);

        void getDetailsMessageUnSuccessful(String s);

        void UpdateMessageSuccessful(String s);

        void UpdateMessageUnSuccessful(String s);

        void deleteMessageSuccessful(String s);

        void deleteMessageUnSuccessful(String s);
    }
}
