package com.example.daxesh.hotel.Model;

/**
 * Created by Daxesh on 10/28/2017.
 */

public class User {

    private String Name;
    private String Password;
    private String Phone;


    public User(String name, String password) {
        Name = name;
        Password = password;

    }


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }
    public  User(){

    }
}

