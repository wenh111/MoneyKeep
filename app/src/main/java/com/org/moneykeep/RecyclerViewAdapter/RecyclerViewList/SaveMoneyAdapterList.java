package com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList;

public class SaveMoneyAdapterList {
    private String type;
    private double savedMoney;
    private String deadlineDate;
    private String date;
    private double targetMoney;
    private String title;
    private String text;
    private int weight;
    private String objectId;
    private int everyday_save;
    private long longDDL;
    private int amount;
    private int remainingTimes;

    public int getRemainingTimes() {
        return remainingTimes;
    }

    public void setRemainingTimes(int remainingTimes) {
        this.remainingTimes = remainingTimes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getLongDDL() {
        return longDDL;
    }

    public void setLongDDL(long longDDL) {
        this.longDDL = longDDL;
    }

    public int getEveryday_save() {
        return everyday_save;
    }

    public void setEveryday_save(int everyday_save) {
        this.everyday_save = everyday_save;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }


    public double getSavedMoney() {
        return savedMoney;
    }

    public void setSavedMoney(double savedMoney) {
        this.savedMoney = savedMoney;
    }

    public double getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(double targetMoney) {
        this.targetMoney = targetMoney;
    }
}
