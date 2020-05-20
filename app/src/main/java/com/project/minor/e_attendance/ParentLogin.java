package com.project.minor.e_attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;

public class ParentLogin extends AppCompatActivity {

    Button attendance;
    Button notice;
    Button result;
    Button logout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        toolbar = findViewById(R.id.ptoolbar1);
        attendance = findViewById(R.id.viewAttendancep);
        notice = findViewById(R.id.viewNoticesp);
        result = findViewById(R.id.viewResultsp);
        logout = findViewById(R.id.logoutp);
    }
}
