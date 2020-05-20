package com.project.minor.e_attendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.minor.e_attendance.object.Teacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TeacherLogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static long back_pressed;
    String item;
    String message;
    Toolbar mToolbar;
    String tdep;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        //to get username from login page
//        Bundle bundle1 = getIntent().getExtras();
//        message = bundle1.getString("message");
        message = "FACIT001";
        mToolbar= findViewById(R.id.takeattendancebar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(message+"'s Dashboard  - "+date);

        TextView txtView = findViewById(R.id.textView1);
        txtView.setText(String.format("Welcome : %s", message));

        FirebaseDatabase.getInstance().getReference("Teacher").child(message).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teacher teacher = dataSnapshot.getValue(Teacher.class);
                if (teacher == null) {
                    Log.e("TA", "null recevied");
                }
                assert teacher != null;
                tdep = teacher.getBranch();
                Log.e("TA", tdep);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Spinner click listener
//        spinner2.setOnItemSelectedListener(this);
//
//        // Spinner Drop down elements
//        List<String> categories = new ArrayList<>();
//        categories.add("CS-A");
//        categories.add("CS-B");
//        categories.add("CS-C");
//        categories.add("CS-D");
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner2.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void takeAttendanceButton(View v){
        Bundle basket= new Bundle();
        basket.putString("tid", message);
        basket.putString("tdep",tdep);


        Intent intent = new Intent(this, TakeAttendance.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void  previous_records(View v){
        Bundle basket= new Bundle();
        basket.putString("tid", message);
        basket.putString("tdep",tdep);

        Intent intent = new Intent(this, TeacherAttendanceSheet.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void logoutTeacher(View view) {
        Intent logoutTeacher=new Intent(TeacherLogin.this,LoginActivity.class);
        logoutTeacher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logoutTeacher);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
