package com.org.moneykeep.Activity.PunchCardView;

import android.util.Log;

import com.org.moneykeep.Until.ChangeDouble;
import com.org.moneykeep.retrofitBean.UDSaveMoneyEventBean;

import java.util.Calendar;

public class PunchCardViewPresenterImplements implements PunchCardViewInterface.IPresenter {
    private PunchCardViewInterface.IView iView;
    private PunchCardViewInterface.IModel iModel;

    public PunchCardViewPresenterImplements(PunchCardViewInterface.IView iView) {
        this.iView = iView;
        iModel = new PunchCardViewModelImplements(this);
    }

    @Override
    public void showPunchCardEvent() {

    }

    @Override
    public void deletePunchCardEvent(int id) {
        UDSaveMoneyEventBean udSaveMoneyEventBean = new UDSaveMoneyEventBean(-1, null, id, -1);
        iModel.deletePunchCardEvent(udSaveMoneyEventBean, 1);
    }

    @Override
    public void punchCard(double sSavedMoney, double remarkSavedMoney, int remainingTimes, int id) {
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        int week = c.get(Calendar.WEEK_OF_YEAR);
        double newSavedMoney = ChangeDouble.addDouble(sSavedMoney, remarkSavedMoney);
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        String date = year + "-" + month + "-" + day + "-" + week;
        remainingTimes = remainingTimes - 1;
        UDSaveMoneyEventBean udSaveMoneyEventBean = new UDSaveMoneyEventBean();
        udSaveMoneyEventBean.setSavedMoney(newSavedMoney);
        udSaveMoneyEventBean.setRemainingTimes(remainingTimes);
        udSaveMoneyEventBean.setDate(date);
        udSaveMoneyEventBean.setId(id);
        Log.i("udSaveMoneyEventBean", "date == "+udSaveMoneyEventBean.getDate());
        Log.i("udSaveMoneyEventBean", "newSavedMoney == "+udSaveMoneyEventBean.getSavedMoney());
        Log.i("udSaveMoneyEventBean", "id == "+udSaveMoneyEventBean.getId());
        Log.i("udSaveMoneyEventBean", "remainingTimes == "+udSaveMoneyEventBean.getRemainingTimes());
        iModel.updatePunchCardEvent(udSaveMoneyEventBean,-1);
    }

    @Override
    public void showPunchCardEventSuccessfulCallBack() {

    }

    @Override
    public void showPunchCardEventUnSuccessfulCallBack() {

    }

    @Override
    public void deletePunchCardEventSuccessfulCallBack(String message) {
        iView.deletePunchCardEventSuccessful(message);
    }

    @Override
    public void deletePunchCardEventUnSuccessfulCallBack(String s) {
        iView.deletePunchCardEventUnSuccessful(s);
    }

    @Override
    public void PunchCardSuccessfulCallBack(String s) {
        iView.PunchCardSuccessful(s);
    }

    @Override
    public void PunchCardUnSuccessfulCallBack(String s) {
        iView.PunchCardUnSuccessful(s);
    }
}
