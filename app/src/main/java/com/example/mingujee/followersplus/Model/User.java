package com.example.mingujee.followersplus.Model;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by mingu.jee on 2016-08-01.
 */
public class User extends Observable implements Serializable{
    private String id;
    private String username;
    private String url;
    private String access_token;
    private String fullname;

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public void setProfileURL (String URL) {this.url = URL;}

    public String getProfileURL () {return url;}

    public void setFullName (String fullname) {this.fullname = fullname;}

    public String getFullName () {return fullname;}

    public void setUserName(String username) {
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getAccessToken() {
        return access_token;
    }
}
