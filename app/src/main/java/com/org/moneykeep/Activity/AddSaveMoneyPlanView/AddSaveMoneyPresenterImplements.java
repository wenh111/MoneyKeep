package com.org.moneykeep.Activity.AddSaveMoneyPlanView;

import android.content.Context;
import android.util.Log;

import com.org.moneykeep.Excention.ErrorAddPayEvent;
import com.org.moneykeep.R;
import com.org.moneykeep.retrofitBean.SaveMoneyEventBean;

import java.util.Calendar;

public class AddSaveMoneyPresenterImplements implements AddSaveMoneyInterface.IPresenter{

    private final AddSaveMoneyInterface.IView iView;
    private final AddSaveMoneyInterface.IModel iModel;

    public AddSaveMoneyPresenterImplements(AddSaveMoneyInterface.IView iView) {
        this.iView = iView;
        iModel = new AddSaveMoneyModelImplements(this);
    }

    @Override
    public void addSaveMoney(String account, String number, String type, String title, String date, String targetMoney, String custom_text) {
        int id = -1;
        String message = null;
        try {
            if (date.equals("") || targetMoney.equals("") || title.equals("") ||
                    (type.equals("自定义") && number.equals(""))) {
                if (title.equals("")) {
                    id = R.id.ed_title;
                    throw new ErrorAddPayEvent("信息不完善");
                } else if (date.equals("")) {
                    id = R.id.ed_time;
                    throw new ErrorAddPayEvent("信息不完善");
                } else if (targetMoney.equals("")) {
                    id = R.id.ed_target_money;
                    throw new ErrorAddPayEvent("信息不完善");
                } else if ((type.equals("自定义") && number.equals(""))) {
                    id = R.id.ed_custom_number;
                    throw new ErrorAddPayEvent("信息不完善");
                }
            }
            String[] dates = date.split("-");

            SaveMoneyEventBean saveMoneyEventBean = new SaveMoneyEventBean();
            //创建Calendar实例
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(dates[0]));
            cal.set(Calendar.MONTH, Integer.parseInt(dates[1]) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));
            Log.i("dateofcal", cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH));
            String startDate = date + "-" + cal.get(Calendar.WEEK_OF_YEAR);
            //System.out.println(format.format(cal.getTime()));
            String dealDate;
            String customType = "无";
            if (type.equals("12月存钱")) {
                cal.add(Calendar.MONTH, 12);
                saveMoneyEventBean.setAmount(12);
                saveMoneyEventBean.setRemainingTimes(12);
                Log.i("dateofcal", cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH));
            } else if (type.equals("30天存钱")) {
                cal.add(Calendar.DAY_OF_YEAR, 30);
                Log.i("dateofcal", cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH));
                saveMoneyEventBean.setAmount(30);
                saveMoneyEventBean.setRemainingTimes(30);
            } else if (type.equals("365天存钱")) {
                cal.add(Calendar.DAY_OF_YEAR, 365);
                Log.i("dateofcal", cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH));
                saveMoneyEventBean.setAmount(365);
                saveMoneyEventBean.setRemainingTimes(365);
            } else if (type.equals("52周存钱")) {
                cal.add(Calendar.WEEK_OF_YEAR, 52);
                saveMoneyEventBean.setRemainingTimes(52);
                Log.i("dateofcal", cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH));
                saveMoneyEventBean.setAmount(52);
            } else if (type.equals("4周存钱")) {
                cal.add(Calendar.WEEK_OF_YEAR, 4);
                saveMoneyEventBean.setAmount(4);
                saveMoneyEventBean.setRemainingTimes(4);
                Log.i("dateofcal", cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH));
            }else if(type.equals("自定义")){
                customType = custom_text;
                int i_number = Integer.parseInt(number);
                if(custom_text.contains("年")){
                    cal.add(Calendar.YEAR, i_number);
                }else if(custom_text.contains("月")){
                    cal.add(Calendar.MONTH, i_number);
                }else if(custom_text.contains("周")){
                    cal.add(Calendar.WEEK_OF_YEAR, i_number);
                }else if(custom_text.contains("日")){
                    cal.add(Calendar.DAY_OF_YEAR, i_number);
                }
                saveMoneyEventBean.setAmount(i_number);
                saveMoneyEventBean.setRemainingTimes(i_number);
            }
            saveMoneyEventBean.setCustomType(customType);

            String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + 1);
            if (cal.get(Calendar.MONTH) < 10) {
                month = "0" + month;
            }
            if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
                day = "0" + day;
            }
            dealDate = cal.get(Calendar.YEAR) + "-" + month + "-" + day;
            saveMoneyEventBean.setDate(startDate);
            saveMoneyEventBean.setDeadlineDate(dealDate);
            saveMoneyEventBean.setType(type);
            saveMoneyEventBean.setTitle(title);
            saveMoneyEventBean.setAccount(account);
            saveMoneyEventBean.setTargetMoney(Double.valueOf(targetMoney));

            iModel.insertSaveMoneyEvent(saveMoneyEventBean);

        }catch (ErrorAddPayEvent errorAddPayEvent){
            iView.addSaveMoneyUnSuccessful(id, "请完善信息");
        }
    }

    @Override
    public void addSaveMoneySuccessfulCallBack(String SuccessfulMessage) {
        iView.addSaveMoneySuccessful(SuccessfulMessage);
    }

    @Override
    public void addSaveMoneyUnSuccessfulCallBack(String UnSuccessfulMessage) {
        iView.addSaveMoneyUnSuccessful(-1,UnSuccessfulMessage);
    }
}
