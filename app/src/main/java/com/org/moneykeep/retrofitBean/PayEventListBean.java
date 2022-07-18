package com.org.moneykeep.retrofitBean;

import java.util.List;

public class PayEventListBean {


    private Integer count;
    private List<AllPayListDTO> allPayList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<AllPayListDTO> getAllPayList() {
        return allPayList;
    }

    public void setAllPayList(List<AllPayListDTO> allPayList) {
        this.allPayList = allPayList;
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
}
