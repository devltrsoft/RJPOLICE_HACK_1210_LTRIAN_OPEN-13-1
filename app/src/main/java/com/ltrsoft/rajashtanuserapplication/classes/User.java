package com.ltrsoft.rajashtanuserapplication.classes;



public class User {
    String user_fname,user_mname,user_lname,user_address,city_name,district_name,country_name,state_name,user_password,
            user_email,user_gender,user_dob,user_mobile1,user_adhar,photo,user_fcn_token,user_pan;

    public User(String user_fname, String user_password, String user_email, String user_mobile1, String user_fcn_token) {
        this.user_fname = user_fname;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_mobile1 = user_mobile1;
        this.user_fcn_token = user_fcn_token;
    }

    public User(String user_fname, String user_mname, String user_lname, String user_address, String city_name, String district_name, String country_name, String state_name, String user_password, String user_email, String user_gender, String user_dob, String user_mobile1, String user_adhar, String photo, String user_fcn_token, String user_pan) {
        this.user_fname = user_fname;
        this.user_mname = user_mname;
        this.user_lname = user_lname;
        this.user_address = user_address;
        this.city_name = city_name;
        this.district_name = district_name;
        this.country_name = country_name;
        this.state_name = state_name;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_gender = user_gender;
        this.user_dob = user_dob;
        this.user_mobile1 = user_mobile1;
        this.user_adhar = user_adhar;
        this.photo = photo;
        this.user_fcn_token = user_fcn_token;
        this.user_pan = user_pan;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUser_fcn_token() {
        return user_fcn_token;
    }

    public void setUser_fcn_token(String user_fcn_token) {
        this.user_fcn_token = user_fcn_token;
    }

    public String getUser_pan() {
        return user_pan;
    }

    public void setUser_pan(String user_pan) {
        this.user_pan = user_pan;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_mname() {
        return user_mname;
    }

    public void setUser_mname(String user_mname) {
        this.user_mname = user_mname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getUser_mobile1() {
        return user_mobile1;
    }

    public void setUser_mobile1(String user_mobile1) {
        this.user_mobile1 = user_mobile1;
    }

    public String getUser_adhar() {
        return user_adhar;
    }

    public void setUser_adhar(String user_adhar) {
        this.user_adhar = user_adhar;
    }
}
