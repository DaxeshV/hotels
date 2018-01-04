package com.example.daxesh.hotel.Model;

/**
 * Created by Daxesh on 10/28/2017.
 */

public class Category {

    private String Name;
    private String Image;

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setName(String name) {
        Name = name;
    }

    public  Category(){

    }
}
