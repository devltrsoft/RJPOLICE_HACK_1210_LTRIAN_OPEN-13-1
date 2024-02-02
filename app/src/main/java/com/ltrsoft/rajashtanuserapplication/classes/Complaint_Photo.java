package com.ltrsoft.rajashtanuserapplication.classes;

public class Complaint_Photo {

    private String complaint_photo_id ,complaint_photo_path,complaint_photo_description;

    public Complaint_Photo(String complaint_photo_id, String complaint_photo_path, String complaint_photo_description) {
        this.complaint_photo_id = complaint_photo_id;
        this.complaint_photo_path = complaint_photo_path;
        this.complaint_photo_description = complaint_photo_description;
    }

    public String getComplaint_photo_id() {
        return complaint_photo_id;
    }

    public void setComplaint_photo_id(String complaint_photo_id) {
        this.complaint_photo_id = complaint_photo_id;
    }

    public String getComplaint_photo_path() {
        return complaint_photo_path;
    }

    public void setComplaint_photo_path(String complaint_photo_path) {
        this.complaint_photo_path = complaint_photo_path;
    }

    public String getComplaint_photo_description() {
        return complaint_photo_description;
    }

    public void setComplaint_photo_description(String complaint_photo_description) {
        this.complaint_photo_description = complaint_photo_description;
    }
}
