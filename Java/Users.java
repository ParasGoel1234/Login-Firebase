package com.codewithparas.whatsapp.Model;

public class Users {
    private String Id;
    private String UserName;
    private String imageURL;

    //constructor



    public Users(String id, String userName, String imageURL) {
        Id = id;
        UserName = userName;
        this.imageURL = imageURL;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
