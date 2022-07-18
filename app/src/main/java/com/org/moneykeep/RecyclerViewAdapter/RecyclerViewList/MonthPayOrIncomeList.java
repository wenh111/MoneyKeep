package com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList;

import java.util.List;

public class MonthPayOrIncomeList {
    private String date;
    private double dayIncome;
    private double dayPay;
    private int int_date;
    private double allPay;
    private double allIncome;

    public double getAllPay() {
        return allPay;
    }

    public void setAllPay(double allPay) {
        this.allPay = allPay;
    }

    public double getAllIncome() {
        return allIncome;
    }

    public void setAllIncome(double allIncome) {
        this.allIncome = allIncome;
    }

    public int getInt_date() {
        return int_date;
    }

    public void setInt_date(int int_date) {
        this.int_date = int_date;
    }

    public double getDayIncome() {
        return dayIncome;
    }

    public void setDayIncome(double dayIncome) {
        this.dayIncome = dayIncome;
    }

    public double getDayPay() {
        return dayPay;
    }

    public void setDayPay(double dayPay) {
        this.dayPay = dayPay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
