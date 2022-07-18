package com.org.moneykeep.retrofitBean;

import java.util.List;


public class UserMessageBeen {


    private Integer count;

    private List<UserMessageDTO> userMessage;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserMessageDTO> getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(List<UserMessageDTO> userMessage) {
        this.userMessage = userMessage;
    }


    public static class UserMessageDTO {

        private Integer id;

        private String name;

        private String account;

        private String password;

        private String telephone_number;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTelephone_number() {
            return telephone_number;
        }

        public void setTelephone_number(String telephone_number) {
            this.telephone_number = telephone_number;
        }
    }
}
