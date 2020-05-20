package com.project.minor.e_attendance.object;

/**
 * Created by christin on 29/8/2019.
 */

public class Teacher {
    private String tname;
    private String tid;
    private String branch;
    private String username;
    private String tpass;

  /*  public Teacher(String tname, String tid, EditText subject, Spinner classes){

    }*/

    public Teacher() {

    }

    public Teacher(String tname, String tid, String branch, String username, String tpass) {
        this.tname = tname;
        this.tid = tid;
        this.branch = branch;
        this.username = username;
        this.tpass = tpass;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTpass() {
        return tpass;
    }

    public void setTpass(String tpass) {
        this.tpass = tpass;
    }
}
