package com.jannat.cosmetica.Model;

import java.util.Map;

public class Users {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    // Constructors;
    public Users() {
    }

    public Users(String id, String username, String imageURL, String phone, String role , String route_name , String route_no,Map<String,Object> fine) {
        this.id = id;
        this.username = username;
    }

}
