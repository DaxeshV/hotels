package com.example.daxesh.hotel.Model;

import java.util.List;

/**
 * Created by Daxesh on 11/13/2017.
 */

public class Request {

    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;

    private List<Order>foods; // list of food orders


    public  Request(String phone, String name, String address, String total, List<Order> foods){
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;

        this.foods = foods;
        this.status = "0";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Request(){


    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getTotal() {
        return total;
    }

    public List<Order> getFoods() {
        return foods;
    }
}
