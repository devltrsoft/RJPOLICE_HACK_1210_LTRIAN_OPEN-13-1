package com.ltrsoft.rajashtanuserapplication.classes;

public class Camera {

    String user_id,photo_path,station_id,description,address;


    public Camera(String user_id, String photo_path, String station_id, String description, String address) {
        this.user_id = user_id;
        this.photo_path = photo_path;
        this.station_id = station_id;
        this.description = description;
        this.address = address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
