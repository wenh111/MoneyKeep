package com.org.moneykeep.Activity.PunchCardView;

import com.org.moneykeep.retrofitBean.UDSaveMoneyEventBean;

public interface PunchCardViewInterface {

    interface IModel {
        void selectPunchCardEvent();
        void deletePunchCardEvent(UDSaveMoneyEventBean udSaveMoneyEventBean, int isDelete);
        void updatePunchCardEvent(UDSaveMoneyEventBean udSaveMoneyEventBean, int i);
    }

    interface IPresenter {
        void showPunchCardEvent();

        void deletePunchCardEvent(int id);

        void punchCard(double sSavedMoney, double remarkSavedMoney,int remainingTimes,int id);

        void showPunchCardEventSuccessfulCallBack();

        void showPunchCardEventUnSuccessfulCallBack();

        void deletePunchCardEventSuccessfulCallBack(String message);

        void deletePunchCardEventUnSuccessfulCallBack(String s);

        void PunchCardSuccessfulCallBack(String s);

        void PunchCardUnSuccessfulCallBack(String s);
    }

    interface IView {
        void showPunchCardEventSuccessful();

        void showPunchCardEventUnSuccessful();

        void deletePunchCardEventSuccessful(String message);

        void deletePunchCardEventUnSuccessful(String s);

        void PunchCardSuccessful(String s);

        void PunchCardUnSuccessful(String s);
    }
}
