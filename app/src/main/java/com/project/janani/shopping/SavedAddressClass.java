package com.project.janani.shopping;

public class SavedAddressClass {
    String userName, userAddress, userPinCode, userCity, userState, userPhoneNumber;

    public SavedAddressClass(String userName, String userAddress, String userPinCode, String userCity, String userState, String userPhoneNumber) {
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhoneNumber = userPhoneNumber;
        this.userPinCode = userPinCode;
        this.userCity = userCity;
        this.userState = userState;
    }

    public String getUserPinCode() {
        return userPinCode;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserState() {
        return userState;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }
}
