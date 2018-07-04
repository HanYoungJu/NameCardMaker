package com.example.q.NameCardMaker.models;

import android.graphics.Bitmap;

public class ModelContacts {
    private Bitmap photo;
    private String name, number, email;

    public ModelContacts(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getPhoto(){
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
