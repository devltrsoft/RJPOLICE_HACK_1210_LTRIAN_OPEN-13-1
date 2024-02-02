package com.ltrsoft.rajashtanuserapplication.classes;

public class ComplaintType {
    String complaint_type_id,complaint_type_name;

    public ComplaintType(String complaint_type_id, String complaint_type_name) {
        this.complaint_type_id = complaint_type_id;
        this.complaint_type_name = complaint_type_name;
    }

    public String getComplaint_type_id() {
        return complaint_type_id;
    }

    public void setComplaint_type_id(String complaint_type_id) {
        this.complaint_type_id = complaint_type_id;
    }

    public String getComplaint_type_name() {
        return complaint_type_name;
    }

    public void setComplaint_type_name(String complaint_type_name) {
        this.complaint_type_name = complaint_type_name;
    }
}
