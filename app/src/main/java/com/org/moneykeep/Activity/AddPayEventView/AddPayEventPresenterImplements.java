package com.org.moneykeep.Activity.AddPayEventView;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.org.moneykeep.retrofitBean.PayEventBean;

public class AddPayEventPresenterImplements implements AddPayEventInterface.IPresenter{

    private AddPayEventInterface.IView iView;
    private AddPayEventInterface.IModel iModel;
    private Context context;

    public AddPayEventPresenterImplements(AddPayEventInterface.IView iView, Context context) {
        this.iView = iView;
        this.context = context;
        iModel = new AddPayEventModelImplements(this);
    }

    @Override
    public void AddPayEvent(String time, String location, String type, String remark, double money) {
        String[] dates = time.split(" ");
        String summit_data = dates[0];
        String summit_time = dates[dates.length-1];
        String []MonthYearDay = dates[0].split("-");
        String summit_month = MonthYearDay[1];
        String summit_year = MonthYearDay[0];
        String summit_day = MonthYearDay[2];


        SharedPreferences userdata = null;
        if (userdata == null) userdata = context.getSharedPreferences("user", MODE_PRIVATE);

        String user_account = userdata.getString("user_email","");
        PayEventBean payEvent = new PayEventBean();
        payEvent.setDate(summit_data);
        payEvent.setAccount(user_account);
        payEvent.setPayTime(time);
        payEvent.setLocation(location);
        payEvent.setCost(money);
        payEvent.setCategory(type);
        payEvent.setTime(summit_time);
        payEvent.setMonth(summit_month);
        payEvent.setYear(summit_year);
        if(10 > Integer.parseInt(summit_month) || Integer.parseInt(summit_day) < 10){
            if(Integer.parseInt(summit_month) < 10 ){
                summit_month = "0" + summit_month;
            }
            if(Integer.parseInt(summit_day) < 10) {
                summit_day = "0" + summit_day;
            }
        }
        payEvent.setInt_date(Integer.valueOf(summit_year+summit_month+summit_day));
        if(!remark.equals("")){
            payEvent.setRemark(remark);
        }else{
            payEvent.setRemark("无");
        }

        iModel.InsertPayEvent(payEvent);
    }

    @Override
    public void AddPayEventSuccessfulCallBack(String s) {
        SharedPreferences keep = null;
        if (keep == null) {
            keep = context.getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor user_editor = keep.edit();
        user_editor.putBoolean("isdelete", true);
        user_editor.commit();
        iView.AddPayEventSuccessful(s);
    }

    @Override
    public void AddPayEventUnSuccessfulCallBack(String s) {
        iView.AddPayEventUnSuccessful(s);
    }
}
