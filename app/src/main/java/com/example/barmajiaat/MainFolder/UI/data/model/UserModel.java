package com.example.barmajiaat.MainFolder.UI.data.model;

public class UserModel {

    private String userName;
    private String email;
    private String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    // this line prevents the app from crashing if the user didn't input any data in the registration process, but in this case, it also prevents it from crashing if he had Inputted data too.
    public UserModel() {
    }

    public UserModel(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;


    }
}
