package com.ltrsoft.rajashtanuserapplication.classes;

public class VictimClass {

    String cmp_id,complaint_victim_fname,complaint_victim_mname,complaint_victim_lname,address,gender,aadhar,photo,dob,mobile,
            state_name,district_name,country_name,city_name,isVictim,photoUrl;

    public VictimClass(String cmp_id, String complaint_victim_fname, String complaint_victim_mname, String complaint_victim_lname, String address, String gender, String aadhar, String photo, String dob, String mobile, String state_name, String district_name, String country_name) {
        this.cmp_id = cmp_id;
        this.complaint_victim_fname = complaint_victim_fname;
        this.complaint_victim_mname = complaint_victim_mname;
        this.complaint_victim_lname = complaint_victim_lname;
        this.address = address;
        this.gender = gender;
        this.aadhar = aadhar;
        this.photo = photo;
        this.dob = dob;
        this.mobile = mobile;
        this.state_name = state_name;
        this.district_name = district_name;
        this.country_name = country_name;
        this.city_name = city_name;
    }

//    public VictimClass(String cmp_id, String complaint_victim_fname,
//                       String complaint_victim_mname, String complaint_victim_lname,
//                       String address, String gender, String aadhar, String photo,
//                       String dob, String mobile, String state_name, String district_name,
//                       String country_name,
//                       String city_name, String isVictim, String photoUrl) {
//        this.cmp_id = cmp_id;
//        this.complaint_victim_fname = complaint_victim_fname;
//        this.complaint_victim_mname = complaint_victim_mname;
//        this.complaint_victim_lname = complaint_victim_lname;
//        this.address = address;
//        this.gender = gender;
//        this.aadhar = aadhar;
//        this.photo = photo;
//        this.dob = dob;
//        this.mobile = mobile;
//        this.state_name = state_name;
//        this.district_name = district_name;
//        this.country_name = country_name;
//        this.city_name = city_name;
//        this.isVictim = isVictim;
//        this.photoUrl = photoUrl;
//    }

    public String getIsVictim() {
        return isVictim;
    }

    public void setIsVictim(String isVictim) {
        this.isVictim = isVictim;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCmp_id() {
        return cmp_id;
    }

    public void setCmp_id(String cmp_id) {
        this.cmp_id = cmp_id;
    }

    public String getComplaint_victim_fname() {
        return complaint_victim_fname;
    }

    public void setComplaint_victim_fname(String complaint_victim_fname) {
        this.complaint_victim_fname = complaint_victim_fname;
    }

    public String getComplaint_victim_mname() {
        return complaint_victim_mname;
    }

    public void setComplaint_victim_mname(String complaint_victim_mname) {
        this.complaint_victim_mname = complaint_victim_mname;
    }

    public String getComplaint_victim_lname() {
        return complaint_victim_lname;
    }

    public void setComplaint_victim_lname(String complaint_victim_lname) {
        this.complaint_victim_lname = complaint_victim_lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
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

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
