package com.example.gamer.supers;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Gamer on 2/3/2018.
 */

public class User {
    private String email;
    private String name;
    private String chanalid;
    private String apiread;
    private String image;
    private String id;
    private ArrayList<Sensors> sensors;


    public User(){}
    public User(String email,String name ,String image,String id){
        this.email=email;
        this.name=name;
        this.image=image;
        this.id=id;
    }
    public String getEmail() {
        return email;
    }

    public ArrayList<Sensors> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<Sensors> sensors) {
        this.sensors = sensors;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChanalid() {
        return chanalid;
    }

    public void setChanalid(String chanalid) {
        this.chanalid = chanalid;
    }

    public String getApiread() {
        return apiread;
    }

    public void setApiread(String apiread) {
        this.apiread = apiread;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", chanalid='" + chanalid + '\'' +
                ", apiread='" + apiread + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                ", sensors=" + sensors +
                '}';
    }
}
