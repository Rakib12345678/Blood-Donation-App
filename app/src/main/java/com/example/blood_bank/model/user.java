package com.example.blood_bank.model;

public class user
{
    String don_blood,don_id,don_ph,id,type,search,profilepictureurl,don_email,don_name;

    public user(String don_blood, String don_id, String don_ph, String id, String type, String search, String profilepictureurl, String don_email, String don_name) {
        this.don_blood = don_blood;
        this.don_id = don_id;
        this.don_ph = don_ph;
        this.id = id;
        this.type = type;
        this.search = search;
        this.profilepictureurl = profilepictureurl;
        this.don_email = don_email;
        this.don_name = don_name;
    }
public user()
{

}
    public String getDon_blood() {
        return don_blood;
    }

    public void setDon_blood(String don_blood) {
        this.don_blood = don_blood;
    }

    public String getDon_id() {
        return don_id;
    }

    public void setDon_id(String don_id) {
        this.don_id = don_id;
    }

    public String getDon_ph() {
        return don_ph;
    }

    public void setDon_ph(String don_ph) {
        this.don_ph = don_ph;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getProfilepictureurl() {
        return profilepictureurl;
    }

    public void setProfilepictureurl(String profilepictureurl) {
        this.profilepictureurl = profilepictureurl;
    }

    public String getDon_email() {
        return don_email;
    }

    public void setDon_email(String don_email) {
        this.don_email = don_email;
    }

    public String getDon_name() {
        return don_name;
    }

    public void setDon_name(String don_name) {
        this.don_name = don_name;
    }
}
