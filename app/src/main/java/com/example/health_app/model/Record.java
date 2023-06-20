package com.example.health_app.model;

public class Record {

    String pname, bloodgroup, healthcondition, ht, wt, username;

    public Record(String pname, String bloodgroup, String healthcondition, String ht, String wt, String username) {
        this.pname = pname;
        this.bloodgroup = bloodgroup;
        this.healthcondition = healthcondition;
        this.ht = ht;
        this.wt = wt;
        this.username = username;
    }

    public Record() {
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getHealthcondition() {
        return healthcondition;
    }

    public void setHealthcondition(String healthcondition) {
        this.healthcondition = healthcondition;
    }

    public String getHt() {
        return ht;
    }

    public void setHt(String ht) {
        this.ht = ht;
    }

    public String getWt() {
        return wt;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
