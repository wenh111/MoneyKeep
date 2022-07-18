package com.org.moneykeep.Activity.DetailsView;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.org.moneykeep.Until.UpdateList;
import com.org.moneykeep.retrofitBean.PayEventBean;

public class DetailsPresentersImplements implements DetailsInterface.IPresenter{
    private final DetailsInterface.IView iView;
    private final DetailsInterface.IModel iModel;
    private final Context context;

    public DetailsPresentersImplements(DetailsInterface.IView iView, Context context) {
        this.iView = iView;
        iModel = new DetailsModelImplements(this);
        this.context = context;
    }

    @Override
    public void getDetailsMessage(int id) {
        iModel.getDetailsMessage(id);
    }

    @Override
    public void UpdateMessage(UpdateList newUpdateList) {
        if(newUpdateList != null){
            String[] dates = newUpdateList.getDate().split(" ");
            String[] date = dates[0].split("-");
            String year = date[0];
            String month = date[1];
            String day = date[2];
            String time = dates[dates.length - 1];
            String cost = newUpdateList.getCost();
            String category = newUpdateList.getType();
            String remark = newUpdateList.getRemark();

            PayEventBean allPay = new PayEventBean();
            allPay.setId(newUpdateList.getId());
            Log.i("newUpdateListID", String.valueOf(newUpdateList.getId()));
            allPay.setYear(year);
            allPay.setMonth(month);
            allPay.setTime(time);
            allPay.setDate(dates[0]);
            allPay.setPayTime(newUpdateList.getDate());
            allPay.setCategory(category);
            allPay.setRemark(remark);
            allPay.setCost(Double.parseDouble(cost));
            if(Integer.parseInt(month) <10){
                month = "0" + month;
            }
            if(Integer.parseInt(day) <10){
                day = "0" + day;
            }
            int int_date = Integer.parseInt(year + month + day);
            allPay.setInt_date(int_date);
            iModel.UpdateMessage(allPay);
        }else{
            iView.UpdateMessageUnSuccessful("更新失败了...");
        }
    }

    @Override
    public void deleteMessage(int id) {
        iModel.deleteMessage(id);
    }

    @Override
    public void getDetailsMessageSuccessfulCallBack(PayEventBean body) {
        iView.getDetailsMessageSuccessful(body);
    }

    @Override
    public void getDetailsMessageUnSuccessfulCallBack(String s) {
        iView.getDetailsMessageUnSuccessful(s);
    }

    @Override
    public void UpdateMessageSuccessfulCallBack(String s) {
        SharedPreferences keep = null;
        if (keep == null) {
            keep = context.getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor user_editor = keep.edit();
        user_editor.putBoolean("isdelete", true);
        user_editor.commit();
        iView.UpdateMessageSuccessful(s);
    }

    @Override
    public void UpdateMessageUnSuccessfulCallBack(String s) {
        iView.UpdateMessageUnSuccessful(s);
    }

    @Override
    public void deleteMessageSuccessfulCallBack(String s) {
        SharedPreferences keep;
        keep = context.getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
        SharedPreferences.Editor user_editor = keep.edit();
        user_editor.putBoolean("isdelete", true);
        user_editor.apply();
        iView.deleteMessageSuccessful(s);
    }

    @Override
    public void deleteMessageUnSuccessfulCallBack(String s) {
        iView.deleteMessageUnSuccessful(s);
    }
}
