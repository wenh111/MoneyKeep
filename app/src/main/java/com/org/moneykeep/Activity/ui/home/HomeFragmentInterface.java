package com.org.moneykeep.Activity.ui.home;


import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.DayPayOrIncomeList;
import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.MonthPayOrIncomeList;
import com.org.moneykeep.retrofitBean.PayEventBean;
import com.org.moneykeep.retrofitBean.PayEventListBean;

import java.util.List;
import java.util.Map;

public interface HomeFragmentInterface {
    interface IModel {
        void SelectDayMessage(PayEventBean payEventBean);

        void SelectMonthMessage(PayEventBean payEventBean);

        void SelectYearMessage(PayEventBean payEventBean);

        void SelectAMonthMessage(PayEventBean payEventBean, int since, int perPage);

        void deletePayEvent(int id);
    }

    interface IPresenter {
        void deletePayEvent(int id);

        void getDayMessage(String account, String type, String date);

        void getMonthMessage(String user_account, String select_type, String select_month, String select_year);

        void getYearMessage(String user_account, String select_type, String select_year);

        void SelectDayMessageSuccessfulCallBack(PayEventListBean body);

        void SelectDayMessageUnSuccessfulCallBack(String message);

        void SelectMonthAndYearMessageSuccessfulCallBack(PayEventListBean body);

        void SelectMonthAndYearMessageUnSuccessfulCallBack(String s);

        void SelectAMonthMessageSuccessfulCallBack(PayEventListBean listBean);

        void SelectAMonthMessageUnSuccessfulCallBack(String errorMessage);

        void getAMonthMessage(String user_account, String select_type, String select_month, String select_year, int since, int perPage);

        void deletePayEventSuccessfulCallBack(String s);

        void deletePayEventUnSuccessfulCallBack(String s);
    }

    interface IView {
        void getDayMessageSuccessful(String s, List<DayPayOrIncomeList> newDayPayOrIncomeDate, double countIncome, double countPay);

        void getDayMessageUnSuccessful(String message);

        void getMonthAndYearMessageSuccessful(String s, List<MonthPayOrIncomeList> newMonthPayOrIncomeDate, Map<String, List<PayEventListBean.AllPayListDTO>> map, List<PayEventListBean.AllPayListDTO> allSelect, double countIncome, double countPay);

        void getMonthAndYearMessageUnSuccessful(String s);

        void getAMonthMessageSuccessful(String s, PayEventListBean body, double countIncome, double countPay);

        void getAMonthMessageUnSuccessful();

        void deletePayEventSuccessful(String s);

        void deletePayEventUnSuccessful(String s);
    }
     interface LoadInterface {
        void OnLoadLister(Integer since, Integer perPage);
    }
}
