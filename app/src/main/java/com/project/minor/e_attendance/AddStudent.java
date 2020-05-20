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
import com.project.minor.e_attendance.object.Attendance;
import com.project.minor.e_attendance.object.Student;

public class AddStudent extends AppCompatActivity {

    EditText Sname;
    EditText Sid, spassword;
    String sname;
    String sid;
    String batchs;
    String branchs;
    String spass;
    Spinner branch, batch;
    DatabaseReference databaseStudent;
    DatabaseReference attendanceDB;
    Toolbar mToolbar;
    TabLayout tabLayout;
    String studentidbatch;
    String studentidbranch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");

        Sname = findViewById(R.id.editText1);
        Sid = findViewById(R.id.editText3);
        branch = findViewById(R.id.branch);
        batch = findViewById(R.id.batch);
        spassword = findViewById(R.id.editText4);
        mToolbar= findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add/Remove Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tabs);

        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.white));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));

        tabLayout.addTab(tabLayout.newTab().setText("ADD"));
        tabLayout.addTab(tabLayout.newTab().setText("REMOVE"));


       /* addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                addTeacher();
            }
        });*/
        studentidbatch = "16ERE";
        studentidbranch = "IT";

        batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        studentidbatch="16ERE";
                        break;
                    case 1:
                        studentidbatch="17ERE";
                        break;
                    case 2:
                        studentidbatch="18ERE";
                        break;
                    case 3:
                        studentidbatch="19ERE";
                        break;
                }
                setUname();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentidbranch = returnShortBranch(branch.getSelectedItem().toString());
                setUname();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addStudent(View v){


        if ((!(TextUtils.isEmpty(Sname.getText().toString()))) || (!(TextUtils.isEmpty(spassword.getText().toString())))) {
            //String id = databaseStudent.push().getKey();
            sname = Sname.getText().toString();
            sid = Sid.getText().toString();
            batchs = batch.getSelectedItem().toString();
            branchs = branch.getSelectedItem().toString();

            try {
                spass = EncDecUtil.encrypt(spassword.getText().toString(), sid);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Student student =new Student(sname , batchs, branchs, spass, sid );
            databaseStudent.child(sid).setValue(student);

            //ATTENDANCE INITIALIZATION
            attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(branch.getSelectedItem().toString()).child(sid);
            //For Information Technology
            if(branch.getSelectedItem().toString().equals("Information Technology")) {
                //SEMESTER-1
                attendanceDB.child("Semester-1").child("EM-1").setValue(new Attendance("EM-1"));
                attendanceDB.child("Semester-1").child("BME").setValue(new Attendance("BME"));
                attendanceDB.child("Semester-1").child("ECE").setValue(new Attendance("ECE"));
                attendanceDB.child("Semester-1").child("Physics").setValue(new Attendance("Physics"));
                attendanceDB.child("Semester-1").child("CP").setValue(new Attendance("CP"));
                attendanceDB.child("Semester-1").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-1").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-1").child("SemPercentage").setValue((float) 0);
                //SEMESTER-2
                attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                attendanceDB.child("Semester-2").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-2").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-2").child("SemPercentage").setValue((float) 0);
                //SEMESTER-3
                attendanceDB.child("Semester-3").child("DS").setValue(new Attendance("DS"));
                attendanceDB.child("Semester-3").child("PP").setValue(new Attendance("PP"));
                attendanceDB.child("Semester-3").child("FDS").setValue(new Attendance("FDS"));
                attendanceDB.child("Semester-3").child("CO").setValue(new Attendance("CO"));
                attendanceDB.child("Semester-3").child("DEL").setValue(new Attendance("DEL"));
                attendanceDB.child("Semester-3").child("LA").setValue(new Attendance("LA"));
                attendanceDB.child("Semester-3").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-3").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-3").child("SemPercentage").setValue((float) 0);
                //SEMESTER-4
                attendanceDB.child("Semester-4").child("AM").setValue(new Attendance("AM"));
                attendanceDB.child("Semester-4").child("OS").setValue(new Attendance("OS"));
                attendanceDB.child("Semester-4").child("JWT").setValue(new Attendance("JWT"));
                attendanceDB.child("Semester-4").child("SE").setValue(new Attendance("SE"));
                attendanceDB.child("Semester-4").child("DS").setValue(new Attendance("DS"));
                attendanceDB.child("Semester-4").child("MT").setValue(new Attendance("MT"));
                attendanceDB.child("Semester-4").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-4").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-4").child("SemPercentage").setValue((float) 0);
                //SEMESTER-5
                attendanceDB.child("Semester-5").child("DBMS").setValue(new Attendance("DBMS"));
                attendanceDB.child("Semester-5").child("TOC").setValue(new Attendance("TOC"));
                attendanceDB.child("Semester-5").child("CN").setValue(new Attendance("CN"));
                attendanceDB.child("Semester-5").child("IT").setValue(new Attendance("IT"));
                attendanceDB.child("Semester-5").child("OR").setValue(new Attendance("OR"));
                attendanceDB.child("Semester-5").child("BAF").setValue(new Attendance("BAF"));
                attendanceDB.child("Semester-5").child("QR").setValue(new Attendance("QR"));
                attendanceDB.child("Semester-5").child("ES").setValue(new Attendance("ES"));
                attendanceDB.child("Semester-5").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-5").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-5").child("SemPercentage").setValue((float) 0);
                //SEMESTER-6
                attendanceDB.child("Semester-6").child("DSP").setValue(new Attendance("DSP"));
                attendanceDB.child("Semester-6").child("BI").setValue(new Attendance("BI"));
                attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                attendanceDB.child("Semester-6").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-6").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-6").child("SemPercentage").setValue((float) 0);
                //SEMESTER-7
                attendanceDB.child("Semester-7").child("DSRM").setValue(new Attendance("DSRM"));
                attendanceDB.child("Semester-7").child("SP").setValue(new Attendance("SP"));
                attendanceDB.child("Semester-7").child("OOAD").setValue(new Attendance("OOAD"));
                attendanceDB.child("Semester-7").child("MC").setValue(new Attendance("MC"));
                attendanceDB.child("Semester-7").child("CGM").setValue(new Attendance("CGM"));
                attendanceDB.child("Semester-7").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-7").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-7").child("SemPercentage").setValue((float) 0);
                //SEMESTER-8
                attendanceDB.child("Semester-8").child("SOA").setValue(new Attendance("SOA"));
                attendanceDB.child("Semester-8").child("ADBMS").setValue(new Attendance("ADBMS"));
                attendanceDB.child("Semester-8").child("STQA").setValue(new Attendance("STQA"));
                attendanceDB.child("Semester-8").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-8").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-8").child("SemPercentage").setValue((float) 0);
            }
            //For Computer Science
            else if(branch.getSelectedItem().toString().equals("Computer Science")) {
                //SEMESTER-1
                attendanceDB.child("Semester-1").child("EM-1").setValue(new Attendance("EM-1"));
                attendanceDB.child("Semester-1").child("BME").setValue(new Attendance("BME"));
                attendanceDB.child("Semester-1").child("ECE").setValue(new Attendance("ECE"));
                attendanceDB.child("Semester-1").child("Physics").setValue(new Attendance("Physics"));
                attendanceDB.child("Semester-1").child("CP").setValue(new Attendance("CP"));
                attendanceDB.child("Semester-1").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-1").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-1").child("SemPercentage").setValue((float) 0);
                //SEMESTER-2
                attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                attendanceDB.child("Semester-2").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-2").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-2").child("SemPercentage").setValue((float) 0);
                //SEMESTER-3
                attendanceDB.child("Semester-3").child("DS").setValue(new Attendance("DS"));
                attendanceDB.child("Semester-3").child("PP").setValue(new Attendance("PP"));
                attendanceDB.child("Semester-3").child("FDS").setValue(new Attendance("FDS"));
                attendanceDB.child("Semester-3").child("CO").setValue(new Attendance("CO"));
                attendanceDB.child("Semester-3").child("DEL").setValue(new Attendance("DEL"));
                attendanceDB.child("Semester-3").child("LA").setValue(new Attendance("LA"));
                attendanceDB.child("Semester-3").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-3").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-3").child("SemPercentage").setValue((float) 0);
                //SEMESTER-4
                attendanceDB.child("Semester-4").child("AM").setValue(new Attendance("AM"));
                attendanceDB.child("Semester-4").child("OS").setValue(new Attendance("OS"));
                attendanceDB.child("Semester-4").child("JWT").setValue(new Attendance("JWT"));
                attendanceDB.child("Semester-4").child("SE").setValue(new Attendance("SE"));
                attendanceDB.child("Semester-4").child("DS").setValue(new Attendance("DS"));
                attendanceDB.child("Semester-4").child("MT").setValue(new Attendance("MT"));
                attendanceDB.child("Semester-4").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-4").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-4").child("SemPercentage").setValue((float) 0);
                //SEMESTER-5
                attendanceDB.child("Semester-5").child("DBMS").setValue(new Attendance("DBMS"));
                attendanceDB.child("Semester-5").child("TOC").setValue(new Attendance("TOC"));
                attendanceDB.child("Semester-5").child("CN").setValue(new Attendance("CN"));
                attendanceDB.child("Semester-5").child("IT").setValue(new Attendance("IT"));
                attendanceDB.child("Semester-5").child("OR").setValue(new Attendance("OR"));
                attendanceDB.child("Semester-5").child("DAA").setValue(new Attendance("DAA"));
                attendanceDB.child("Semester-5").child("BAF").setValue(new Attendance("BAF"));
                attendanceDB.child("Semester-5").child("QR").setValue(new Attendance("QR"));
                attendanceDB.child("Semester-5").child("ES").setValue(new Attendance("ES"));
                attendanceDB.child("Semester-5").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-5").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-5").child("SemPercentage").setValue((float) 0);
                //SEMESTER-6
                attendanceDB.child("Semester-6").child("DSP").setValue(new Attendance("DSP"));
                attendanceDB.child("Semester-6").child("BI").setValue(new Attendance("BI"));
                attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                attendanceDB.child("Semester-6").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-6").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-6").child("SemPercentage").setValue((float) 0);
                //SEMESTER-7
                attendanceDB.child("Semester-7").child("DSRM").setValue(new Attendance("DSRM"));
                attendanceDB.child("Semester-7").child("SP").setValue(new Attendance("SP"));
                attendanceDB.child("Semester-7").child("OOAD").setValue(new Attendance("OOAD"));
                attendanceDB.child("Semester-7").child("MC").setValue(new Attendance("MC"));
                attendanceDB.child("Semester-7").child("NS").setValue(new Attendance("NS"));
                attendanceDB.child("Semester-7").child("CGM").setValue(new Attendance("CGM"));
                attendanceDB.child("Semester-7").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-7").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-7").child("SemPercentage").setValue((float) 0);
                //SEMESTER-8
                attendanceDB.child("Semester-8").child("CC").setValue(new Attendance("CC"));
                attendanceDB.child("Semester-8").child("ADBMS").setValue(new Attendance("ADBMS"));
                attendanceDB.child("Semester-8").child("STQA").setValue(new Attendance("STQA"));
                attendanceDB.child("Semester-8").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-8").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-8").child("SemPercentage").setValue((float) 0);
            }
            //For Mechanical
            else if(branch.getSelectedItem().toString().equals("Mechanical")) {
                //SEMESTER-1
                attendanceDB.child("Semester-1").child("EM-1").setValue(new Attendance("EM-1"));
                attendanceDB.child("Semester-1").child("BME").setValue(new Attendance("BME"));
                attendanceDB.child("Semester-1").child("ECE").setValue(new Attendance("ECE"));
                attendanceDB.child("Semester-1").child("Physics").setValue(new Attendance("Physics"));
                attendanceDB.child("Semester-1").child("CP").setValue(new Attendance("CP"));
                attendanceDB.child("Semester-1").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-1").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-1").child("SemPercentage").setValue((float) 0);
                //SEMESTER-2
                attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                attendanceDB.child("Semester-2").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-2").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-2").child("SemPercentage").setValue((float) 0);
                //SEMESTER-3
                attendanceDB.child("Semester-3").child("AM").setValue(new Attendance("AM"));
                attendanceDB.child("Semester-3").child("SOM").setValue(new Attendance("SOM"));
                attendanceDB.child("Semester-3").child("MAM").setValue(new Attendance("MAM"));
                attendanceDB.child("Semester-3").child("TOM-1").setValue(new Attendance("TOM-1"));
                attendanceDB.child("Semester-3").child("MT").setValue(new Attendance("MT"));
                attendanceDB.child("Semester-3").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-3").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-3").child("SemPercentage").setValue((float) 0);
                //SEMESTER-4
                attendanceDB.child("Semester-4").child("FM").setValue(new Attendance("FM"));
                attendanceDB.child("Semester-4").child("MD-1").setValue(new Attendance("MD-1"));
                attendanceDB.child("Semester-4").child("HT").setValue(new Attendance("HT"));
                attendanceDB.child("Semester-4").child("MS").setValue(new Attendance("MS"));
                attendanceDB.child("Semester-4").child("TOM-2").setValue(new Attendance("TOM-2"));
                attendanceDB.child("Semester-4").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-4").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-4").child("SemPercentage").setValue((float) 0);
                //SEMESTER-5
                attendanceDB.child("Semester-5").child("ICE").setValue(new Attendance("ICE"));
                attendanceDB.child("Semester-5").child("PM").setValue(new Attendance("PM"));
                attendanceDB.child("Semester-5").child("MD-2").setValue(new Attendance("MD-2"));
                attendanceDB.child("Semester-5").child("CDCM").setValue(new Attendance("CDCM"));
                attendanceDB.child("Semester-5").child("FM").setValue(new Attendance("FM"));
                attendanceDB.child("Semester-5").child("MT").setValue(new Attendance("MT"));
                attendanceDB.child("Semester-5").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-5").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-5").child("SemPercentage").setValue((float) 0);
                //SEMESTER-6
                attendanceDB.child("Semester-6").child("NCPP").setValue(new Attendance("NCPP"));
                attendanceDB.child("Semester-6").child("PVD").setValue(new Attendance("PVD"));
                attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                attendanceDB.child("Semester-6").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-6").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-6").child("SemPercentage").setValue((float) 0);
                //SEMESTER-7
                attendanceDB.child("Semester-7").child("OR").setValue(new Attendance("OR"));
                attendanceDB.child("Semester-7").child("TQM").setValue(new Attendance("TQM"));
                attendanceDB.child("Semester-7").child("ES").setValue(new Attendance("ES"));
                attendanceDB.child("Semester-7").child("MSD").setValue(new Attendance("MSD"));
                attendanceDB.child("Semester-7").child("LA").setValue(new Attendance("LA"));
                attendanceDB.child("Semester-7").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-7").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-7").child("SemPercentage").setValue((float) 0);
                //SEMESTER-8
                attendanceDB.child("Semester-8").child("FEM").setValue(new Attendance("FEM"));
                attendanceDB.child("Semester-8").child("RAC").setValue(new Attendance("RAC"));
                attendanceDB.child("Semester-8").child("IAR").setValue(new Attendance("IAR"));
                attendanceDB.child("Semester-8").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-8").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-8").child("SemPercentage").setValue((float) 0);
            }
            //For Civil
            else if(branch.getSelectedItem().toString().equals("Civil")) {
                //SEMESTER-1
                attendanceDB.child("Semester-1").child("EM-1").setValue(new Attendance("EM-1"));
                attendanceDB.child("Semester-1").child("BME").setValue(new Attendance("BME"));
                attendanceDB.child("Semester-1").child("ECE").setValue(new Attendance("ECE"));
                attendanceDB.child("Semester-1").child("Physics").setValue(new Attendance("Physics"));
                attendanceDB.child("Semester-1").child("CP").setValue(new Attendance("CP"));
                attendanceDB.child("Semester-1").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-1").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-1").child("SemPercentage").setValue((float) 0);
                //SEMESTER-2
                attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                attendanceDB.child("Semester-2").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-2").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-2").child("SemPercentage").setValue((float) 0);
                //SEMESTER-3
                attendanceDB.child("Semester-3").child("SM").setValue(new Attendance("SM"));
                attendanceDB.child("Semester-3").child("FM-1").setValue(new Attendance("FM-1"));
                attendanceDB.child("Semester-3").child("CT").setValue(new Attendance("CT"));
                attendanceDB.child("Semester-3").child("BCM").setValue(new Attendance("BCM"));
                attendanceDB.child("Semester-3").child("EG").setValue(new Attendance("EG"));
                attendanceDB.child("Semester-3").child("LA").setValue(new Attendance("LA"));
                attendanceDB.child("Semester-3").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-3").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-3").child("SemPercentage").setValue((float) 0);
                //SEMESTER-4
                attendanceDB.child("Semester-4").child("AM").setValue(new Attendance("AM"));
                attendanceDB.child("Semester-4").child("SA-1").setValue(new Attendance("SA-1"));
                attendanceDB.child("Semester-4").child("BDD").setValue(new Attendance("BDD"));
                attendanceDB.child("Semester-4").child("FM-2").setValue(new Attendance("FM-2"));
                attendanceDB.child("Semester-4").child("SV-1").setValue(new Attendance("SV-1"));
                attendanceDB.child("Semester-4").child("ES").setValue(new Attendance("ES"));
                attendanceDB.child("Semester-4").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-4").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-4").child("SemPercentage").setValue((float) 0);
                //SEMESTER-5
                attendanceDB.child("Semester-5").child("SA-2").setValue(new Attendance("SA-2"));
                attendanceDB.child("Semester-5").child("TE-1").setValue(new Attendance("TE-1"));
                attendanceDB.child("Semester-5").child("QSV").setValue(new Attendance("QSV"));
                attendanceDB.child("Semester-5").child("EE-1").setValue(new Attendance("EE-1"));
                attendanceDB.child("Semester-5").child("SD-1").setValue(new Attendance("SD-1"));
                attendanceDB.child("Semester-5").child("SV-2").setValue(new Attendance("SV-2"));
                attendanceDB.child("Semester-5").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-5").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-5").child("SemPercentage").setValue((float) 0);
                //SEMESTER-6
                attendanceDB.child("Semester-6").child("WPE").setValue(new Attendance("WPE"));
                attendanceDB.child("Semester-6").child("ES").setValue(new Attendance("ES"));
                attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                attendanceDB.child("Semester-6").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-6").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-6").child("SemPercentage").setValue((float) 0);
                //SEMESTER-7
                attendanceDB.child("Semester-7").child("EE-2").setValue(new Attendance("EE-2"));
                attendanceDB.child("Semester-7").child("GE-1").setValue(new Attendance("GE-1"));
                attendanceDB.child("Semester-7").child("CTM").setValue(new Attendance("CTM"));
                attendanceDB.child("Semester-7").child("SD-2").setValue(new Attendance("SD-2"));
                attendanceDB.child("Semester-7").child("TE-2").setValue(new Attendance("TE-2"));
                attendanceDB.child("Semester-7").child("WRE").setValue(new Attendance("WRE"));
                attendanceDB.child("Semester-7").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-7").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-7").child("SemPercentage").setValue((float) 0);
                //SEMESTER-8
                attendanceDB.child("Semester-8").child("GE-2").setValue(new Attendance("GE-2"));
                attendanceDB.child("Semester-8").child("SD-3").setValue(new Attendance("SD-3"));
                attendanceDB.child("Semester-8").child("DHS").setValue(new Attendance("DHS"));
                attendanceDB.child("Semester-8").child("SemAttended").setValue(0);
                attendanceDB.child("Semester-8").child("SemAttendance").setValue(0);
                attendanceDB.child("Semester-8").child("SemPercentage").setValue((float) 0);
            }

            Toast.makeText(getApplicationContext(),"student added successfully", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getApplicationContext(),"fields cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    private String returnShortBranch(String longBranch) {
        String shortBranch="";
        switch(longBranch) {
            case "Information Technology":
                shortBranch="IT";
                break;
            case "Computer Science":
                shortBranch="CS";
                break;
            case "Mechanical":
                shortBranch="ME";
                break;
            case "Civil":
                shortBranch="CE";
                break;
        }
        return shortBranch;
    }

    public void setUname(){
        String username = studentidbatch + studentidbranch;
        Sid.setText(username);
    }

    public void removeStudent(View v){
        if (!TextUtils.isEmpty(Sid.getText().toString())) {
            sid = Sid.getText().toString();
            databaseStudent.child(sid).setValue(null);
            Toast.makeText(getApplicationContext(),"teacher removed successfully", Toast.LENGTH_LONG).show();

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
