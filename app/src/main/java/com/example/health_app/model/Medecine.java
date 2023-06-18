package com.example.health_app.model;

public class Medecine {
//    String pname, String mname, String qty, String date, String nodays, String time
    String uname, name, mname, qty, date, nodays, time;

    public Medecine(String name, String mname, String qty, String date, String nodays, String time) {
        this.name = name;
        this.mname = mname;
        this.qty = qty;
        this.date = date;
        this.nodays = nodays;
        this.time = time;
    }

    public Medecine() {
    }

    public String getName() {
        return name;
    }

    public String getUname() {
        return uname;
    }

    public String getMname() {
        return mname;
    }

    public String getQty() {
        return qty;
    }

    public String getDate() {
        return date;
    }

    public String getNodays() {
        return nodays;
    }

    public String getTime() {
        return time;
    }

    //setters

    public void setName(String name) {
        this.name = name;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNodays(String nodays) {
        this.nodays = nodays;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
