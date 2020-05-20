package com.project.minor.e_attendance.object;

public class Parent {
    private String pname;
    private String mno;
    private String sid;
    private String sname;
    private String parentpassword;
    private String classs;

    public Parent(String pname, String mno, String sid, String sname, String parentpassword, String classs) {
        this.pname = pname;
        this.mno = mno;
        this.sid = sid;
        this.sname = sname;
        this.parentpassword = parentpassword;
        this.classs = classs;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getParentpassword() {
        return parentpassword;
    }

    public void setParentpassword(String parentpassword) {
        this.parentpassword = parentpassword;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

}
