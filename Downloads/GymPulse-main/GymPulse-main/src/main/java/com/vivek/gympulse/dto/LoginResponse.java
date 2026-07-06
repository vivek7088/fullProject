package com.vivek.gympulse.dto;

public class LoginResponse {

    private String token;
    private Object owner;

    public LoginResponse() {
    }

    public LoginResponse(String token, Object owner) {
        this.token = token;
        this.owner = owner;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }
}