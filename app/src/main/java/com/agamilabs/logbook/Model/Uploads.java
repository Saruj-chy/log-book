package com.agamilabs.logbook.Model;

public class Uploads {
    String name,profile_image, userid ;
    String type, time,  message,   check ;
    String attachment_url ;

    public Uploads() {

    }

    public Uploads(String name, String profile_image, String userid, String type, String time, String message, String check, String attachment_url) {
        this.name = name;
        this.profile_image = profile_image;
        this.userid = userid;
        this.type = type;
        this.time = time;
        this.message = message;
        this.check = check;
        this.attachment_url = attachment_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getAttachment_url() {
        return attachment_url;
    }

    public void setAttachment_url(String attachment_url) {
        this.attachment_url = attachment_url;
    }
}