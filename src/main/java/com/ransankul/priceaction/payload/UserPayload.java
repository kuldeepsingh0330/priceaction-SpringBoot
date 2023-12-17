package com.ransankul.priceaction.payload;

public class UserPayload {

    private String username;
    private String phoneNumber;
    private String emailId;
    private int AddedApi;
    private int ActiveApi;

    public UserPayload() {
    }

    public UserPayload(String username, String phoneNumber, String emailId) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public UserPayload(String username, String phoneNumber, String emailId, int addedApi, int activeApi) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        AddedApi = addedApi;
        ActiveApi = activeApi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getAddedApi() {
        return AddedApi;
    }

    public void setAddedApi(int addedApi) {
        AddedApi = addedApi;
    }

    public int getActiveApi() {
        return ActiveApi;
    }

    public void setActiveApi(int activeApi) {
        ActiveApi = activeApi;
    }
}
