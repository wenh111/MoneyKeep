package com.org.moneykeep.retrofitBean;

import java.util.List;

public class PayOrIncomeEventListBean {

    private List<AllPayListDTO> allPayList;
    private List<AllIncomeListDTO> allIncomeList;
    private Integer payCount;
    private Integer incomeCount;

    public List<AllPayListDTO> getAllPayList() {
        return allPayList;
    }

    public void setAllPayList(List<AllPayListDTO> allPayList) {
        this.allPayList = allPayList;
    }

    public List<AllIncomeListDTO> getAllIncomeList() {
        return allIncomeList;
    }

    public void setAllIncomeList(List<AllIncomeListDTO> allIncomeList) {
        this.allIncomeList = allIncomeList;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public Integer getIncomeCount() {
        return incomeCount;
    }

    public void setIncomeCount(Integer incomeCount) {
        this.incomeCount = incomeCount;
    }

    public static class AllPayListDTO {
        private Integer id;
        private String account;
        private String payTime;
        private String location;
        private String category;
        private Double cost;
        private String remark;
        private String date;
        private String time;
        private String year;
        private String month;
        private Integer int_date;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Double getCost() {
            return cost;
        }

        public void setCost(Double cost) {
            this.cost = cost;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public Integer getInt_date() {
            return int_date;
        }

        public void setInt_date(Integer int_date) {
            this.int_date = int_date;
        }
    }

    public static class AllIncomeListDTO {
        private Integer id;
        private String account;
        private String payTime;
        private String location;
        private String category;
        private Double cost;
        private String remark;
        private String date;
        private String time;
        private String year;
        private String month;
        private Integer int_date;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Double getCost() {
            return cost;
        }

        public void setCost(Double cost) {
            this.cost = cost;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public Integer getInt_date() {
            return int_date;
        }

        public void setInt_date(Integer int_date) {
            this.int_date = int_date;
        }
    }
}
