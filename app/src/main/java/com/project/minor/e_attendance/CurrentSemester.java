package com.project.minor.e_attendance;

public class CurrentSemester {

    public static String getCurrSemesterShort(String batch) {
        String semester="";
        switch(batch) {
            case "2016-20":
                semester="Semester-8";
                break;
            case "2017-21":
                semester="Semester-6";
                break;
            case "2018-22":
                semester="Semester-4";
                break;
            case "2019-23":
                semester="Semester-2";
        }
        return semester;
    }
}
