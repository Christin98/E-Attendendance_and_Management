package com.project.minor.e_attendance.object;

/**
 * Created by christin on 2/9/2019.
 */

public class Student {
    private String sname;
    private String batch;
    private String branch;
    private String spass;
    private String sid;

    public Student(){

    }

    public Student(String sname, String batch, String branch, String spass, String sid) {
        this.sname = sname;
        this.batch = batch;
        this.branch = branch;
        this.spass = spass;
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSpass() {
        return spass;
    }

    public void setSpass(String spass) {
        this.spass = spass;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
