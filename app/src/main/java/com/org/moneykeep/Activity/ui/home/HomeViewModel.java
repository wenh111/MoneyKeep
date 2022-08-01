package com.org.moneykeep.Activity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<String> date;
    private MutableLiveData<String> type;

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
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}