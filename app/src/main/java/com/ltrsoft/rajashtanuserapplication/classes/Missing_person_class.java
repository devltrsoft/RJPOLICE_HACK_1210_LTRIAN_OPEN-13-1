package com.ltrsoft.rajashtanuserapplication.classes;
public class Missing_person_class {
    int person_image;
    String person_name,person_description, person_contact,person_location ;

    public Missing_person_class(int person_image, String person_name, String person_description, String person_contact, String person_location) {
        this.person_image = person_image;
        this.person_name = person_name;
        this.person_description = person_description;
        this.person_contact = person_contact;
        this.person_location = person_location;
    }

    public Missing_person_class() {
    }

    public int getPerson_image() {
        return person_image;
    }

    public void setPerson_image(int person_image) {
        this.person_image = person_image;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_description() {
        return person_description;
    }

    public void setPerson_description(String person_description) {
        this.person_description = person_description;
    }

    public String getPerson_contact() {
        return person_contact;
    }

    public void setPerson_contact(String person_contact) {
        this.person_contact = person_contact;
    }

    public String getPerson_location() {
        return person_location;
    }

    public void setPerson_location(String person_location) {
        this.person_location = person_location;
    }
}