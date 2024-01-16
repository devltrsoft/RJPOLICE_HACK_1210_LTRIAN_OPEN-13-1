package com.ltrsoft.rajashtanuserapplication.classes;
public class SuspectClass {
   public String  fname,mname,lname,  dob, gender, mobile, email, adharno, countyname, stname, distname, cityname,isSuspect,photourl,pan;
   public int id,suspectid,caseid;

    public SuspectClass(String fname, String mname, String lname, String dob,
                        String gender, String mobile, String email, String adharno,
                        String countyname, String stname, String distname, String cityname,
                        String isSuspect, String photourl,  int suspectid, int caseid) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.dob = dob;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.adharno = adharno;
        this.countyname = countyname;
        this.stname = stname;
        this.distname = distname;
        this.cityname = cityname;
        this.isSuspect = isSuspect;
        this.photourl = photourl;
        this.suspectid = suspectid;
        this.caseid = caseid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdharno() {
        return adharno;
    }

    public void setAdharno(String adharno) {
        this.adharno = adharno;
    }

    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public String getDistname() {
        return distname;
    }

    public void setDistname(String distname) {
        this.distname = distname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getIsSuspect() {
        return isSuspect;
    }

    public void setIsSuspect(String isSuspect) {
        this.isSuspect = isSuspect;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuspectid() {
        return suspectid;
    }

    public void setSuspectid(int suspectid) {
        this.suspectid = suspectid;
    }

    public int getCaseid() {
        return caseid;
    }

    public void setCaseid(int caseid) {
        this.caseid = caseid;
    }
}