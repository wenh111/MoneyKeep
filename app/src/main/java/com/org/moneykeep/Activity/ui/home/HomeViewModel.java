package com.org.moneykeep.Activity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.org.moneykeep.R;

import java.util.HashMap;
import java.util.Map;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<String> date;
    private MutableLiveData<String> type;

    public MutableLiveData<Integer> since;
    public MutableLiveData<Integer> perPage;
    public MutableLiveData<Boolean> isOver;
    private final Map<String, Integer> img;

    public MutableLiveData<String> getDate() {
        return date;
    }

    public MutableLiveData<String> getType() {
        return type;
    }

    public void changDate(String changDate) {
        date.setValue(changDate);
    }

    public void changType(String changType) {
        type.setValue(changType);
    }

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        date = new MutableLiveData<>();
        type = new MutableLiveData<>();
        since = new MutableLiveData<>();
        perPage = new MutableLiveData<>();
        isOver = new MutableLiveData<>();
        img = new HashMap<>();
        isOver.setValue(false);
        since.setValue(-1);
        perPage.setValue(-1);
        mText.setValue("This is home fragment");

        {
            img.put("餐饮",R.drawable.restaurant_64);
            img.put("交通", R.drawable.traffic_64);
            img.put("服饰", R.drawable.clothes_64);
            img.put("购物", R.drawable.shopping_64);
            img.put("服务", R.drawable.service_64);
            img.put("教育", R.drawable.teach_64);
            img.put("娱乐", R.drawable.entertainment_64);
            img.put("运动", R.drawable.motion_64);
            img.put("生活缴费", R.drawable.living_payment_64);
            img.put("旅行", R.drawable.travel_64);
            img.put("宠物", R.drawable.pets_64);
            img.put("医疗", R.drawable.medical_64);
            img.put("保险", R.drawable.insurance_64);
            img.put("公益", R.drawable.welfare_64);
            img.put("发红包", R.drawable.envelopes_64);
            img.put("转账", R.drawable.transfer_accounts_64);
            img.put("亲属卡", R.drawable.kinship_card_64);
            img.put("做人情", R.drawable.human_64);
            img.put("其它支出", R.drawable.others_64);
            img.put("生意", R.drawable.business_64);
            img.put("工资", R.drawable.wages_64);
            img.put("奖金", R.drawable.bonus_64);
            img.put("收红包", R.drawable.envelopes_64);
            img.put("收转账", R.drawable.transfer_accounts_64);
            img.put("其它收入", R.drawable.others_64);
            img.put("建设银行", R.drawable.construction_bank_64);
            img.put("农业银行", R.drawable.agricultural_bank_64);
            img.put("全部类型",R.mipmap.all_64);
        }


    }

    public MutableLiveData<Boolean> getIsOver() {
        return isOver;
    }

    public MutableLiveData<Integer> getSince() {
        return since;
    }

    public MutableLiveData<Integer> getPerPage() {
        return perPage;
    }

    public void dataChange(int cSince, int cPerPage) {
        since.setValue(cSince);
        perPage.setValue(cPerPage);
    }

    public void dataLoadOver(boolean cIsOver) {
        isOver.setValue(cIsOver);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public int getSrc(String type){
        return img.getOrDefault(type, R.drawable.others_64);
    }
}