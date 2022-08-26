package com.org.moneykeep.Activity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<String> date;
    private MutableLiveData<String> type;

    public MutableLiveData<Integer> since;
    public MutableLiveData<Integer> perPage;
    public MutableLiveData<Boolean> isOver;


    public MutableLiveData<String> getDate() {
        return date;
    }

    public MutableLiveData<String> getType() {
        return type;
    }
    public void changDate(String changDate){
        date.setValue(changDate);
    }
    public void changType(String changType){
        type.setValue(changType);
    }
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        date = new MutableLiveData<>();
        type = new MutableLiveData<>();
        since = new MutableLiveData<>();
        perPage = new MutableLiveData<>();
        isOver = new MutableLiveData<>();
        isOver.setValue(false);
        since.setValue(-1);
        perPage.setValue(-1);
        mText.setValue("This is home fragment");
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

    public void dataChange(int cSince,int cPerPage){
        since.setValue(cSince);
        perPage.setValue(cPerPage);
    }
    public void dataLoadOver(boolean cIsOver){
        isOver.setValue(cIsOver);
    }
    public LiveData<String> getText() {
        return mText;
    }
}