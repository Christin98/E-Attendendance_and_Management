package com.project.minor.e_attendance;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.minor.e_attendance.object.Student;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class TakeAttendance extends AppCompatActivity {

    String teacher_id;
    String class_selected;
    Spinner batch;
    Spinner sub;
    String periodno;
    String tdep;
    ArrayList<String> selectedItems;
    ArrayList<String> nonselectedItems;
    Toolbar mToolbar;
    TextView semtv;
    TextView classname;

    String ssem;
    String subj;
    ArrayList<Student> ul;
    ListView listView;
    ArrayList<String> Userlist = new ArrayList<>();
    ArrayList<Object> Usernames = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference teacherdb;
    DatabaseReference atdref;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    private ArrayAdapter adapter;
    int stdatd;
    int tlatd;
    float percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        mToolbar= findViewById(R.id.takeattendancebar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        batch = findViewById(R.id.batchatd);
        sub = findViewById(R.id.subatd);
        semtv = findViewById(R.id.semtv);
        classname = findViewById(R.id.textView);

        ul = new ArrayList<>();

        Bundle bundle1 = getIntent().getExtras();
        teacher_id = bundle1.getString("tid");
        tdep = bundle1.getString("tdep");
        Log.e("TA", teacher_id);
        teacherdb = ref.child("Teacher").child(teacher_id);


        // ArrayList Userlist;
        selectedItems = new ArrayList<>();

        batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ssem = "Semester-1";
                ArrayList<String> list1 = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.batches)));
                if (batch.getSelectedItem().toString().equals(list1.get(0))) {
                    ssem = "Semester-8";
                } else  if (batch.getSelectedItem().toString().equals(list1.get(1))) {
                    ssem = "Semester-6";
                } else  if (batch.getSelectedItem().toString().equals(list1.get(2))) {
                    ssem = "Semester-4";
                } else  if (batch.getSelectedItem().toString().equals(list1.get(3))) {
                    ssem = "Semester-2";
                }
                semtv.setText(ssem);
                Log.e("TA", "onItemSelected: "+ ssem );
                switch (tdep) {
                    case "Information Technology":
                        switch (ssem) {
                            case "Semester-1": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EM-1");
                                sub1.add("BME");
                                sub1.add("ECE");
                                sub1.add("Physics");
                                sub1.add("CP");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-2": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EM-2");
                                sub1.add("BEEE");
                                sub1.add("EM");
                                sub1.add("Chemistry");
                                sub1.add("CS");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-3": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("DS");
                                sub1.add("PP");
                                sub1.add("FDS");
                                sub1.add("CD");
                                sub1.add("CO");
                                sub1.add("DEL");
                                sub1.add("LA");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-4": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("AM");
                                sub1.add("OS");
                                sub1.add("JWT");
                                sub1.add("SE");
                                sub1.add("DS");
                                sub1.add("MT");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-5": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("DBMS");
                                sub1.add("TOC");
                                sub1.add("CN");
                                sub1.add("IT");
                                sub1.add("OR");
                                sub1.add("BAF");
                                sub1.add("QR");
                                sub1.add("ES");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-6": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("DSP");
                                sub1.add("BI");
                                sub1.add("PM");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-7": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("DSRM");
                                sub1.add("SP");
                                sub1.add("OOAD");
                                sub1.add("MC");
                                sub1.add("CGM");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-8": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("SOA");
                                sub1.add("ADBMS");
                                sub1.add("STQA");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        break;
                    case "Computer Science":
                        switch (ssem) {
                            case "Semester-1": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EM-1");
                                sub1.add("BME");
                                sub1.add("ECE");
                                sub1.add("Physics");
                                sub1.add("CP");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-2": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EM-2");
                                sub1.add("BEEE");
                                sub1.add("EM");
                                sub1.add("Chemistry");
                                sub1.add("CS");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-3": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("DS");
                                sub1.add("PP");
                                sub1.add("FDS");
                                sub1.add("CD");
                                sub1.add("CO");
                                sub1.add("DEL");
                                sub1.add("LA");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-4": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("AM");
                                sub1.add("OS");
                                sub1.add("JWT");
                                sub1.add("SE");
                                sub1.add("DS");
                                sub1.add("MT");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-5": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("DBMS");
                                sub1.add("TOC");
                                sub1.add("CN");
                                sub1.add("IT");
                                sub1.add("OR");
                                sub1.add("BAF");
                                sub1.add("QR");
                                sub1.add("ES");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-6": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("DSP");
                                sub1.add("BI");
                                sub1.add("PM");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-7": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("DSRM");
                                sub1.add("SP");
                                sub1.add("OOAD");
                                sub1.add("MC");
                                sub1.add("NS");
                                sub1.add("CGM");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-8": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("CC");
                                sub1.add("ADBMS");
                                sub1.add("STQA");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        break;
                    case "Mechanical":
                        switch (ssem) {
                            case "Semester-1": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EM-1");
                                sub1.add("BME");
                                sub1.add("ECE");
                                sub1.add("Physics");
                                sub1.add("CP");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-2": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EM-2");
                                sub1.add("BEEE");
                                sub1.add("EM");
                                sub1.add("Chemistry");
                                sub1.add("CS");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-3": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("AM");
                                sub1.add("SOM");
                                sub1.add("MAM");
                                sub1.add("TOM-1");
                                sub1.add("MT");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-4": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("FM");
                                sub1.add("MD-1");
                                sub1.add("MT");
                                sub1.add("MS");
                                sub1.add("TOM-2");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-5": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("ICE");
                                sub1.add("PM");
                                sub1.add("MD-2");
                                sub1.add("CDCM");
                                sub1.add("FM");
                                sub1.add("MT");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-6": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("NCPP");
                                sub1.add("PVD");
                                sub1.add("PM");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-7": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("OR");
                                sub1.add("TQM");
                                sub1.add("ES");
                                sub1.add("MCD");
                                sub1.add("LA");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-8": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("FEM");
                                sub1.add("RAC");
                                sub1.add("IAR");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        break;
                    case "Civil":
                        switch (ssem) {
                            case "Semester-1": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EM-1");
                                sub1.add("BME");
                                sub1.add("ECE");
                                sub1.add("Physics");
                                sub1.add("CP");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                subj = sub.getSelectedItem().toString();
                                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case "Semester-2": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EM-2");
                                sub1.add("BEEE");
                                sub1.add("EM");
                                sub1.add("Chemistry");
                                sub1.add("CS");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                break;
                            }
                            case "Semester-3": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("SM");
                                sub1.add("FM-1");
                                sub1.add("CT");
                                sub1.add("BCM");
                                sub1.add("EG");
                                sub1.add("LA");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                break;
                            }
                            case "Semester-4": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("AM");
                                sub1.add("SA-1");
                                sub1.add("BDD");
                                sub1.add("FM-2");
                                sub1.add("SV-2");
                                sub1.add("ES");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                break;
                            }
                            case "Semester-5": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("SA-2");
                                sub1.add("TE-1");
                                sub1.add("QSV");
                                sub1.add("EE-1");
                                sub1.add("SD-1");
                                sub1.add("SV-2");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                break;
                            }
                            case "Semester-6": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("WPE");
                                sub1.add("ES");
                                sub1.add("PM");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                break;
                            }
                            case "Semester-7": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("EE-2");
                                sub1.add("GE-1");
                                sub1.add("CTM");
                                sub1.add("SD-2");
                                sub1.add("TE-2");
                                sub1.add("WRE");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                break;
                            }
                            case "Semester-8": {
                                List<String> sub1 = new ArrayList<>();
                                sub1.add("GE-2");
                                sub1.add("SD-3");
                                sub1.add("DHS");
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TakeAttendance.this,
                                        android.R.layout.simple_spinner_item, sub1);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                dataAdapter.notifyDataSetChanged();
                                sub.setAdapter(dataAdapter);
                                break;
                            }
                        }
                        break;
                }
                subj = sub.getSelectedItem().toString();
                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj = sub.getSelectedItem().toString();
                Toast.makeText(TakeAttendance.this, subj, Toast.LENGTH_SHORT).show();
                classname.setText("CS");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Handler handler=new Handler();
        handler.postDelayed(this::adduser, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void adduser () {
        DatabaseReference dbuser = ref.child("Student");
        dbuser.orderByChild("branch").equalTo(tdep).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString()); //add result into array list
                    Usernames.add(dsp.child("sname").getValue().toString());
                    ul.add(dsp.getValue(Student.class));
                }
                OnStart(Userlist, Usernames, ul);
//                Userlist.clear();
//                Usernames.clear();
//                ul.clear();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void OnStart(ArrayList<String> userlist, ArrayList<Object> usernames, ArrayList<Student> ul1) {
        nonselectedItems = userlist;
//        Toast.makeText(TakeAttendance.this, ul1.size(), Toast.LENGTH_SHORT).show();

        //create an instance of ListView
        ListView chl = findViewById(R.id.checkable_list);
        //set multiple selection mode
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //supply data itmes to ListView
        ArrayAdapter<String> attendanceAdapter = new ArrayAdapter<>(TakeAttendance.this, R.layout.checkable_list_layout, R.id.txt_title, userlist);
        chl.setAdapter(attendanceAdapter);
        attendanceAdapter.notifyDataSetChanged();
        //set OnItemClickListener
        chl.setOnItemClickListener((parent, view, position, id) -> {
            // selected item
            String selectedItem = ((TextView) view).getText().toString();
            if (selectedItems.contains(selectedItem))
                selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
            else
                selectedItems.add(selectedItem); //add selected item to the list of selected items
        });



    }

    public void showSelectedItems(View view) {
        StringBuilder selItems = new StringBuilder();


        ref = FirebaseDatabase.getInstance().getReference();

            for (String item : selectedItems) {
                dbAttendance = ref.child("Attendance").child(tdep).child(item).child(ssem).child(subj).child("atd");
                nonselectedItems.remove(item);
                dbAttendance.child(date).setValue("P");
                atdref = ref.child("Attendance").child(tdep).child(item).child(ssem).child(subj);
                atdref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            stdatd = dataSnapshot.child("studentAttendance").getValue(Integer.class);
                            tlatd = dataSnapshot.child("totalAttendanceTaken").getValue(Integer.class);
                            percentage = (float) (((stdatd+1)/(tlatd+1))*100);
                            atdref.child("percentage").setValue(percentage);
                            atdref.child("studentAttendance").setValue(stdatd+1);
                            atdref.child("totalAttendanceTaken").setValue(tlatd+1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                if (selItems.toString().equals(""))
                    selItems = new StringBuilder(item);
                else
                    selItems.append("/").append(item);
            }
            // Toast.makeText(this, selItems, Toast.LENGTH_LONG).show();


            //for making absent
            for (String item : nonselectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                dbAttendance = ref.child("Attendance").child(tdep).child(item).child(ssem).child(subj).child("atd");
                dbAttendance.child(date).setValue("A");
//                Toast.makeText(this, "absentees:" + nonselectedItems, Toast.LENGTH_LONG).show();
                atdref = ref.child("Attendance").child(tdep).child(item).child(ssem).child(subj);
                atdref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        stdatd = dataSnapshot.child("studentAttendance").getValue(Integer.class);
                        tlatd = dataSnapshot.child("totalAttendanceTaken").getValue(Integer.class);
                        percentage = (float) ((stdatd/(tlatd+1))*100);
                        atdref.child("percentage").setValue(percentage);
                        atdref.child("studentAttendance").setValue(stdatd);
                        atdref.child("totalAttendanceTaken").setValue(tlatd+1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }


    public void addtoreport(View v) throws IOException, BiffException {


        Workbook workbook=null;
        WritableWorkbook wb=null;
        WritableSheet s=null;
        try {
            workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eattendance/" + class_selected+"_month_"+date.substring(3,5)+ ".xls"));
            wb = createWorkbook(class_selected+"_month_"+date.substring(3,5),workbook);
            s = wb.getSheet(0);

        }
        catch (Exception e){
            //Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            File wbfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eattendance/" + class_selected + ".xls");
            wb = createWorkbook(class_selected+"_month_"+date.substring(3,5));
            // workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eattendance/" + class_selected + ".xls"));
            s = createSheet(wb, "month_", 0);//to create month's sheet
        }








        int i = s.getColumns();
        if(i==0){
            try {
                //for header
                Label newCell=new Label(0,0,"Student_id");
                Label newCell2=new Label(1,0,"Student_name");
                WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
                WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
                //center align the cells' contents
                headerFormat.setAlignment(Alignment.CENTRE);
                newCell.setCellFormat(headerFormat);
                newCell2.setCellFormat(headerFormat);
                s.addCell(newCell);
                s.addCell(newCell2);
            } catch (WriteException e) {
                e.printStackTrace();
            }
            for (Object item : Userlist) {
                int j = s.getRows();
                String name=Usernames.get(j-1).toString();

                Label label = new Label(0, j, item.toString());
                Label label2 = new Label(1, j, name);

                try {
                    s.addCell(label);
                    s.addCell(label2);


                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }



        }
        i=s.getColumns();
        // Toast.makeText(this, i  , Toast.LENGTH_LONG).show();
        int j=1;
        try {
            Label newCell=new Label(i,0, date);
            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
            //center align the cells' contents
            headerFormat.setAlignment(Alignment.CENTRE);
            newCell.setCellFormat(headerFormat);
            s.addCell(newCell);

        } catch (WriteException e) {
            e.printStackTrace();
        }
        for (Object item : Userlist) {

            Label label2;
            // Label label2;

            if (selectedItems.contains(item)) {
                label2=new Label(i,j,"P");
                //Toast.makeText(this, item.toString() + "  present :", Toast.LENGTH_LONG).show();
            } else {
                label2=new Label(i,j,"A");
                //Toast.makeText(this, item.toString() + "  absent :", Toast.LENGTH_LONG).show();
            }
            j++;
            try {
                s.addCell(label2);
            } catch (Exception e) {
                Toast.makeText(this, "Unable to create sheet", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }
        //for making consolidate report
        Date today = new Date();

        String tomorrow =new SimpleDateFormat("dd-MM-yyyy").format(new Date(today.getTime() + (1000 * 60 * 60 * 24)));// new Date(today.getTime() + (1000 * 60 * 60 * 24));
        if(tomorrow.substring(0,2).equals("01")){

            int row =s.getRows();
            int col=s.getColumns();
            String xx="";
            int nop,tc;//to remove two xtra columns

            for(i = 0; i<row; i++)
            {
                nop=0;
                tc=-2;
                for (int c=0;c<col;c++)
                {
                    Cell z=s.getCell(c,i);

                    xx=z.getContents();
                    if(xx.equals("P"))
                        nop++;
                    if(!xx.isEmpty()||!xx.equals("")) {
                        tc++;
                    }
                }
                xx=xx+"\n";
                Label label = new Label(col, i,""+nop);

                Label label2 = new Label(col+1,i,nop*100/tc+"%");
                try {
                    if(i==0) {
                        label = new Label(col, i, "Total=" + tc);
                        label2 = new Label(col+1, i, "percentage");

                    }
                    s.addCell(label);
                    s.addCell(label2);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            wb.write();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"sheet  created successfully",Toast.LENGTH_LONG).show();
    }

    public WritableWorkbook createWorkbook(String fileName, Workbook workbook){
        //exports must use a temp file while writing to avoid memory hogging
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setUseTemporaryFileDuringWrite(true);
        //get the sdcard's directory
        File sdCard = Environment.getExternalStorageDirectory();
        //add on the your app's path
        File dir = new File(sdCard.getAbsolutePath() + "/eattendance");
        //make them in case they're not there
        dir.mkdirs();
        //create a standard java.io.File object for the Workbook to use
        File wbfile = new File(dir,fileName+".xls");
        WritableWorkbook wb = null;
        try{
            //create a new WritableWorkbook using the java.io.File and
            //WorkbookSettings from above
            wb = Workbook.createWorkbook(wbfile,workbook/*wbSettings*/);
        }/*catch(IOException ex){
          //  Log.e(TAG,ex.getStackTrace().toString());
          //  Log.e(TAG, ex.getMessage());
        }*/ catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public WritableWorkbook createWorkbook(String fileName)  {

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setUseTemporaryFileDuringWrite(true);
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/eattendance");
        dir.mkdirs();
        File wbfile = new File(dir,fileName+".xls");
        WritableWorkbook wb = null;
        try{
            wb = Workbook.createWorkbook(wbfile,wbSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public WritableSheet createSheet(WritableWorkbook wb, String sheetName, int sheetIndex){
        //create a new WritableSheet and return it

        return wb.createSheet(sheetName, sheetIndex);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
