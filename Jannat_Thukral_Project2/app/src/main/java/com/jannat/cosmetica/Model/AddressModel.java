package com.jannat.cosmetica.Model;

public class AddressModel {
    private String address;
    private String city;
    private String country;
    private String email;
    private String full_name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    private String phone;
    private String state;
    private String zip;

    public AddressModel (){};
    public AddressModel(String address, String city, String country, String email, String full_name, String phone, String state, String zip){
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.full_name = full_name;
        this.phone = phone;
        this.state = state;
        this.zip = zip;
    };

}
