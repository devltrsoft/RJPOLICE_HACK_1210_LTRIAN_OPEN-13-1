package com.ltrsoft.rajashtanuserapplication.classes;

public class EvidenceClass {
String Imagerl,id,description;

    public EvidenceClass(String imagerl, String id, String description) {
        this.Imagerl = imagerl;
        this.id = id;
        this.description = description;
    }
    public EvidenceClass(String imagerl, String description) {
        this.Imagerl = imagerl;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public EvidenceClass() {
//    }

    public String getImagerl() {
        return Imagerl;
    }

    public void setImagerl(String imagerl) {
        Imagerl = imagerl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
