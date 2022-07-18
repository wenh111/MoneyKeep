package com.org.moneykeep.Activity.ui.SaveMoney;

import android.content.Context;
import android.util.Log;

import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.SaveMoneyAdapterList;
import com.org.moneykeep.retrofitBean.SaveMoneyEventBean;
import com.org.moneykeep.retrofitBean.SaveMoneyEventListBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SaveMoneyFragmentPresenterImplements implements SaveMoneyFragmentInterface.IPresenter {
    private SaveMoneyFragmentInterface.IModel iModel;
    private Context context;
    private SaveMoneyFragmentInterface.IView iView;

    public SaveMoneyFragmentPresenterImplements(Context context, SaveMoneyFragmentInterface.IView iView) {
        this.context = context;
        this.iView = iView;
        iModel = new SaveMoneyFragmentModelImplements(this);
    }


    @Override
    public void showAllSaveMoneyPlanEvent(String user_account, String type) {
        iModel.getSaveMoneyPlanEvent(user_account, type);
    }


    public CardState PunchCardUntil(SaveMoneyEventBean list) {
        double savedMoney = list.getSavedMoney();
        double targetMoney = list.getTargetMoney();
        String type = list.getType();
        Calendar c = Calendar.getInstance();
        String date = list.getDate();
        String deadlineDate = list.getDeadlineDate();
        String[] dates = date.split("-");
        String[] deadlineDates = deadlineDate.split("-");
        String DDL = "";
        for (String ddl : deadlineDates) {
            DDL = DDL + ddl;
        }
        long longDDL = Long.parseLong(DDL);
        int everyday_save = (int) targetMoney / list.getAmount();
        Log.i("everydaySave", String.valueOf(everyday_save));
        String text = "";
        String thismonth = "";
        String custom_type = "无";
        if (list.getCustomType() != null) {
            custom_type = list.getCustomType();
        }
        if (c.get(Calendar.MONTH) + 1 < 10) {
            thismonth = "0" + String.valueOf(c.get(Calendar.MONTH) + 1);
        } else {
            thismonth = String.valueOf(c.get(Calendar.MONTH) + 1);
        }
        String thisDate = "";
        if (c.get(Calendar.DATE) < 10) {
            thisDate = "0" + c.get(Calendar.DATE);
        } else {
            thisDate = String.valueOf(c.get(Calendar.DATE));
        }
        if (type.contains("月") || (type.equals("自定义") && custom_type.contains("月"))) {
            if (!(c.get(Calendar.YEAR) + thismonth).equals(dates[0] + dates[1]) || savedMoney == 0) {
                text = "未打卡";
            } else {
                text = "已打卡";
            }
        } else if (type.contains("周") || (type.equals("自定义") && custom_type.contains("周"))) {
            if (!(c.get(Calendar.YEAR) + String.valueOf(c.get(Calendar.WEEK_OF_YEAR))).equals(dates[0] + dates[3]) || savedMoney == 0) {
                text = "未打卡";
            } else {
                text = "已打卡";
            }
        } else if (type.contains("天") || (type.equals("自定义") && custom_type.contains("日"))) {
            if (!((c.get(Calendar.YEAR) + thismonth + thisDate)
                    .equals(dates[0] + dates[1] + dates[2])) || savedMoney == 0) {
                text = "未打卡";
            } else {
                text = "已打卡";
            }
        } else if (type.equals("自定义") && custom_type.contains("年")) {
            if (c.get(Calendar.YEAR) != Integer.parseInt(dates[0]) || savedMoney == 0) {
                text = "未打卡";
            } else {
                text = "已打卡";
            }
        }

        if (c.get(Calendar.YEAR) < Integer.parseInt(dates[0])) {
            text = "未开始";
        } else if (c.get(Calendar.YEAR) == Integer.parseInt(dates[0]) && c.get(Calendar.MONTH) + 1 < Integer.parseInt(dates[1])) {
            text = "未开始";
        } else if (c.get(Calendar.YEAR) == Integer.parseInt(dates[0]) && c.get(Calendar.MONTH) + 1 == Integer.parseInt(dates[1])
                && c.get(Calendar.DAY_OF_MONTH) < Integer.parseInt(dates[2])) {
            text = "未开始";
        } else if (c.get(Calendar.YEAR) > Integer.parseInt(deadlineDates[0])) {
            text = "未完成";
        } else if (c.get(Calendar.YEAR) == Integer.parseInt(deadlineDates[0]) && c.get(Calendar.MONTH) + 1 > Integer.parseInt(deadlineDates[1])) {
            text = "未完成";
        } else if (c.get(Calendar.YEAR) == Integer.parseInt(deadlineDates[0]) && c.get(Calendar.MONTH) + 1 == Integer.parseInt(deadlineDates[1])
                && c.get(Calendar.DAY_OF_MONTH) > Integer.parseInt(deadlineDates[2])) {
            text = "未完成";
        }

        if (savedMoney >= targetMoney) {
            text = "已完成";
        }

        /*if (text.equals("未打卡") || text.equals("已打卡")) {
            doingThing = doingThing + 1;
        }*/
        CardState cardState = new CardState();
        cardState.setText(text);
        if (text.equals("未打卡")) {
            cardState.setWeight(0);
        } else if (text.equals("已打卡")) {
            cardState.setWeight(1);
        } else if (text.equals("已完成")) {
            cardState.setWeight(2);
        } else if (text.equals("未开始")) {
            cardState.setWeight(3);
        } else if (text.equals("未完成")) {
            cardState.setWeight(4);
        }
        return cardState;
    }


    @Override
    public void showAllSaveMoneyPlanEventSuccessfulCallBack(String successfulMessage, SaveMoneyEventListBean body) {
        String message = successfulMessage;
        if (body.getCount() == 0) {
            message = "查询成功但没有数据...";
            iView.showAllSaveMoneyPlanEventSuccessful(message, -1, null);
        } else {
            int doingThing = 0;
            List<SaveMoneyEventBean> saveMoneyEventBeanList = body.getSaveMoneyEvents();
            List<SaveMoneyAdapterList> newSaveData = new ArrayList<>();
            for (SaveMoneyEventBean list : saveMoneyEventBeanList) {
                SaveMoneyAdapterList saveMoneyAdapterList = new SaveMoneyAdapterList();
                double targetMoney = list.getTargetMoney();
                String deadlineDate = list.getDeadlineDate();
                String[] deadlineDates = deadlineDate.split("-");
                String DDL = "";
                for (String ddl : deadlineDates) {
                    DDL = DDL + ddl;
                }
                long longDDL = Long.parseLong(DDL);
                int everyday_save = (int) targetMoney / list.getAmount();
                Log.i("everydaySave", String.valueOf(everyday_save));
                String text = "";

                CardState cardState = PunchCardUntil(list);
                int weight = cardState.getWeight();
                text = cardState.getText();
                if (text.equals("未打卡") || text.equals("已打卡")) {
                    doingThing = doingThing + 1;
                }
                saveMoneyAdapterList.setRemainingTimes(list.getRemainingTimes());
                saveMoneyAdapterList.setWeight(weight);
                saveMoneyAdapterList.setAmount(list.getAmount());
                saveMoneyAdapterList.setLongDDL(longDDL);
                saveMoneyAdapterList.setText(text);
                saveMoneyAdapterList.setTitle(list.getTitle());
                saveMoneyAdapterList.setType(list.getType());
                saveMoneyAdapterList.setDeadlineDate(list.getDeadlineDate());
                saveMoneyAdapterList.setDate(list.getDate());
                saveMoneyAdapterList.setTargetMoney(list.getTargetMoney());
                saveMoneyAdapterList.setSavedMoney(list.getSavedMoney());
                saveMoneyAdapterList.setObjectId(String.valueOf(list.getId()));
                saveMoneyAdapterList.setEveryday_save(everyday_save);
                newSaveData.add(saveMoneyAdapterList);
            }
            iView.showAllSaveMoneyPlanEventSuccessful(message, doingThing, newSaveData);
        }

    }

    @Override
    public void showAllSaveMoneyPlanEventUnSuccessfulCallBack(String unsuccessfulMessage) {
        iView.showAllSaveMoneyPlanEventUnSuccessful(unsuccessfulMessage);
    }

    public static class CardState {
        int weight;
        String text;

        public int getWeight() {

            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
