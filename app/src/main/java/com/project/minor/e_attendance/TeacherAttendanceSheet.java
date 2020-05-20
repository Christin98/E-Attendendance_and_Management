package com.project.minor.e_attendance;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TeacherAttendanceSheet extends AppCompatActivity {
    ListView listView;
    String teacher_id, ssem, tdep;


    public static TextView date;
    ArrayList<Object> Userlist = new ArrayList<>();
    ArrayList Studentlist = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;
    String required_date;
    String subj;
    Toolbar mToolbar;
    Spinner batch;
    Spinner sub;
    TextView semtv;


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day  = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @SuppressLint("SetTextI18n")
        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (month < 9) {
                date.setText(day + "-0" + (month + 1) + "-" + year);
            } else {
                date.setText(day + "-" + (month + 1) + "-" + year);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_sheet);
        listView = findViewById(R.id.list);
        date = findViewById(R.id.date);
        batch = findViewById(R.id.batchat);
        sub = findViewById(R.id.subat);
        semtv = findViewById(R.id.semt);
        mToolbar= findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Previous Record");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle1 = getIntent().getExtras();
        teacher_id = bundle1.getString("tid");
        tdep = bundle1.getString("tdep");

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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-2": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("EM-2");
                        sub1.add("BEEE");
                        sub1.add("EM");
                        sub1.add("Chemistry");
                        sub1.add("CS");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-6": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("DSP");
                        sub1.add("BI");
                        sub1.add("PM");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-7": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("DSRM");
                        sub1.add("SP");
                        sub1.add("OOAD");
                        sub1.add("MC");
                        sub1.add("CGM");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-8": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("SOA");
                        sub1.add("ADBMS");
                        sub1.add("STQA");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-2": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("EM-2");
                        sub1.add("BEEE");
                        sub1.add("EM");
                        sub1.add("Chemistry");
                        sub1.add("CS");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-6": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("DSP");
                        sub1.add("BI");
                        sub1.add("PM");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-8": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("CC");
                        sub1.add("ADBMS");
                        sub1.add("STQA");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-2": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("EM-2");
                        sub1.add("BEEE");
                        sub1.add("EM");
                        sub1.add("Chemistry");
                        sub1.add("CS");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-3": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("AM");
                        sub1.add("SOM");
                        sub1.add("MAM");
                        sub1.add("TOM-1");
                        sub1.add("MT");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-4": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("FM");
                        sub1.add("MD-1");
                        sub1.add("MT");
                        sub1.add("MS");
                        sub1.add("TOM-2");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-6": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("NCPP");
                        sub1.add("PVD");
                        sub1.add("PM");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-7": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("OR");
                        sub1.add("TQM");
                        sub1.add("ES");
                        sub1.add("MCD");
                        sub1.add("LA");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-8": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("FEM");
                        sub1.add("RAC");
                        sub1.add("IAR");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
                                android.R.layout.simple_spinner_item, sub1);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        sub.setAdapter(dataAdapter);
                        subj = sub.getSelectedItem().toString();
                        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "Semester-2": {
                        List<String> sub1 = new ArrayList<>();
                        sub1.add("EM-2");
                        sub1.add("BEEE");
                        sub1.add("EM");
                        sub1.add("Chemistry");
                        sub1.add("CS");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TeacherAttendanceSheet.this,
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
        Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
        });

        sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj = sub.getSelectedItem().toString();
                Toast.makeText(TeacherAttendanceSheet.this, subj, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void viewlist(View v) {
        Userlist.clear();
        dbStudent = ref.child("Student");
        dbStudent.orderByChild("branch").equalTo(tdep).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString()); //add result into array list
                }
                display_list(Userlist);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void display_list(final ArrayList<Object> userlist) {

        Studentlist.clear();
        required_date = date.getText().toString();
        dbAttendance = ref.child("Attendance");
        Toast.makeText(getApplicationContext(), userlist.get(0).toString(), Toast.LENGTH_LONG).show();
        Studentlist.add("     Date      " + "      SID              " + "          Status     ");
        for (Object sid : userlist) {
            dbAttendance.child(tdep).child(sid.toString()).child(ssem).child(subj).child("atd").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Toast.makeText(getApplicationContext(), dataSnapshot.getKey(), Toast.LENGTH_LONG).show();

                    for (DataSnapshot dsp: dataSnapshot.getChildren()) {
                        String p1 = dsp.getValue().toString();
                        Toast.makeText(getApplicationContext(), p1, Toast.LENGTH_LONG).show();

                        if ((p1.equals("A")) || (p1.equals("P"))) {
                            if (dsp.getKey().equals(required_date)) {
                                Studentlist.add(dsp.getKey() + "    "+ sid + "                " + p1) ;
                            }
                        }
                    }
                    list(Studentlist);
                }
                  /*String p2=dataSnapshot.child(sid.toString()).child("p2").getValue().toString().substring(0,1);
                    String p3=dataSnapshot.child(sid.toString()).child("p3").getValue().toString().substring(0,1);
                    String p4=dataSnapshot.child(sid.toString()).child("p4").getValue().toString().substring(0,1);
                    String p5=dataSnapshot.child(sid.toString()).child("p5").getValue().toString().substring(0,1);
                    String p6=dataSnapshot.child(sid.toString()).child("p6").getValue().toString().substring(0,1);
                    String p7=dataSnapshot.child(sid.toString()).child("p7").getValue().toString().substring(0,1);
                    String p8=dataSnapshot.child(sid.toString()).child("p8").getValue().toString().substring(0,1);
                   */
                    //  Studentlist.add(dataSnapshot.getKey().toString() + "    " + p1); //add result into array list

                    //Toast.makeText(getApplicationContext(),Studentlist.toString(), Toast.LENGTH_LONG).show();

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }

            });


        }

    }
    public void list(ArrayList studentlist){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentlist);
        // Assign adapter to ListView
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
