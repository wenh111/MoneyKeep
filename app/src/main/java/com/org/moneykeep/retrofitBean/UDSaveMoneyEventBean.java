package com.org.moneykeep.retrofitBean;


public class UDSaveMoneyEventBean {
    private double savedMoney;
    private String date;
    private int id;
    private int remainingTimes;

    public UDSaveMoneyEventBean(double savedMoney, String date, int id, int remainingTimes) {
        this.savedMoney = savedMoney;
        this.date = date;
        this.id = id;
        this.remainingTimes = remainingTimes;
    }

    public UDSaveMoneyEventBean() {
    }

    public double getSavedMoney() {
        return savedMoney;
    }

    public void setSavedMoney(double savedMoney) {
        this.savedMoney = savedMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRemainingTimes() {
        return remainingTimes;
    }

    public void setRemainingTimes(int remainingTimes) {
        this.remainingTimes = remainingTimes;
    }
}
