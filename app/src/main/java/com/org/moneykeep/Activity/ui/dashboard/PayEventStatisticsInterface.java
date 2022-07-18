package com.org.moneykeep.Activity.ui.dashboard;

import com.org.moneykeep.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.org.moneykeep.retrofitBean.PayOrIncomeEventListBean;

public interface PayEventStatisticsInterface {
    interface IModel {
        void SelectDayPayStatistics(String account, String date);
        void SelectMonthPayStatistics(String account, String month, String year);
        void SelectYearPayStatistics(String user_account, String selectYear);
    }

    interface IPresenter{
        void GetDayPayStatistics(String user_account, String date);
        void GetMonthPayStatistics(String account, String month, String year);
        void GetYearPayStatistics(String user_account, String selectYear);

        void GetAllStatisticsPieSuccessfulCallBack(PayOrIncomeEventListBean body);
        void GetAllStatisticsPieUnSuccessfulCallBack(String s);
        void GetMonthPayColumnsCallBack(PayOrIncomeEventListBean body);
        void GetYearPayColumnsCallBack(PayOrIncomeEventListBean body);
    }

    interface IView{
        void GetDayPayStatisticsSuccessful(String s, AAChartModel payChartModel, AAChartModel incomeChartModel);
        void GetDayPayStatisticsUnSuccessful(String s);
        void GetMonthPayColumnsSuccessful(String message, AAChartModel columnModelPay);

        void GetYearPayColumnsSuccessful(String message, AAChartModel columnModelPay);

    }
}
