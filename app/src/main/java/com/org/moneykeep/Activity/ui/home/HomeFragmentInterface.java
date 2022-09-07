package com.org.moneykeep.Activity.ui.home;


import com.org.moneykeep.retrofitBean.PayEventBean;
import com.org.moneykeep.retrofitBean.PayEventListBean;

public interface HomeFragmentInterface {
    interface IModel {
        void SelectDayMessage(PayEventBean payEventBean);

/*        void SelectMonthMessage(PayEventBean payEventBean);

        void SelectYearMessage(PayEventBean payEventBean);*/

        void SelectAMonthMessage(PayEventBean payEventBean, int since, int perPage,int selectType);

        void deletePayEvent(int id);
    }

    interface IPresenter {
        void deletePayEvent(int id);

        void getDayMessage(String account, String type, String date);

        /*void getMonthMessage(String user_account, String select_type, String select_month, String select_year);

        void getYearMessage(String user_account, String select_type, String select_year);*/

        void SelectDayMessageSuccessfulCallBack(PayEventListBean body);

        void SelectDayMessageUnSuccessfulCallBack(String message);

/*        void SelectMonthAndYearMessageSuccessfulCallBack(PayEventListBean body);

        void SelectMonthAndYearMessageUnSuccessfulCallBack(String s);*/

        void SelectAMonthOrYearMessageSuccessfulCallBack(PayEventListBean listBean,int selectType);

        void SelectAMonthOrYearMessageUnSuccessfulCallBack(String errorMessage);

        void getAMonthOrYearMessage(String user_account, String select_type, String select_month, String select_year, int since, int perPage, int selectType, String select_date);

        void deletePayEventSuccessfulCallBack(String s);

        void deletePayEventUnSuccessfulCallBack(String s);
    }

    interface IView {
        void getDayMessageSuccessful(String s, PayEventListBean body);

        void getDayMessageUnSuccessful(String message);

        /*void getMonthAndYearMessageSuccessful(String s, List<MonthPayOrIncomeList> newMonthPayOrIncomeDate, Map<String, List<PayEventListBean.AllPayListDTO>> map, List<PayEventListBean.AllPayListDTO> allSelect, double countIncome, double countPay);

        void getMonthAndYearMessageUnSuccessful(String s);*/

        void getAMonthOrYearMessageSuccessful(String s, PayEventListBean body, double countIncome, double countPay,int selectType);

        void getAMonthOrYearMessageUnSuccessful(String errorMessage);

        void deletePayEventSuccessful(String s);

        void deletePayEventUnSuccessful(String s);
    }
     interface LoadInterface {
        void OnLoadLister(Integer since, Integer perPage);
    }
}
