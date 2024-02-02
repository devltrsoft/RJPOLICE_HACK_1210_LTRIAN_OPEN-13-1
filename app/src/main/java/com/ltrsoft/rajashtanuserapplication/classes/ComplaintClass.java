package com.ltrsoft.rajashtanuserapplication.classes;

public class ComplaintClass {
        private String fir_id,complaint_type_name,complaintdescription,status_name,complaint_against,station_id,user_id,latitude,longitude
    ,suspect_fname,suspect_mname,suspect_lname,suspect_address,suspect_gender,suspect_mobile_no,suspect_photo,
            investigation_witness_fname,investigation_witness_mname,complaintORfir_name,investigation_witness_lname,
            investigation_witness_address,investigation_witness_dob,investigation_witness_gender,
            investigation_witness_mobile,investigation_witness_photo,victim_fname,victim_mname,victim_lname,
             victim_address,victim_gender,victim_mobile_no,victim_photo,complaint_type_id;
    private String complaint_id,complaint_subject,complaint_description,incident_date,complaint_location,user_address,cmpid;

    public ComplaintClass(String complaint_against, String station_id, String latitude, String longitude, String complaint_subject, String complaint_description,
                          String incident_date,  String user_address,String userid,String complaint_type_id,String sample) {
        this.complaint_against = complaint_against;
        this.station_id = station_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.complaint_subject = complaint_subject;
        this.complaint_description = complaint_description;
        this.incident_date = incident_date;
        this.user_address = user_address;
        this.user_id = userid;
        this.complaint_type_id = complaint_type_id;
    }
    public ComplaintClass(String complaint_type_name, String status_name, String complaint_against, String complaint_id, String complaint_subject, String complaint_description, String incident_date, String complaint_location, String user_address, String cmpid) {
        this.complaint_type_name = complaint_type_name;
        this.status_name = status_name;
        this.complaint_against = complaint_against;
        this.complaint_id = complaint_id;
        this.complaint_subject = complaint_subject;
        this.complaint_description = complaint_description;
        this.incident_date = incident_date;
        this.complaint_location = complaint_location;
        this.user_address = user_address;
        this.cmpid = cmpid;
    }

    public String getFir_id() {
        return fir_id;
    }

    public String getCmpid() {
        return cmpid;
    }

    public void setCmpid(String cmpid) {
        this.cmpid = cmpid;
    }

    public void setFir_id(String fir_id) {
        this.fir_id = fir_id;
    }

    public String getComplaint_type_name() {
        return complaint_type_name;
    }

    public void setComplaint_type_name(String complaint_type_name) {
        this.complaint_type_name = complaint_type_name;
    }

    public String getComplaintdescription() {
        return complaintdescription;
    }

    public void setComplaintdescription(String complaintdescription) {
        this.complaintdescription = complaintdescription;
    }

    public String getComplaint_against() {
        return complaint_against;
    }

    public void setComplaint_against(String complaint_against) {
        this.complaint_against = complaint_against;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSuspect_fname() {
        return suspect_fname;
    }

    public void setSuspect_fname(String suspect_fname) {
        this.suspect_fname = suspect_fname;
    }

    public String getSuspect_mname() {
        return suspect_mname;
    }

    public void setSuspect_mname(String suspect_mname) {
        this.suspect_mname = suspect_mname;
    }

    public String getSuspect_lname() {
        return suspect_lname;
    }

    public void setSuspect_lname(String suspect_lname) {
        this.suspect_lname = suspect_lname;
    }

    public String getSuspect_address() {
        return suspect_address;
    }

    public void setSuspect_address(String suspect_address) {
        this.suspect_address = suspect_address;
    }

    public String getSuspect_gender() {
        return suspect_gender;
    }

    public void setSuspect_gender(String suspect_gender) {
        this.suspect_gender = suspect_gender;
    }

    public String getSuspect_mobile_no() {
        return suspect_mobile_no;
    }

    public void setSuspect_mobile_no(String suspect_mobile_no) {
        this.suspect_mobile_no = suspect_mobile_no;
    }

    public String getSuspect_photo() {
        return suspect_photo;
    }

    public void setSuspect_photo(String suspect_photo) {
        this.suspect_photo = suspect_photo;
    }

    public String getInvestigation_witness_fname() {
        return investigation_witness_fname;
    }

    public void setInvestigation_witness_fname(String investigation_witness_fname) {
        this.investigation_witness_fname = investigation_witness_fname;
    }

    public String getInvestigation_witness_mname() {
        return investigation_witness_mname;
    }

    public void setInvestigation_witness_mname(String investigation_witness_mname) {
        this.investigation_witness_mname = investigation_witness_mname;
    }

    public String getComplaintORfir_name() {
        return complaintORfir_name;
    }

    public void setComplaintORfir_name(String complaintORfir_name) {
        this.complaintORfir_name = complaintORfir_name;
    }

    public String getInvestigation_witness_lname() {
        return investigation_witness_lname;
    }

    public void setInvestigation_witness_lname(String investigation_witness_lname) {
        this.investigation_witness_lname = investigation_witness_lname;
    }

    public String getInvestigation_witness_address() {
        return investigation_witness_address;
    }

    public void setInvestigation_witness_address(String investigation_witness_address) {
        this.investigation_witness_address = investigation_witness_address;
    }

    public String getInvestigation_witness_dob() {
        return investigation_witness_dob;
    }

    public void setInvestigation_witness_dob(String investigation_witness_dob) {
        this.investigation_witness_dob = investigation_witness_dob;
    }

    public String getInvestigation_witness_gender() {
        return investigation_witness_gender;
    }

    public void setInvestigation_witness_gender(String investigation_witness_gender) {
        this.investigation_witness_gender = investigation_witness_gender;
    }

    public String getInvestigation_witness_mobile() {
        return investigation_witness_mobile;
    }

    public void setInvestigation_witness_mobile(String investigation_witness_mobile) {
        this.investigation_witness_mobile = investigation_witness_mobile;
    }

    public String getInvestigation_witness_photo() {
        return investigation_witness_photo;
    }

    public void setInvestigation_witness_photo(String investigation_witness_photo) {
        this.investigation_witness_photo = investigation_witness_photo;
    }

    public String getVictim_fname() {
        return victim_fname;
    }

    public void setVictim_fname(String victim_fname) {
        this.victim_fname = victim_fname;
    }

    public String getVictim_mname() {
        return victim_mname;
    }

    public void setVictim_mname(String victim_mname) {
        this.victim_mname = victim_mname;
    }

    public String getVictim_lname() {
        return victim_lname;
    }

    public void setVictim_lname(String victim_lname) {
        this.victim_lname = victim_lname;
    }

    public String getVictim_address() {
        return victim_address;
    }

    public void setVictim_address(String victim_address) {
        this.victim_address = victim_address;
    }

    public String getVictim_gender() {
        return victim_gender;
    }

    public void setVictim_gender(String victim_gender) {
        this.victim_gender = victim_gender;
    }

    public String getVictim_mobile_no() {
        return victim_mobile_no;
    }

    public void setVictim_mobile_no(String victim_mobile_no) {
        this.victim_mobile_no = victim_mobile_no;
    }

    public String getVictim_photo() {
        return victim_photo;
    }

    public void setVictim_photo(String victim_photo) {
        this.victim_photo = victim_photo;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getComplaint_subject() {
        return complaint_subject;
    }

    public void setComplaint_subject(String complaint_subject) {
        this.complaint_subject = complaint_subject;
    }

    public String getComplaint_description() {
        return complaint_description;
    }

    public void setComplaint_description(String complaint_description) {
        this.complaint_description = complaint_description;
    }

    public String getIncident_date() {
        return incident_date;
    }

    public void setIncident_date(String incident_date) {
        this.incident_date = incident_date;
    }

    public String getComplaint_location() {
        return complaint_location;
    }

    public void setComplaint_location(String complaint_location) {
        this.complaint_location = complaint_location;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
}
}
