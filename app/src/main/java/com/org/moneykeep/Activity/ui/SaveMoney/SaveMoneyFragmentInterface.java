package com.org.moneykeep.Activity.ui.SaveMoney;

import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.SaveMoneyAdapterList;
import com.org.moneykeep.retrofitBean.SaveMoneyEventListBean;

import java.util.List;

public interface SaveMoneyFragmentInterface {
    interface IModel {
        void getSaveMoneyPlanEvent(String user_account, String type);

    }

    interface IPresenter {
        void showAllSaveMoneyPlanEvent(String user_account, String type);

        void showAllSaveMoneyPlanEventSuccessfulCallBack(String successfulMessage, SaveMoneyEventListBean body);
        void showAllSaveMoneyPlanEventUnSuccessfulCallBack(String unsuccessfulMessage);
    }

    interface IView {

        void showAllSaveMoneyPlanEventSuccessful(String successfulMessage, int doingThing, List<SaveMoneyAdapterList> newSaveData);
        void showAllSaveMoneyPlanEventUnSuccessful(String unsuccessfulMessage);
    }
}
