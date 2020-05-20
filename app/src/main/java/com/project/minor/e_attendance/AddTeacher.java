package com.project.minor.e_attendance;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.minor.e_attendance.object.Teacher;

public class AddTeacher extends AppCompatActivity {

    EditText Tname;
    EditText Tid;
    EditText username;
    EditText tpassword;
    String tname;
    String tid;
    String bran;
    String uname;
    String tpass;
    String tidbranch;
    Spinner branch;
    DatabaseReference databaseTeacher;
    Toolbar mToolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        databaseTeacher = FirebaseDatabase.getInstance().getReference("Teacher");

        Tname = findViewById(R.id.tename);
        Tid = findViewById(R.id.tid);
        branch = findViewById(R.id.tspbranch);
        username = findViewById(R.id.tusername);
        tpassword = findViewById(R.id.tpass);
        mToolbar= findViewById(R.id.ftoolbar);
        tabLayout = findViewById(R.id.tabs2);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add/Remove Teacher");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.white));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));

        tabLayout.addTab(tabLayout.newTab().setText("ADD"));
        tabLayout.addTab(tabLayout.newTab().setText("REMOVE"));

        tidbranch = "FACIT";

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tidbranch = "FACIT";
                        break;
                    case 1:
                        tidbranch = "FACCS";
                        break;
                    case 2:
                        tidbranch = "FACME";
                        break;
                    case 3:
                        tidbranch = "FACCE";
                        break;
                }
                Tid.setText(tidbranch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void addTeacher(View v){
        tname = Tname.getText().toString();
        tid = Tid.getText().toString();
        bran = branch.getSelectedItem().toString();
        uname = username.getText().toString();

        try {
            tpass = EncDecUtil.encrypt(tpassword.getText().toString(), tid);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if ((!(TextUtils.isEmpty(Tid.getText().toString()))) || (!(TextUtils.isEmpty(Tname.getText().toString()))) || (!(TextUtils.isEmpty(username.getText().toString())))) {
            // String id = databaseTeacher.push().getKey();
            Teacher teacher =new Teacher(tname, tid , bran, uname, tpass);
            databaseTeacher.child(tid).setValue(teacher);
            Toast.makeText(getApplicationContext(),"Teacher added successfully", Toast.LENGTH_LONG).show();
            finish();

        }else {
            Toast.makeText(getApplicationContext(),"Fields cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    public void removeTeacher(View v){
        if (!TextUtils.isEmpty(Tid.getText().toString())) {
            tid = Tid.getText().toString();
            databaseTeacher.child(tid).setValue(null);
            Toast.makeText(getApplicationContext(),"Teacher removed successfully", Toast.LENGTH_LONG).show();
            finish();

        }else {
            Toast.makeText(getApplicationContext(),"id cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
