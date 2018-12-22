package com.labAdvance.carRental.models;

public class LoginUser {

    /**
     * Email:String
     * Password:String
     * StayLogged:boolean
     */
    private String email, password;
    private boolean stayLogged;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginUser(String email, String password, boolean stayLogged) {
        this.email = email;
        this.password = password;

        this.stayLogged = stayLogged;

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
    public boolean isStayLogged() {
        return stayLogged;
    }

    public void setStayLogged(boolean stayLogged) {
        this.stayLogged = stayLogged;
    }
}
