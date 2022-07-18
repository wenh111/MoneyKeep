package com.org.moneykeep.retrofitBean;

import java.util.List;

public class SaveMoneyEventListBean {

    private Integer count;
    private List<SaveMoneyEventBean> saveMoneyEvents;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SaveMoneyEventBean> getSaveMoneyEvents() {
        return saveMoneyEvents;
    }

    public void setSaveMoneyEvents(List<SaveMoneyEventBean> saveMoneyEvents) {
        this.saveMoneyEvents = saveMoneyEvents;
    }
}
