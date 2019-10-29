package com.theapp.afshatquiz.Model;

public class User {
    private String Name,Image,Email,UserID;


    public User() {
    }

    public User(String name, String image, String email, String userID) {
        Name = name;
        Image = image;
        Email = email;
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
