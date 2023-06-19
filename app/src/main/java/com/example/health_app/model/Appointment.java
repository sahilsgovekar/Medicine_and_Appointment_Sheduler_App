package com.example.health_app.model;

public class Appointment {
    String pname, dhname, desc, date;

    public Appointment(String pname, String dhname, String desc, String date) {
        this.pname = pname;
        this.dhname = dhname;
        this.desc = desc;
        this.date = date;
    }

    public Appointment() {
    }

    public String getPname() {
        return pname;
    }

    public String getDhname() {
        return dhname;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setDhname(String dhname) {
        this.dhname = dhname;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
